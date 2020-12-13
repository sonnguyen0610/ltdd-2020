package com.example.mp3app.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mp3app.R;
import com.example.mp3app.activity.PlayNhacActivity;
import com.example.mp3app.model.BaiHat;
import com.example.mp3app.service.APIService;
import com.example.mp3app.service.DataService;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchBaiHatAdapter extends RecyclerView.Adapter<SearchBaiHatAdapter.ViewHolder> {
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    public ProgressDialog mProgressDialog;

    Context context;
    ArrayList<BaiHat> listBaiHat;

    public SearchBaiHatAdapter(Context context, ArrayList<BaiHat> listBaiHat) {
        this.context = context;
        this.listBaiHat = listBaiHat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.line_search_bai_hat, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baihat = listBaiHat.get(position);
        holder.txtNameSearch.setText(baihat.getTenBaiHat());
        holder.txtAuthorSearch.setText(baihat.getCaSi());
        Picasso.get().load(baihat.getHinhCaSi()).into(holder.imgSongSeach);
    }

    @Override
    public int getItemCount() {
        return listBaiHat.size();
    }

    // class view holder để tăng tóc độ ánh xạ các component
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameSearch, txtAuthorSearch;
        ImageView imgSongSeach, imgLuotThichSearch, imgDownloadSearch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameSearch = itemView.findViewById(R.id.txtNameSearch);
            txtAuthorSearch = itemView.findViewById(R.id.txtAuthorSearch);
            imgSongSeach = itemView.findViewById(R.id.imgSongSearch);
            imgLuotThichSearch = itemView.findViewById(R.id.imgLuotThichSearch);
            imgDownloadSearch = itemView.findViewById(R.id.imgDownloadSearch);

            // sự kiện khi nhấn vào icon download bài hát
            imgDownloadSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, listBaiHat.get(getPosition()).getLinkBaiHat() + "", Toast.LENGTH_LONG).show();
                    String urlSong = listBaiHat.get(getPosition()).getLinkBaiHat();
                    new DownloadFileAsync().execute(urlSong);
                }
            });

            // sự kiện khi nhấn vào icon lượt thích
            imgLuotThichSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, listBaiHat.get(getPosition()).getTenBaiHat(), Toast.LENGTH_LONG).show();
                    // khi click vào thì sẽ đổi thành trái tim đỏ
                    imgLuotThichSearch.setImageResource(R.drawable.iconloved);
                    // tương tác lên Server update lại lượt thích
                    DataService dataService = APIService.getService();
                    // chỉ update 1 lần 1 lượt thích, update theo id bài hát
                    Call<String> callBack = dataService.updateLuotThich("1", listBaiHat.get(getPosition()).getIdBaiHat());
                    // gửi lên Server
                    callBack.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            // kết quả trả về
                            String result = response.body();
                            if (response.equals("Sucess")) {
                                Toast.makeText(context, "Loved!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, "Error!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(context, "Loved", Toast.LENGTH_LONG).show();
                        }
                    });
                    // sau khi đã thích thì set  lại disable
                    imgLuotThichSearch.setEnabled(false);
                }
            });

            // xử lí khi nhấn vào từng item bài hát
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // chuyển dữ liệu qua màn hình play nhạc
                    Intent intent = new Intent(context, PlayNhacActivity.class);
                    // truyền nguyên đối tượng bài hát qua màn hình Play nhac
                    intent.putExtra("baihat", listBaiHat.get(getPosition()));
                    context.startActivity(intent);
                }
            });

        }
    }



    class DownloadFileAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage("Downloading mp3...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();

        }
        @Override
        protected String doInBackground(String... aurl) {
            int count;
            try {
                URL url = new URL(aurl[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();
                int lenghtOfFile = conexion.getContentLength();
                Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);
                //Toast.makeText(context, "Length of file: " + lenghtOfFile, Toast.LENGTH_LONG).show();
                InputStream input = new BufferedInputStream(url.openStream());
                String fileName = "/download.mp3";
                String storageDir = Environment.getExternalStorageDirectory().getAbsolutePath();
                Toast.makeText(context, storageDir, Toast.LENGTH_LONG).show();
                File file = new File(storageDir + fileName);

                OutputStream output = new FileOutputStream("C:\\Users\\TRANTHANHDIEN1\\Desktop\\download.mp3");
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }
            return null;
        }

        protected void onProgressUpdate(String... progress) {
            Log.d("ANDRO_ASYNC", progress[0]);
            mProgressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String unused) {
            //onCreateDialog(DIALOG_DOWNLOAD_PROGRESS);

            mProgressDialog.dismiss();
            String mp3Path = Environment.getExternalStorageDirectory().getAbsolutePath();
            Toast.makeText(context, "Download successfully!", Toast.LENGTH_LONG).show();
            Toast.makeText(context, mp3Path, Toast.LENGTH_LONG).show();
        }
    }


}
