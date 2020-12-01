package queries;

import fileio.Input;

import java.util.ArrayList;
import java.util.List;

public interface ActorQuery {
    /**
     * Method that returns a list of actors based on filters words
     */
    ArrayList<String> filterDescriptionActors(Input input, String sortType,
                                        List<List<String>> filters);

    /**
     * Method that returns a list of actors based on filters awards
     */
    ArrayList<String> awardsActors(Input input, String sortType,
                             List<List<String>> filters);

    /**
     * Method that returns a list of actors based on the rated shows they played in
     */
    ArrayList<String> averageActors(Input input, String sortType,
                              List<List<String>> filters, int numberOfActors);
}

