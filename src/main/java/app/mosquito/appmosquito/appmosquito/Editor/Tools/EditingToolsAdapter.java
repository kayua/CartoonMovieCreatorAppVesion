package app.mosquito.appmosquito.appmosquito.Editor.Tools;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.mosquito.appmosquito.appmosquito.R;

public class EditingToolsAdapter extends RecyclerView.Adapter<EditingToolsAdapter.ViewHolder> {

    private List<ToolModel> mToolList = new ArrayList<>();
    private OnItemSelected mOnItemSelected;

    public EditingToolsAdapter(OnItemSelected onItemSelected) {

        mOnItemSelected = onItemSelected;
        mToolList.add(new ToolModel("Shape", R.drawable.brush, ToolType.SHAPE));
        mToolList.add(new ToolModel("Text", R.drawable.text, ToolType.TEXT));
        mToolList.add(new ToolModel("Eraser", R.drawable.erase_icon, ToolType.ERASER));
        mToolList.add(new ToolModel("Filter", R.drawable.filtros_icon, ToolType.FILTER));
        mToolList.add(new ToolModel("Emoji", R.drawable.ic_insert_emoticon, ToolType.EMOJI));
        mToolList.add(new ToolModel("Sticker", R.drawable.ic_sticker, ToolType.STICKER));
    }

    public interface OnItemSelected { void onToolSelected(ToolType toolType);}

    class ToolModel {

        private String mToolName;
        private int mToolIcon;
        private ToolType mToolType;

        ToolModel(String toolName, int toolIcon, ToolType toolType) {

            mToolName = toolName;
            mToolIcon = toolIcon;
            mToolType = toolType;

        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_image_edito_tools, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ToolModel item = mToolList.get(position);
        holder.txtTool.setText(item.mToolName);
        holder.imgToolIcon.setImageResource(item.mToolIcon);

    }

    @Override
    public int getItemCount() { return mToolList.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgToolIcon;
        TextView txtTool;

        ViewHolder(View itemView) {

            super(itemView);
            imgToolIcon = itemView.findViewById(R.id.imgToolIcon);
            txtTool = itemView.findViewById(R.id.txtTool);
            itemView.setOnClickListener(v -> mOnItemSelected.onToolSelected(mToolList.get(getLayoutPosition()).mToolType));

        }

    }

}
