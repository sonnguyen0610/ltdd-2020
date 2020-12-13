package com.example.mp3app.service;

import com.example.mp3app.model.Account;
import com.example.mp3app.model.Acoustic;
import com.example.mp3app.model.Album;
import com.example.mp3app.model.BaiHat;
import com.example.mp3app.model.ChuDe;
import com.example.mp3app.model.ChuDeVaTheLoai;
import com.example.mp3app.model.PlayList;
import com.example.mp3app.model.QuangCao;
import com.example.mp3app.model.TheLoai;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


// này chỉ là interface có các phương thức
public interface DataService {
    // gửi tương tác lên Server
    // vì file JSON là thuộc dạng mảng các Object nên trả về phải là dạng mảng ==> dùng List<đối tượng gì đó>

    // lấy dữ liêu quảng cáo
    @GET("songbanner.php")
    Call<List<QuangCao>> getDataBanner();

    // lấy dữ liêu PlayList trên CSDL
    @GET("playlistforcurrentday.php")
    Call<List<PlayList>> getPlayListCurrentDay();

    // lấy dữ liêu chủ đề và thể loại trên CSDL
    @GET("chudevatheloaitrongngay.php")
    Call<ChuDeVaTheLoai> getCategoryMusic();



    // lấy dữ liệu album
    @GET("albumhot.php")
    Call<List<Album>> getDataAlbum();

    // lấy dữ liệu acoustic
    @GET("acoustic.php")
    Call<List<Acoustic>> getDataAcoustic();

    // lấy dữ liệu BaiHat
    @GET("baihatyeuthich.php")
    Call<List<BaiHat>> getDataBaiHat();

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> getDataBaiHatTheoQuangCao(@Field("idquangcao") String idquangcao);

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> getDataBaiHatTheoPlayList(@Field("idplaylist") String idplaylist);

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> getDataBaiHatTheoAlbum(@Field("idalbum") String idalbum);

    // lấy dữ liệu thể loại dựa vào chủ đề
    @FormUrlEncoded
    @POST("theloaitheochude.php")
    Call<List<TheLoai>> getDataTheLoaiTheoChuDe(@Field("idchude") String idchude);

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> getDataBaiHatTheoAcoustic(@Field("idacoustic") String idacoustic);

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> getDataBaiHatTheoTheLoai(@Field("idtheloai") String idtheloai);


    // trả về chuỗi
    @FormUrlEncoded
    @POST("updateluotthich.php")
    Call<String> updateLuotThich(@Field("luotthich") String luothich, @Field("idbaihat") String idbaihat);


    // lấy dữ liệu playlist
    @GET("danhsachplaylist.php")
    Call<List<PlayList>> getDataPlayList();

    // lấy dữ liệu tất cả album
    @GET("allalbum.php")
    Call<List<Album>> getDataAllAlbum();

    // lấy dữ liệu tất cả chủ đề
    @GET("allchude.php")
    Call<List<ChuDe>> getDataAllChuDe();


    // login, trả về mảng dữ liệu của Account
    // tất cả những biến đưa vào để gửi lên Server phải trùng nhau
    @FormUrlEncoded
    @POST("login.php")
    Call<List<Account>> getDataAccount(@Field("username") String username, @Field("password") String password);

    // lấy dữ liệu tìm kiếm
    @FormUrlEncoded
    @POST("searchbaihat.php")
    Call<List<BaiHat>> getDataSearchBaiHat(@Field("tukhoa") String tukhoa);
}
