package recommendations;

import fileio.Input;
import fileio.UserInputData;

public interface AllUsersRecommendation {
    /**
     * Basic user method that returns the first unseen show from database
     */
    String standardRecommendation(Input input, UserInputData user);
    /**
     * Basic user method that returns the first unseen show from database based on ratings
     */
    String bestUnseenRecommendation(Input input, UserInputData user);
}


