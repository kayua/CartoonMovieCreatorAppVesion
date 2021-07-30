package app.mosquito.appmosquito.appmosquito.Posts.Profile;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.mosquito.appmosquito.appmosquito.R;

public class ProfileSynchronize extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_posts_list_view);
        ListView listPosts = (ListView) findViewById(R.id.lista);
        List<ProfileStructure> typePosts = allTypePosts();
        ProfileViewAdapter adapter = new ProfileViewAdapter(typePosts, this);
        listPosts.setAdapter(adapter);

    }

    private List<ProfileStructure> allTypePosts() {

        ProfileStructure postOne = new ProfileStructure();
        ProfileStructure postTwo = new ProfileStructure();
        ProfileStructure postThree = new ProfileStructure();

        postOne.setPostName("Primeiro Post");
        postOne.setPostUserName("Kayuã Oleques Paim");
        postOne.setPostDate("28/07/21 23:36");
        postOne.setPostCategorical(ProfileCategorical.postPhoto);
        postOne.setPostLocalization("Alegrete - RS");
        postOne.setPostNumberLikes(100);
        postOne.setPostNumberShares(31);
        postOne.setPostNumberComments(31);
        postOne.setPostText("Texto de exemplo post");
        postOne.setPostState(ProfileState.FAZENDO);

        postTwo.setPostName("Segundo Post");
        postTwo.setPostUserName("Kayuã Oleques Paim");
        postTwo.setPostDate("28/07/21 23:36");
        postTwo.setPostCategorical(ProfileCategorical.postPhoto);
        postTwo.setPostLocalization("Alegrete - RS");
        postOne.setPostText("Texto de exemplo post");
        postTwo.setPostNumberLikes(100);
        postTwo.setPostNumberShares(31);
        postTwo.setPostNumberComments(31);
        postTwo.setPostState(ProfileState.FAZENDO);

        postThree.setPostName("Terceiro Post");
        postThree.setPostUserName("Kayuã Oleques Paim");
        postThree.setPostDate("28/07/21 23:36");
        postOne.setPostText("Texto de exemplo post");
        postThree.setPostCategorical(ProfileCategorical.postPhoto);
        postThree.setPostLocalization("Alegrete - RS");
        postThree.setPostNumberLikes(100);
        postThree.setPostNumberShares(31);
        postThree.setPostNumberComments(31);
        postThree.setPostState(ProfileState.FAZENDO);

        return new ArrayList<>(Arrays.asList(postOne, postTwo, postThree));

    }
}