package classes;

import actor.ActorsAwards;
import fileio.*;

import java.util.*;

public class Query implements MovieQuery, SerialQuery, UserQuery, ActorQuery {
    @Override
    public ArrayList<String> longestMovie(Input input, String sortType , List<List<String>> filters){
        ArrayList<MovieInputData> movies = new ArrayList<>();
        String year = null;
        String genre = null;
        if(filters.get(0).get(0) != null)
            year = filters.get(0).get(0);
        if(filters.get(1).get(0) != null)
            genre = filters.get(1).get(0);
        if (input.getMovies().size() != 0) {
            for (int i = 0; i < input.getMovies().size(); i++) {
                MovieInputData movie = input.getMovies().get(i);
                if (year != null && genre != null)
                    if(movie.getYear() == Integer.parseInt(year) &&
                            movie.getGenres().contains(genre))
                        movies.add(movie);
                if (year != null && genre == null)
                    if(movie.getYear() == Integer.parseInt(year))
                movies.add(movie);
                if (year == null && genre != null)
                    if (movie.getGenres().contains(genre))
                        movies.add(movie);

                if (year == null && genre == null)
                    movies.add(movie);
                }
            MovieInputData aux;
            if (sortType.equals("asc"))
                for (int i = 0; i < movies.size() - 1; i++)
                    for (int j = 0; j < (movies.size() - i - 1); j++){
                        if (movies.get(j).getDuration() > movies.get(j + 1).getDuration()) {
                            aux = movies.get(j);
                            movies.set(j, movies.get(j + 1));
                            movies.set((j + 1), aux);
                        }
                }
            if (sortType.equals("desc"))
                for (int i = 0; i < movies.size() - 1; i++)
                    for (int j = 0; j < (movies.size() - i - 1); j++){
                        if (movies.get(j).getDuration() < movies.get(j + 1).getDuration()) {
                            aux = movies.get(j);
                            movies.set(j, movies.get(j + 1));
                            movies.set((j + 1), aux);
                        }
                    }
            ArrayList<String> movieTitles = new ArrayList<>();
            for (MovieInputData movie : movies) movieTitles.add(movie.getTitle());
            return movieTitles;
        }
        return null;
    }

    @Override
    public ArrayList<String> longestSerial(Input input, String sortType, List<List<String>> filters) {
        ArrayList<SerialInputData> serials = new ArrayList<>();
        String year = null;
        String genre = null;
        if(filters.get(0).get(0) != null)
            year = filters.get(0).get(0);
        if(filters.get(1).get(0) != null)
            genre = filters.get(1).get(0);
        if (input.getSerials().size() != 0) {
            for (int i = 0; i < input.getSerials().size(); i++) {
                SerialInputData serial = input.getSerials().get(i);
                if (year != null && genre != null)
                    if(serial.getYear() == Integer.parseInt(year) &&
                            serial.getGenres().contains(genre))
                        serials.add(serial);
                if (year != null && genre == null)
                    if(serial.getYear() == Integer.parseInt(year))
                        serials.add(serial);
                if (year == null && genre != null)
                    if (serial.getGenres().contains(genre))
                        serials.add(serial);

                if (year == null && genre == null)
                    serials.add(serial);
            }
            SerialInputData aux;
            if (sortType.equals("asc"))
                for (int i = 0; i < serials.size() - 1; i++)
                    for (int j = 0; j < (serials.size() - i - 1); j++){
                        if (serials.get(j).totalDuration() > serials.get(j + 1).totalDuration()) {
                            aux = serials.get(j);
                            serials.set(j, serials.get(j + 1));
                            serials.set((j + 1), aux);
                        }
                    }

            if (sortType.equals("desc"))
                for (int i = 0; i < serials.size() - 1; i++)
                    for (int j = 0; j < (serials.size() - i - 1); j++){
                        if (serials.get(j).totalDuration() < serials.get(j + 1).totalDuration()) {
                            aux = serials.get(j);
                            serials.set(j, serials.get(j + 1));
                            serials.set((j + 1), aux);
                        }
                    }

            ArrayList<String> serialTitles = new ArrayList<>();
            for (SerialInputData serial : serials) serialTitles.add(serial.getTitle());
            return serialTitles;
        }
        return null;
    }

