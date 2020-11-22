package main;

import checker.Checkstyle;
import checker.Checker;
import com.sun.net.httpserver.Authenticator;
import common.Constants;
import fileio.ActionInputData;
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

        //fileWriter.writeFile(1, "Success", "aaa");
        //TODO add here the entry point to your implementation

        int res = 1;
        for (int i = 0; i < input.getCommands().size(); i++) {
            String actionType = input.getCommands().get(i).getActionType();
            switch (actionType) {
                case "command":
                    String type = input.getCommands().get(i).getType();
                    switch (type) {
                        case "view":
                            String messageView = "success";
                            String theShow = input.getCommands().get(i).getTitle();
                            JSONObject fS = fileWriter.writeFile(input.getCommands().get(0).getActionId(),
                                    "message", messageView + " -> " + theShow
                                            + " was viewed with total views of " + res);
                            arrayResult.add(fS);
                            res++;
                            break;

                        case "favorite":
                            String outcomeAction = new String();
                            messageView = new String();
                            String username = input.getCommands().get(i).getUsername();
                            for (int j = 0; j < input.getUsers().size(); j++) {
                                if (username.equals(input.getUsers().get(j).getUsername())) {
                                    if (input.getUsers().get(j).getHistory().
                                            containsKey(input.getCommands().get(j).getTitle())) {
                                        messageView = "success";
                                        outcomeAction = " was added as favourite";
                                    } else {
                                        messageView = "error";
                                        outcomeAction = " is not seen";
                                    }
                                    if (input.getUsers().get(j).getFavoriteMovies().
                                            contains(input.getCommands().get(j).getTitle())) {
                                        messageView = "error";
                                        outcomeAction = " is already in favourite list";
                                    }
                                }
                            }
                            theShow = input.getCommands().get(i).getTitle();
                            fS = fileWriter.writeFile(input.getCommands().get(0).getActionId(),
                                    "message", messageView + " -> "
                                            + theShow + outcomeAction);
                            arrayResult.add(fS);
                            res++;
                            break;
                        case "rating":
                            messageView = "success";
                            theShow = input.getCommands().get(i).getTitle();
                            double rating = input.getCommands().get(i).getGrade();
                            username = input.getCommands().get(i).getUsername();
                            fS = fileWriter.writeFile(input.getCommands().get(0).getActionId(),
                                    "message", messageView + " -> " + theShow
                                            + " was rated with " + rating + " by " + username);
                            arrayResult.add(fS);

                            res++;
                            break;


                        default:
                            fS = fileWriter.writeFile(0, "message", "fail");
                            arrayResult.add(fS);
                    }
            }
        }
        fileWriter.closeJSON(arrayResult);
    }
}
