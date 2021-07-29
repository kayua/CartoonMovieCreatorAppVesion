package app.mosquito.appmosquito.appmosquito.Posts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

public class MessageFragment extends Fragment {

    private MessageViewModel galleryViewModel;

    ArrayAdapter<String> adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = new ViewModelProvider(this).get(MessageViewModel.class);

        View root = inflater.inflate(R.layout.fragment_message, container, false);
        ListView list = root.findViewById(R.id.chat_list_view);
        EditText textEdit = root.findViewById(R.id.chat_edit_text1);
        ListView lista = (ListView) root.findViewById(R.id.chat_list_view);
        List<PostStructure> cursos = todosOsCursos();
        ViewAdapter adapter = new ViewAdapter(cursos, getActivity());
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

        postOne.setPostName("Primeiro Post");
        postOne.setPostUserName("Kayuã Oleques Paim");
        postOne.setPostDate("28/07/21 23:36");
        postOne.setPostCategorical(CategoricalPosts.postPhoto);
        postOne.setPostLocalization("Alegrete - RS");
        postOne.setPostNumberLikes(100);
        postOne.setPostNumberShares(31);
        postOne.setPostNumberComments(31);
        postOne.setPostText("Texto de exemplo post");
        postOne.setPostState(EstadoAtual.FAZENDO);

        postTwo.setPostName("Segundo Post");
        postTwo.setPostUserName("Kayuã Oleques Paim");
        postTwo.setPostDate("28/07/21 23:36");
        postTwo.setPostCategorical(CategoricalPosts.postText);
        postTwo.setPostLocalization("Alegrete - RS");
        postTwo.setPostText("Texto de exemplo post");
        postTwo.setPostNumberLikes(100);
        postTwo.setPostNumberShares(31);
        postTwo.setPostNumberComments(31);
        postTwo.setPostState(EstadoAtual.FAZENDO);

        postThree.setPostName("Terceiro Post");
        postThree.setPostUserName("Kayuã Oleques Paim");
        postThree.setPostDate("28/07/21 23:36");
        postThree.setPostText("Texto de exemplo post");
        postThree.setPostCategorical(CategoricalPosts.postMovie);
        postThree.setPostLocalization("Alegrete - RS");
        postThree.setPostNumberLikes(100);
        postThree.setPostNumberShares(31);
        postThree.setPostNumberComments(31);
        postThree.setPostState(EstadoAtual.FAZENDO);

    return new ArrayList<>(Arrays.asList(postOne, postTwo, postThree));
}
}