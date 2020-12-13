package com.example.mp3app.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.mp3app.R;
import com.example.mp3app.adapter.ViewPagerList;
import com.example.mp3app.fragment.Fragment_Disk;
import com.example.mp3app.fragment.Fragment_Loi_Bai_Hat;
import com.example.mp3app.fragment.Fragment_Play_Danh_Sach_Bai_Hat;
import com.example.mp3app.model.BaiHat;
import com.example.mp3app.service.OnClearFromRecentService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class PlayNhacActivity extends AppCompatActivity implements Playable {
    Toolbar toolbar;
    public ViewPager viewPagerPlayNhac;
    TextView txtTimeSong, txtTimeTotal;
    SeekBar skSong;
    ImageButton imgPre, imgPlay, imgPause, imgNext, imgRepeat, imgShuff;
    public static ArrayList<BaiHat> listSong = new ArrayList<>();
    public static ViewPagerList adapterNhac;
    Fragment_Disk fragment_disk;
    Fragment_Play_Danh_Sach_Bai_Hat fragment_play_danh_sach_bai_hat;
    Fragment_Loi_Bai_Hat fragment_loi_bai_hat;
    public static MediaPlayer mediaPlayer = new MediaPlayer();
    int position = 0, old_position = 0;
    boolean repeat = false;
    boolean checkRandom = false;
    boolean next = false;

    NotificationManager notificationManager;
    static boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_nhac);
        // kiểm tra tín hiệu mạng
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        getDataFromIntent();    //PHẢI LẤY DỮ LIỆU TRƯỚC KHI addControls() THÌ MỚI CÓ BÀI HÁT ĐỂ PHÁT
        CreateNotification.createNotification(getApplicationContext(), listSong.get(position),
                R.drawable.ic_pause_black_24dp, position, listSong.size() - 1);
        isPlaying = true;
//        unregisterReceiver(broadcastReceiver);
        if (mediaPlayer != null) {
            Log.d("fff", "aaaaaaaaa");
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        getDataFromIntent();    //PHẢI LẤY DỮ LIỆU TRƯỚC KHI addControls() THÌ MỚI CÓ BÀI HÁT ĐỂ PHÁT
        addControls();
        addEvents();

        //Music player on notification
        notification();

    }

    private void notification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
            registerReceiver(broadcastReceiver, new IntentFilter("TRACKS_TRACKS"));
            startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
        }
        onTrackPlay();

    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CreateNotification.CHANNEL_ID,
                    "KOD Dev", NotificationManager.IMPORTANCE_LOW);

            notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("ABC", "receiveeeeeeeeed");
            String action = intent.getExtras().getString("actionname");

            switch (action) {
                case CreateNotification.ACTION_PREVIUOS:
                    onTrackPrevious();
                    break;
                case CreateNotification.ACTION_PLAY:
                    Log.d("ABC", "playyyyyyyy");
                    if (isPlaying) {
                        onTrackPause();
                    } else {
                        onTrackPlay();
                    }
                    break;
                case CreateNotification.ACTION_NEXT:
                    onTrackNext();
                    break;
            }
        }
    };

    @Override
    public void onTrackPrevious() {
//        position--;
        old_position = position;
        if (listSong.size() > 0) {
            if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            if (repeat) {
                position = position;
            } else {
                position--;
                if (position == -1) {
                    position = listSong.size() - 1;
                }
                if (checkRandom) {
                    Random random = new Random();
                    int viTriRandom = random.nextInt(listSong.size());
                    position = viTriRandom;
                }
            }
            new PlayMP3().execute(listSong.get(position).getLinkBaiHat());
            imgPlay.setImageResource(R.drawable.iconpause1);
            fragment_disk.playNhac(listSong.get(position).getHinhCaSi());
            fragment_disk.txtNameCaSi.setText(listSong.get(position).getCaSi());
            fragment_disk.txtTenBaiHat.setText(listSong.get(position).getTenBaiHat());
            getSupportActionBar().setTitle(listSong.get(position).getTenBaiHat());
            updateTime();
            if (fragment_disk.objectAnimator != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    fragment_disk.objectAnimator.resume();
                }
            }
        }
        CreateNotification.createNotification(getApplicationContext(), listSong.get(position),
                R.drawable.ic_pause_black_24dp, position, listSong.size() - 1);
        isPlaying = true;
    }

    @Override
    public void onTrackPlay() {
        // mediaPlayer.stop();
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
        imgPlay.setImageResource(R.drawable.iconpause1);
        // giữ màn hình không đc tắt khi play nhạc
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (fragment_disk.objectAnimator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                fragment_disk.objectAnimator.resume();
            }
        }
