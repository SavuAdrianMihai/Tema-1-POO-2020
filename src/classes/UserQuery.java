package classes;

import fileio.Input;

import java.util.ArrayList;

public interface UserQuery {
    public ArrayList<String> numberOfRatings(Input input, int numberOfUsers, String sortType);
}
