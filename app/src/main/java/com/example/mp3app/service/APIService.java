package com.example.mp3app.service;

public class APIService {
    public static String base_url = "https://ttdien.000webhostapp.com/Server/";

    public static DataService getService() {
        return APIRetrofitClient.getClient(base_url).create(DataService.class);
    }
}