//        if (listSong.size() > 0) {
        CreateNotification.createNotification(getApplicationContext(), listSong.get(position),
                R.drawable.ic_pause_black_24dp, position, listSong.size() - 1);
        isPlaying = true;
//        }

    }

    @Override
    public void onTrackPause() {
        // nếu đang mở mà click vô thì tạm dừng
        mediaPlayer.pause();
        imgPlay.setImageResource(R.drawable.iconplay1);
        if (fragment_disk.objectAnimator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                fragment_disk.objectAnimator.pause();
            }
        }
        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        CreateNotification.createNotification(getApplicationContext(), listSong.get(position),
                R.drawable.ic_play_arrow_black_24dp, position, listSong.size() - 1);
        isPlaying = false;
    }

    @Override
    public void onTrackNext() {
//        position++;

        old_position = position;
        // kiểm tra nếu có dữ liệu
        if (listSong.size() > 0) {
            if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            if (repeat) {
                position = position;
            } else {
                position++;
                if (position == listSong.size()) {
                    position = 0;
                }
                if (checkRandom) {
                    Random random = new Random();
                    int viTriRandom = random.nextInt(listSong.size());
                    position = viTriRandom;
                }
            }
            new PlayMP3().execute(listSong.get(position).getLinkBaiHat());
            imgPlay.setImageResource(R.drawable.iconpause1);
            fragment_disk.playNhac(listSong.get(position).getHinhCaSi());
            fragment_disk.txtNameCaSi.setText(listSong.get(position).getCaSi());
            fragment_disk.txtTenBaiHat.setText(listSong.get(position).getTenBaiHat());
            getSupportActionBar().setTitle(listSong.get(position).getTenBaiHat());
            updateTime();
            if (fragment_disk.objectAnimator != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    fragment_disk.objectAnimator.resume();
                }
            }
        }
        CreateNotification.createNotification(getApplicationContext(), listSong.get(position),
                R.drawable.ic_pause_black_24dp, position, listSong.size() - 1);
        isPlaying = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Log.d("ABC", "11111111");
//        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                Log.d("ABC", "receive11111111");
//                String action = intent.getExtras().getString("actionname");
//
//                switch (action){
//                    case CreateNotification.ACTION_PREVIUOS:
//                        onTrackPrevious();
//                        break;
//                    case CreateNotification.ACTION_PLAY:
//                        Log.d("ABC", "play1111111");
//                        if (isPlaying){
//                            onTrackPause();
//                        } else {
//                            onTrackPlay();
//                        }
//                        break;
//                    case CreateNotification.ACTION_NEXT:
//                        onTrackNext();
//                        break;
//                }
//            }
//        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.cancelAll();
        }

        unregisterReceiver(broadcastReceiver);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            Log.d("ABC", "222222222");
