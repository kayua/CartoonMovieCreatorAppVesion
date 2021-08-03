package app.mosquito.appmosquito.appmosquito.Editor.Tools;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
        mToolList.add(new ToolModel("Eraser", R.drawable.erase, ToolType.ERASER));
        mToolList.add(new ToolModel("Eraser", R.drawable.icon_camera, ToolType.FILTER));
        mToolList.add(new ToolModel("Emoji", R.drawable.icon_emoji, ToolType.EMOJI));
        mToolList.add(new ToolModel("Sticker", R.drawable.p, ToolType.STICKER));
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
                .inflate(R.layout.layout_image_editor_tools, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ToolModel item = mToolList.get(position);

        holder.imgToolIcon.setImageResource(item.mToolIcon);

    }

    @Override
    public int getItemCount() { return mToolList.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgToolIcon;

        ViewHolder(View itemView) {

            super(itemView);
            imgToolIcon = itemView.findViewById(R.id.imgToolIcon);
            itemView.setOnClickListener(v -> mOnItemSelected.onToolSelected(mToolList.get(getLayoutPosition()).mToolType));

        }

    }

}
