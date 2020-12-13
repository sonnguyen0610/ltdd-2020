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

import com.example.mp3app.R;
import com.example.mp3app.activity.DanhSachBaiHatActivity;
import com.example.mp3app.model.Acoustic;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

// lớp này dùng để lấy data và đưa lên layout
public class AcousticAdapter extends RecyclerView.Adapter<AcousticAdapter.ViewHolder> {
    Context context;
    ArrayList<Acoustic> listAcoustics;

    public AcousticAdapter(Context context, ArrayList<Acoustic> listAcoustics) {
        this.context = context;
        this.listAcoustics = listAcoustics;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.line_acoustic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Acoustic acoustic = listAcoustics.get(position);
        holder.txtNameAcoustic.setText(acoustic.getTenAlbum());
        holder.txtAuthorAcoustic.setText(acoustic.getTenCasiAlbum());
        Picasso.get().load(acoustic.getHinhAnhAlbum()).into(holder.imgAcoustic);
    }

    // bao nhiêu item trong RecycleView
    @Override
    public int getItemCount() {
        return listAcoustics.size();
    }

    // class view holder để tăng tóc độ ánh xạ các component
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameAcoustic, txtAuthorAcoustic;
        ImageView imgAcoustic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameAcoustic = itemView.findViewById(R.id.txtNameAcoustic);
            txtAuthorAcoustic = itemView.findViewById(R.id.txtAuthorAcoustic);
            imgAcoustic = itemView.findViewById(R.id.imgAcoustic);

            // sự kiện khi nhấn vào RecyvleView ở phần Album (ở ngoài giao diện chính)
            imgAcoustic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // gửi data và chuyển qua màn hình danh sách các bài hát
                    Intent intent = new Intent(context, DanhSachBaiHatActivity.class);
                    intent.putExtra("acoustic", listAcoustics.get(getPosition()));// key put vào trùng với key playlist
                    //intent.putExtra("size", listAcoustics);// key put vào trùng với key playlist
                    context.startActivity(intent);
                }
            });
        }
    }

}
