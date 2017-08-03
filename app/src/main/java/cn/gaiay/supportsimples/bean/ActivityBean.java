package cn.gaiay.supportsimples.bean;

/**
 * <p>Created by RenTao on 2017/6/18.
 */

public class ActivityBean {
    public String title;
    public Class<?> activity;

    public ActivityBean() {
    }

    public ActivityBean(String title, Class<?> activity) {
        this.title = title;
        this.activity = activity;
    }
}
