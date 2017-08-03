package cn.gaiay.supportsimples;

import android.app.Application;

/**
 * <p>Created by RenTao on 2017/6/28.
 */

public class App extends Application {
    public static App app;

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;
    }
}
