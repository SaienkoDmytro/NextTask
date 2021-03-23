package com.example.nexttask.MainActivityMVP;

public class MainPresenter implements MainContract.Presenter, MainModel.Callback {

    private MainContract.View view;
    private MainContract.mainProcess process;


    public MainPresenter(MainContract.View view) {
        this.view = view;
        this.process = new MainModel(this);
    }

    @Override
    public void doWork() {
        view.recievePermissions();
        process.checkLocation();
    }

    @Override
    public void onDestroy() {
        view = null;
        process = null;
    }

    @Override
    public void getPermissionResult(String keyCity) {
        view.permissionResult(keyCity);
    }

}
