package classes;

import java.util.ArrayList;

public class Movie extends Show {
    /**
     * Duration in minutes of a season
     */
    private int duration;

    public Movie(String title, ArrayList<String> cast,
                          ArrayList<String> genres, int year,
                          int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "MovieInputData{" + "title= "
                + super.getTitle() + "year= "
                + super.getYear() + "duration= "
                + duration + "cast {"
                + super.getCast() + " }\n"
                + "genres {" + super.getGenres() + " }\n ";
    }
}
