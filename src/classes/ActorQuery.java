package classes;

import fileio.Input;

import java.util.ArrayList;
import java.util.List;

public interface ActorQuery {
    ArrayList<String> filterDescription(Input input, String sortType, List<List<String>> filters);
    ArrayList<String> awards(Input input, String sortType, List<List<String>> filters);
    ArrayList<String> average(Input input, String sortType, List<List<String>> filters, int numberOfActors);
}

