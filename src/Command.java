/*public class Command extends Action {
    private String type;
    private String user;
    private String title;

    public Command(int actionId, String actionType, String type, String user, String title) {
        super(actionId, actionType);
        setType(type);
        setUser(user);
        setTitle(title);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
/*
    public void favorite(String user, String title){
        if(User.getUsername().contains(user))
            User.favoriteVideos.add(title);
    }

    public void view(Video video){

    }

} */