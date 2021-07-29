package app.mosquito.appmosquito.appmosquito.Posts;

public class PostStructure {

    private String postName;
    private String postUserName;
    private String postDate;
    private String postText;
    private String postLocalization;

    private int postNumberLikes;
    private int postNumberShares;
    private int postNumberComments;

    private EstadoAtual postState;
    private CategoricalPosts postCategorical;

    public PostStructure() { }

    public String getPostName() { return postName; }

    public void setPostName(String postName) { this.postName = postName; }

    public String getPostUserName() { return postUserName; }

    public void setPostUserName(String postUserName) { this.postUserName = postUserName; }

    public String getPostDate() { return postDate; }

    public void setPostDate(String postDate) { this.postDate = postDate; }

    public String getPostText() { return postText; }

    public void setPostText(String postText) { this.postText = postText; }

    public String getPostLocalization() { return postLocalization; }

    public void setPostLocalization(String postLocalization) { this.postLocalization = postLocalization; }

    public int getPostNumberLikes() { return postNumberLikes; }

    public void setPostNumberLikes(int postNumberLikes) { this.postNumberLikes = postNumberLikes; }

    public int getPostNumberShares() { return postNumberShares; }

    public void setPostNumberShares(int postNumberShares) { this.postNumberShares = postNumberShares; }

    public int getPostNumberComments() { return postNumberComments; }

    public void setPostNumberComments(int postNumberComments) { this.postNumberComments = postNumberComments; }

    public EstadoAtual getPostState() { return postState; }

    public void setPostState(EstadoAtual postState) { this.postState = postState; }

    public CategoricalPosts getPostCategorical() { return postCategorical; }

    public void setPostCategorical(CategoricalPosts postCategorical) { this.postCategorical = postCategorical; }

    @Override
    public String toString() {

        return "Curso: " + postName + " Descrição: " +
                postNumberLikes + " Estado: " + postState;
    }
}