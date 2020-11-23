package main;

import checker.Checkstyle;
import checker.Checker;
import classes.Command;
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
                                    command.view(input.getUsers().get(j), title);
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
                            String outcomeAction = new String();
                            messageView = new String();
                            command = new Command();
                            username = input.getCommands().get(i).getUsername();
                            title = input.getCommands().get(i).getTitle();
                            for (int j = 0; j < input.getUsers().size(); j++) {
                                if (username.equals(input.getUsers().get(j).getUsername())) {
                                    if (input.getUsers().get(j).getFavoriteMovies().
                                            contains(title)) {
                                        messageView = "error";
                                        outcomeAction = " is already in favourite list";
                                        break;
                                    }
                                    if (input.getUsers().get(j).getHistory().
                                            containsKey(title)) {
                                        command.favorite(input.getUsers().get(j), title);
                                        if (input.getUsers().get(j).getFavoriteMovies().
                                                contains(title)) {
                                            messageView = "success";
                                            outcomeAction = " was added as favourite";
                                        }
                                    } else {
                                        messageView = "error";
                                        outcomeAction = " is not seen";
                                    }
                                }
                            }
                            title = input.getCommands().get(i).getTitle();
                            fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                    "message", messageView + " -> "
                                            + title + outcomeAction);
                            arrayResult.add(fS);
                            break;
                        case "rating":
                            int viewed = 1;
                            messageView = "error";
                            title = input.getCommands().get(i).getTitle();
                            double rating = input.getCommands().get(i).getGrade();
                            username = input.getCommands().get(i).getUsername();
                            int seasonNumber = input.getCommands().get(i).getSeasonNumber();
                            command = new Command();
                            for (int j = 0; j < input.getUsers().size(); j++) {
                                if (username.equals(input.getUsers().get(j).getUsername())) {
                                    if (input.getUsers().get(j).getHistory().containsKey(title)){
                                        if (seasonNumber == 0) {
                                            for (int k = 0; k < input.getMovies().size(); k++) {
                                                if (title.equals(input.getMovies().get(k).getTitle()))
                                                    command.ratingMovie(input.getUsers().get(j),
                                                            input.getMovies().get(k), title, rating);
                                                if (input.getMovies().get(k).getRatings().contains(rating))
                                                    messageView = "success";
                                            }
                                        } else {
                                            for (int k = 0; k < input.getSerials().size(); k++) {
                                                if (title.equals(input.getSerials().get(k).getTitle())) {
                                                    command.ratingSerial(input.getUsers().get(j),
                                                            input.getSerials().get(k),
                                                            title, rating, seasonNumber);
                                                    if (input.getSerials().get(k).getSeasons().
                                                            get(seasonNumber - 1).getRatings().contains(rating)) {
                                                        messageView = "success";
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    else viewed = 0;
                                }
                            }
                            if (viewed == 1) {
                                fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                        "message", messageView + " -> " + title
                                                + " was rated with " + rating + " by " + username);
                                arrayResult.add(fS);
                                break;
                            }

                            fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                    "message", messageView + " -> " + title
                                            + " is not seen");
                            arrayResult.add(fS);
                            break;

                        default:
                            fS = fileWriter.writeFile(0, "message", "fail");
                            arrayResult.add(fS);
                    }
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
                            JSONObject fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                    "message", "StandardRecommendation result: " + showName);
                            arrayResult.add(fS);
                            break;
                       /*
                       default:
                            fS = fileWriter.writeFile(0, "message", "fail");
                            arrayResult.add(fS);
                        */
                        case "best_unseen":
                            recommendation = new Recommendation();
                            username = input.getCommands().get(i).getUsername();
                            showName = "";
                            for (int j = 0; j < input.getUsers().size(); j++) {
                                if (username.equals(input.getUsers().get(j).getUsername())) {
                                    showName = recommendation.bestUnseenRecommendation
                                            (input, input.getUsers().get(j));
                                }
                            }
                            fS = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                    "message", "BestRatedUnseenRecommendation result: " + showName);
                            arrayResult.add(fS);
                            break;
                    }
            }
        }
        fileWriter.closeJSON(arrayResult);
    }
}
