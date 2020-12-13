package com.example.mp3app.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Account implements Parcelable {

    @SerializedName("Id_User")
    @Expose
    private String idUser;
    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("HinhAnh")
    @Expose
    private String hinhAnh;
    @SerializedName("Role")
    @Expose
    private String role;

    public Account(String idUser, String username, String password, String hinhAnh, String role) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.hinhAnh = hinhAnh;
        this.role = role;
    }

    protected Account(Parcel in) {
        idUser = in.readString();
        username = in.readString();
        password = in.readString();
        hinhAnh = in.readString();
        role = in.readString();
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idUser);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(hinhAnh);
        dest.writeString(role);
    }
}