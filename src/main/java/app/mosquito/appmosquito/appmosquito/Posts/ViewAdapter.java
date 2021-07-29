package app.mosquito.appmosquito.appmosquito.Posts;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import app.mosquito.appmosquito.appmosquito.R;



public class ViewAdapter extends BaseAdapter {

    private final List<PostStructure> cursos;
    private final Activity act;

    public ViewAdapter(List<PostStructure> cursos, Activity act) {
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


        PostStructure curso = cursos.get(position);

        View view = null;

        CategoricalPosts categoria = curso.getCategorical();

        if (categoria.equals(CategoricalPosts.JAVA)) {

            view = act.getLayoutInflater().inflate(R.layout.layout_post_multi_images, parent, false);
            //TextView nome = (TextView)
            //        view.findViewById(R.id.lista_curso_personalizada_nome);
            //TextView descricao = (TextView)
            //        view.findViewById(R.id.lista_curso_personalizada_descricao);
            //ImageView imagem = (ImageView)
            //        view.findViewById(R.id.lista_curso_personalizada_imagem);

            //imagem.setImageResource(R.drawable.ponte_arrabid);
            //nome.setText(curso.getNome());
            //descricao.setText(curso.getDescricao());

        } else if (categoria.equals(CategoricalPosts.ANDROID)) {
            view = act.getLayoutInflater().inflate(R.layout.layout_post_image, parent, false);
            //TextView nome = (TextView)
            //        view.findViewById(R.id.lista_curso_personalizada_nome);
            // TextView descricao = (TextView)
            //        view.findViewById(R.id.lista_curso_personalizada_descricao);
            // ImageView imagem = (ImageView)
            //         view.findViewById(R.id.lista_curso_personalizada_imagem);
            // imagem.setImageResource(R.drawable.ponte_arrabid);
            // nome.setText(curso.getNome());
            // descricao.setText(curso.getDescricao());
        }

        return view;
    }
}