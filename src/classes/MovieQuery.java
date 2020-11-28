package classes;

import fileio.Input;

import java.util.ArrayList;
import java.util.List;

public interface MovieQuery {
    ArrayList<String> longestMovie(Input input, String sortType , List<List<String>> filters);
    ArrayList<String> mostViewedMovie(Input input, String sortType , List<List<String>> filters, int numberOfMovies);
    ArrayList<String> favoriteMovie(Input input, String sortType , List<List<String>> filters, int numberOfMovies);
    ArrayList<String> ratingMovie(Input input, String sortType , List<List<String>> filters, int numberOfMovies);
}
