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

    public String getPostName() {
        return postName;
    }

    public String getPostUserName(){return postUserName;}

    public String getPostDate(){return postDate;}

    public String getPostLocalization(){return postLocalization;}

    public int getNumberSharesPost(){return postNumberLikes;}

    public int getPostNumberShares(){return postNumberShares;}

    public int getCommentsPost(){return postNumberComments;}

    public int getPostState(){return postNumberShares;}

    public int getCommentsPost(){return postNumberComments;}





    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostNumberLikes() {
        return postNumberLikes;
    }

    public void setPostNumberLikes(String postNumberLikes) { this.postNumberLikes = postNumberLikes; }

    public EstadoAtual getState() { return postState; }

    public void setPostState(EstadoAtual postState) { this.postState = postState; }

    public CategoricalPosts getPostCategorical() {
        return postCategorical;
    }

    public void setPostCategorical(CategoricalPosts postCategorical) { this.postCategorical = postCategorical; }

    @Override
    public String toString() {

        return "Curso: " + postName + " Descrição: " +
                postNumberLikes + " Estado: " + postState;
    }
}