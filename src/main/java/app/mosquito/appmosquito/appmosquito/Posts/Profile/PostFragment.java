package app.mosquito.appmosquito.appmosquito.Posts.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.mosquito.appmosquito.appmosquito.R;

public class PostFragment extends Fragment {

    private PostViewModel galleryViewModel;

    ArrayAdapter<String> adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = new ViewModelProvider(this).get(PostViewModel.class);

        View root = inflater.inflate(R.layout.fragment_message, container, false);
        ListView list = root.findViewById(R.id.chat_list_view);

        ListView lista = (ListView) root.findViewById(R.id.chat_list_view);
        List<PostStructure> cursos = todosOsCursos();
        PostViewAdapter adapter = new PostViewAdapter(cursos, getActivity());
        lista.setAdapter(adapter);


        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }
    private List<PostStructure> todosOsCursos() {

        PostStructure postOne = new PostStructure();
        PostStructure postTwo = new PostStructure();
        PostStructure postThree = new PostStructure();
        PostStructure postFour = new PostStructure();
        PostStructure postHeader = new PostStructure();

        postOne.setPostName("Primeiro Post");
        postOne.setPostUserName("Kayuã Oleques Paim");
        postOne.setPostDate("28/07/21 23:36");
        postOne.setPostCategorical(PostCategorical.postSuggest);
        postOne.setPostLocalization("Alegrete - RS");
        postOne.setPostNumberLikes(100);
        postOne.setPostNumberShares(31);
        postOne.setPostNumberComments(31);
        postOne.setPostText("Texto de exemplo post");
        postOne.setPostState(PostState.FAZENDO);


        postThree.setPostName("Terceiro Post");
        postThree.setPostUserName("Kayuã Oleques Paim");
        postThree.setPostDate("28/07/21 23:36");
        postThree.setPostText("Texto de exemplo post");
        postThree.setPostCategorical(PostCategorical.postMovie);
        postThree.setPostLocalization("Alegrete - RS");
        postThree.setPostNumberLikes(100);
        postThree.setPostNumberShares(31);
        postThree.setPostNumberComments(31);
        postThree.setPostState(PostState.FAZENDO);

        postFour.setPostName("Terceiro Post");
        postFour.setPostUserName("Kayuã Oleques Paim");
        postFour.setPostDate("28/07/21 23:36");
        postFour.setPostText("Texto de exemplo post");
        postFour.setPostCategorical(PostCategorical.postText);
        postFour.setPostLocalization("Alegrete - RS");
        postFour.setPostNumberLikes(100);
        postFour.setPostNumberShares(31);
        postFour.setPostNumberComments(31);
        postFour.setPostState(PostState.FAZENDO);

        postHeader.setPostName("Terceiro Post");
        postHeader.setPostUserName("Kayuã Oleques Paim");
        postHeader.setPostDate("28/07/21 23:36");
        postHeader.setPostText("Texto de exemplo post");
        postHeader.setPostCategorical(PostCategorical.postText);
        postHeader.setPostLocalization("Alegrete - RS");
        postHeader.setPostNumberLikes(100);
        postHeader.setPostNumberShares(31);
        postHeader.setPostNumberComments(31);
        postHeader.setPostState(PostState.FAZENDO);

    return new ArrayList<>(Arrays.asList(postHeader, postOne, postThree, postFour));
}
}