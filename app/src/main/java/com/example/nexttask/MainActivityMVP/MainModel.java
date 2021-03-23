package com.example.nexttask.MainActivityMVP;

import com.example.nexttask.Network.APIService;
import com.example.nexttask.Network.City;
import com.example.nexttask.Network.NetworkClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MainModel implements MainContract.mainProcess {

    private final Callback callback;


    public MainModel(Callback callback) {
        this.callback = callback;
    }


    @Override
    public void checkLocation() {

        APIService apiService = NetworkClient.getClient().create(APIService.class);
        Call<List<City>> call = apiService.searchCity(NetworkClient.API_KEY, "Odesa");
        call.enqueue(new retrofit2.Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    String keyCity = response.body().get(0).getKey();
                    //Odesa "Key": "325343"
                    callback.getPermissionResult(keyCity);
                }

            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


    interface Callback {
        void getPermissionResult(String keyCity);
    }



}
