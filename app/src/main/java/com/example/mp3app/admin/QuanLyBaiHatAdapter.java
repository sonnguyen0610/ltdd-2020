package com.example.mp3app.admin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mp3app.R;
import com.example.mp3app.model.BaiHat;
import com.squareup.picasso.Picasso;

import java.util.List;

public class QuanLyBaiHatAdapter extends BaseAdapter {
    private QuanLyBaiHatActivity context;
    private int layout;
    private List<BaiHat> listBaiHat;

    public QuanLyBaiHatAdapter(QuanLyBaiHatActivity context, int layout, List<BaiHat> listBaiHat) {
        this.context = context;
        this.layout = layout;
        this.listBaiHat = listBaiHat;
    }

    @Override
    // trả về số dòng mà hiển thị cho ListView
    public int getCount() {
        return listBaiHat.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return Integer.parseInt(listBaiHat.get(position).getIdBaiHat());
    }

    @Override
    // đọc từng dòng rồi trả về view
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolderBaiHat viewHolder;
        // lấy context
        if (view == null) { // lần đầu chạy nó sẽ null
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            // ánh xạ view
            viewHolder = new ViewHolderBaiHat();
            viewHolder.imgQuanLyBaiHat = (ImageView) view.findViewById(R.id.imgQuanLyBaiHat);
            viewHolder.imgEditBaiHat = (ImageView) view.findViewById(R.id.imgEditBaiHat);
            viewHolder.imgRemoveBaiHat = (ImageView) view.findViewById(R.id.imgRemoveBaiHat);
//            viewHolder.imgEdit = (ImageView) view.findViewById(R.id.imgEdit);
//            viewHolder.imgDelete = (ImageView) view.findViewById(R.id.imgRemove);
            viewHolder.txtNameQuanLyBaiHat = (TextView) view.findViewById(R.id.txtNameQuanLyBaiHat);
            viewHolder.txtAuthorQuanLyBaiHat = (TextView) view.findViewById(R.id.txtAuthorQuanLyBaiHat);
            view.setTag(viewHolder); // giữ view đã ánh xạ
        } else {
            viewHolder = (ViewHolderBaiHat) view.getTag(); // lấy ra view đã ánh xạ
        }

        // gán giá trị
        final BaiHat baiHat = listBaiHat.get(i); // lấy ra đối tượng Song
        //viewHolder.imgSong.setImageResource(account.getImage());
        // dùng thư viện Picasso để load hình ảnh
        Picasso.get().load(baiHat.getHinhCaSi()).into(viewHolder.imgQuanLyBaiHat);
        viewHolder.txtNameQuanLyBaiHat.setText(baiHat.getTenBaiHat());
        viewHolder.txtAuthorQuanLyBaiHat.setText(baiHat.getCaSi());


        // bắt sự kiện update và delete bài hát cho ListView ở đây
        viewHolder.imgEditBaiHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Id = " + String.valueOf(baiHat.getIdBaiHat()), Toast.LENGTH_LONG).show();
                // gởi dữ liệu bài hát qua màn hình sửa bài hát
                Intent intent = new Intent(context, UpdateBaiHatActivity.class);
                intent.putExtra("data", baiHat);
                context.startActivity(intent);

            }
        });
        viewHolder.imgRemoveBaiHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Id = " + baiHat.getIdBaiHat(), Toast.LENGTH_LONG).show();
                deleteSong(Integer.parseInt(baiHat.getIdBaiHat()), baiHat.getTenBaiHat());
            }
        });

        return view;
    }

    public void deleteSong(final int id, String name) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Xoá bài hát!");
        dialog.setMessage("Bạn có chắc chắn muốn xoá [" + name + "] không?");
        dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    // gọi hàm xoá bên QuanLyBaiHatActivity
                context.deleteSong(id);
            }
        });
        dialog.show();

    }
}

// class ViewHolder để làm giảm thiểu việc ánh xạ mỗi dòng ListView, làm tăng tốc load dữ liệu
class ViewHolderBaiHat {
    TextView txtNameQuanLyBaiHat, txtAuthorQuanLyBaiHat;
    ImageView imgQuanLyBaiHat, imgEditBaiHat, imgRemoveBaiHat;

}
