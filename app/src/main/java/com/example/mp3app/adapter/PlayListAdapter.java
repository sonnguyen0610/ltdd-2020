package com.example.mp3app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.mp3app.model.PlayList;
import com.example.mp3app.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlayListAdapter extends ArrayAdapter<PlayList> {
    public PlayListAdapter(@NonNull Context context, int resource, @NonNull List<PlayList> objects) {
        super(context, resource, objects);
    }
    // class view holder để tăng tóc độ ánh xạ các component
    class ViewHolder {
        TextView txtNamePlayList;
        ImageView imgBgPlayList, imgPlayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        // nếu chưa có view nào bên trong thì gắn layout vào
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.line_playlist, null);
            // new ra rồi ánh xạ
            viewHolder = new ViewHolder();
            viewHolder.txtNamePlayList = convertView.findViewById(R.id.txtNamePlayList);
            viewHolder.imgBgPlayList = convertView.findViewById(R.id.imgBackgroundPlayList);
            viewHolder.imgPlayList = convertView.findViewById(R.id.imgPlayList);
            convertView.setTag(viewHolder);
        } else {
            // ngược lại có view rồi, lấy ra những giá trị lưu trong ConvertView ra
            viewHolder = (ViewHolder) convertView.getTag();

        }
        PlayList playList = getItem(position);
        // gắn hình ảnh vào dùng Picasso
        Picasso.get().load(playList.getHinhPlayList()).into(viewHolder.imgBgPlayList);
        Picasso.get().load(playList.getIcon()).into(viewHolder.imgPlayList);
//        Picasso.with(getContext()).load(playList.getHinhPlayList()).into(viewHolder.imgBgPlayList); // gắn hình background
//        Picasso.with(getContext()).load(playList.getIcon()).into(viewHolder.imgPlayList); // gắn icon
        //setText cho PlayList
        viewHolder.txtNamePlayList.setText(playList.getTen());


        return convertView; // trả về view
    }
}
