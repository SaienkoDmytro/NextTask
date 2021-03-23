package com.example.nexttask.FirstFragmentMVP;

public class FirstFragmentContract {
    interface View {
        void setTodayResult(String degree, String status, String light);
        void showResult();
    }

    interface Presenter {
        void doWork();
        void onDestroy();
    }

    interface allProcess {
        void startRetrofit();
    }

}
