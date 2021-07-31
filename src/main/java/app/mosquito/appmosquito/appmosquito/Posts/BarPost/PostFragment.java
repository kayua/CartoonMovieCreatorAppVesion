package app.mosquito.appmosquito.appmosquito.Posts.BarPost;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
    ImageView myButton;
    ImageView myButton_a;
    View myView;
    boolean isUp;
    ArrayAdapter<String> adapter;
    ListView lista;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = new ViewModelProvider(this).get(PostViewModel.class);

        View root = inflater.inflate(R.layout.fragment_message, container, false);
        ListView list = root.findViewById(R.id.chat_list_view);

        lista = (ListView) root.findViewById(R.id.chat_list_view);
        List<PostStructure> cursos = todosOsCursos();
        PostViewAdapter adapter = new PostViewAdapter(cursos, getActivity());
        lista.setAdapter(adapter);
        myView = root.findViewById(R.id.my_view);
        myButton = root.findViewById(R.id.enter_chat1);

        myButton_a = root.findViewById(R.id.imageView23);

        myButton_a.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                onSlideViewButtonClick(view);

            }

        });
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                onSlideViewButtonClick(view);
            }

        });
        // initialize as invisible (could also do in xml)
        myView.setVisibility(View.INVISIBLE);

        isUp = false;

        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    public void slideUp(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.bringToFront();
    }

    // slide the view from its current position to below itself
    public void slideDown(View view){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(750);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setEnabled(false);
        view.setFocusableInTouchMode(false);
        view.setFocusable(false);
        lista.bringToFront();

    }

    public void onSlideViewButtonClick(View view) {
        if (isUp) {
            slideDown(myView);

        } else {
            slideUp(myView);

        }
        isUp = !isUp;
    }


    private List<PostStructure> todosOsCursos() {

        PostStructure postOne = new PostStructure();
        PostStructure postThree = new PostStructure();
        PostStructure postFour = new PostStructure();
        PostStructure postHeader = new PostStructure();

        postOne.setPostName("Primeiro Post");
        postOne.setPostUserName("Kayuã Oleques Paim");
        postOne.setPostDate("28/07/21 23:36");
        postOne.setPostCategorical(PostCategorical.postMovie);
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
        postHeader.setPostCategorical(PostCategorical.postSuggest);
        postHeader.setPostLocalization("Alegrete - RS");
        postHeader.setPostNumberLikes(100);
        postHeader.setPostNumberShares(31);
        postHeader.setPostNumberComments(31);
        postHeader.setPostState(PostState.FAZENDO);

    return new ArrayList<>(Arrays.asList(postHeader, postOne, postThree, postFour));
}


}