package queries;

import fileio.Input;

import java.util.ArrayList;
import java.util.List;

public interface MovieQuery {
    /**
     * method that returns the first N movies with the longest playtime from database
     */
    ArrayList<String> longestMovie(Input input, String sortType, List<List<String>> filters,
                                   int numberOfMovies);
    /**
     * method that returns the first N movies with the most views from database
     */
    ArrayList<String> mostViewedMovie(Input input, String sortType, List<List<String>> filters,
                                      int numberOfMovies);
    /**
     * method that returns the first N movies with the most occurrences
     * in favorite lists from database
     */
    ArrayList<String> favoriteMovie(Input input, String sortType, List<List<String>> filters,
                                    int numberOfMovies);
    /**
     * method that returns the first N movies with the best ratings lists from database
     */
    ArrayList<String> ratingMovie(Input input, String sortType, List<List<String>> filters,
                                  int numberOfMovies);
}
