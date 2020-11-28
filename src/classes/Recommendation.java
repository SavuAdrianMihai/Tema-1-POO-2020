package classes;

import entertainment.Genre;
import fileio.*;

import java.util.*;

public class Recommendation implements AllUsersRecommendation, PremiumUsersRecommendation {

    @Override
    public String standardRecommendation(Input input, UserInputData user) {
        for (int i = 0; i < input.getMovies().size(); i++) {
            MovieInputData movie = input.getMovies().get(i);
            if (!user.getHistory().containsKey(movie.getTitle()))
                return movie.getTitle();
        }

        for (int i = 0; i < input.getSerials().size(); i++) {
            SerialInputData serial = input.getSerials().get(i);
            if (!user.getHistory().containsKey(serial.getTitle()))
                return serial.getTitle();
        }
        return "";
    }

    @Override
    public String bestUnseenRecommendation(Input input, UserInputData user) {
        double ratingMovie = 0, ratingSerial = 0;
        int indexMovie = 0, indexSerial = 0, indexFirstMovie = 0, indexFirstSerial = 0;
        int movieFound = 0, serialFound = 0;
        for (int i = 0; i < input.getMovies().size(); i++) {
            MovieInputData movie = input.getMovies().get(i);
            if (!user.getHistory().containsKey(movie.getTitle())) {
                if (movieFound == 0)
                    indexFirstMovie = i;
                if (movie.calculateRating() > ratingMovie) {
                    ratingMovie = movie.calculateRating();
                    indexMovie = i;
                }
                movieFound = 1;
            }
        }
        for (int i = 0; i < input.getSerials().size(); i++){
            SerialInputData serial = input.getSerials().get(i);
            if (!user.getHistory().containsKey(serial.getTitle())) {
                if (serialFound == 0)
                    indexFirstSerial = i;
                if (serial.averageSerialRating() > ratingSerial) {
                    ratingSerial = serial.averageSerialRating();
                    indexSerial = i;
                }
                serialFound = 1;
            }
        }

        if(movieFound == 0 && serialFound == 0) {
            ArrayList<String> errorMessage = new ArrayList<>();
            errorMessage.add("fail");
            return errorMessage.get(0);
        }

        if ((ratingMovie == 0) && (movieFound != 0))
            return input.getMovies().get(indexFirstMovie).getTitle();

        if ((movieFound == 0) && (serialFound != 0) && (ratingSerial == 0))
            return input.getSerials().get(indexFirstSerial).getTitle();

        if (ratingMovie >= ratingSerial)
            return input.getMovies().get(indexMovie).getTitle();
            else return input.getSerials().get(indexSerial ).getTitle();
    }

    @Override
    public String popular(UserInputData user, Input input) {
        if(user.getSubscriptionType().equals("PREMIUM")) {
            Map<String, Integer> genres = new HashMap<>();

            ArrayList<MovieInputData> viewedMovies = new ArrayList<>();
            ArrayList<SerialInputData> viewedSerials = new ArrayList<>();
            ArrayList<String> mostViewedGenres = new ArrayList<>();

            // get the most popular genre from movies
            for (int i = 0; i < input.getMovies().size(); i++)
                if (input.getMovies().get(i).getViews() != 0)
                    viewedMovies.add(input.getMovies().get(i));

            for (int i = 0; i < viewedMovies.size(); i++)
                for (int j = 0; j < viewedMovies.get(i).getGenres().size(); j++) {
                    if (genres.size() != 0  && genres.get(viewedMovies.get(i).getGenres().get(j)) != null)
                    genres.put(viewedMovies.get(i).getGenres().get(j),
                            genres.get(viewedMovies.get(i).getGenres().get(j)) +
                                    viewedMovies.get(i).getViews());
                    else{
                        genres.put(viewedMovies.get(i).getGenres().get(j),
                                viewedMovies.get(i).getViews());
                    }
                }

            // get the most popular genre from serials and add to movie genres
            for (int i = 0; i < input.getSerials().size(); i++)
                if (input.getSerials().get(i).getViews() != 0)
                    viewedSerials.add(input.getSerials().get(i));

            for (int i = 0; i < viewedSerials.size(); i++)
                for (int j = 0; j < viewedSerials.get(i).getGenres().size(); j++) {
                    if (genres.size() != 0 && genres.get(viewedSerials.get(i).getGenres().get(j)) != null)
                    genres.put(viewedSerials.get(i).getGenres().get(j),
                            genres.get(viewedSerials.get(i).getGenres().get(j)) +
                                    viewedSerials.get(i).getViews());
                    else {
                        genres.put(viewedSerials.get(i).getGenres().get(j),
                                        viewedSerials.get(i).getViews());
                    }
                }


            // TO DO SORTARE   //


           // ArrayList<Integer> genresViews = new ArrayList<>(genres.values());

           // Collections.sort(genresViews, Collections.reverseOrder());
/*
            ArrayList<String> sortedGenres =
                    new ArrayList<String>(genres.keySet());

            Collections.sort(sortedGenres, Collections.reverseOrder());

            for(int i = 0; i < genres.size() - 1; i++)
                for (int j = 0; j < genres.size() - i - 1; j++)
                    if genres.
*/

            for (int j = 0; j < genres.size(); j++)
                for (int i = 0; i < viewedMovies.size(); i++) {
                        if (j < viewedMovies.get(i).getGenres().size())
                        if (!(user.getHistory().containsKey(viewedMovies.get(i).getTitle())) &&
                                genres.containsKey(viewedMovies.get(i).getGenres().get(j)))
                            return viewedMovies.get(i).getTitle();
                    }

            for (int j = 0; j < genres.size(); j++)
                for (int i = 0; i < viewedSerials.size(); i++) {
                    if (j < viewedSerials.get(i).getGenres().size())
                    if (!(user.getHistory().containsKey(viewedSerials.get(i).getTitle())) &&
                            genres.containsKey(viewedSerials.get(i).getGenres().get(j)))
                        return viewedSerials.get(i).getTitle();
                }

        }
        return "";
    }

