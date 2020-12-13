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
import com.example.mp3app.model.Account;
import com.squareup.picasso.Picasso;

import java.util.List;

public class QuanLyUserAdapter extends BaseAdapter {
    private QuanLyUserActivity context;
    private int layout;
    private List<Account> listAccount;

    public QuanLyUserAdapter(QuanLyUserActivity context, int layout, List<Account> listAccount) {
        this.context = context;
        this.layout = layout;
        this.listAccount = listAccount;
    }

    @Override
    // trả về số dòng mà hiển thị cho ListView
    public int getCount() {
        return listAccount.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return Integer.parseInt(listAccount.get(position).getIdUser());
    }

    @Override
    // đọc từng dòng rồi trả về view
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        // lấy context
        if (view == null) { // lần đầu chạy nó sẽ null
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            // ánh xạ view
            viewHolder = new ViewHolder();
            viewHolder.imgUser = (ImageView) view.findViewById(R.id.imgUser);
            viewHolder.imgEdit = (ImageView) view.findViewById(R.id.imgEdit);
            viewHolder.imgRemove = (ImageView) view.findViewById(R.id.imgRemove);
//            viewHolder.imgEdit = (ImageView) view.findViewById(R.id.imgEdit);
//            viewHolder.imgDelete = (ImageView) view.findViewById(R.id.imgRemove);
            viewHolder.txtUsername = (TextView) view.findViewById(R.id.txtUsername);
            viewHolder.txtPassword = (TextView) view.findViewById(R.id.txtPassword);
            view.setTag(viewHolder); // giữ view đã ánh xạ
        } else {
            viewHolder = (ViewHolder) view.getTag(); // lấy ra view đã ánh xạ
        }

        // gán giá trị
        final Account account = listAccount.get(i); // lấy ra đối tượng Song
        //viewHolder.imgSong.setImageResource(account.getImage());
        // dùng thư viện Picasso để load hình ảnh
        Picasso.get().load(account.getHinhAnh()).into(viewHolder.imgUser);
        viewHolder.txtUsername.setText(account.getUsername());
        viewHolder.txtPassword.setText(account.getPassword());


        // bắt sự kiện update và delete bài hát cho ListView ở đây
        viewHolder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Id = " +  account.getIdUser(), Toast.LENGTH_LONG).show();
                // gởi dữ liệu bài hát qua màn hình sửa bài hát
                Intent intent = new Intent(context, UpdateUserActivity.class);
                intent.putExtra("data", account);
                context.startActivity(intent);

            }
        });
        viewHolder.imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Id = " + account.getIdUser(), Toast.LENGTH_LONG).show();
                deleteUser(Integer.parseInt(account.getIdUser()), account.getUsername());
            }
        });

        return view;
    }

    public void deleteUser(final int id, String name) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Xoá User!");
        dialog.setMessage("Bạn có chắc chắn muốn xoá " + name + " không?");
        dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // gọi hàm xoá bên QuanLyUserActivity
                context.deleteUser(id);
            }
        });
        dialog.show();

    }
}

// class ViewHolder để làm giảm thiểu việc ánh xạ mỗi dòng ListView, làm tăng tốc load dữ liệu
class ViewHolder {
    TextView txtUsername, txtPassword;
    ImageView imgUser, imgEdit, imgRemove;

}
