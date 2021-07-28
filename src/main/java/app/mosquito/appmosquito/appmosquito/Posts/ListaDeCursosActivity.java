package app.mosquito.appmosquito.appmosquito.Posts;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.mosquito.appmosquito.appmosquito.R;

public class ListaDeCursosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_cursos);

        ListView lista = (ListView) findViewById(R.id.lista);
        List<Curso> cursos = todosOsCursos();
        AdapterCursosPersonalizado adapter = new AdapterCursosPersonalizado(cursos, this);
        lista.setAdapter(adapter);
    }

    private List<Curso> todosOsCursos() {
        return new ArrayList<>(Arrays.asList(
                new Curso("Java", "    Olá mundo. kkkkkk", EstadoAtual.FINALIZADO, Categoria.JAVA),
                new Curso("HTML e CSS", "    HTML 5 e suas novidades", EstadoAtual.FAZENDO, Categoria.HTML),
                new Curso("Java", "básico de Java", EstadoAtual.FINALIZADO, Categoria.JAVA),
                new Curso("HTML e CSS", "HTML 5 e suas novidades", EstadoAtual.FAZENDO, Categoria.HTML),
                new Curso("Java", "  básico de Java", EstadoAtual.FINALIZADO, Categoria.JAVA),
                new Curso("HTML e CSS", "  HTML 5 e suas novidades", EstadoAtual.FAZENDO, Categoria.HTML),
                new Curso("Java", "  básico de Java", EstadoAtual.FINALIZADO, Categoria.JAVA),
                new Curso("HTML e CSS", "  HTML 5 e suas novidades", EstadoAtual.FAZENDO, Categoria.HTML),
                new Curso("Java", "  básico de Java", EstadoAtual.FINALIZADO, Categoria.JAVA),
                new Curso("HTML e CSS", "  HTML 5 e suas novidades", EstadoAtual.FAZENDO, Categoria.HTML),
                new Curso("Java", "  básico de Java", EstadoAtual.FINALIZADO, Categoria.JAVA),
                new Curso("HTML e CSS", "  HTML 5 e suas novidades", EstadoAtual.FAZENDO, Categoria.HTML),
                new Curso("Android", "  boas de práticas", EstadoAtual.FINALIZADO, Categoria.ANDROID)));
    }
}