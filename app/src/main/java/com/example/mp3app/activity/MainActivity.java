package com.example.mp3app.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.mp3app.R;
import com.example.mp3app.adapter.MainViewPagerAdapter;
import com.example.mp3app.fragment.Fragment_Create_Playlists;
import com.example.mp3app.fragment.Fragment_Downloaded;
import com.example.mp3app.fragment.Fragment_Home;
import com.example.mp3app.fragment.Fragment_PlayList;
import com.example.mp3app.fragment.Fragment_Profile_Group;
import com.example.mp3app.fragment.Fragment_Search;
import com.example.mp3app.fragment.Fragment_Send;
import com.example.mp3app.fragment.Fragment_Vote;
import com.example.mp3app.model.Account;
import com.felix.bottomnavygation.BadgeIndicator;
import com.felix.bottomnavygation.BottomNav;
import com.felix.bottomnavygation.ItemNav;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager; // dùng để lướt qua lại giữa các màn hình
    private TabLayout tabLayout;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle; // điều khiển việc đóng mở DrawerLayout
    private BottomNav bottomNav;
    private FloatingActionButton fab, fab_facebook, fab_youtube, fab_twitter;
    boolean isHide = false; // để xem đã nhấn vào Floating Button chưa
    Toolbar toolbar;
    NavigationView navigationView;
    ItemNav itemNavHome, itemNavSearch, itemNavNoti, itemNavProfile;
    ArrayList<Account> listAccount;
    //boolea state;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        listAccount = intent.getParcelableArrayListExtra("user_account");

        addControls();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Home()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
        addEvents();


    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void addEvents() {

        FragmentManager manager = getSupportFragmentManager();
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(manager);
        // thêm các Fragment
        mainViewPagerAdapter.addFragment(new Fragment_Home(), "Home");
        mainViewPagerAdapter.addFragment(new Fragment_PlayList(), "Search");
        viewPager.setAdapter(mainViewPagerAdapter); // set cho view pager
//        tabLayout.setupWithViewPager(viewPager); // set view pager cho tablayout
//        tabLayout.getTabAt(0).setIcon(R.drawable.icontrangchu); // set icon cho trang chủ
//        tabLayout.getTabAt(1).setIcon(R.drawable.icontimkiem); // set icon cho tìm kiếm

        // sự kiện khi nhấn vào từng menu bên tay trái
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Home()).commit();
                        break;
                    case R.id.nav_profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Profile_Group()).commit();
                        break;
                    case R.id.nav_playlist:
                        // bắt buộc phải đăng nhập mới cho tạo playlist
                        if (listAccount != null && listAccount.size() > 0) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Create_Playlists()).commit();
                            break;
                        } else {
                            // chưa có dữ liệu thì gửi qua trang login
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            //intent.putExtra("state", state);
                            startActivity(intent);
                        }

                    case R.id.nav_favorite:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Vote()).commit();
                        break;
                    case R.id.nav_downloaded:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Downloaded()).commit();
                        break;
                    case R.id.nav_send:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Send()).commit();
                        break;
                    case R.id.nav_logout:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Share()).commit();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        break;

                }
                // sau khi mở thì tắt menu bên trái đi
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
//        itemNavHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Toast.makeText(MainActivity.this, "Xin chào", Toast.LENGTH_LONG).show();
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Home()).commit();
//
//            }
//        });


        // sự kiện khi nhấn vào Floating Button
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(MainActivity.this, "Bạn đã chọn", Toast.LENGTH_LONG).show();
////                displayFloatingButton();
//                if (isHide==false) {
//                    displayFloatingButton();
//                    isHide = true;
//                } else {
//                    hideFloatingButton();
//                    isHide = false;
//                }
//            }
//        });
//        fab_facebook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "FAB Facebook", Toast.LENGTH_LONG).show();
//            }
//        });
//        fab_youtube.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "FAB Youtube", Toast.LENGTH_LONG).show();
//            }
//        });
//        fab_twitter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "FAB Twitter", Toast.LENGTH_LONG).show();
//            }
//        });

    }
    // hàm nhấn vào Floating button sẽ hiển thị lên những nút khác
//    public void displayFloatingButton() {
//        fab_youtube.show();
//        fab_twitter.show();
//        fab_facebook.show();
//
//    }
//    // hàm ẩn Floating button
//    public void hideFloatingButton() {
//        fab_facebook.hide();
//        fab_twitter.hide();
//        fab_youtube.hide();
//    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void addControls() {
        // ánh xạ component


        BottomNav.OnTabSelectedListener listener = new BottomNav.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                if (position == 0) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Home()).commit();
                } if (position == 1) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Search()).commit();
                }
                else {
                    Toast.makeText(MainActivity.this, "Click " + position, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTabLongSelected(int position) {
                Toast.makeText(MainActivity.this, "Long posicao" + position, Toast.LENGTH_SHORT).show();
            }
        };
        // ánh xạ cho phần menu và drawer layout
        toolbar = (Toolbar) findViewById(R.id.toolBarMain);
        setSupportActionBar(toolbar);
        if (listAccount != null && listAccount.size() > 0) {
            getSupportActionBar().setTitle("Hi user: " + listAccount.get(0).getUsername());
        }
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        viewPager = (ViewPager) findViewById(R.id.myViewPaper);
//        fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab_facebook = (FloatingActionButton) findViewById(R.id.fab_facebook);
//        fab_twitter = (FloatingActionButton) findViewById(R.id.fab_twitter);
//        fab_youtube = (FloatingActionButton) findViewById(R.id.fab_youtube);

        //tabLayout = (TabLayout) findViewById(R.id.myTabLayout);
        final BadgeIndicator badgeIndicator = new BadgeIndicator(this, android.R.color.holo_red_dark, android.R.color.white);

        bottomNav = findViewById(R.id.bottomNav);
        itemNavHome = new ItemNav(this, R.drawable.feed, R.drawable.feed_sel, "Home");
        bottomNav.addItemNav(itemNavHome);
        itemNavSearch = new ItemNav(this, R.drawable.explore, R.drawable.explore_sel, "Search");
        bottomNav.addItemNav(itemNavSearch);
        itemNavNoti = new ItemNav(this, R.drawable.atividades, R.drawable.atividades_sel, "Notification").addColorAtive(android.R.color.holo_blue_bright);
        bottomNav.addItemNav(itemNavNoti);
        itemNavProfile = new ItemNav(this, R.drawable.perfil, R.drawable.perfil_sel, "Profile").addProfileColorAtive(android.R.color.holo_red_dark).addProfileColorInative(android.R.color.black);
        bottomNav.addItemNav(itemNavProfile);

//        bottomNav.addItemNav(new ItemNav(this, R.drawable.feed, R.drawable.feed_sel, "Home").addBadgeIndicator(badgeIndicator));
//        bottomNav.addItemNav(new ItemNav(this, R.drawable.explore, R.drawable.explore_sel, "Search"));
//        bottomNav.addItemNav(new ItemNav(this, R.drawable.atividades, R.drawable.atividades_sel, "Notification").addColorAtive(android.R.color.holo_blue_bright));
//        bottomNav.addItemNav(new ItemNav(this, R.drawable.perfil, R.drawable.perfil_sel, "Profile").isProfileItem().addProfileColorAtive(android.R.color.holo_red_dark).addProfileColorInative(android.R.color.black));
        bottomNav.setTabSelectedListener(listener);
        bottomNav.build();

    }
}
