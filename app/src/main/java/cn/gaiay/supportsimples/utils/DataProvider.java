package cn.gaiay.supportsimples.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Created by RenTao on 2017/6/18.
 */

public class DataProvider {

    public static List<String> provideString(int size) {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            data.add("test data " + i);
        }
        return data;
    }

    public static List<String> provideString(String base, int size) {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            data.add(base + " " + i);
        }
        return data;
    }

    public static List<String> provideNumberString(int size) {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            data.add("" + i);
        }
        return data;
    }

}
