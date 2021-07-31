package app.mosquito.appmosquito.appmosquito.Posts.BarMakePost;

public class MakePostStructure {

    private String postName;
    private String postUserName;
    private String postDate;
    private String postText;
    private String postLocalization;
    private int postNumberLikes;
    private int postNumberShares;
    private int postNumberComments;
    private MakePostState postState;
    private MakePostCategorical postCategorical;

    public MakePostStructure() { }

    public String getPostName() { return postName; }
    public String getPostUserName() { return postUserName; }
    public String getPostDate() { return postDate; }
    public String getPostText() { return postText; }
    public String getPostLocalization() { return postLocalization; }
    public int getPostNumberLikes() { return postNumberLikes; }
    public int getPostNumberShares() { return postNumberShares; }
    public int getPostNumberComments() { return postNumberComments; }
    public MakePostState getPostState() { return postState; }
    public MakePostCategorical getPostCategorical() { return postCategorical; }

    public void setPostUserName(String postUserName) { this.postUserName = postUserName; }
    public void setPostName(String postName) { this.postName = postName; }
    public void setPostDate(String postDate) { this.postDate = postDate; }
    public void setPostText(String postText) { this.postText = postText; }
    public void setPostLocalization(String postLocalization) { this.postLocalization = postLocalization; }
    public void setPostNumberLikes(int postNumberLikes) { this.postNumberLikes = postNumberLikes; }
    public void setPostNumberShares(int postNumberShares) { this.postNumberShares = postNumberShares; }
    public void setPostNumberComments(int postNumberComments) { this.postNumberComments = postNumberComments; }
    public void setPostState(MakePostState postState) { this.postState = postState; }
    public void setPostCategorical(MakePostCategorical postCategorical) { this.postCategorical = postCategorical; }

    @Override
    public String toString() {

        return "Curso: " + postName + " Descrição: " +
                postNumberLikes + " Estado: " + postState;
    }
}