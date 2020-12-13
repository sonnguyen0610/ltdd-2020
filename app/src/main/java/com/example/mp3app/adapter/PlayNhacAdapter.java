package com.example.mp3app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mp3app.R;
import com.example.mp3app.activity.PlayNhacActivity;
import com.example.mp3app.model.BaiHat;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

// lớp này dùng để lấy data và đưa lên layout
public class PlayNhacAdapter extends RecyclerView.Adapter<PlayNhacAdapter.ViewHolder> {
    Context context;
    ArrayList<BaiHat> listSong;

    public PlayNhacAdapter(Context context, ArrayList<BaiHat> listSong) {
        this.context = context;
        this.listSong = listSong;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.line_play_nhac, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baiHat = listSong.get(position);
        holder.txtIndexSong.setText(position + 1 + "");
        holder.txtNameSong.setText(baiHat.getTenBaiHat());
        holder.txtAuthorSong.setText(baiHat.getCaSi());
        Picasso.get().load(baiHat.getHinhCaSi()).into(holder.imgSong);
    }

    // bao nhiêu item trong RecycleView
    @Override
    public int getItemCount() {
        return listSong.size();
    }

    // class view holder để tăng tóc độ ánh xạ các component
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtIndexSong, txtNameSong, txtAuthorSong;
        ImageView imgSong;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIndexSong = itemView.findViewById(R.id.txtIndexPlayNhac);
            txtNameSong = itemView.findViewById(R.id.txtNamePlayNhac);
            txtAuthorSong = itemView.findViewById(R.id.txtAuthorPlayNhac);
            imgSong = itemView.findViewById(R.id.imgSongPlayNhac);

            // sự kiện nhấn vào từng item trong list
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PlayNhacActivity playNhacActivity = (PlayNhacActivity) context;
                    playNhacActivity.playBaiHatCuaFragmentPlayDSBH(getAdapterPosition());
                }
            });
        }
    }

}
