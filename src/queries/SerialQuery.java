package queries;

import fileio.Input;

import java.util.ArrayList;
import java.util.List;

public interface SerialQuery {
    /**
     * method that returns the first N serials with the longest playtime from database
     */
    ArrayList<String> longestSerial(Input input, String sortType, List<List<String>> filters,
                                    int numberOfSerials);
    /**
     * method that returns the first N serials with the most views from database
     */
    ArrayList<String> mostViewedSerial(Input input, String sortType, List<List<String>> filters,
                                       int numberOfSerials);
    /**
     * method that returns the first N serials with the most occurrences
     * in favorite lists from database
     */
    ArrayList<String> favoriteSerial(Input input, String sortType, List<List<String>> filters,
                                     int numberOfSerials);
    /**
     * method that returns the first N serials with the best ratings lists from database
     */
    ArrayList<String> ratingSerial(Input input, String sortType, List<List<String>> filters,
                                   int numberOfSerials);
}
