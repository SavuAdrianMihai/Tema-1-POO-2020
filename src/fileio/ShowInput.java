package fileio;

import java.util.ArrayList;

/**
 * General information about show (video), retrieved from parsing the input
 * test files
 * <p>
 * DO NOT MODIFY
 */
public abstract class ShowInput {
    /**
     * Show's title
     */
    private final String title;
    /**
     * The year the show was released
     */
    private final int year;
    /**
     * Show casting
     */
    private final ArrayList<String> cast;
    /**
     * Show genres
     */
    private final ArrayList<String> genres;

    private int views;

    private int numberOfFavorites;

    public ShowInput(final String title, final int year,
                     final ArrayList<String> cast,
                     final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
        this.views = 0;
        this.numberOfFavorites = 0;
    }

    public final String getTitle() {
        return title;
    }

    public final int getYear() {
        return year;
    }

    public final ArrayList<String> getCast() {
        return cast;
    }

    public final ArrayList<String> getGenres() {
        return genres;
    }

    public final int getViews() {
        return views;
    }

    public final void setViews(int views) {
        this.views = views;
    }

    public final int getNumberOfFavorites() {
        return numberOfFavorites;
    }

    public final void setNumberOfFavorites(int numberOfFavorites) {
        this.numberOfFavorites = numberOfFavorites;
    }

}
