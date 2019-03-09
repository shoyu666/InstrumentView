package com.shoyu666.module.ui.circle;

import android.app.Application;
/**
 * @author xining
 * @date 2019/3/1
 */
public class MAPP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MDP_PX.mMAPP =this;
    }
}