//            createChannel();
//            registerReceiver(broadcastReceiver, new IntentFilter("TRACKS_TRACKS"));
//            startService(new Intent(getApplicationContext(), OnClearFromRecentService.class));
//        }
    }

    public void playBaiHatCuaFragmentPlayDSBH(int viTri) {
        old_position = position;
        position = viTri;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        new PlayMP3().execute(listSong.get(position).getLinkBaiHat());
        imgPlay.setImageResource(R.drawable.iconpause1);
        fragment_disk.playNhac(listSong.get(position).getHinhCaSi());
        fragment_disk.txtNameCaSi.setText(listSong.get(position).getCaSi());
        fragment_disk.txtTenBaiHat.setText(listSong.get(position).getTenBaiHat());
        getSupportActionBar().setTitle(listSong.get(position).getTenBaiHat());
        updateTime();
        if (fragment_disk.objectAnimator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                fragment_disk.objectAnimator.resume();
            }
        }
        CreateNotification.createNotification(PlayNhacActivity.this, listSong.get(position),
                R.drawable.ic_pause_black_24dp, position, listSong.size() - 1);
        isPlaying = true;
    }

    private void getDataFromIntent() {
        // lấy intent từ BaiHatAdapter và DanhSachBaiHatAdapter qua
        Intent intent = getIntent();
        if (listSong != null) {
            listSong.clear();
        } else listSong = new ArrayList<>();
        if (intent != null) { // nếu có data
            if (intent.hasExtra("baihat")) {
                // lấy BaiHat ra
                BaiHat baiHat = intent.getParcelableExtra("baihat"); // lấy data dạng Object
                //Toast.makeText(PlayNhacActivity.this, baiHat.getTenBaiHat(), Toast.LENGTH_LONG).show();
                listSong.add(baiHat);
            }
            // lấy intent từ DanhSachBaiHatActivity qua (danh sách bài hát)
            if (intent.hasExtra("listsong")) {
                // lấy danh sách bài hát
                ArrayList<BaiHat> arrayList = intent.getParcelableArrayListExtra("listsong");
                //listSong = arrayList;
                // duyệt vòng for để lấy các bài hát trong danh sách
//                for (int i = 0; i < arrayList.size(); i++) {
//                    //Toast.makeText(PlayNhacActivity.this, baiHat.getTenBaiHat(), Toast.LENGTH_LONG).show();
//                    listSong.add(arrayList.get(i));
//                    //Toast.makeText(PlayNhacActivity.this, listSong.size(), Toast.LENGTH_LONG).show();
//                }
                if (arrayList != null) {
                    for (BaiHat baiHat : arrayList) {
                        //Toast.makeText(PlayNhacActivity.this, baiHat.getLinkBaiHat(), Toast.LENGTH_LONG).show();
                        listSong.add(baiHat);
                    }
                }

            }
        }

    }

    private void addEvents() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (adapterNhac.getItem(1) != null) {
                    if (listSong.size() > 0) {
                        fragment_disk.playNhac(listSong.get(position).getHinhCaSi());
                        fragment_disk.txtNameCaSi.setText(listSong.get(position).getCaSi());
                        fragment_disk.txtTenBaiHat.setText(listSong.get(position).getTenBaiHat());

                        RecyclerView.ViewHolder viewHolder = fragment_play_danh_sach_bai_hat.rvPlayNhac.findViewHolderForAdapterPosition(old_position);
                        if (viewHolder != null) {
                            View view = viewHolder.itemView;
                            view.setBackgroundColor(Color.TRANSPARENT);
                        }
                        RecyclerView.ViewHolder viewHolder1 = fragment_play_danh_sach_bai_hat.rvPlayNhac.findViewHolderForAdapterPosition(position);
                        if (viewHolder1 != null) {
                            View view1 = viewHolder1.itemView;
                            view1.setBackgroundColor(Color.argb(100, 255, 0, 255));
                        }
                        fragment_loi_bai_hat.setLoiBaiHatLenGiaoDien(listSong.get(position).getLoiBaiHat());
                        //handler.removeCallbacks(this);
                        handler.postDelayed(this, 300);

                    } else {
                        // nửa giây cập nhật rồi chạy lại
                        handler.postDelayed(this, 300);
                    }
                }


                //handler.postDelayed(this, 500); // nửa giây cập nhật rồi chạy lại
            }
        }, 500);

        // sự kiện nhấn nút play
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // nếu đang mở mà click vô thì tạm dừng
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    imgPlay.setImageResource(R.drawable.iconplay1);
                    if (fragment_disk.objectAnimator != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            fragment_disk.objectAnimator.pause();
                        }
                    }
                    CreateNotification.createNotification(PlayNhacActivity.this, listSong.get(position),
                            R.drawable.ic_play_arrow_black_24dp, position, listSong.size() - 1);
                    isPlaying = false;
                    //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                } else { // ngược lại
                    // mediaPlayer.stop();
                    mediaPlayer.start();
                    imgPlay.setImageResource(R.drawable.iconpause1);
                    // giữ màn hình không đc tắt khi play nhạc
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    if (fragment_disk.objectAnimator != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            fragment_disk.objectAnimator.resume();
                        }
                    }
                    CreateNotification.createNotification(PlayNhacActivity.this, listSong.get(position),
                            R.drawable.ic_pause_black_24dp, position, listSong.size() - 1);
                    isPlaying = true;
                }
            }
        });

        imgRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (repeat) {
                    repeat = false;
                    imgRepeat.setImageResource(R.drawable.icon_repeat_one_white);
                } else {
                    repeat = true;
                    checkRandom = false; //vì mình muốn tại 1 thời điểm chỉ có thể chọn 1 cái: repeat hoặc random
                    imgRepeat.setImageResource(R.drawable.icon_repeat_one_purple);
                    imgShuff.setImageResource(R.drawable.icon_shuffle_white);
                }
            }
        });
        imgShuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkRandom == false) {
                    if (repeat == true) {
                        repeat = false;
                        imgShuff.setImageResource(R.drawable.icon_shuffle_purple);
                        imgRepeat.setImageResource(R.drawable.icon_repeat_one_white);
                    }
                    imgShuff.setImageResource(R.drawable.icon_shuffle_purple);
                    checkRandom = true;
                } else {
                    imgShuff.setImageResource(R.drawable.iconsuffle1);
                    checkRandom = false;

                }
            }
        });
        skSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                old_position = position;
                // kiểm tra nếu có dữ liệu
                if (listSong.size() > 0) {
                    if (mediaPlayer.isPlaying() && mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if (repeat) {
                        position = position;
                    } else {
                        position++;
                        if (position == listSong.size()) {
                            position = 0;
                        }
                        if (checkRandom) {
                            Random random = new Random();
                            int viTriRandom = random.nextInt(listSong.size());
                            position = viTriRandom;
                        }
                    }
                    new PlayMP3().execute(listSong.get(position).getLinkBaiHat());
                    imgPlay.setImageResource(R.drawable.iconpause1);
                    fragment_disk.playNhac(listSong.get(position).getHinhCaSi());
                    fragment_disk.txtNameCaSi.setText(listSong.get(position).getCaSi());
                    fragment_disk.txtTenBaiHat.setText(listSong.get(position).getTenBaiHat());
                    getSupportActionBar().setTitle(listSong.get(position).getTenBaiHat());
                    updateTime();
                    if (fragment_disk.objectAnimator != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            fragment_disk.objectAnimator.resume();
                        }
                    }
                    CreateNotification.createNotification(PlayNhacActivity.this, listSong.get(position),
                            R.drawable.ic_pause_black_24dp, position, listSong.size() - 1);
                    isPlaying = true;
                }
                imgPre.setClickable(false);
                imgNext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgPre.setClickable(true);
                        imgNext.setClickable(true);
                    }
                }, 5000);
            }
        });
        imgPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                old_position = position;
                if (listSong.size() > 0) {
                    if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if (repeat) {
                        position = position;
                    } else {
                        position--;
                        if (position == -1) {
                            position = listSong.size() - 1;
                        }
                        if (checkRandom) {
                            Random random = new Random();
                            int viTriRandom = random.nextInt(listSong.size());
                            position = viTriRandom;
                        }
                    }
                    new PlayMP3().execute(listSong.get(position).getLinkBaiHat());
                    imgPlay.setImageResource(R.drawable.iconpause1);
                    fragment_disk.playNhac(listSong.get(position).getHinhCaSi());
                    fragment_disk.txtNameCaSi.setText(listSong.get(position).getCaSi());
                    fragment_disk.txtTenBaiHat.setText(listSong.get(position).getTenBaiHat());
                    getSupportActionBar().setTitle(listSong.get(position).getTenBaiHat());
                    updateTime();
                    if (fragment_disk.objectAnimator != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            fragment_disk.objectAnimator.resume();
                        }
                    }
                    CreateNotification.createNotification(PlayNhacActivity.this, listSong.get(position),
                            R.drawable.ic_pause_black_24dp, position, listSong.size() - 1);
                    isPlaying = true;

                }
                imgPre.setClickable(false);
                imgNext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgPre.setClickable(true);
                        imgNext.setClickable(true);
                    }
                }, 5000);
            }
        });


    }

    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.tbPlayNhac);
        txtTimeSong = (TextView) findViewById(R.id.txtTimeSong);
        txtTimeTotal = (TextView) findViewById(R.id.txtTimeTotal);
        skSong = (SeekBar) findViewById(R.id.seekBarSong);
        imgShuff = (ImageButton) findViewById(R.id.imgButtonShuff);
        imgPre = (ImageButton) findViewById(R.id.imgButtonPre);
        imgPlay = (ImageButton) findViewById(R.id.imgButtonPlay);
        imgNext = (ImageButton) findViewById(R.id.imgButtonNext);
        imgRepeat = (ImageButton) findViewById(R.id.imgButtonRepeat);
        viewPagerPlayNhac = (ViewPager) findViewById(R.id.vpNhac);
        //imgDisk = (ImageView) findViewById(R.id.imgDisk);


        // vì đã xoá action bar nên thay thế bằng SupportAction
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PlayNhacActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i);
//                finish();
//                mediaPlayer.stop();
//                listSong.clear();

            }
        });
        toolbar.setTitleTextColor(Color.WHITE);
        // gắn ViewPager
        adapterNhac = new ViewPagerList(getSupportFragmentManager());
        fragment_disk = new Fragment_Disk(); // new ra Fragment đĩa nhạc
        fragment_play_danh_sach_bai_hat = new Fragment_Play_Danh_Sach_Bai_Hat(); // new ra Fragment danh sách bài hát
        fragment_loi_bai_hat = new Fragment_Loi_Bai_Hat();
        // thêm vào View Pager (View Pager để chuyển qua lại các màn hình)

        // Fragment danh sách sẽ nằm bên trái Fragment đĩa nhạc
        adapterNhac.addFragment(fragment_play_danh_sach_bai_hat);   //VỊ TRÍ 0
        adapterNhac.addFragment(fragment_disk);                     //VỊ TRÍ 1
        adapterNhac.addFragment(fragment_loi_bai_hat);              //VỊ TRÍ 2
        viewPagerPlayNhac.setAdapter(adapterNhac);
        viewPagerPlayNhac.setCurrentItem(1);

        fragment_play_danh_sach_bai_hat = (Fragment_Play_Danh_Sach_Bai_Hat) adapterNhac.getItem(0);
        fragment_disk = (Fragment_Disk) adapterNhac.getItem(1);
        fragment_loi_bai_hat = (Fragment_Loi_Bai_Hat) adapterNhac.getItem(2);
        // kiểm tra danh sách bài hát mà có dữ liệu thì sẽ play ca khúc đầu tiên
        if (listSong.size() > 0) {
            // set lại title bài hát đó
            getSupportActionBar().setTitle(listSong.get(position).getTenBaiHat());
            new PlayMP3().execute(listSong.get(position).getLinkBaiHat());
            // sau khi bài hát đã play thì set lại icon thành đang play nhạc
            imgPlay.setImageResource(R.drawable.iconpause1);

        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent i = new Intent(PlayNhacActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
    }

    public class PlayMP3 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String baihat) {
            super.onPostExecute(baihat);
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC); // play nhạc dưới dạng online
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();

                    }
                });

                mediaPlayer.setDataSource(baihat); // khởi tạo đường link bài hát
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            timeSong();
            updateTime();
        }
    }

    private void timeSong() {
        // lấy time hiện tại cập nhật lại phút giây
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        txtTimeTotal.setText(simpleDateFormat.format(mediaPlayer.getDuration())); // lấy vị trí hiện tại của Media Player
        // gán max của thanh Seekbar = tổng thời gian bài hát (getDuration)
        skSong.setMax(mediaPlayer.getDuration());
    }

    private void updateTime() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // nếu có dữ liệu
                if (mediaPlayer != null) {
                    skSong.setProgress(mediaPlayer.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    txtTimeSong.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition())); // lấy vị trí hiện tại của Media Player
                    handler.postDelayed(this, 300); // lắng nghe liên tục mỗi 0.3s
                    // kiểm tra cuối bài chưa
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            next = true;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }, 300);

        //Tự động chuyển bài hát kế tiếp khi phát xong bài hát hiện tại
        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (next == true) {
                    old_position = position;
                    // kiểm tra nếu có dữ liệu
                    if (listSong.size() > 0) {
                        if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                            mediaPlayer = null;
                        }
                        if (repeat) {
                            position = position;
                        } else {
                            position++;
                            if (position == listSong.size()) {
                                position = 0;
                            }
                            if (checkRandom) {
                                Random random = new Random();
                                int viTriRandom = random.nextInt(listSong.size());
                                position = viTriRandom;
                            }
                        }
                        new PlayMP3().execute(listSong.get(position).getLinkBaiHat());
                        imgPlay.setImageResource(R.drawable.iconpause1);
                        fragment_disk.playNhac(listSong.get(position).getHinhCaSi());
                        fragment_disk.txtNameCaSi.setText(listSong.get(position).getCaSi());
                        fragment_disk.txtTenBaiHat.setText(listSong.get(position).getTenBaiHat());
                        getSupportActionBar().setTitle(listSong.get(position).getTenBaiHat());
                        updateTime();
                        CreateNotification.createNotification(PlayNhacActivity.this, listSong.get(position),
                                R.drawable.ic_pause_black_24dp, position, listSong.size() - 1);
                        isPlaying = true;
                    }

                    imgPre.setClickable(false);
                    imgNext.setClickable(false);
                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imgPre.setClickable(true);
                            imgNext.setClickable(true);
                        }
                    }, 5000);
                    next = false;
                    handler1.removeCallbacks(this); // xoá cái cũ đi
                } else {
                    handler1.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }


}