    @Override
    public String favorite(UserInputData user, Input input) {
        if(user.getSubscriptionType().equals("PREMIUM")){

            ArrayList<MovieInputData> favoriteMovies = new ArrayList<>();
            ArrayList<SerialInputData> favoriteSerials = new ArrayList<>();

            // update from database //

            for (int i = 0; i < input.getMovies().size(); i++) {
                MovieInputData movie = input.getMovies().get(i);
                if (!user.getHistory().containsKey(movie.getTitle()))
                    favoriteMovies.add(movie);
            }

            for (int i = 0; i < input.getSerials().size(); i++) {
                SerialInputData serial = input.getSerials().get(i);
                if (!user.getHistory().containsKey(serial.getTitle()))
                    favoriteSerials.add(serial);
            }

            if(favoriteMovies.size() != 0)
                for(int i = 0; i < favoriteMovies.size(); i++)
                    for(int j = 0; j < input.getUsers().size(); j++)
                        if( input.getUsers().get(j).getFavoriteMovies().contains(favoriteMovies.get(i).getTitle()))
                            favoriteMovies.get(i).setNumberOfFavorites(favoriteMovies.get(i).getNumberOfFavorites() + 1);

            if(favoriteSerials.size() != 0)
                for(int i = 0; i < favoriteSerials.size(); i++)
                    for(int j = 0; j < input.getUsers().size(); j++)
                        if (input.getUsers().get(j).getFavoriteMovies().contains(favoriteSerials.get(i).getTitle()))
                            favoriteSerials.get(i).setNumberOfFavorites(favoriteSerials.get(i).getNumberOfFavorites() + 1);
/*
            if(favoriteMovies.size() != 0)
                for (int i = 0; i < favoriteMovies.size(); i++)
                    if (favoriteMovies.get(i).getNumberOfFavorites() == 0)
                        favoriteMovies.remove(favoriteMovies.get(i));

            if(favoriteSerials.size() != 0)
                for (int i = 0; i < favoriteSerials.size(); i++)
                    if (favoriteSerials.get(i).getNumberOfFavorites() == 0)
                        favoriteSerials.remove(favoriteSerials.get(i));
*/
            MovieInputData aux1;
            if(favoriteMovies.size() != 0) {
                //favoriteMovies.sort(Comparator.comparing(ShowInput::getTitle));
                for (int i = 0; i < favoriteMovies.size() - 1; i++)
                    for (int j = 0; j < (favoriteMovies.size() - i - 1); j++) {
                        if (favoriteMovies.get(j).getNumberOfFavorites() < favoriteMovies.get(j + 1).getNumberOfFavorites()) {
                            aux1 = favoriteMovies.get(j);
                            favoriteMovies.set(j, favoriteMovies.get(j + 1));
                            favoriteMovies.set((j + 1), aux1);
                        }
                    }
            }

            SerialInputData aux2;
            if(favoriteSerials.size() != 0) {
                //favoriteSerials.sort(Comparator.comparing(ShowInput::getTitle));
                for (int i = 0; i < favoriteSerials.size() - 1; i++)
                    for (int j = 0; j < (favoriteSerials.size() - i - 1); j++) {
                        if (favoriteSerials.get(j).getNumberOfFavorites() < favoriteSerials.get(j + 1).getNumberOfFavorites()) {
                            aux2 = favoriteSerials.get(j);
                            favoriteSerials.set(j, favoriteSerials.get(j + 1));
                            favoriteSerials.set((j + 1), aux2);
                        }
                    }
            }

            if(favoriteMovies.size() == 0 && favoriteSerials.size() == 0)
                return "";

            if(favoriteSerials.size() == 0)
                return favoriteMovies.get(0).getTitle();

            if(favoriteMovies.size() == 0)
                return favoriteSerials.get(0).getTitle();

            if(favoriteMovies.get(0).getNumberOfFavorites() > favoriteSerials.get(0).getNumberOfFavorites())
                return favoriteMovies.get(0).getTitle();
            return favoriteSerials.get(0).getTitle();

        }
        return "";
    }

