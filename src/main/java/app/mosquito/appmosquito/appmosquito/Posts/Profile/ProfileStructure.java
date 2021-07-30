package app.mosquito.appmosquito.appmosquito.Posts.Profile;

public class ProfileStructure {

    private String postName;
    private String postUserName;
    private String postDate;
    private String postText;
    private String postLocalization;
    private int postNumberLikes;
    private int postNumberShares;
    private int postNumberComments;
    private ProfileState postState;
    private ProfileCategorical postCategorical;

    public ProfileStructure() { }

    public String getPostName() { return postName; }
    public String getPostUserName() { return postUserName; }
    public String getPostDate() { return postDate; }
    public String getPostText() { return postText; }
    public String getPostLocalization() { return postLocalization; }
    public int getPostNumberLikes() { return postNumberLikes; }
    public int getPostNumberShares() { return postNumberShares; }
    public int getPostNumberComments() { return postNumberComments; }
    public ProfileState getPostState() { return postState; }
    public ProfileCategorical getPostCategorical() { return postCategorical; }

    public void setPostUserName(String postUserName) { this.postUserName = postUserName; }
    public void setPostName(String postName) { this.postName = postName; }
    public void setPostDate(String postDate) { this.postDate = postDate; }
    public void setPostText(String postText) { this.postText = postText; }
    public void setPostLocalization(String postLocalization) { this.postLocalization = postLocalization; }
    public void setPostNumberLikes(int postNumberLikes) { this.postNumberLikes = postNumberLikes; }
    public void setPostNumberShares(int postNumberShares) { this.postNumberShares = postNumberShares; }
    public void setPostNumberComments(int postNumberComments) { this.postNumberComments = postNumberComments; }
    public void setPostState(ProfileState postState) { this.postState = postState; }
    public void setPostCategorical(ProfileCategorical postCategorical) { this.postCategorical = postCategorical; }

    @Override
    public String toString() {

        return "Curso: " + postName + " Descrição: " +
                postNumberLikes + " Estado: " + postState;
    }
}