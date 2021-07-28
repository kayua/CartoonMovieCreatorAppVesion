package app.mosquito.appmosquito.appmosquito.Posts;

public class PostStructure {

    private String nome;
    private String descricao;
    private EstadoAtual estado;
    private CategoricalPosts categoria;

    public PostStructure(String nome, String descricao, EstadoAtual estado, CategoricalPosts categoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.estado = estado;
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public EstadoAtual getEstado() { return estado; }

    public void setEstado(EstadoAtual estado) { this.estado = estado; }

    public CategoricalPosts getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoricalPosts categoria) { this.categoria = categoria; }

    @Override
    public String toString() {

        return "Curso: " + nome + " Descrição: " +
                descricao + " Estado: " + estado;
    }
}