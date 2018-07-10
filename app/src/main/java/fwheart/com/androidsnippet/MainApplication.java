package fwheart.com.androidsnippet;

import android.app.Application;

/**
 * Created by yeming on 2018/7/10.
 */

public class MainApplication extends Application {
    private static MainApplication context;
    public static MainApplication getContext(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
