package app.mosquito.appmosquito.appmosquito.Posts.BarMakePost;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.mosquito.appmosquito.appmosquito.R;

public class MakePostSynchronize extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_posts_list_view);
        ListView listPosts = (ListView) findViewById(R.id.lista);
        List<MakePostStructure> typePosts = allTypePosts();
        MakePostViewAdapter adapter = new MakePostViewAdapter(typePosts, this);
        listPosts.setAdapter(adapter);

    }

    private List<MakePostStructure> allTypePosts() {

        MakePostStructure postOne = new MakePostStructure();
        MakePostStructure postTwo = new MakePostStructure();
        MakePostStructure postThree = new MakePostStructure();

        postOne.setPostName("Primeiro Post");
        postOne.setPostUserName("Kayuã Oleques Paim");
        postOne.setPostDate("28/07/21 23:36");
        postOne.setPostCategorical(MakePostCategorical.postPhoto);
        postOne.setPostLocalization("Alegrete - RS");
        postOne.setPostNumberLikes(100);
        postOne.setPostNumberShares(31);
        postOne.setPostNumberComments(31);
        postOne.setPostText("Texto de exemplo post");
        postOne.setPostState(MakePostState.FAZENDO);

        postTwo.setPostName("Segundo Post");
        postTwo.setPostUserName("Kayuã Oleques Paim");
        postTwo.setPostDate("28/07/21 23:36");
        postTwo.setPostCategorical(MakePostCategorical.postPhoto);
        postTwo.setPostLocalization("Alegrete - RS");
        postOne.setPostText("Texto de exemplo post");
        postTwo.setPostNumberLikes(100);
        postTwo.setPostNumberShares(31);
        postTwo.setPostNumberComments(31);
        postTwo.setPostState(MakePostState.FAZENDO);

        postThree.setPostName("Terceiro Post");
        postThree.setPostUserName("Kayuã Oleques Paim");
        postThree.setPostDate("28/07/21 23:36");
        postOne.setPostText("Texto de exemplo post");
        postThree.setPostCategorical(MakePostCategorical.postPhoto);
        postThree.setPostLocalization("Alegrete - RS");
        postThree.setPostNumberLikes(100);
        postThree.setPostNumberShares(31);
        postThree.setPostNumberComments(31);
        postThree.setPostState(MakePostState.FAZENDO);

        return new ArrayList<>(Arrays.asList(postOne, postTwo, postThree));

    }
}