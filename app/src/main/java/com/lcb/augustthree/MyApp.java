package com.lcb.augustthree;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class MyApp extends Application {

    private static MyApp instance;
    private List<SearchBean> list;

    public List<SearchBean> getList() {
        return list;
    }

    public static MyApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        list = new ArrayList<>();
    }

}
