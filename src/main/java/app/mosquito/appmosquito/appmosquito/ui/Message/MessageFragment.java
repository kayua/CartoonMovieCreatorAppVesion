package app.mosquito.appmosquito.appmosquito.ui.Message;

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
        List<Curso> cursos = todosOsCursos();
        AdapterCursosPersonalizado adapter = new AdapterCursosPersonalizado(cursos, getActivity());
        lista.setAdapter(adapter);


        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }
    private List<Curso> todosOsCursos() {
    return new ArrayList<>(Arrays.asList(
            new Curso("Java", "  Olá mundo. kkkkkk", EstadoAtual.FINALIZADO, Categoria.JAVA),
            new Curso("HTML e CSS", "  HTML 5 e suas novidades", EstadoAtual.FAZENDO, Categoria.HTML),
            new Curso("Android", "  boas de práticas", EstadoAtual.FINALIZADO, Categoria.ANDROID),
            new Curso("Java", "  Olá mundo. kkkkkk", EstadoAtual.FINALIZADO, Categoria.JAVA),
            new Curso("Java", "  Olá mundo. kkkkkk", EstadoAtual.FINALIZADO, Categoria.JAVA),
            new Curso("Android", "  boas de práticas", EstadoAtual.FINALIZADO, Categoria.ANDROID),
            new Curso("Android", "  boas de práticas", EstadoAtual.FINALIZADO, Categoria.ANDROID),
            new Curso("Java", "  Olá mundo. kkkkkk", EstadoAtual.FINALIZADO, Categoria.JAVA),
            new Curso("Android", "  boas de práticas", EstadoAtual.FINALIZADO, Categoria.ANDROID),
            new Curso("Java", "  Olá mundo. kkkkkk", EstadoAtual.FINALIZADO, Categoria.JAVA),
            new Curso("Android", "  boas de práticas", EstadoAtual.FINALIZADO, Categoria.ANDROID),
            new Curso("Android", "  boas de práticas", EstadoAtual.FINALIZADO, Categoria.ANDROID)));
}
}