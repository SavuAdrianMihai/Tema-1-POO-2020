package fileio;

import java.util.ArrayList;
import java.util.Map;

/**
 * Information about a movie, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class MovieInputData extends ShowInput {
    /**
     * Duration in minutes of a season
     */
    private final int duration;

    private final ArrayList<Double> ratings;

    public MovieInputData() {
        super(null, 0, null, null);
        this.duration = 0;
        this.ratings = new ArrayList<> ();
    }

    public MovieInputData(final String title, final ArrayList<String> cast,
                          final ArrayList<String> genres, final int year,
                          final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
        this.ratings = new ArrayList<> ();
    }

    public int getDuration() {
        return duration;
    }

    public ArrayList<Double> getRatings() {
        return ratings;
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

    public double calculateRating(){
        double sum = 0;
        if (this.getRatings().size() != 0) {
            for (int i = 0; i < this.getRatings().size(); i++)
                sum += this.getRatings().get(i);
            sum = sum / this.getRatings().size();
            return sum;
        }
        return 0;
    }
}
