package recommendations;

import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Recommendation implements AllUsersRecommendation, PremiumUsersRecommendation {

    @Override
    public String standardRecommendation(final Input input, final UserInputData user) {
        for (int i = 0; i < input.getMovies().size(); i++) {
            MovieInputData movie = input.getMovies().get(i);
            if (!user.getHistory().containsKey(movie.getTitle())) {
                return movie.getTitle();
            }
        }

        for (int i = 0; i < input.getSerials().size(); i++) {
            SerialInputData serial = input.getSerials().get(i);
            if (!user.getHistory().containsKey(serial.getTitle())) {
                return serial.getTitle();
            }
        }
        return "";
    }

    @Override
    public String bestUnseenRecommendation(final Input input, final UserInputData user) {
        double ratingMovie = 0, ratingSerial = 0;
        int indexMovie = 0, indexSerial = 0, indexFirstMovie = 0, indexFirstSerial = 0,
                movieFound = 0, serialFound = 0;
        for (int i = 0; i < input.getMovies().size(); i++) {
            MovieInputData movie = input.getMovies().get(i);
            if (!user.getHistory().containsKey(movie.getTitle())) {
                if (movieFound == 0) {
                    indexFirstMovie = i;
                }
                if (movie.calculateRating() > ratingMovie) {
                    ratingMovie = movie.calculateRating();
                    indexMovie = i;
                }
                movieFound = 1;
            }
        }
        for (int i = 0; i < input.getSerials().size(); i++) {
            SerialInputData serial = input.getSerials().get(i);
            if (!user.getHistory().containsKey(serial.getTitle())) {
                if (serialFound == 0) {
                    indexFirstSerial = i;
                }
                if (serial.averageSerialRating() > ratingSerial) {
                    ratingSerial = serial.averageSerialRating();
                    indexSerial = i;
                }
                serialFound = 1;
            }
        }

        if (movieFound == 0 && serialFound == 0) {
            ArrayList<String> errorMessage = new ArrayList<>();
            errorMessage.add("fail");
            return errorMessage.get(0);
        }

        if ((ratingMovie == 0) && (movieFound != 0)) {
            return input.getMovies().get(indexFirstMovie).getTitle();
        }

        if ((movieFound == 0) && (ratingSerial == 0)) {
            return input.getSerials().get(indexFirstSerial).getTitle();
        }

        if (ratingMovie >= ratingSerial) {
            return input.getMovies().get(indexMovie).getTitle();
        } else {
            return input.getSerials().get(indexSerial).getTitle();
        }
    }

    @Override
    public String popular(final UserInputData user, final Input input) {
        if (user.getSubscriptionType().equals("PREMIUM")) {
            Map<String, Integer> genres = new HashMap<>();

            ArrayList<MovieInputData> viewedMovies = new ArrayList<>();
            ArrayList<SerialInputData> viewedSerials = new ArrayList<>();

            for (int i = 0; i < input.getMovies().size(); i++) {
                if (input.getMovies().get(i).getViews() != 0) {
                    viewedMovies.add(input.getMovies().get(i));
                }
            }

            for (MovieInputData viewedMovie : viewedMovies) {
                for (int j = 0; j < viewedMovie.getGenres().size(); j++) {
                    if (genres.size() != 0 && genres.get(viewedMovie.
                            getGenres().get(j)) != null) {
                        genres.put(viewedMovie.getGenres().get(j),
                                genres.get(viewedMovie.getGenres().get(j))
                                        + viewedMovie.getViews());
                    } else {
                        genres.put(viewedMovie.getGenres().get(j),
                                viewedMovie.getViews());
                    }
                }
            }

            for (int i = 0; i < input.getSerials().size(); i++) {
                if (input.getSerials().get(i).getViews() != 0) {
                    viewedSerials.add(input.getSerials().get(i));
                }
            }

            for (SerialInputData serial : viewedSerials) {
                for (int j = 0; j < serial.getGenres().size(); j++) {
                    if (genres.size() != 0 && genres.get(serial.getGenres().
                            get(j)) != null) {
                        genres.put(serial.getGenres().get(j),
                                genres.get(serial.getGenres().get(j))
                                        + serial.getViews());
                    } else {
                        genres.put(serial.getGenres().get(j),
                                serial.getViews());
                    }
                }
            }

            for (int j = 0; j < genres.size(); j++) {
                for (MovieInputData viewedMovie : viewedMovies) {
                    if (j < viewedMovie.getGenres().size()) {
                        if (!(user.getHistory().containsKey(viewedMovie.
                                getTitle())) && genres.
                                containsKey(viewedMovie.getGenres().get(j))) {
                            return viewedMovie.getTitle();
                        }
                    }
                }
            }

            for (int j = 0; j < genres.size(); j++) {
                for (SerialInputData viewedSerial : viewedSerials) {
                    if (j < viewedSerial.getGenres().size()) {
                        if (!(user.getHistory().containsKey(viewedSerial.getTitle()))
                                && genres.containsKey(viewedSerial.getGenres().get(j))) {
                            return viewedSerial.getTitle();
                        }
                    }
                }
            }

        }
        return "";
    }

    @Override
    public String favorite(final UserInputData user, final Input input) {
        if (user.getSubscriptionType().equals("PREMIUM")) {

            ArrayList<MovieInputData> favoriteMovies = new ArrayList<>();
            ArrayList<SerialInputData> favoriteSerials = new ArrayList<>();

            // update from database //

            for (int i = 0; i < input.getMovies().size(); i++) {
                MovieInputData movie = input.getMovies().get(i);
                if (!user.getHistory().containsKey(movie.getTitle())) {
                    favoriteMovies.add(movie);
                }
            }

            for (int i = 0; i < input.getSerials().size(); i++) {
                SerialInputData serial = input.getSerials().get(i);
                if (!user.getHistory().containsKey(serial.getTitle())) {
                    favoriteSerials.add(serial);
                }
            }

            if (favoriteMovies.size() != 0) {
                for (MovieInputData favoriteMovie : favoriteMovies) {
                    for (int j = 0; j < input.getUsers().size(); j++) {
                        if (input.getUsers().get(j).getFavoriteMovies().
                                contains(favoriteMovie.getTitle())) {
                            favoriteMovie.
                                    setNumberOfFavorites(favoriteMovie.
                                            getNumberOfFavorites() + 1);
                        }
                    }
                }
            }

            if (favoriteSerials.size() != 0) {
                for (SerialInputData favoriteSerial : favoriteSerials) {
                    for (int j = 0; j < input.getUsers().size(); j++) {
                        if (input.getUsers().get(j).getFavoriteMovies().
                                contains(favoriteSerial.getTitle())) {
                            favoriteSerial.setNumberOfFavorites(favoriteSerial.
                                            getNumberOfFavorites() + 1);
                        }
                    }
                }
            }

            if (favoriteMovies.size() != 0) {
                favoriteMovies.sort(Comparator.comparing(MovieInputData::getTitle));
                favoriteMovies.sort(Comparator.comparing(MovieInputData::getNumberOfFavorites).
                        reversed());
            }

            if (favoriteSerials.size() != 0) {
                favoriteSerials.sort(Comparator.comparing(SerialInputData::getTitle));
                favoriteSerials.sort(Comparator.comparing(SerialInputData::getNumberOfFavorites).
                        reversed());
            }

            if (favoriteMovies.size() == 0 && favoriteSerials.size() == 0) {
                return "";
            }

            if (favoriteSerials.size() == 0) {
                return favoriteMovies.get(0).getTitle();
            }

            if (favoriteMovies.size() == 0) {
                return favoriteSerials.get(0).getTitle();
            }

            if (favoriteMovies.get(0).getNumberOfFavorites()
                    > favoriteSerials.get(0).getNumberOfFavorites()) {
                return favoriteMovies.get(0).getTitle();
            }
            return favoriteSerials.get(0).getTitle();
        }
        return "";
    }

    @Override
    public ArrayList<String> search(final UserInputData user, final Input input,
                                    final String genre) {
        if (user.getSubscriptionType().equals("PREMIUM")) {
            ArrayList<MovieInputData> eligibleMovies = new ArrayList<>();
            ArrayList<SerialInputData> eligibleSerials = new ArrayList<>();

            for (int i = 0; i < input.getMovies().size(); i++) {
                MovieInputData movie = input.getMovies().get(i);
                if (!user.getHistory().containsKey(movie.getTitle())
                        && movie.getGenres().contains(genre)) {
                    eligibleMovies.add(movie);
                }
            }

            for (int i = 0; i < input.getSerials().size(); i++) {
                SerialInputData serial = input.getSerials().get(i);
                if (!user.getHistory().containsKey(serial.getTitle())
                        && serial.getGenres().contains(genre)) {
                    eligibleSerials.add(serial);
                }
            }

            if (eligibleMovies.size() != 0) {
                eligibleMovies.sort(Comparator.comparing(MovieInputData::getTitle));
                eligibleMovies.sort(Comparator.comparing(MovieInputData::getNumberOfFavorites).
                        reversed());
            }

            if (eligibleSerials.size() != 0) {
                eligibleSerials.sort(Comparator.comparing(SerialInputData::getTitle));
                eligibleSerials.sort(Comparator.comparing(SerialInputData::getNumberOfFavorites).
                        reversed());
            }

            ArrayList<String> videos = new ArrayList<>();
            int j = 0, k = 0;
            for (int i = 0; i
                    < eligibleMovies.size() + eligibleSerials.size(); i++) {
                if (eligibleMovies.size() != 0 && eligibleSerials.size() != 0) {
                    if (eligibleMovies.get(j).calculateRating()
                            > eligibleSerials.get(k).averageSerialRating()) {
                        videos.add(eligibleMovies.get(j).getTitle());
                        j++;
                    } else if (eligibleMovies.get(j).calculateRating()
                            < eligibleSerials.get(k).averageSerialRating()) {
                        videos.add(eligibleSerials.get(j).getTitle());
                        k++;
                    }
                }
            }

            if (eligibleMovies.size() != 0 && eligibleSerials.size() == 0) {
                for (MovieInputData eligibleMovie : eligibleMovies) {
                    videos.add(eligibleMovie.getTitle());
                }
            }
            if (eligibleMovies.size() == 0 && eligibleSerials.size() != 0) {
                for (SerialInputData eligibleSerial : eligibleSerials) {
                    videos.add(eligibleSerial.getTitle());
                }
            }

            if (videos.size() != 0) {
                return videos;
            }
        }
        ArrayList<String> errorMessage = new ArrayList<>();
        errorMessage.add("fail");
        return errorMessage;
    }
}
