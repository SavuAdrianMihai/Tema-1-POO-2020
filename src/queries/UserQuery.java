package queries;

import fileio.Input;

import java.util.ArrayList;

public interface UserQuery {
    /**
     * Method returns the first N users based on their number of ratings
     */
    ArrayList<String> numberOfRatingsUsers(Input input, int numberOfUsers,
                                      String sortType);
}
