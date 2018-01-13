package com.gaiay.library.tablayout.adapter;

import android.content.Context;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Created by RenTao on 2017/9/17.
 */

public class TitleTabAdapter extends CommonTabAdapter {
    private List mTexts;

    public TitleTabAdapter(Context context, List texts) {
        super(context);
        this.mTexts = texts;
    }

    @Override
    public void onPrepare(ViewGroup parent) {
        List<Tab> tabs = new ArrayList<>(mTexts.size());
        for (Object text : mTexts) {
            if (text instanceof String) {
                tabs.add(newTextTab(String.valueOf(text)));
            }
            else if (text instanceof Integer) {
                tabs.add(newTextTab((Integer) text));
            }
        }
        setTabs(tabs);
    }

    public void setSelectedColor(int color) {
        this.mTextSelectedColor = color;
    }

    public void setUnselectedColor(int color) {
        this.mTextUnselectedColor = color;
    }

    public void setTitleSize(int size) {
        this.mTextSize = size;
    }
}