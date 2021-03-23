package com.example.nexttask.FirstFragmentMVP;

import com.example.nexttask.Network.APIService;
import com.example.nexttask.Network.NetworkClient;
import com.example.nexttask.Network.Today;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class BaseModel  implements FirstFragmentContract.allProcess {

    private final Callback callback;


    public BaseModel(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void startRetrofit() {
        APIService apiService = NetworkClient.getClient().create(APIService.class);
        Call<List<Today>> call = apiService.searchToday(NetworkClient.API_KEY, "true");
        call.enqueue(new retrofit2.Callback<List<Today>>() {
            @Override
            public void onResponse(Call<List<Today>> call, Response<List<Today>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    String date = response.body().get(1).getDateTime();
                    String temp = response.body().get(1).getTemp();
                    String icon = response.body().get(2).getIcon();
                    String day = response.body().get(3).getLight();
                    //Odesa "Key": "325343"
                    callback.getTodayResult(temp, icon, day);
                }

            }

            @Override
            public void onFailure(Call<List<Today>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    interface Callback {
        void getTodayResult(String degree, String status, String light);
    }

}
