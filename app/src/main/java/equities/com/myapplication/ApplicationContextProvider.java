package equities.com.myapplication;

import android.app.Application;
import android.content.Context;

public class ApplicationContextProvider extends Application {
    private static ApplicationContextProvider instance;
    private static Context mContext;

    public static ApplicationContextProvider getInstance() {
        return instance;
    }

    public static Context getContext() {
        //  return instance.getApplicationContext();
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //  instance = this;
        mContext = getApplicationContext();
    }
}