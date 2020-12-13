package com.example.mp3app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mp3app.R;
import com.example.mp3app.model.Admin;

import java.util.List;

public class AdminAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Admin> listFunctions;

    public AdminAdapter(Context context, int layout, List<Admin> listFunctions) {
        this.context = context;
        this.layout = layout;
        this.listFunctions = listFunctions;
    }

    @Override
    // trả về số dòng mà hiển thị cho ListView
    public int getCount() {
        return listFunctions.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    // đọc từng dòng rồi trả về view
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        // lấy context
        if (view == null) { // lần đầu chạy nó sẽ null
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            // ánh xạ view
            viewHolder = new ViewHolder();
            viewHolder.imgFunction = (ImageView) view.findViewById(R.id.imgFunction);

            viewHolder.txtFunctionName = (TextView) view.findViewById(R.id.txtFunctionName);
            view.setTag(viewHolder); // giữ view đã ánh xạ
        } else {
            viewHolder = (ViewHolder) view.getTag(); // lấy ra view đã ánh xạ
        }

        // gán giá trị
        Admin admin = listFunctions.get(i); // lấy ra đối tượng Song
        viewHolder.imgFunction.setImageResource(admin.getImage());
        viewHolder.txtFunctionName.setText(admin.getTitle());
        return view;
    }
    // dùng để custom listview cho adapter


}

// class ViewHolder để làm giảm thiểu việc ánh xạ mỗi dòng ListView, làm tăng tốc load dữ liệu
class ViewHolder {
    TextView txtFunctionName;
    ImageView imgFunction;

}
