package app.mosquito.appmosquito.appmosquito.ui.Message;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.mosquito.appmosquito.appmosquito.R;


/**
 * Created by alex on 02/07/17.
 */

public class AdapterCursosPersonalizado extends BaseAdapter {

    private final List<Curso> cursos;
    private final Activity act;

    public AdapterCursosPersonalizado(List<Curso> cursos, Activity act) {
        this.cursos = cursos;
        this.act = act;
    }

    @Override
    public int getCount() {
        return cursos.size();
    }

    @Override
    public Object getItem(int position) {
        return cursos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        Curso curso = cursos.get(position);

        View view = null;

        Categoria categoria = curso.getCategoria();

        if (categoria.equals(Categoria.JAVA)) {
            view = act.getLayoutInflater().inflate(R.layout.lista_curso_personalizada, parent, false);
            TextView nome = (TextView)
                    view.findViewById(R.id.lista_curso_personalizada_nome);
            TextView descricao = (TextView)
                    view.findViewById(R.id.lista_curso_personalizada_descricao);
            ImageView imagem = (ImageView)
                    view.findViewById(R.id.lista_curso_personalizada_imagem);
            imagem.setImageResource(R.drawable.icon_casa_verde);
            nome.setText(curso.getNome());
            descricao.setText(curso.getDescricao());
        } else if (categoria.equals(Categoria.ANDROID)) {
            view = act.getLayoutInflater().inflate(R.layout.list_msg, parent, false);
            TextView nome = (TextView)
                    view.findViewById(R.id.lista_curso_personalizada_nome);
            TextView descricao = (TextView)
                    view.findViewById(R.id.lista_curso_personalizada_descricao);
            ImageView imagem = (ImageView)
                    view.findViewById(R.id.lista_curso_personalizada_imagem);
            imagem.setImageResource(R.drawable.icon_casa_verde);
            nome.setText(curso.getNome());
        } else if (categoria.equals(Categoria.HTML)) {
            view = act.getLayoutInflater().inflate(R.layout.lista_curso_personalizada, parent, false);
            TextView nome = (TextView)
                    view.findViewById(R.id.lista_curso_personalizada_nome);
            TextView descricao = (TextView)
                    view.findViewById(R.id.lista_curso_personalizada_descricao);
            ImageView imagem = (ImageView)
                    view.findViewById(R.id.lista_curso_personalizada_imagem);
            imagem.setImageResource(R.drawable.icon_casa_verde);
            nome.setText(curso.getNome());
        }

        return view;
    }
}