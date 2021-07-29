package app.mosquito.appmosquito.appmosquito.Posts;

public class PostStructure {

    private String nomePost;
    private String usernamePost;
    private String datePost;
    private String textPost;

    private int numberShares;
    private int numberLikes;
    private int numberComments;

    private EstadoAtual estado;
    private CategoricalPosts categoria;

    public PostStructure() { }

    public String getNomePost() {
        return nomePost;
    }

    public void setNomePost(String nomePost) {
        this.nomePost = nomePost;
    }

    public String getNumberShares() {
        return numberShares;
    }

    public void setNumberShares(String numberShares) { this.numberShares = numberShares; }

    public EstadoAtual getEstado() { return estado; }

    public void setEstado(EstadoAtual estado) { this.estado = estado; }

    public CategoricalPosts getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoricalPosts categoria) { this.categoria = categoria; }

    @Override
    public String toString() {

        return "Curso: " + nomePost + " Descrição: " +
                numberShares + " Estado: " + estado;
    }
}