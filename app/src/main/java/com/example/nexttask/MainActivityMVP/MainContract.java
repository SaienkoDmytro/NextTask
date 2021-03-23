package com.example.nexttask.MainActivityMVP;

public class MainContract {
    public interface View {
        void permissionResult(String keyCity);
        void recievePermissions();
    }

    public interface Presenter {
        void doWork();
        void onDestroy();
    }

    interface mainProcess {
        void checkLocation();
    }

}