    @Override
    public ArrayList<String> search(UserInputData user, Input input, String genre) {
        if(user.getSubscriptionType().equals("PREMIUM")) {
            ArrayList<MovieInputData> eligibleMovies = new ArrayList<>();
            ArrayList<SerialInputData> eligibleSerials = new ArrayList<>();

                for (int i = 0; i < input.getMovies().size(); i++) {
                    MovieInputData movie = input.getMovies().get(i);
                    if (!user.getHistory().containsKey(movie.getTitle())
                            && movie.getGenres().contains(genre))
                        eligibleMovies.add(movie);
                }

                for (int i = 0; i < input.getSerials().size(); i++) {
                    SerialInputData serial = input.getSerials().get(i);
                    if (!user.getHistory().containsKey(serial.getTitle())
                            && serial.getGenres().contains(genre))
                        eligibleSerials.add(serial);
                }

                // TO DO SORT //

            MovieInputData aux1;
            if(eligibleMovies.size() != 0) {
                eligibleMovies.sort(Comparator.comparing(ShowInput::getTitle));
                for (int i = 0; i < eligibleMovies.size() - 1; i++)
                    for (int j = 0; j < (eligibleMovies.size() - i - 1); j++) {
                        if (eligibleMovies.get(j).getNumberOfFavorites() < eligibleMovies.get(j + 1).getNumberOfFavorites()) {
                            aux1 = eligibleMovies.get(j);
                            eligibleMovies.set(j, eligibleMovies.get(j + 1));
                            eligibleMovies.set((j + 1), aux1);
                        }
                    }
            }

            SerialInputData aux2;
            if(eligibleSerials.size() != 0) {
                eligibleSerials.sort(Comparator.comparing(ShowInput::getTitle));
                for (int i = 0; i < eligibleSerials.size() - 1; i++)
                    for (int j = 0; j < (eligibleSerials.size() - i - 1); j++) {
                        if (eligibleSerials.get(j).getNumberOfFavorites() < eligibleSerials.get(j + 1).getNumberOfFavorites()) {
                            aux2 = eligibleSerials.get(j);
                            eligibleSerials.set(j, eligibleSerials.get(j + 1));
                            eligibleSerials.set((j + 1), aux2);
                        }
                    }
            }

            ArrayList<String> videos = new ArrayList<>();
            int j = 0, k = 0;
            for(int i = 0; i < eligibleMovies.size() + eligibleSerials.size(); i++) {
                if (eligibleMovies.size() != 0 && eligibleSerials.size() != 0) {
                    if (eligibleMovies.get(j).calculateRating() > eligibleSerials.get(k).averageSerialRating()) {
                        videos.add(eligibleMovies.get(j).getTitle());
                        j++;
                    } else if (eligibleMovies.get(j).calculateRating() < eligibleSerials.get(k).averageSerialRating()) {
                        videos.add(eligibleSerials.get(j).getTitle());
                        k++;
                    }
                }
               // else if (eligibleMovies.get(j).calculateRating() == eligibleSerials.get(k).averageSerialRating())
               //     if (eligibleMovies.get(j).getTitle() > )
            }

            if (eligibleMovies.size() != 0 && eligibleSerials.size() == 0)
                for(int i = 0; i < eligibleMovies.size(); i++)
                    videos.add(eligibleMovies.get(i).getTitle());
            if (eligibleMovies.size() == 0 && eligibleSerials.size() != 0)
                for(int i = 0; i < eligibleSerials.size(); i++)
                    videos.add(eligibleSerials.get(i).getTitle());

            if (videos.size() != 0)
                return videos;

        }
        ArrayList<String> errorMessage = new ArrayList<>();
        errorMessage.add("fail");
        return errorMessage;
    }
}
