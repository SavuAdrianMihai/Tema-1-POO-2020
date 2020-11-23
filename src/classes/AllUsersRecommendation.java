package classes;

import fileio.ShowInput;
import fileio.UserInputData;
import fileio.Input;

public interface AllUsersRecommendation {
    String standardRecommendation(Input input, UserInputData user);

    String bestUnseenRecommendation(Input input, UserInputData user);
    }


