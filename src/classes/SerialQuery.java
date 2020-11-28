package classes;

import fileio.Input;

import java.util.ArrayList;
import java.util.List;

public interface SerialQuery {
    ArrayList<String> longestSerial(Input input, String sortType , List<List<String>> filters);
    ArrayList<String> mostViewedSerial(Input input, String sortType , List<List<String>> filters, int numberOfSerials);
    ArrayList<String> favoriteSerial(Input input, String sortType , List<List<String>> filters, int numberOfSerials);
    ArrayList<String> ratingSerial(Input input, String sortType , List<List<String>> filters, int numberOfSerials);
}
