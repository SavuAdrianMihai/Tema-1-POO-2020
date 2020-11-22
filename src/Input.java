import java.util.List;

public class Input {
    /**
     * List of actors
     */
    private final List<Actor> actors;
    /**
     * List of users
     */
    private final List<User> users;
    /**
     * List of commands
     */
    private final List<Action> commands;
    /**
     * List of movies
     */
    private final List<Movie> movies;
    /**
     * List of serials aka tv shows
     */
    private final List<Serial> serialsData;

    public Input() {
        this.actors = null;
        this.users = null;
        this.commands = null;
        this.movies = null;
        this.serialsData = null;
    }

    public Input(final List<Actor> actors, final List<User> users,
                 final List<Action> commands,
                 final List<Movie> movies,
                 final List<Serial> serials) {
        this.actors = actors;
        this.users = users;
        this.commands = commands;
        this.movies = movies;
        this.serialsData = serials;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Action> getCommands() {
        return commands;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public List<Serial> getSerials() {
        return serialsData;
    }
}
