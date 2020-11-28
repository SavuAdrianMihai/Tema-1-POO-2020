package classes;

import fileio.Input;
import fileio.UserInputData;

import java.util.Map;

public class Command{
        public String favorite(UserInputData user, String title, Input input){
            String result;
            if (user.getFavoriteMovies().contains(title)) {
                result = " is already in favourite list";
                return result;
            }
            if (user.getHistory().containsKey(title)) {
                user.getFavoriteMovies().add(title);
                for(int i = 0; i < input.getMovies().size(); i++)
                    if(title.equals(input.getMovies().get(i).getTitle()))
                        input.getMovies().get(i).setNumberOfFavorites(input.
                                getMovies().get(i).getNumberOfFavorites() + 1);
                for(int i = 0; i < input.getSerials().size(); i++)
                    if(title.equals(input.getSerials().get(i).getTitle()))
                        input.getSerials().get(i).setViews(input.
                                getSerials().get(i).getNumberOfFavorites() + 1);
                result = " was added as favourite";
                return result;
            }
            result = " is not seen";
            return result;
        }

    public void view(UserInputData user, String title, Input input){
        if (user.getHistory().containsKey(title)) {
            user.getHistory().replace(title,
                    user.getHistory().get(title),
                    user.getHistory().get(title) + 1);
            for(int i = 0; i < input.getMovies().size(); i++)
                if(title.equals(input.getMovies().get(i).getTitle()))
                    input.getMovies().get(i).setViews(input.getMovies().get(i).getViews() + 1);
            for(int i = 0; i < input.getSerials().size(); i++)
                if(title.equals(input.getSerials().get(i).getTitle()))
                    input.getSerials().get(i).setViews(input.getSerials().get(i).getViews() + 1);
        }
        else {
            user.getHistory().put(title, 1);
            for(int i = 0; i < input.getMovies().size(); i++)
                if(title.equals(input.getMovies().get(i).getTitle()))
                    input.getMovies().get(i).setViews(input.getMovies().get(i).getViews() + 1);
            for(int i = 0; i < input.getSerials().size(); i++)
                if(title.equals(input.getSerials().get(i).getTitle()))
                    input.getSerials().get(i).setViews(input.getSerials().get(i).getViews() + 1);
        }

    }

    public String rating(UserInputData user, String title, Input input, double grade, int seasonNumber) {

            for (int i = 0; i < user.getRatings().size(); i++)
                for (Map.Entry<String, Integer> mapElement : user.getRatingData().entrySet())
                    if(mapElement.getKey().equals(title) && mapElement.getValue().equals(seasonNumber))
                        return "error: already rated";
            if (seasonNumber == 0) {
                for (int i = 0; i < input.getMovies().size(); i++)
                    if (user.getHistory().containsKey(title)){
                        if (title.equals(input.getMovies().get(i).getTitle())) {
                            input.getMovies().get(i).getRatings().add(grade);
                            user.getRatings().add(grade);
                            user.getRatingData().put(title, seasonNumber);
                            return "success";
                        }
                    }
            } else {
                for (int i = 0; i < input.getSerials().size(); i++)
                    if (user.getHistory().containsKey(title)) {
                        if (title.equals(input.getSerials().get(i).getTitle())) {
                            input.getSerials().get(i).getSeasons().get(seasonNumber - 1).
                                    getRatings().add(grade);
                            user.getRatings().add(grade);
                            user.getRatingData().put(title, seasonNumber);
                            return "success";
                        }
                    }
            }
            return "error: is not seen";
    }
}