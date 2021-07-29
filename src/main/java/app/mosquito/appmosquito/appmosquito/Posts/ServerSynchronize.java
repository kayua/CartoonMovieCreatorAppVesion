package app.mosquito.appmosquito.appmosquito.Posts;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.mosquito.appmosquito.appmosquito.R;

public class ServerSynchronize extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_cursos);

        ListView lista = (ListView) findViewById(R.id.lista);
        List<PostStructure> cursos = todosOsCursos();
        ViewAdapter adapter = new ViewAdapter(cursos, this);
        lista.setAdapter(adapter);
    }

    private List<PostStructure> todosOsCursos() {
        return new ArrayList<>(Arrays.asList(
                new PostStructure("Java", "    Olá mundo. kkkkkk", EstadoAtual.FINALIZADO, CategoricalPosts.postMovie),
                new PostStructure("HTML e CSS", "    HTML 5 e suas novidades", EstadoAtual.FAZENDO, CategoricalPosts.postText),
                new PostStructure("Java", "básico de Java", EstadoAtual.FINALIZADO, CategoricalPosts.postMovie),
                new PostStructure("HTML e CSS", "HTML 5 e suas novidades", EstadoAtual.FAZENDO, CategoricalPosts.postText),
                new PostStructure("Java", "  básico de Java", EstadoAtual.FINALIZADO, CategoricalPosts.postMovie),
                new PostStructure("HTML e CSS", "  HTML 5 e suas novidades", EstadoAtual.FAZENDO, CategoricalPosts.postText),
                new PostStructure("Java", "  básico de Java", EstadoAtual.FINALIZADO, CategoricalPosts.postMovie),
                new PostStructure("HTML e CSS", "  HTML 5 e suas novidades", EstadoAtual.FAZENDO, CategoricalPosts.postText),
                new PostStructure("Java", "  básico de Java", EstadoAtual.FINALIZADO, CategoricalPosts.postMovie),
                new PostStructure("HTML e CSS", "  HTML 5 e suas novidades", EstadoAtual.FAZENDO, CategoricalPosts.postText),
                new PostStructure("Java", "  básico de Java", EstadoAtual.FINALIZADO, CategoricalPosts.postMovie),
                new PostStructure("HTML e CSS", "  HTML 5 e suas novidades", EstadoAtual.FAZENDO, CategoricalPosts.postText),
                new PostStructure("Android", "  boas de práticas", EstadoAtual.FINALIZADO, CategoricalPosts.postPhoto)));
    }
}