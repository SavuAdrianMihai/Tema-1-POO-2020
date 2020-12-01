package queries;

import fileio.Input;
import fileio.UserInputData;
import fileio.ActorInputData;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

public class OtherQuery implements UserQuery, ActorQuery {
    @Override
    public ArrayList<String> numberOfRatingsUsers(final Input input, final int numberOfUsers,
                                             final String sortType) {
        ArrayList<UserInputData> users = new ArrayList<>();
        for (int i = 0; i < input.getUsers().size(); i++) {
            if (input.getUsers().get(i).getRatings().size() != 0) {
                users.add(input.getUsers().get(i));
            }
        }

        if (sortType.equals("asc")) {
            users.sort(Comparator.comparing(UserInputData::getUsername));
            for (int i = 0; i < users.size() - 1; i++) {
                for (int j = 0; j < (users.size() - i - 1); j++) {
                    if (users.get(j).getRatings().size() > users.get(j + 1).getRatings().size()) {
                        UserInputData aux = users.get(j);
                        users.set(j, users.get(j + 1));
                        users.set((j + 1), aux);
                    }
                }
            }
        }
        if (sortType.equals("desc")) {
            users.sort(Comparator.comparing(UserInputData::getUsername).reversed());
            for (int i = 0; i < users.size() - 1; i++) {
                for (int j = 0; j < (users.size() - i - 1); j++) {
                    if (users.get(j).getRatings().size() < users.get(j + 1).getRatings().size()) {
                        UserInputData aux = users.get(j);
                        users.set(j, users.get(j + 1));
                        users.set((j + 1), aux);
                    }
                }
            }
        }
        int numberOfUsersToBeReturned = Math.min(numberOfUsers, users.size());
        ArrayList<String> usersName = new ArrayList<>();
        for (int i = 0; i < numberOfUsersToBeReturned; i++) {
            usersName.add(users.get(i).getUsername());
        }
        return usersName;
    }

    @Override
    public ArrayList<String> filterDescriptionActors(final Input input, final String sortType,
                                               final List<List<String>> filters) {
        ArrayList<String> words = new ArrayList<>();
        ArrayList<ActorInputData> actors = new ArrayList<>();
        ArrayList<String> actorsName = new ArrayList<>();
        int actorFitsDescription;
        // get the words from action's filter
        for (int i = 0; i < filters.get(2).size(); i++) {
            if (filters.get(2).get(i) != null) {
                words.add(filters.get(2).get(i));
            }
        }
        if (input.getActors().size() != 0) {
            for (int i = 0; i < input.getActors().size(); i++) {
                // initially we assume that every actors has all the words in
                // its description
                actorFitsDescription = 1;
                for (String word : words) {
                    word = " " + word;
                    if (!Pattern.compile(Pattern.quote(word), Pattern.CASE_INSENSITIVE)
                            .matcher(input.getActors().get(i).getCareerDescription()).find()) {
                        actorFitsDescription = 0;
                        break;
                    }
                }
                if (actorFitsDescription == 1) {
                    actors.add(input.getActors().get(i));
                }
            }
        }
        for (ActorInputData actor : actors) {
            actorsName.add(actor.getName());
        }

        if (sortType.equals("asc")) {
            java.util.Collections.sort(actorsName);
        }
        if (sortType.equals("desc")) {
            actorsName.sort(java.util.Collections.reverseOrder());
        }
        return actorsName;
    }

    @Override
    public ArrayList<String> awardsActors(final Input input, final String sortType,
                                    final List<List<String>> filters) {
        ArrayList<String> awards = new ArrayList<>();
        ArrayList<ActorInputData> actors = new ArrayList<>();
        ArrayList<String> actorsName = new ArrayList<>();
        int actorHasAwards;
        // get the awards from action's filter
        for (int i = 0; i < filters.get(3).size(); i++) {
            if (filters.get(3).get(i) != null) {
                awards.add(filters.get(3).get(i));
            }
        }
        if (input.getActors().size() != 0) {
            for (int i = 0; i < input.getActors().size(); i++) {
                // initially we assume that every actors has all the awards
                actorHasAwards = 1;
                for (String award : awards) {
                    if (!input.getActors().get(i).getAwards().toString().contains(award)) {
                        actorHasAwards = 0;
                    }
                }
                if (actorHasAwards == 1) {
                    actors.add(input.getActors().get(i));
                }
            }
        }

        if (sortType.equals("asc")) {
            actors.sort(Comparator.comparing(ActorInputData::getName));
            actors.sort(Comparator.comparing(ActorInputData::totalNumberOfAwards));
        }

        if (sortType.equals("desc")) {
            actors.sort(Comparator.comparing(ActorInputData::getName).reversed());
            actors.sort(Comparator.comparing(ActorInputData::totalNumberOfAwards).reversed());
        }

        for (ActorInputData actor : actors) {
            actorsName.add(actor.getName());
        }
        return actorsName;
    }

    @Override
    public ArrayList<String> averageActors(final Input input, final String sortType,
                                     final List<List<String>> filters, final int numberOfActors) {
        ArrayList<ActorInputData> actors = new ArrayList<>();
        ArrayList<String> actorsName = new ArrayList<>();
        double numberOfElements;
        for (int i = 0; i < input.getActors().size(); i++) {
            numberOfElements = 0.0;
            for (int j = 0; j < input.getMovies().size(); j++) {
                if (input.getActors().get(i).getFilmography().contains(input.getMovies().get(j).
                        getTitle()) && input.getMovies().get(j).calculateRating() != 0.0) {
                    if (input.getActors().get(i).getRating() == 0.0) {
                        input.getActors().get(i).setRating(input.getMovies().get(j).
                                calculateRating());
                    } else {
                        input.getActors().get(i).setRating(input.getMovies().get(j).
                                calculateRating() + input.getActors().get(i).getRating());
                    }
                    numberOfElements = numberOfElements + 1.0;
                }
            }
            for (int j = 0; j < input.getSerials().size(); j++) {
                if (input.getActors().get(i).getFilmography().contains(input.getSerials().get(j).
                        getTitle()) && input.getSerials().get(j).averageSerialRating() != 0.0) {
                    input.getActors().get(i).setRating(input.getSerials().get(j).
                            averageSerialRating() + input.getActors().get(i).getRating());
                    numberOfElements = numberOfElements + 1.0;
                }
            }
            if (numberOfElements >= 1.0) {
                double newRating = input.getActors().get(i).getRating();
                newRating = newRating / (numberOfElements);
                input.getActors().get(i).setRating(newRating);
            }
            if (input.getActors().get(i).getRating() != 0.0) {
                actors.add(input.getActors().get(i));
            }
        }

        if (sortType.equals("asc")) {
            actors.sort(Comparator.comparing(ActorInputData::getName));
            actors.sort(Comparator.comparing(ActorInputData::getRating));
        }

        if (sortType.equals("desc")) {
            actors.sort(Comparator.comparing(ActorInputData::getName).reversed());
            actors.sort(Comparator.comparing(ActorInputData::getRating).reversed());
        }

        int numberOfActorsToBeReturned = Math.min(numberOfActors, actors.size());

        for (int i = 0; i < numberOfActorsToBeReturned; i++) {
            actorsName.add(actors.get(i).getName());
        }
        return actorsName;
    }
}

