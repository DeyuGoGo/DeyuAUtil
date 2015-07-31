package go.deyu.util;

import android.content.Context;

/**
 * Created by huangeyu on 15/7/31.
 */
public class AppContextSingleton {
    private static AppContextSingleton INSTANCE;

    private Context appContext;

    private AppContextSingleton(Context context){
        this.appContext = context;
    }

    public static synchronized AppContextSingleton initialize(Context context){
        if (INSTANCE == null) {
            INSTANCE = new AppContextSingleton(context.getApplicationContext());
        }
        return INSTANCE;
    }

    public static Context getApplicationContext() {
        return INSTANCE.appContext;
    }
}