    @Override
    public ArrayList<String> mostViewedMovie(Input input, String sortType, List<List<String>> filters, int numberOfMovies) {
        ArrayList<MovieInputData> movies = new ArrayList<>();
        String year = null;
        String genre = null;
        if(filters.get(0).get(0) != null)
            year = filters.get(0).get(0);
        if(filters.get(1).get(0) != null)
            genre = filters.get(1).get(0);
        if (input.getMovies().size() != 0) {
            for (int i = 0; i < input.getMovies().size(); i++) {
                MovieInputData movie = input.getMovies().get(i);
                if (year != null && genre != null)
                    if (movie.getYear() == Integer.parseInt(year) &&
                            movie.getGenres().contains(genre))
                        movies.add(movie);
                if (year != null && genre == null)
                    if (movie.getYear() == Integer.parseInt(year))
                        movies.add(movie);
                if (year == null && genre != null)
                    if (movie.getGenres().contains(genre))
                        movies.add(movie);

                if (year == null && genre == null)
                    movies.add(movie);
            }
            for (MovieInputData movie : movies)
                for (int j = 0; j < input.getUsers().size(); j++)
                    if (input.getUsers().get(j).getHistory().containsKey(movie.getTitle()))
                        movie.setViews(movie.getViews() +
                                input.getUsers().get(j).getHistory().get(movie.getTitle()));

            MovieInputData aux;
            if (sortType.equals("asc"))
                for (int i = 0; i < movies.size() - 1; i++)
                    for (int j = 0; j < (movies.size() - i - 1); j++) {
                        if (movies.get(j).getViews() > movies.get(j + 1).getViews()) {
                            aux = movies.get(j);
                            movies.set(j, movies.get(j + 1));
                            movies.set((j + 1), aux);
                        }
                    }
            if (sortType.equals("desc"))
                for (int i = 0; i < movies.size() - 1; i++)
                    for (int j = 0; j < (movies.size() - i - 1); j++) {
                        if (movies.get(j).getViews() < movies.get(j + 1).getViews()) {
                            aux = movies.get(j);
                            movies.set(j, movies.get(j + 1));
                            movies.set((j + 1), aux);
                        }
                    }
            ArrayList<String> movieTitles = new ArrayList<>();
            if (numberOfMovies > movies.size())
                numberOfMovies = movies.size();
            for (int i = 0; i < numberOfMovies; i++)
                if (movies.get(i).getViews() != 0)
                    movieTitles.add(movies.get(i).getTitle());
            return movieTitles;
        }
        return null;
    }

