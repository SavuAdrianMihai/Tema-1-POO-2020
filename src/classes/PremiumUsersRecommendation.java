package classes;

import entertainment.Genre;
import fileio.Input;
import fileio.UserInputData;

import java.util.ArrayList;

public interface PremiumUsersRecommendation {
    String popular(UserInputData user, Input input);
    String favorite(UserInputData user, Input input);
    ArrayList<String> search(UserInputData user, Input input, String genre);
}
