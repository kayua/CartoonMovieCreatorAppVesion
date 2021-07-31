package app.mosquito.appmosquito.appmosquito.Posts.Profile;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import app.mosquito.appmosquito.appmosquito.R;


public class ProfileViewAdapter extends BaseAdapter {

    private final List<ProfileStructure> PostList;
    private final Activity act;

    public ProfileViewAdapter(List<ProfileStructure> postInformations, Activity act) {
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

        ProfileStructure curso = PostList.get(position);
        View view = null;

        ProfileCategorical Categorical = curso.getPostCategorical();

        if (Categorical.equals(ProfileCategorical.postText)) {

            view = act.getLayoutInflater().inflate(R.layout.layout_post_page_make, parent, false);

            //TextView postUserName = (TextView) view.findViewById(R.id.dasccdasasdg);
            //TextView postText = (TextView) view.findViewById(R.id.dasdasasdg);
            //TextView postDate = (TextView) view.findViewById(R.id.texdtView2);
            //postUserName.setText(curso.getPostUserName());
            //postText.setText(curso.getPostText());
            //postDate.setText(curso.getPostDate());


        } else if (Categorical.equals(ProfileCategorical.postPhoto)) {

            view = act.getLayoutInflater().inflate(R.layout.layout_posts_image, parent, false);

            TextView postUserName = (TextView) view.findViewById(R.id.textVieasdw);
            TextView postText = (TextView) view.findViewById(R.id.texasdasdtView);
            TextView postDate = (TextView) view.findViewById(R.id.textView2);

            postUserName.setText(curso.getPostUserName());
            postText.setText(curso.getPostText());
            postDate.setText(curso.getPostDate());

        } else if (Categorical.equals(ProfileCategorical.postMovie)) {

            view = act.getLayoutInflater().inflate(R.layout.layout_post_video, parent, false);

            TextView postUserName = (TextView) view.findViewById(R.id.tasdextView);
            TextView postText = (TextView) view.findViewById(R.id.textdddView);
            TextView postDate = (TextView) view.findViewById(R.id.textVsdasiew2);
            postUserName.setText(curso.getPostUserName());
            postText.setText(curso.getPostText());
            postDate.setText(curso.getPostDate());

        }else if (Categorical.equals(ProfileCategorical.postProfile)) {

            view = act.getLayoutInflater().inflate(R.layout.layout_profile, parent, false);

        }
        return view;
    }
}