    @Override
    public ArrayList<String> mostViewedSerial(Input input, String sortType, List<List<String>> filters, int numberOfSerials) {
        ArrayList<SerialInputData> serials = new ArrayList<>();
        String year = null;
        String genre = null;
        if(filters.get(0).get(0) != null)
            year = filters.get(0).get(0);
        if(filters.get(1).get(0) != null)
            genre = filters.get(1).get(0);
        if (input.getSerials().size() != 0) {
            for (int i = 0; i < input.getSerials().size(); i++) {
                SerialInputData serial = input.getSerials().get(i);
                if (year != null && genre != null)
                    if(serial.getYear() == Integer.parseInt(year) &&
                            serial.getGenres().contains(genre))
                        serials.add(serial);
                if (year != null && genre == null)
                    if(serial.getYear() == Integer.parseInt(year))
                        serials.add(serial);
                if (year == null && genre != null)
                    if (serial.getGenres().contains(genre))
                        serials.add(serial);

                if (year == null && genre == null)
                    serials.add(serial);
            }
            for (SerialInputData serial : serials)
                for (int j = 0; j < input.getUsers().size(); j++)
                    if (input.getUsers().get(j).getHistory().containsKey(serial.getTitle()))
                        serial.setViews(serial.getViews() +
                                input.getUsers().get(j).getHistory().get(serial.getTitle()));
            SerialInputData aux;
            if (sortType.equals("asc"))
                for (int i = 0; i < serials.size() - 1; i++)
                    for (int j = 0; j < (serials.size() - i - 1); j++){
                        if (serials.get(j).getViews() > serials.get(j + 1).getViews()) {
                            aux = serials.get(j);
                            serials.set(j, serials.get(j + 1));
                            serials.set((j + 1), aux);
                        }
                    }

            if (sortType.equals("desc"))
                for (int i = 0; i < serials.size() - 1; i++)
                    for (int j = 0; j < (serials.size() - i - 1); j++){
                        if (serials.get(j).getViews() < serials.get(j + 1).getViews()) {
                            aux = serials.get(j);
                            serials.set(j, serials.get(j + 1));
                            serials.set((j + 1), aux);
                        }
                    }

            if (numberOfSerials > serials.size())
                numberOfSerials = serials.size();
            ArrayList<String> serialTitles = new ArrayList<>();
            for (int i = 0; i < numberOfSerials; i++)
                if (serials.get(i).getViews() != 0)
                    serialTitles.add(serials.get(i).getTitle());
            return serialTitles;
        }
        return null;
    }

    @Override
    public ArrayList<String> favoriteMovie(Input input, String sortType, List<List<String>> filters, int numberOfMovies) {
        ArrayList<MovieInputData> movies = new ArrayList<>();
        String year = null;
        String genre = null;
        if(filters.get(0).get(0) != null)
            year = filters.get(0).get(0);
        if(filters.get(1).get(0) != null)
            genre = filters.get(1).get(0);
        if (input.getMovies().size() != 0) {
            for (int i = 0; i < input.getMovies().size(); i++) {
                MovieInputData movie = input.getMovies().get(i);
                if (year != null && genre != null)
                    if (movie.getYear() == Integer.parseInt(year) &&
                            movie.getGenres().contains(genre))
                        movies.add(movie);
                if (year != null && genre == null)
                    if (movie.getYear() == Integer.parseInt(year))
                        movies.add(movie);
                if (year == null && genre != null)
                    if (movie.getGenres().contains(genre))
                        movies.add(movie);

                if (year == null && genre == null)
                    movies.add(movie);
            }

            // adding the favorite movies from database

            for (MovieInputData movie : movies)
                for (int j = 0; j < input.getUsers().size(); j++)
                    if (input.getUsers().get(j).getFavoriteMovies().contains(movie.getTitle()))
                        movie.setNumberOfFavorites(movie.getNumberOfFavorites() + 1);

            MovieInputData aux;
            if (sortType.equals("asc")) {
                movies.sort(Comparator.comparing(ShowInput::getTitle));
                for (int i = 0; i < movies.size() - 1; i++)
                    for (int j = 0; j < (movies.size() - i - 1); j++) {
                        if (movies.get(j).getNumberOfFavorites() > movies.get(j + 1).getNumberOfFavorites()) {
                            aux = movies.get(j);
                            movies.set(j, movies.get(j + 1));
                            movies.set((j + 1), aux);
                        }
                    }
            }
            if (sortType.equals("desc")) {
                movies.sort(Comparator.comparing(ShowInput::getTitle).reversed());
                for (int i = 0; i < movies.size() - 1; i++)
                    for (int j = 0; j < (movies.size() - i - 1); j++) {
                        if (movies.get(j).getNumberOfFavorites() < movies.get(j + 1).getNumberOfFavorites()) {
                            aux = movies.get(j);
                            movies.set(j, movies.get(j + 1));
                            movies.set((j + 1), aux);
                        }
                    }
            }

            ArrayList<String> movieTitles = new ArrayList<>();
            if (numberOfMovies > movies.size())
                numberOfMovies = movies.size();
            for (int i = 0; i < numberOfMovies; i++)
                if (movies.get(i).getNumberOfFavorites() != 0)
                    movieTitles.add(movies.get(i).getTitle());
            return movieTitles;
        }
        return null;
    }

