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

    private EstadoAtual state;
    private CategoricalPosts categorical;

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

    public int getState(){return postNumberShares;}

    public int getCommentsPost(){return postNumberComments;}





    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostNumberLikes() {
        return postNumberLikes;
    }

    public void setPostNumberLikes(String postNumberLikes) { this.postNumberLikes = postNumberLikes; }

    public EstadoAtual getState() { return state; }

    public void setState(EstadoAtual state) { this.state = state; }

    public CategoricalPosts getCategorical() {
        return categorical;
    }

    public void setCategorical(CategoricalPosts categorical) { this.categorical = categorical; }

    @Override
    public String toString() {

        return "Curso: " + postName + " Descrição: " +
                postNumberLikes + " Estado: " + state;
    }
}