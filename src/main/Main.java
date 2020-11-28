package main;

import checker.Checkstyle;
import checker.Checker;
import classes.Command;
import classes.Query;
import classes.Recommendation;
import common.Constants;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //TODO add here the entry point to your implementation


        for (int i = 0; i < input.getCommands().size(); i++) {
            String actionType = input.getCommands().get(i).getActionType();
            switch (actionType) {
                case "command":
                    String type = input.getCommands().get(i).getType();
                    switch (type) {
                        case "view":
                            Command command = new Command();
                            String username = input.getCommands().get(i).getUsername();
                            String title = input.getCommands().get(i).getTitle();
                            int nrOfViews = 0;
                            String messageView = "fail";
                            for (int j = 0; j < input.getUsers().size(); j++) {
                                if (username.equals(input.getUsers().get(j).getUsername())) {
                                    command.view(input.getUsers().get(j), title, input);
                                    if (input.getUsers().get(j).getHistory().
                                            containsKey(title)) {
                                        messageView = "success";
                                        nrOfViews = input.getUsers().get(j).getHistory().get(title);
                                    }
                                }
                            }
                            JSONObject fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                    "message", messageView + " -> " + title
                                            + " was viewed with total views of " + nrOfViews);
                            arrayResult.add(fS);
                            break;

                        case "favorite":
                            String outcomeFavorite = "";
                            messageView = "";
                            command = new Command();
                            username = input.getCommands().get(i).getUsername();
                            title = input.getCommands().get(i).getTitle();
                            for (int j = 0; j < input.getUsers().size(); j++) {
                                if (username.equals(input.getUsers().get(j).getUsername())) {
                                    outcomeFavorite = command.favorite(input.
                                            getUsers().get(j), title, input);
                                    if (outcomeFavorite.equals(" is already in favourite list"))
                                        messageView = "error";
                                    if (outcomeFavorite.equals(" was added as favourite"))
                                            messageView = "success";
                                    if (outcomeFavorite.equals(" is not seen"))
                                        messageView = "error";
                                }
                            }
                            fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                    "message", messageView + " -> "
                                            + title + outcomeFavorite);
                            arrayResult.add(fS);
                            break;
                        case "rating":
                            messageView = "error";
                            String outputMessage = "";
                            title = input.getCommands().get(i).getTitle();
                            double rating = input.getCommands().get(i).getGrade();
                            username = input.getCommands().get(i).getUsername();
                            int seasonNumber = input.getCommands().get(i).getSeasonNumber();
                            command = new Command();
                            for (int j = 0; j < input.getUsers().size(); j++) {
                                if (username.equals(input.getUsers().get(j).getUsername())) {
                                    outputMessage = command.rating(input.getUsers().get(j), title,
                                            input, rating, seasonNumber);
                                }
                            }
                            if (outputMessage.equals("success")) {
                                messageView = "success";
                                fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                        "message", messageView + " -> " + title
                                                + " was rated with " + rating + " by " + username);
                                arrayResult.add(fS);
                                break;
                            }

                            if (outputMessage.equals("error: is not seen")) {
                                fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                        "message", messageView + " -> " + title
                                                + " is not seen");
                                arrayResult.add(fS);
                                break;
                            }

                            if (outputMessage.equals("error: already rated")) {
                                fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                        "message", messageView + " -> " + title
                                                + " has been already rated");
                                arrayResult.add(fS);
                                break;
                            }

                        default:
                        //    fS = fileWriter.writeFile(0, "message", "fail");
                         //   arrayResult.add(fS);
                        break;
                    }
                    break;
                case "recommendation":
                    type = input.getCommands().get(i).getType();
                    switch(type){
                        case "standard":
                            Recommendation recommendation = new Recommendation();
                            String username = input.getCommands().get(i).getUsername();
                            String showName = "";
                            for (int j = 0; j < input.getUsers().size(); j++) {
                                if (username.equals(input.getUsers().get(j).getUsername())) {
                                    showName = recommendation.standardRecommendation
                                            (input, input.getUsers().get(j));
                                }
                            }
                            if (showName.equals("")){
                                JSONObject fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                        "message","StandardRecommendation cannot be applied!");
                                arrayResult.add(fS);
                                break;
                            }
                            JSONObject fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                    "message", "StandardRecommendation result: " + showName);
                            arrayResult.add(fS);
                            break;

                        case "best_unseen":
                            recommendation = new Recommendation();
                            username = input.getCommands().get(i).getUsername();
                            showName = "";
                            for (int j = 0; j < input.getUsers().size(); j++)
                                if (username.equals(input.getUsers().get(j).getUsername())) {
                                    showName = recommendation.bestUnseenRecommendation
                                            (input, input.getUsers().get(j));
                                    break;
                                }

                            if (showName.equals("fail")){
                                fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                        "message","BestRatedUnseenRecommendation cannot be applied!");
                                arrayResult.add(fS);
                                break;
                            }

                            fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                    "message", "BestRatedUnseenRecommendation result: " + showName);
                            arrayResult.add(fS);
                            break;

                        case "popular":
                            recommendation = new Recommendation();
                            username = input.getCommands().get(i).getUsername();
                            showName = "";
                            for (int j = 0; j < input.getUsers().size(); j++)
                                if (username.equals(input.getUsers().get(j).getUsername())) {
                                    showName = recommendation.popular
                                            (input.getUsers().get(j), input);
                                    break;
                                }
                            if (showName.equals("")){
                                fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                        "message","PopularRecommendation cannot be applied!");
                                arrayResult.add(fS);
                                break;
                            }

                            fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                    "message", "PopularRecommendation result: " + showName);
                            arrayResult.add(fS);
                            break;

                        case "favorite":
                            recommendation = new Recommendation();
                            username = input.getCommands().get(i).getUsername();
                            showName = "";
                            for (int j = 0; j < input.getUsers().size(); j++)
                                if (username.equals(input.getUsers().get(j).getUsername())) {
                                    showName = recommendation.favorite
                                            (input.getUsers().get(j), input);
                                    break;
                                }
                            if (showName.equals("")){
                                fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                        "message","FavoriteRecommendation cannot be applied!");
                                arrayResult.add(fS);
                                break;
                            }

                            fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                    "message", "FavoriteRecommendation result: " + showName);
                            arrayResult.add(fS);
                            break;

                        case "search":
                            recommendation = new Recommendation();
                            username = input.getCommands().get(i).getUsername();
                            String genre = input.getCommands().get(i).getGenre();
                            ArrayList<String> showNames = new ArrayList<>();
                            for (int j = 0; j < input.getUsers().size(); j++)
                                if (username.equals(input.getUsers().get(j).getUsername())) {
                                    showNames = recommendation.search
                                            (input.getUsers().get(j), input, genre);
                                    break;
                                }
                            if (showNames.get(0).equals("fail")){
                                fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                        "message","SearchRecommendation cannot be applied!");
                                arrayResult.add(fS);
                                break;
                            }

                            fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                    "message", "SearchRecommendation result: " + showNames);
                            arrayResult.add(fS);
                            break;
                    }
                    break;
                case "query":
                    String objectType = "";
                    if (input.getCommands().get(i).getObjectType() != null)
                        objectType = input.getCommands().get(i).getObjectType();
                    switch (objectType) {
                        case "movies":
                            String criteria = input.getCommands().get(i).getCriteria();
                            switch (criteria){
                                case "longest":
                                    Query query = new Query();
                                    String sortType = input.getCommands().get(i).getSortType();
                                    List<List<String>> filters = input.getCommands().get(i).getFilters();
                                    int numberOfMovies;
                                    ArrayList<String> movieName = query.longestMovie(input, sortType, filters);
                                    JSONObject fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                            "message","Query result: " + movieName);
                                    arrayResult.add(fS);
                                    break;
                                case "most_viewed":
                                    query = new Query();
                                    sortType = input.getCommands().get(i).getSortType();
                                    filters = input.getCommands().get(i).getFilters();
                                    numberOfMovies = input.getCommands().get(i).getNumber();
                                    movieName = query.mostViewedMovie(input, sortType, filters, numberOfMovies);
                                    fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                            "message","Query result: " + movieName);
                                    arrayResult.add(fS);
                                    break;
                                case "favorite":
                                    query = new Query();
                                    sortType = input.getCommands().get(i).getSortType();
                                    filters = input.getCommands().get(i).getFilters();
                                    numberOfMovies = input.getCommands().get(i).getNumber();
                                    movieName = query.favoriteMovie(input, sortType, filters, numberOfMovies);
                                    fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                            "message","Query result: " + movieName);
                                    arrayResult.add(fS);
                                    break;
                                case "ratings":
                                    query = new Query();
                                    sortType = input.getCommands().get(i).getSortType();
                                    filters = input.getCommands().get(i).getFilters();
                                    numberOfMovies = input.getCommands().get(i).getNumber();
                                    movieName = query.ratingMovie(input, sortType, filters, numberOfMovies);
                                    fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                            "message","Query result: " + movieName);
                                    arrayResult.add(fS);
                                    break;
                            }
                            break;
                        case "shows":
                            criteria = input.getCommands().get(i).getCriteria();
                            switch (criteria){
                                case "longest":
                                    Query query = new Query();
                                    String sortType = input.getCommands().get(i).getSortType();
                                    List<List<String>> filters = input.getCommands().get(i).getFilters();
                                    int numberOfSerials = input.getCommands().get(i).getNumber();
                                    ArrayList<String> serialName = query.longestSerial(input, sortType, filters);
                                    JSONObject fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                            "message","Query result: " + serialName);
                                    arrayResult.add(fS);
                                    break;
                                case "most_viewed":
                                    query = new Query();
                                    sortType = input.getCommands().get(i).getSortType();
                                    filters = input.getCommands().get(i).getFilters();
                                    numberOfSerials = input.getCommands().get(i).getNumber();
                                    serialName = query.mostViewedSerial(input, sortType, filters, numberOfSerials);
                                    fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                            "message","Query result: " + serialName);
                                    arrayResult.add(fS);
                                    break;
                                case "favorite":
                                    query = new Query();
                                    sortType = input.getCommands().get(i).getSortType();
                                    filters = input.getCommands().get(i).getFilters();
                                    numberOfSerials = input.getCommands().get(i).getNumber();
                                    serialName = query.favoriteSerial(input, sortType, filters, numberOfSerials);
                                    fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                            "message","Query result: " + serialName);
                                    arrayResult.add(fS);
                                    break;
                                case "ratings":
                                    query = new Query();
                                    sortType = input.getCommands().get(i).getSortType();
                                    filters = input.getCommands().get(i).getFilters();
                                    numberOfSerials = input.getCommands().get(i).getNumber();
                                    serialName = query.ratingSerial(input, sortType, filters, numberOfSerials);
                                    fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                            "message","Query result: " + serialName);
                                    arrayResult.add(fS);
                                    break;
                            }
                        case "actors":
                            criteria = input.getCommands().get(i).getCriteria();
                            switch (criteria){
                                case "filter_description":
                                    Query query = new Query();
                                    String sortType = input.getCommands().get(i).getSortType();
                                    List<List<String>> filters = input.getCommands().get(i).getFilters();
                                    ArrayList<String> actorsName = new ArrayList<>();
                                    actorsName = query.filterDescription(input, sortType, filters);
                                    JSONObject fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                            "message","Query result: " + actorsName);
                                    arrayResult.add(fS);
                                    break;
                                case "awards":
                                    query = new Query();
                                    sortType = input.getCommands().get(i).getSortType();
                                    filters = input.getCommands().get(i).getFilters();
                                    actorsName = new ArrayList<>();
                                    actorsName = query.awards(input, sortType, filters);
                                    fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                            "message","Query result: " + actorsName);
                                    arrayResult.add(fS);
                                    break;
                                case "average":
                                    query = new Query();
                                    sortType = input.getCommands().get(i).getSortType();
                                    filters = input.getCommands().get(i).getFilters();
                                    int numberOfActors = input.getCommands().get(i).getNumber();
                                    actorsName = new ArrayList<>();
                                    actorsName = query.average(input, sortType, filters, numberOfActors);
                                    fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                            "message","Query result: " + actorsName);
                                    arrayResult.add(fS);
                                    break;
                            }
                        case "users":
                            criteria = input.getCommands().get(i).getCriteria();
                            if ("num_ratings".equals(criteria)) {
                                Query query = new Query();
                                String sortType = input.getCommands().get(i).getSortType();
                                int numberOfUsers = input.getCommands().get(i).getNumber();
                                ArrayList<String> usersName = new ArrayList<>();
                                usersName = query.numberOfRatings(input, numberOfUsers, sortType);
                                JSONObject fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                        "message", "Query result: " + usersName);
                                arrayResult.add(fS);
                            }
                    }
            }
        }
        fileWriter.closeJSON(arrayResult);
    }
}