    @Override
    public ArrayList<String> favoriteSerial(Input input, String sortType, List<List<String>> filters, int numberOfSerials) {
        ArrayList<SerialInputData> serials = new ArrayList<>();
        String year = null;
        String genre = null;
        if(filters.get(0).get(0) != null)
            year = filters.get(0).get(0);
        if(filters.get(1).get(0) != null)
            genre = filters.get(1).get(0);
        if (input.getSerials().size() != 0) {
            for (int i = 0; i < input.getSerials().size(); i++) {
                SerialInputData serial = input.getSerials().get(i);
                if (year != null && genre != null)
                    if(serial.getYear() == Integer.parseInt(year) &&
                            serial.getGenres().contains(genre))
                        serials.add(serial);
                if (year != null && genre == null)
                    if(serial.getYear() == Integer.parseInt(year))
                        serials.add(serial);
                if (year == null && genre != null)
                    if (serial.getGenres().contains(genre))
                        serials.add(serial);

                if (year == null && genre == null)
                    serials.add(serial);
            }

            for (SerialInputData serial : serials)
                for (int j = 0; j < input.getUsers().size(); j++)
                    if (input.getUsers().get(j).getFavoriteMovies().contains(serial.getTitle()))
                        serial.setNumberOfFavorites(serial.getNumberOfFavorites() + 1);

            //Collections.sort(serials, (c1, c2) -> c1.getTitle().compareTo(c2.getTitle()));

            SerialInputData aux;
            if (sortType.equals("asc")) {
                serials.sort(Comparator.comparing(ShowInput::getTitle));
                for (int i = 0; i < serials.size() - 1; i++)
                    for (int j = 0; j < (serials.size() - i - 1); j++) {
                        if (serials.get(j).getNumberOfFavorites() > serials.get(j + 1).getNumberOfFavorites()) {
                            aux = serials.get(j);
                            serials.set(j, serials.get(j + 1));
                            serials.set((j + 1), aux);
                        }
                    }
            }

            if (sortType.equals("desc")) {
                serials.sort(Comparator.comparing(ShowInput::getTitle).reversed());
                for (int i = 0; i < serials.size() - 1; i++)
                    for (int j = 0; j < (serials.size() - i - 1); j++) {
                        if (serials.get(j).getNumberOfFavorites() < serials.get(j + 1).getNumberOfFavorites()) {
                            aux = serials.get(j);
                            serials.set(j, serials.get(j + 1));
                            serials.set((j + 1), aux);
                        }
                    }
            }

            if (numberOfSerials > serials.size())
                numberOfSerials = serials.size();
            ArrayList<String> serialTitles = new ArrayList<>();
            for (int i = 0; i < numberOfSerials; i++)
                if (serials.get(i).getNumberOfFavorites() != 0)
                    serialTitles.add(serials.get(i).getTitle());
            return serialTitles;
        }
        return null;
    }

