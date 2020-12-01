package fileio;

import entertainment.Season;

import java.util.ArrayList;

/**
 * Information about a tv show, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class SerialInputData extends ShowInput {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private final ArrayList<Season> seasons;

    public SerialInputData(final String title, final ArrayList<String> cast,
                           final ArrayList<String> genres,
                           final int numberOfSeasons,
                           final ArrayList<Season> seasons,
                           final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
    }

    public int getNumberSeason() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    @Override
    public String toString() {
        return "SerialInputData{" + " title= "
                + super.getTitle() + " " + " year= "
                + super.getYear() + " cast {"
                + super.getCast() + " }\n" + " genres {"
                + super.getGenres() + " }\n "
                + " numberSeason= " + numberOfSeasons
                + ", seasons=" + seasons + "\n\n" + '}';
    }

    /**
     * Method that calculates the average rating of a serial
     */
    public double averageSerialRating() {
        double sum = 0.0;
        if (this.numberOfSeasons != 0) {
            for (int i = 0; i < this.numberOfSeasons; i++) {
                double sumSeason = 0.0;
                if (this.seasons.get(i).getRatings().size() != 0) {
                    for (int j = 0; j < this.seasons.get(i).getRatings().size(); j++) {
                        sumSeason += this.seasons.get(i).getRatings().get(j);
                    }
                    sumSeason /= this.seasons.size();
                }
                sum += sumSeason;
            }
            sum /= this.numberOfSeasons;
            return sum;
        }
        return 0.0;
    }

    /**
     * Method that calculates the total duration of a serial
     */
    public int totalDuration() {
        int sum = 0;
        if (this.seasons.size() != 0) {
            for (Season season : this.seasons) {
                sum += season.getDuration();
            }
            sum /= this.seasons.size();
        }
        return sum;
    }
}
