package com.example.mp3app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mp3app.R;
import com.example.mp3app.adapter.SearchBaiHatAdapter;
import com.example.mp3app.model.BaiHat;
import com.example.mp3app.service.APIService;
import com.example.mp3app.service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_Search extends Fragment {
    View view;
    //Toolbar toolbar;
    RecyclerView rvSearch;
    TextView txtNoData;
    SearchBaiHatAdapter searchBaiHatAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        //toolbar = view.findViewById(R.id.toolBarSearch);
        rvSearch = view.findViewById(R.id.rvSearch);
        txtNoData = view.findViewById(R.id.txtNoData);
        //((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        //toolbar.setTitle("");
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_seach_bai_hat, menu);
        MenuItem menuItem = menu.findItem(R.id.mnuSearchBaiHat);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchData(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, menuInflater);
    }
    public void searchData(String query) {
        DataService dataService = APIService.getService();
        // chỉ update 1 lần 1 lượt thích, update theo id bài hát
        Call<List<BaiHat>> callBack = dataService.getDataSearchBaiHat(query);
        // gửi lên Server
        callBack.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                ArrayList<BaiHat> listBaiHat = (ArrayList<BaiHat>) response.body();
                if (listBaiHat.size()>0) {
                    searchBaiHatAdapter = new SearchBaiHatAdapter(getActivity(), listBaiHat);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    rvSearch.setLayoutManager(linearLayoutManager);
                    rvSearch.setAdapter(searchBaiHatAdapter);
                    txtNoData.setVisibility(View.GONE);
                    rvSearch.setVisibility(View.VISIBLE);
                } else {
                    rvSearch.setVisibility(View.GONE);
                    txtNoData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }
}
