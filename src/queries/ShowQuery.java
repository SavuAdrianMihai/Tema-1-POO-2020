package queries;

import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ShowQuery implements MovieQuery, SerialQuery {
    @Override
    public ArrayList<String> longestMovie(final Input input, final String sortType,
                                          final List<List<String>> filters,
                                          final int numberOfMovies) {
        ArrayList<MovieInputData> movies = new ArrayList<>();
        String year = null;
        String genre = null;
        if (filters.get(0).get(0) != null) {
            year = filters.get(0).get(0);
        }
        if (filters.get(1).get(0) != null) {
            genre = filters.get(1).get(0);
        }
        if (input.getMovies().size() != 0) {
            for (int i = 0; i < input.getMovies().size(); i++) {
                MovieInputData movie = input.getMovies().get(i);
                if (year != null && genre != null) {
                    if (movie.getYear() == Integer.parseInt(year)
                            && movie.getGenres().contains(genre)) {
                        movies.add(movie);
                    }
                }
                if (year != null && genre == null) {
                    if (movie.getYear() == Integer.parseInt(year)) {
                        movies.add(movie);
                    }
                }
                if (year == null && genre != null) {
                    if (movie.getGenres().contains(genre)) {
                        movies.add(movie);
                    }
                }

                if (year == null && genre == null) {
                    movies.add(movie);
                }
            }

            if (sortType.equals("asc")) {
                movies.sort(Comparator.comparing(MovieInputData::getTitle));
                movies.sort(Comparator.comparing(MovieInputData::getDuration));
            }

            if (sortType.equals("desc")) {
                movies.sort(Comparator.comparing(MovieInputData::getTitle).reversed());
                movies.sort(Comparator.comparing(MovieInputData::getDuration).reversed());
            }
            ArrayList<String> movieTitles = new ArrayList<>();
            int numberOfMoviesToBeReturned = Math.min(numberOfMovies, movies.size());
            for (int i = 0; i < numberOfMoviesToBeReturned; i++) {
                movieTitles.add(movies.get(i).getTitle());
            }
            return movieTitles;
        }
        return null;
    }

    @Override
    public ArrayList<String> longestSerial(final Input input, final String sortType,
                                           final List<List<String>> filters,
                                           final int numberOfSerials) {
        ArrayList<SerialInputData> serials = new ArrayList<>();
        String year = null;
        String genre = null;
        if (filters.get(0).get(0) != null) {
            year = filters.get(0).get(0);
        }
        if (filters.get(1).get(0) != null) {
            genre = filters.get(1).get(0);
        }
        if (input.getSerials().size() != 0) {
            for (int i = 0; i < input.getSerials().size(); i++) {
                SerialInputData serial = input.getSerials().get(i);
                if (year != null && genre != null) {
                    if (serial.getYear() == Integer.parseInt(year)
                            && serial.getGenres().contains(genre)) {
                        serials.add(serial);
                    }
                }
                if (year != null && genre == null) {
                    if (serial.getYear() == Integer.parseInt(year)) {
                        serials.add(serial);
                    }
                }
                if (year == null && genre != null) {
                    if (serial.getGenres().contains(genre)) {
                        serials.add(serial);
                    }
                }

                if (year == null && genre == null) {
                    serials.add(serial);
                }
            }
            if (sortType.equals("asc")) {
                serials.sort(Comparator.comparing(SerialInputData::getTitle));
                serials.sort(Comparator.comparing(SerialInputData::totalDuration));
            }

            if (sortType.equals("desc")) {
                serials.sort(Comparator.comparing(SerialInputData::getTitle).reversed());
                serials.sort(Comparator.comparing(SerialInputData::totalDuration).
                        reversed());
            }

            ArrayList<String> serialTitles = new ArrayList<>();
            int numberOfSerialsToBeReturned = Math.min(numberOfSerials, serials.size());
            for (int i = 0; i < numberOfSerialsToBeReturned; i++) {
                serialTitles.add(serials.get(i).getTitle());
            }
            return serialTitles;
        }
        return null;
    }

    @Override
    public ArrayList<String> mostViewedMovie(final Input input, final String sortType,
                                             final List<List<String>> filters,
                                             final int numberOfMovies) {
        ArrayList<MovieInputData> movies = new ArrayList<>();
        String year = null;
        String genre = null;
        if (filters.get(0).get(0) != null) {
            year = filters.get(0).get(0);
        }
        if (filters.get(1).get(0) != null) {
            genre = filters.get(1).get(0);
        }
        if (input.getMovies().size() != 0) {
            for (int i = 0; i < input.getMovies().size(); i++) {
                MovieInputData movie = input.getMovies().get(i);
                if (year != null && genre != null) {
                    if (movie.getYear() == Integer.parseInt(year)
                            && movie.getGenres().contains(genre)) {
                        movies.add(movie);
                    }
                }
                if (year != null && genre == null) {
                    if (movie.getYear() == Integer.parseInt(year)) {
                        movies.add(movie);
                    }
                }
                if (year == null && genre != null) {
                    if (movie.getGenres().contains(genre)) {
                        movies.add(movie);
                    }
                }

                if (year == null && genre == null) {
                    movies.add(movie);
                }
            }
            for (MovieInputData movie : movies) {
                for (int j = 0; j < input.getUsers().size(); j++) {
                    if (input.getUsers().get(j).getHistory().containsKey(movie.getTitle())) {
                        movie.setViews(movie.getViews()
                                + input.getUsers().get(j).getHistory().get(movie.getTitle()));
                    }
                }
            }

            if (sortType.equals("asc")) {
                movies.sort(Comparator.comparing(MovieInputData::getTitle));
                movies.sort(Comparator.comparing(MovieInputData::getViews));
            }

            if (sortType.equals("desc")) {
                movies.sort(Comparator.comparing(MovieInputData::getTitle).reversed());
                movies.sort(Comparator.comparing(MovieInputData::getViews).reversed());
            }

            ArrayList<String> movieTitles = new ArrayList<>();
            int numberOfMoviesToBeReturned = Math.min(numberOfMovies, movies.size());
            for (int i = 0; i < numberOfMoviesToBeReturned; i++) {
                if (movies.get(i).getViews() != 0) {
                    movieTitles.add(movies.get(i).getTitle());
                }
            }
            return movieTitles;
        }
        return null;
    }

    @Override
    public ArrayList<String> mostViewedSerial(final Input input, final String sortType,
                                              final List<List<String>> filters,
                                              final int numberOfSerials) {
        ArrayList<SerialInputData> serials = new ArrayList<>();
        String year = null;
        String genre = null;
        if (filters.get(0).get(0) != null) {
            year = filters.get(0).get(0);
        }
        if (filters.get(1).get(0) != null) {
            genre = filters.get(1).get(0);
        }
        if (input.getSerials().size() != 0) {
            for (int i = 0; i < input.getSerials().size(); i++) {
                SerialInputData serial = input.getSerials().get(i);
                if (year != null && genre != null) {
                    if (serial.getYear() == Integer.parseInt(year)
                            && serial.getGenres().contains(genre)) {
                        serials.add(serial);
                    }
                }
                if (year != null && genre == null) {
                    if (serial.getYear() == Integer.parseInt(year)) {
                        serials.add(serial);
                    }
                }
                if (year == null && genre != null) {
                    if (serial.getGenres().contains(genre)) {
                        serials.add(serial);
                    }
                }

                if (year == null && genre == null) {
                    serials.add(serial);
                }
            }
            for (SerialInputData serial : serials) {
                for (int j = 0; j < input.getUsers().size(); j++) {
                    if (input.getUsers().get(j).getHistory().containsKey(serial.getTitle())) {
                        serial.setViews(serial.getViews()
                                + input.getUsers().get(j).getHistory().get(serial.getTitle()));
                    }
                }
            }
            if (sortType.equals("asc")) {
                serials.sort(Comparator.comparing(SerialInputData::getTitle));
                serials.sort(Comparator.comparing(SerialInputData::getViews));
            }

            if (sortType.equals("desc")) {
                serials.sort(Comparator.comparing(SerialInputData::getTitle).reversed());
                serials.sort(Comparator.comparing(SerialInputData::getViews).reversed());
            }

            ArrayList<String> serialTitles = new ArrayList<>();
            int numberOfSerialsToBeReturned = Math.min(numberOfSerials, serials.size());
            for (int i = 0; i < numberOfSerialsToBeReturned; i++) {
                if (serials.get(i).getViews() != 0) {
                    serialTitles.add(serials.get(i).getTitle());
                }
            }
            return serialTitles;
        }
        return null;
    }

    @Override
    public ArrayList<String> favoriteMovie(final Input input, final String sortType,
                                           final List<List<String>> filters,
                                           final int numberOfMovies) {
        ArrayList<MovieInputData> movies = new ArrayList<>();
        String year = null;
        String genre = null;
        if (filters.get(0).get(0) != null) {
            year = filters.get(0).get(0);
        }
        if (filters.get(1).get(0) != null) {
            genre = filters.get(1).get(0);
        }
        if (input.getMovies().size() != 0) {
            for (int i = 0; i < input.getMovies().size(); i++) {
                MovieInputData movie = input.getMovies().get(i);
                if (year != null && genre != null) {
                    if (movie.getYear() == Integer.parseInt(year)
                            && movie.getGenres().contains(genre)) {
                        movies.add(movie);
                    }
                }
                if (year != null && genre == null) {
                    if (movie.getYear() == Integer.parseInt(year)) {
                        movies.add(movie);
                    }
                }
                if (year == null && genre != null) {
                    if (movie.getGenres().contains(genre)) {
                        movies.add(movie);
                    }
                }

                if (year == null && genre == null) {
                    movies.add(movie);
                }
            }

            // adding the favorite movies from database

            for (MovieInputData movie : movies) {
                for (int j = 0; j < input.getUsers().size(); j++) {
                    if (input.getUsers().get(j).getFavoriteMovies().contains(movie.getTitle())) {
                        movie.setNumberOfFavorites(movie.getNumberOfFavorites() + 1);
                    }
                }
            }

            if (sortType.equals("asc")) {
                movies.sort(Comparator.comparing(MovieInputData::getTitle));
                movies.sort(Comparator.comparing(MovieInputData::getNumberOfFavorites));
            }

            if (sortType.equals("desc")) {
                movies.sort(Comparator.comparing(MovieInputData::getTitle).reversed());
                movies.sort(Comparator.comparing(MovieInputData::getNumberOfFavorites).reversed());
            }

            ArrayList<String> movieTitles = new ArrayList<>();
            int numberOfMoviesToBeReturned = Math.min(numberOfMovies, movies.size());
            for (int i = 0; i < numberOfMoviesToBeReturned; i++) {
                if (movies.get(i).getNumberOfFavorites() != 0) {
                    movieTitles.add(movies.get(i).getTitle());
                }
            }
            return movieTitles;
        }
        return null;
    }

    @Override
    public ArrayList<String> favoriteSerial(final Input input, final String sortType,
                                            final List<List<String>> filters,
                                            final int numberOfSerials) {
        ArrayList<SerialInputData> serials = new ArrayList<>();
        String year = null;
        String genre = null;
        if (filters.get(0).get(0) != null) {
            year = filters.get(0).get(0);
        }
        if (filters.get(1).get(0) != null) {
            genre = filters.get(1).get(0);
        }
        if (input.getSerials().size() != 0) {
            for (int i = 0; i < input.getSerials().size(); i++) {
                SerialInputData serial = input.getSerials().get(i);
                if (year != null && genre != null) {
                    if (serial.getYear() == Integer.parseInt(year)
                            && serial.getGenres().contains(genre)) {
                        serials.add(serial);
                    }
                }
                if (year != null && genre == null) {
                    if (serial.getYear() == Integer.parseInt(year)) {
                        serials.add(serial);
                    }
                }
                if (year == null && genre != null) {
                    if (serial.getGenres().contains(genre)) {
                        serials.add(serial);
                    }
                }

                if (year == null && genre == null) {
                    serials.add(serial);
                }
            }

            for (SerialInputData serial : serials) {
                for (int j = 0; j < input.getUsers().size(); j++) {
                    if (input.getUsers().get(j).getFavoriteMovies().contains(serial.getTitle())) {
                        serial.setNumberOfFavorites(serial.getNumberOfFavorites() + 1);
                    }
                }
            }

            if (sortType.equals("asc")) {
                serials.sort(Comparator.comparing(SerialInputData::getTitle));
                serials.sort(Comparator.comparing(SerialInputData::getNumberOfFavorites));
            }

            if (sortType.equals("desc")) {
                serials.sort(Comparator.comparing(SerialInputData::getTitle).reversed());
                serials.sort(Comparator.comparing(SerialInputData::getNumberOfFavorites).
                        reversed());
            }

            ArrayList<String> serialTitles = new ArrayList<>();
            int numberOfSerialsToBeReturned = Math.min(numberOfSerials, serials.size());
            for (int i = 0; i < numberOfSerialsToBeReturned; i++) {
                if (serials.get(i).getNumberOfFavorites() != 0) {
                    serialTitles.add(serials.get(i).getTitle());
                }
            }
            return serialTitles;
        }
        return null;
    }

    @Override
    public ArrayList<String> ratingMovie(final Input input, final String sortType,
                                         final List<List<String>> filters,
                                         final int numberOfMovies) {
        ArrayList<MovieInputData> movies = new ArrayList<>();
        String year = null;
        String genre = null;
        if (filters.get(0).get(0) != null) {
            year = filters.get(0).get(0);
        }
        if (filters.get(1).get(0) != null) {
            genre = filters.get(1).get(0);
        }
        if (input.getMovies().size() != 0) {
            for (int i = 0; i < input.getMovies().size(); i++) {
                MovieInputData movie = input.getMovies().get(i);
                if (year != null && genre != null) {
                    if (movie.getYear() == Integer.parseInt(year)
                            && movie.getGenres().contains(genre)) {
                        movies.add(movie);
                    }
                }
                if (year != null && genre == null) {
                    if (movie.getYear() == Integer.parseInt(year)) {
                        movies.add(movie);
                    }
                }
                if (year == null && genre != null) {
                    if (movie.getGenres().contains(genre)) {
                        movies.add(movie);
                    }
                }

                if (year == null && genre == null) {
                    movies.add(movie);
                }
            }

            if (sortType.equals("asc")) {
                movies.sort(Comparator.comparing(MovieInputData::getTitle));
                movies.sort(Comparator.comparing(MovieInputData::calculateRating));
            }

            if (sortType.equals("desc") && movies.size() > 1) {
                movies.sort(Comparator.comparing(MovieInputData::getTitle).reversed());
                movies.sort(Comparator.comparing(MovieInputData::calculateRating).reversed());
            }

            ArrayList<String> movieTitles = new ArrayList<>();
            int numberOfMoviesToBeReturned = Math.min(numberOfMovies, movies.size());

            for (int i = 0; i < numberOfMoviesToBeReturned; i++) {
                if (movies.get(i).calculateRating() != (double) 0) {
                    movieTitles.add(movies.get(i).getTitle());
                }
            }
            return movieTitles;
        }
        return null;
    }

    @Override
    public ArrayList<String> ratingSerial(final Input input, final String sortType,
                                          final List<List<String>> filters,
                                          final int numberOfSerials) {
        ArrayList<SerialInputData> serials = new ArrayList<>();
        String year = null;
        String genre = null;
        if (filters.get(0).get(0) != null) {
            year = filters.get(0).get(0);
        }
        if (filters.get(1).get(0) != null) {
            genre = filters.get(1).get(0);
        }
        if (input.getSerials().size() != 0) {
            for (int i = 0; i < input.getSerials().size(); i++) {
                SerialInputData serial = input.getSerials().get(i);
                if (year != null && genre != null) {
                    if (serial.getYear() == Integer.parseInt(year)
                            && serial.getGenres().contains(genre)) {
                        serials.add(serial);
                    }
                }
                if (year != null && genre == null) {
                    if (serial.getYear() == Integer.parseInt(year)) {
                        serials.add(serial);
                    }
                }
                if (year == null && genre != null) {
                    if (serial.getGenres().contains(genre)) {
                        serials.add(serial);
                    }
                }

                if (year == null && genre == null) {
                    serials.add(serial);
                }
            }

            if (sortType.equals("asc")) {
                serials.sort(Comparator.comparing(SerialInputData::getTitle));
                serials.sort(Comparator.comparing(SerialInputData::averageSerialRating));
            }

            if (sortType.equals("desc") && serials.size() > 1) {
                serials.sort(Comparator.comparing(SerialInputData::getTitle).reversed());
                serials.sort(Comparator.comparing(SerialInputData::averageSerialRating).reversed());
            }

            int numberOfSerialsToBeReturned = Math.min(numberOfSerials, serials.size());
            ArrayList<String> serialTitles = new ArrayList<>();
            for (int i = 0; i < numberOfSerialsToBeReturned; i++) {
                double rating = serials.get(i).averageSerialRating();
                if (rating != (double) 0) {
                    serialTitles.add(serials.get(i).getTitle());
                }
            }
            return serialTitles;
        }
        return null;
    }
}