    @Override
    public ArrayList<String> ratingMovie(Input input, String sortType, List<List<String>> filters, int numberOfMovies) {
        ArrayList<MovieInputData> movies = new ArrayList<>();
        String year = null;
        String genre = null;
        if(filters.get(0).get(0) != null)
            year = filters.get(0).get(0);
        if(filters.get(1).get(0) != null)
            genre = filters.get(1).get(0);
        if (input.getMovies().size() != 0) {
            for (int i = 0; i < input.getMovies().size(); i++) {
                MovieInputData movie = input.getMovies().get(i);
                if (year != null && genre != null)
                    if (movie.getYear() == Integer.parseInt(year) &&
                            movie.getGenres().contains(genre))
                        movies.add(movie);
                if (year != null && genre == null)
                    if (movie.getYear() == Integer.parseInt(year))
                        movies.add(movie);
                if (year == null && genre != null)
                    if (movie.getGenres().contains(genre))
                        movies.add(movie);

                if (year == null && genre == null)
                    movies.add(movie);
            }

            MovieInputData aux;
            if (sortType.equals("asc")) {
                movies.sort(Comparator.comparing(ShowInput::getTitle));
                for (int i = 0; i < movies.size() - 1; i++)
                    for (int j = 0; j < (movies.size() - i - 1); j++) {
                        if (movies.get(j).calculateRating() > movies.get(j + 1).calculateRating()) {
                            aux = movies.get(j);
                            movies.set(j, movies.get(j + 1));
                            movies.set((j + 1), aux);
                        }
                    }
            }
            if (sortType.equals("desc")) {
                movies.sort(Comparator.comparing(ShowInput::getTitle).reversed());
                for (int i = 0; i < movies.size() - 1; i++)
                    for (int j = 0; j < (movies.size() - i - 1); j++) {
                        if (movies.get(j).calculateRating() < movies.get(j + 1).calculateRating()) {
                            aux = movies.get(j);
                            movies.set(j, movies.get(j + 1));
                            movies.set((j + 1), aux);
                        }
                    }
            }

            ArrayList<String> movieTitles = new ArrayList<>();
            if (numberOfMovies > movies.size())
                numberOfMovies = movies.size();
            for (int i = 0; i < numberOfMovies; i++)
                if (movies.get(i).calculateRating() != (double)0)
                    movieTitles.add(movies.get(i).getTitle());
            return movieTitles;
        }
        return null;
    }

    @Override
    public ArrayList<String> ratingSerial(Input input, String sortType, List<List<String>> filters, int numberOfSerials) {
        ArrayList<SerialInputData> serials = new ArrayList<>();
        String year = null;
        String genre = null;
        if(filters.get(0).get(0) != null)
            year = filters.get(0).get(0);
        if(filters.get(1).get(0) != null)
            genre = filters.get(1).get(0);
        if (input.getSerials().size() != 0) {
            for (int i = 0; i < input.getSerials().size(); i++) {
                SerialInputData serial = input.getSerials().get(i);
                if (year != null && genre != null)
                    if(serial.getYear() == Integer.parseInt(year) &&
                            serial.getGenres().contains(genre))
                        serials.add(serial);
                if (year != null && genre == null)
                    if(serial.getYear() == Integer.parseInt(year))
                        serials.add(serial);
                if (year == null && genre != null)
                    if (serial.getGenres().contains(genre))
                        serials.add(serial);

                if (year == null && genre == null)
                    serials.add(serial);
            }

            SerialInputData aux;
            if (sortType.equals("asc")) {
                serials.sort(Comparator.comparing(ShowInput::getTitle));
                for (int i = 0; i < serials.size() - 1; i++)
                    for (int j = 0; j < (serials.size() - i - 1); j++) {
                        if (serials.get(j).averageSerialRating() > serials.get(j + 1).averageSerialRating()) {
                            aux = serials.get(j);
                            serials.set(j, serials.get(j + 1));
                            serials.set((j + 1), aux);
                        }
                    }
            }

            if (sortType.equals("desc") && serials.size() > 1) {
                serials.sort(Comparator.comparing(ShowInput::getTitle).reversed());
                for (int i = 0; i < serials.size() - 1; i++)
                    for (int j = 0; j < (serials.size() - i - 1); j++) {
                        if (serials.get(j).averageSerialRating() < serials.get(j + 1).averageSerialRating()) {
                            aux = serials.get(j);
                            serials.set(j, serials.get(j + 1));
                            serials.set((j + 1), aux);
                        }
                    }
            }

            if (numberOfSerials > serials.size())
                numberOfSerials = serials.size();
            ArrayList<String> serialTitles = new ArrayList<>();
            for (int i = 0; i < numberOfSerials; i++) {
                double rating = serials.get(i).averageSerialRating();
                if (rating != (double) 0)
                    serialTitles.add(serials.get(i).getTitle());
            }
            return serialTitles;
        }
        return null;
    }

