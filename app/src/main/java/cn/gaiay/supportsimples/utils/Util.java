package cn.gaiay.supportsimples.utils;

import android.widget.Toast;

import cn.gaiay.supportsimples.App;


/**
 * <p>Created by RenTao on 2017/6/27.
 */

public class Util {

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void toast(String message) {
        Toast.makeText(App.app, message, Toast.LENGTH_SHORT).show();
    }
}
