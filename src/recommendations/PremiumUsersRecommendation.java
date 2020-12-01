package recommendations;

import fileio.Input;
import fileio.UserInputData;

import java.util.ArrayList;

public interface PremiumUsersRecommendation {
    /**
     * Premium user method that returns the first unseen show from database, based on the genre's
     * number of views
     */
    String popular(UserInputData user, Input input);
    /**
     * Premium user method that returns the first unseen show from database, with the most
     * occurrences in other favorite lists
     */
    String favorite(UserInputData user, Input input);
    /**
     * Premium user method that returns a list of unseen shows from database, based on genre
     */
    ArrayList<String> search(UserInputData user, Input input, String genre);
}