    @Override
    public ArrayList<String> numberOfRatings(Input input, int numberOfUsers, String sortType) {
        ArrayList<UserInputData> users = new ArrayList<>();
        for (int i = 0; i < input.getUsers().size(); i++)
            if (input.getUsers().get(i).getRatings().size() != 0)
                users.add(input.getUsers().get(i));

        if (sortType.equals("asc")) {
            users.sort(Comparator.comparing(UserInputData::getUsername));
            for (int i = 0; i < users.size() - 1; i++)
                for (int j = 0; j < (users.size() - i - 1); j++) {
                    if (users.get(j).getRatings().size() > users.get(j + 1).getRatings().size()) {
                        UserInputData aux = users.get(j);
                        users.set(j, users.get(j + 1));
                        users.set((j + 1), aux);
                    }
                }
        }
        if (sortType.equals("desc")) {
            users.sort(Comparator.comparing(UserInputData::getUsername).reversed());
            for (int i = 0; i < users.size() - 1; i++)
                for (int j = 0; j < (users.size() - i - 1); j++) {
                    if (users.get(j).getRatings().size() < users.get(j + 1).getRatings().size()) {
                        UserInputData aux = users.get(j);
                        users.set(j, users.get(j + 1));
                        users.set((j + 1), aux);
                    }
                }
        }
        ArrayList<String> usersName = new ArrayList<>();
                if (numberOfUsers > users.size())
                    numberOfUsers = users.size();
        for (int i = 0; i < numberOfUsers; i++)
            usersName.add(users.get(i).getUsername());
        return usersName;
    }

    @Override
    public ArrayList<String> filterDescription(Input input, String sortType, List<List<String>> filters) {
        ArrayList<String> words = new ArrayList<>();
        ArrayList<ActorInputData> actors = new ArrayList<>();
        ArrayList<String> actorsName = new ArrayList<>();
        int actorFitsDescription;
        // get the words from action's filter
        for(int i = 0; i < filters.get(2).size(); i++)
            if(filters.get(2).get(i) != null)
                words.add(filters.get(2).get(i));
        if (input.getActors().size() != 0)
            for(int i = 0; i < input.getActors().size(); i++) {
                // initially we assume that every actors has all the words in its description
                actorFitsDescription = 1;
                for (String word : words)
                    if (!input.getActors().get(i).getCareerDescription().contains(word)) {
                        actorFitsDescription = 0;
                        break;
                    }
                if(actorFitsDescription == 1)
                    actors.add(input.getActors().get(i));
            }
        for (ActorInputData actor : actors) actorsName.add(actor.getName());

        if (sortType.equals("asc"))
            java.util.Collections.sort(actorsName);
        if (sortType.equals("desc"))
            java.util.Collections.sort(actorsName, java.util.Collections.reverseOrder());
        return actorsName;
    }

