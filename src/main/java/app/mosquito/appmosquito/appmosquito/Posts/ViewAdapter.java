package app.mosquito.appmosquito.appmosquito.Posts;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import app.mosquito.appmosquito.appmosquito.R;


public class ViewAdapter extends BaseAdapter {

    private final List<PostStructure> PostList;
    private final Activity act;

    public ViewAdapter(List<PostStructure> postInformations, Activity act) {
        this.PostList = postInformations;
        this.act = act;
    }

    @Override
    public int getCount() {
        return PostList.size();
    }

    @Override
    public Object getItem(int position) {
        return PostList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PostStructure curso = PostList.get(position);
        View view = null;

        CategoricalPosts Categorical = curso.getPostCategorical();

        if (Categorical.equals(CategoricalPosts.postText)) {

            view = act.getLayoutInflater().inflate(R.layout.layout_post_text, parent, false);

            TextView postUserName = (TextView) view.findViewById(R.id.tasdextView);
            TextView postText = (TextView) view.findViewById(R.id.textdddView);
            TextView postDate = (TextView) view.findViewById(R.id.texdtView2);

            //descricao.setText(curso.getDescricao());

        } else if (Categorical.equals(CategoricalPosts.postPhoto)) {
            view = act.getLayoutInflater().inflate(R.layout.layout_posts_image, parent, false);
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