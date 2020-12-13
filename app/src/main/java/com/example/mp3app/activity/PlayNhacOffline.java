package com.example.mp3app.activity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.mp3app.R;
import com.example.mp3app.adapter.ViewPagerList;
import com.example.mp3app.fragment.Fragment_Disk;
import com.example.mp3app.model.Song;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class PlayNhacOffline extends AppCompatActivity {
    Toolbar toolbar;
    ViewPager viewPagerPlayNhac;
    TextView txtTitle, txtTimeSong, txtTimeTotal;
    SeekBar skSong;
    ImageButton imgPre, imgPlay, imgPause, imgNext, imgRepeat, imgShuff;
    //ImageView imgDisk;
    ArrayList<Song> listSong;
    int position = 0;
    int selected = 0;
    MediaPlayer mediaPlayer; // để play nhạc
    public static ViewPagerList adapterNhac;
    Fragment_Disk fragment_disk;
    Animation animation; // aninmation cho xoay đĩa nhạc

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_nhac);

        addControls();
        addSongs();
        initialMediaPlayer();
        setTimeTotal();
        updateTimeSong();
        addEvents();
    }

    private void updateTimeSong() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // lấy time hiện tại cập nhật lại phút giây
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                txtTimeSong.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition())); // lấy vị trí hiện tại của Media Player

                // update phần progress của Seekbar, cho thanh nó di chuyển khi nhạc chạy
                skSong.setProgress(mediaPlayer.getCurrentPosition());
                // kiểm tra time bài hát, nếu kết thúc thì next qua bài # (MediaPlayer đã hỗ trợ sẵn)
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        // chuyển qua bài khác, tương tự như Next
                        position++;
                        if (position > listSong.size() - 1) {
                            position = 0; // set lại thành bài hát đầu
                        }
                        if (mediaPlayer.isPlaying()) { // nếu đang phát ==> stop nó
                            mediaPlayer.stop();
                            initialMediaPlayer(); // khởi tạo media player
                            mediaPlayer.start();
                        } else {
                            initialMediaPlayer(); // khởi tạo media player
                            mediaPlayer.start();
                            imgPlay.setImageResource(R.drawable.iconpause1);
                        }
                        setTimeTotal();
                        updateTimeSong();
                    }
                });
                handler.postDelayed(this, 500); // nửa giây cập nhật rồi chạy lại
            }
        }, 100);

    }

    private void setTimeTotal() {
        // getDuration: trả về mili giây
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        txtTimeTotal.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        // gán max của thanh Seekbar = tổng thời gian bài hát (getDuration)
        skSong.setMax(mediaPlayer.getDuration());

    }

    private void initialMediaPlayer() {
        mediaPlayer = MediaPlayer.create(PlayNhacOffline.this, listSong.get(selected).getFile());
        toolbar.setTitle(listSong.get(position).getTitle());
        ///txtTitle.setText(listSong.get(position).getTitle());
    }

    private void addSongs() {
        listSong = new ArrayList<Song>();
        listSong.add(new Song("Ai Là Người Thương Em-Quân AP", R.raw.ailanguoithuongem_quanap));
        listSong.add(new Song("Buồn Lắm Em Ơi-Trịnh Đình Quang", R.raw.buonlamemoi_trinhdinhquang));
        listSong.add(new Song("Đau Để Trưởng Thành-Only C", R.raw.daudetruongthanh_onlyc));
        listSong.add(new Song("Rồi Người Thương Cũng Hoá Người Dưng-Hiền Hồ", R.raw.roinguoithuongcunghoanguoidung_hienho));
        listSong.add(new Song("Thay Tôi Yêu Cô Ấy-Thanh Hưng", R.raw.thaytoiyeucoay_thanhhung));

        listSong.add(new Song("Em Nguyện Là Mây-Wendy Thảo", R.raw.emnguyenlamay_wendy_thao));
        listSong.add(new Song("Người Lạ Ơi-Karik", R.raw.nguoilaoi_karik));
        listSong.add(new Song("Sóng Gió-JackKICM", R.raw.songgio_jack_kicm));
        listSong.add(new Song("Ừ Có Anh Đây-Tino", R.raw.ucoanhday_tino));
        listSong.add(new Song("Yêu Đương-OSAD", R.raw.yeuduong_osad));
        listSong.add(new Song("Em Gái Mưa-Hương Tràm", R.raw.emgaimua_huongtram));
        listSong.add(new Song("Rời Bỏ-Hoà Minzy", R.raw.roibo_hoaminzy));

        listSong.add(new Song("Anh Thương Em Nhất Mà-Tường Quân", R.raw.anhthuongemnhatma_tuongquan));
        listSong.add(new Song("Bạc Phận-Jack,K.ICM", R.raw.bacphan_jack_kicm));
        listSong.add(new Song("Chẳng Thể Nói Ra-Hương Ly", R.raw.changthenoira_huongly));
        listSong.add(new Song("Em Gì Ơi-Jack,K.ICM", R.raw.emgioi_jack));
        listSong.add(new Song("Gió Vẫn Hát-Long Phạm", R.raw.giovanhat_longpham));
        listSong.add(new Song("Không Yêu Xin Đừng Gây Thương Nhớ-Lyly-Karik", R.raw.motbuocyeuvandamdau));
        listSong.add(new Song("Một Bước Yêu Vạn Dặm Đau-Mr.Siro", R.raw.roibo_hoaminzy));
        listSong.add(new Song("Sao Em Vô Tình-Jack-Liam", R.raw.saoemvotinh));
    }

    private void addEvents() {

        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // nếu đang mở thì dừng
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    imgPlay.setImageResource(R.drawable.iconplay1);
                    if (fragment_disk.objectAnimator != null) {
                        fragment_disk.objectAnimator.pause();
                    }

                } else {
                    mediaPlayer.start();
                    imgPlay.setImageResource(R.drawable.iconpause1);
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    if (fragment_disk.objectAnimator != null) {
                        fragment_disk.objectAnimator.resume();
                    }

                }
                setTimeTotal();
                updateTimeSong();
                //fragment_disk.circleImageView.startAnimation(animation);
                //imgDisk.startAnimation(animation);
            }
        });
        imgPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position--;
                selected = position;
                if (position < 0) {
                    position = listSong.size() - 1; // set lại thành bài hát cuối
                }
                if (mediaPlayer.isPlaying()) { // nếu đang phát ==> stop nó
                    mediaPlayer.stop();
                    initialMediaPlayer(); // khởi tạo media player
                    mediaPlayer.start();
                } else {
                    initialMediaPlayer(); // khởi tạo media player
                    mediaPlayer.start();
                    imgPlay.setImageResource(R.drawable.iconpause1);
                }
                setTimeTotal();
                updateTimeSong();
                // imgDisk.startAnimation(animation);
            }
        });
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position++;
                selected = position;
                if (position > listSong.size() - 1) {
                    position = 0; // set lại thành bài hát đầu
                }
                if (mediaPlayer.isPlaying()) { // nếu đang phát ==> stop nó
                    mediaPlayer.stop();
                    initialMediaPlayer(); // khởi tạo media player
                    mediaPlayer.start();
                } else {
                    initialMediaPlayer(); // khởi tạo media player
                    mediaPlayer.start();
                    imgPlay.setImageResource(R.drawable.iconpause1);
                }
                setTimeTotal();
                updateTimeSong();
                //imgDisk.startAnimation(animation);
            }
        });
        imgRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected = position;
                // nếu đang mở thì dừng
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    imgPlay.setImageResource(R.drawable.iconplay1);

                } else {
                    mediaPlayer.start();
                    imgPlay.setImageResource(R.drawable.iconpause1);
                    ///fragment_disk.circleImageView.clearAnimation();

                }
                setTimeTotal();
                updateTimeSong();
            }
        });
        imgShuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random rd = new Random();
                position = rd.nextInt(listSong.size() - 1);
                // nếu đang mở thì dừng
                if (mediaPlayer.isPlaying()) { // nếu đang phát ==> stop nó
                    mediaPlayer.stop();
                    initialMediaPlayer(); // khởi tạo media player
                    mediaPlayer.start();
                } else {
                    initialMediaPlayer(); // khởi tạo media player
                    mediaPlayer.start();
                    imgPlay.setImageResource(R.drawable.iconpause1);
                }
                setTimeTotal();
                updateTimeSong();
            }
        });
        // sự kiện trên thanh Seekbar
        skSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // khi nhả chuột ra
                mediaPlayer.seekTo(skSong.getProgress()); // nhảy tới vị trí cần
            }
        });

    }

    private void addControls() {
        //txtTitle = (TextView)findViewById(R.id.txtTitle);
        toolbar = (Toolbar) findViewById(R.id.tbPlayNhac);
        txtTimeSong = (TextView) findViewById(R.id.txtTimeSong);
        txtTimeTotal = (TextView) findViewById(R.id.txtTimeTotal);
        skSong = (SeekBar) findViewById(R.id.seekBarSong);
        imgShuff = (ImageButton) findViewById(R.id.imgButtonShuff);
        imgPre = (ImageButton) findViewById(R.id.imgButtonPre);
        imgPlay = (ImageButton) findViewById(R.id.imgButtonPlay);
        // imgPause = (ImageButton) findViewById(R.id.imgButtonPause);
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
                finish();
                //mediaPlayer.stop();

            }
        });
        toolbar.setTitleTextColor(Color.WHITE);

        adapterNhac = new ViewPagerList(getSupportFragmentManager());
        fragment_disk = new Fragment_Disk();
        adapterNhac.addFragment(fragment_disk);
        viewPagerPlayNhac.setAdapter(adapterNhac);


    }
}
