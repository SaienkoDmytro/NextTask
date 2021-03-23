package com.example.nexttask.FirstFragmentMVP;

public class FirstFragmentPresenter implements FirstFragmentContract.Presenter, BaseModel.Callback {

        private FirstFragmentContract.View view;
        private FirstFragmentContract.allProcess process;


    public FirstFragmentPresenter(FirstFragmentContract.View view) {
            this.view = view;
            this.process = new BaseModel(this);
        }

        @Override
        public void doWork() {
            view.showResult();
            process.startRetrofit();
        }

        @Override
        public void onDestroy() {
            view = null;
            process = null;
        }

        @Override
        public void getTodayResult(String degree, String status, String light) {
            view.setTodayResult(degree, status, light);
        }

    }
