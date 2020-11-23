package classes;

import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import fileio.Input;

public class Recommendation implements AllUsersRecommendation, PremiumUsersRecommendation {

    @Override
    public String standardRecommendation(Input input, UserInputData user) {
        for (int i = 0; i < input.getMovies().size(); i++) {
            MovieInputData movie = input.getMovies().get(i);
            if (!user.getHistory().containsKey(movie.getTitle()))
                return movie.getTitle();
        }

        for (int i = 0; i < input.getSerials().size(); i++) {
            SerialInputData serial = input.getSerials().get(i);
            if (!user.getHistory().containsKey(serial.getTitle()))
                return serial.getTitle();
        }
        return "could not find recommendation";
    }

    @Override
    public String bestUnseenRecommendation(Input input, UserInputData user) {
        double ratingMovie = 0, ratingSerial = 0;
        int indexMovie = 0, indexSerial = 0, indexFirstMovie = 0, indexFirstSerial = 0;
        int movieFound = 0, serialFound = 0;
        for (int i = 0; i < input.getMovies().size(); i++) {
            MovieInputData movie = input.getMovies().get(i);
            if (!user.getHistory().containsKey(movie.getTitle())) {
                if (movieFound == 0)
                    indexFirstMovie = i;
                if (movie.averageMovieRating() > ratingMovie) {
                    ratingMovie = movie.averageMovieRating();
                    indexMovie = i;
                }
                movieFound = 1;
            }
        }
        for (int i = 0; i < input.getSerials().size(); i++){
            SerialInputData serial = input.getSerials().get(i);
            if (!user.getHistory().containsKey(serial.getTitle())) {
                if (serialFound == 0)
                    indexFirstSerial = i;
                if (serial.averageSerialRating() > ratingSerial) {
                    ratingSerial = serial.averageSerialRating();
                    indexSerial = i;
                }
                serialFound = 1;
            }
        }

        if ((ratingMovie == 0) && (movieFound != 0))
            return input.getMovies().get(indexFirstMovie).getTitle();

        if ((movieFound == 0) && (serialFound != 0) && (ratingSerial == 0))
            return input.getSerials().get(indexFirstSerial).getTitle();

        if (ratingMovie >= ratingSerial)
            return input.getMovies().get(indexMovie).getTitle();
            else return input.getSerials().get(indexSerial ).getTitle();
    }
}
