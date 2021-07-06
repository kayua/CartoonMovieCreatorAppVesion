package app.mosquito.appmosquito.appmosquito.Authentication.Gallery;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import app.mosquito.appmosquito.appmosquito.R;

public class GalleryAdapterList extends RecyclerView.Adapter<GalleryAdapterList.MyViewHolder> {

    ArrayList<GalleryModelVideo> videosList = new ArrayList<GalleryModelVideo>();
    Context context;

    GalleryAdapterList(Context context, ArrayList<GalleryModelVideo> videosList){
        this.context = context;
        this.videosList = videosList;

    }

    @NonNull
    @Override
    public GalleryAdapterList.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_video, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryAdapterList.MyViewHolder holder, int position) {
        final GalleryModelVideo item = videosList.get(position);

        Glide.with(context).load(item.getData()).into(holder.imgView_thumbnail);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GalleryPlayer.class);
                intent.putExtra("videoId", item.getId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videosList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgView_thumbnail;
        TextView tv_title, tv_duration;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgView_thumbnail = itemView.findViewById(R.id.imageView_thumbnail);
        }
    }
}
