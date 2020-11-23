package classes;

import fileio.UserInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;

public class Command{
        public void favorite(UserInputData user, String title){
        user.getFavoriteMovies().add(title);
    }

    public void view(UserInputData user, String title){
        if (user.getHistory().containsKey(title))
            user.getHistory().replace(title,
                    user.getHistory().get(title),
                    user.getHistory().get(title) + 1);
        else
            user.getHistory().put(title, 1);
    }

    public void ratingSerial(UserInputData user, SerialInputData serial,
                       String title, double grade, int seasonNumber) {
        if (user.getHistory().containsKey(title)) {
            serial.getSeasons().get(seasonNumber - 1).
                    getRatings().add(grade);
            user.getRatings().add(grade);
        }
    }

    public void ratingMovie(UserInputData user, MovieInputData movie,
                String title, double grade){
        if (user.getHistory().containsKey(title)){
            movie.getRatings().add(grade);
            user.getRatings().add(grade);
        }
    }

}