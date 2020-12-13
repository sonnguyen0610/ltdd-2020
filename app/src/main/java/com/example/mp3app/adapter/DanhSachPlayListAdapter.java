package com.example.mp3app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mp3app.activity.DanhSachBaiHatActivity;
import com.example.mp3app.model.PlayList;
import com.example.mp3app.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class DanhSachPlayListAdapter extends RecyclerView.Adapter<DanhSachPlayListAdapter.ViewHolder> {
    Context context;
    ArrayList<PlayList> playLists;

    public DanhSachPlayListAdapter(Context context, ArrayList<PlayList> playLists) {
        this.context = context;
        this.playLists = playLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.line_danh_sach_play_list, parent, false);
        return new DanhSachPlayListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlayList playList = playLists.get(position);
        Picasso.get().load(playList.getHinhPlayList()).into(holder.imgDanhSachPlayList);
        holder.txtNameDanhSachPlayList.setText(playList.getTen());

    }

    @Override
    public int getItemCount() {
        return playLists.size();
    }

    // View Holder để tăng tốc việc ánh xạ lại cho View
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameDanhSachPlayList;
        ImageView imgDanhSachPlayList;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            txtNameDanhSachPlayList = itemView.findViewById(R.id.txtNameDanhSachPlayList);
            imgDanhSachPlayList = itemView.findViewById(R.id.imgDanhSachPlayList);

            // sự kiện khi nhấn vào hình từng ietm trong PlayList
            imgDanhSachPlayList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // gửi data và chuyển qua màn hình danh sách các bài hát
                    Intent intent = new Intent(context, DanhSachBaiHatActivity.class);
                    intent.putExtra("playlist", playLists.get(getPosition()));// key put vào trùng với key playlist
                    context.startActivity(intent);
                }
            });
        }
    }
}