    @Override
    public ArrayList<String> awards(Input input, String sortType, List<List<String>> filters) {
        ArrayList<String> awards = new ArrayList<>();
        ArrayList<ActorInputData> actors = new ArrayList<>();
        ArrayList<String> actorsName = new ArrayList<>();
        int actorHasAwards;
        // get the awards from action's filter
        for(int i = 0; i < filters.get(3).size(); i++)
            if(filters.get(3).get(i) != null)
                awards.add(filters.get(3).get(i));
        if (input.getActors().size() != 0)
            for(int i = 0; i < input.getActors().size(); i++) {
                // initially we assume that every actors has all the awards
                actorHasAwards = 1;
                for (String award : awards)
                    if (!input.getActors().get(i).getAwards().toString().contains(award))
                        actorHasAwards = 0;
                if(actorHasAwards == 1)
                    actors.add(input.getActors().get(i));
            }

        if (sortType.equals("asc")) {
            for (int i = 0; i < actors.size() - 1; i++)
                for (int j = 0; j < (actors.size() - i - 1); j++)
                    if (actors.get(j).getName().
                            compareTo(actors.get(j + 1).getName()) > 0) {
                        ActorInputData aux = actors.get(j);
                        actors.set(j, actors.get(j + 1));
                        actors.set((j + 1), aux);
                    }
            for (int i = 0; i < actors.size() - 1; i++)
                for (int j = 0; j < (actors.size() - i - 1); j++)
                    if (actors.get(j).totalNumberOfAwards()
                            > actors.get(j + 1).totalNumberOfAwards()) {
                        ActorInputData aux = actors.get(j);
                        actors.set(j, actors.get(j + 1));
                        actors.set((j + 1), aux);
                    }
        }

        if (sortType.equals("desc")) {
            for (int i = 0; i < actors.size() - 1; i++)
                for (int j = 0; j < (actors.size() - i - 1); j++)
                    if (actors.get(j).getName().
                            compareTo(actors.get(j + 1).getName()) < 0) {
                        ActorInputData aux = actors.get(j);
                        actors.set(j, actors.get(j + 1));
                        actors.set((j + 1), aux);
                    }

            for (int i = 0; i < actors.size() - 1; i++)
                for (int j = 0; j < (actors.size() - i - 1); j++)
                    if (actors.get(j).totalNumberOfAwards()
                            < actors.get(j + 1).totalNumberOfAwards()) {
                        ActorInputData aux = actors.get(j);
                        actors.set(j, actors.get(j + 1));
                        actors.set((j + 1), aux);
                    }
        }

        for (ActorInputData actor : actors) actorsName.add(actor.getName());
        return actorsName;
    }

    @Override
    public ArrayList<String> average(Input input, String sortType, List<List<String>> filters, int numberOfActors) {
        ArrayList<ActorInputData> actors = new ArrayList<>();
        ArrayList<String> actorsName = new ArrayList<>();
            for(int i = 0; i < input.getActors().size(); i++) {
                int numberOfElements = 0;
                for(int j = 0; j < input.getMovies().size(); j++)
                    if(input.getActors().get(i).getFilmography().contains(input.getMovies().get(j).getTitle()) &&
                            input.getMovies().get(j).calculateRating() != (double) 0) {
                        input.getActors().get(i).setRating(input.getMovies().get(j).calculateRating() +
                                input.getActors().get(i).getRating());
                        numberOfElements++;
                    }
                for(int j = 0; j < input.getSerials().size(); j++)
                    if(input.getActors().get(i).getFilmography().contains(input.getSerials().get(j).getTitle()) &&
                            input.getSerials().get(j).averageSerialRating() != (double) 0) {
                        double aaa = input.getSerials().get(j).averageSerialRating();
                        input.getActors().get(i).setRating(input.getSerials().get(j).averageSerialRating() +
                                input.getActors().get(i).getRating());
                        numberOfElements++;
                    }
                if (numberOfElements > 1) {
                    double newRating = input.getActors().get(i).getRating();
                    newRating /= numberOfElements;
                    input.getActors().get(i).setRating(newRating);
                }
                if(input.getActors().get(i).getRating() > 0)
                    actors.add(input.getActors().get(i));
            }

            if(sortType.equals("asc")) {
                actors.sort(Comparator.comparing(ActorInputData::getName));
                actors.sort(Comparator.comparing(ActorInputData::getRating));
            }

            if(sortType.equals("desc")) {
                actors.sort(Comparator.comparing(ActorInputData::getName).reversed());
                actors.sort(Comparator.comparing(ActorInputData::getRating).reversed());
            }

        if(numberOfActors > actors.size())
            numberOfActors = actors.size();

        for(int i = 0; i < numberOfActors; i++)
            actorsName.add(actors.get(i).getName());
        return actorsName;
    }
}

