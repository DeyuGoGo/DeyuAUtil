package go.deyu.util;

import android.content.Context;

public class AppUtil {
    private static volatile AppUtil INSTANCE;
    
    private Context appContext;
    
    private AppUtil(Context context){
        this.appContext = context;
    }

    public static AppUtil initialize(Context context){
        if(INSTANCE!=null){
            return INSTANCE;
        }
        synchronized (AppUtil.class) {
            if(INSTANCE==null){
                INSTANCE = new AppUtil(context);

            }
        }
        return INSTANCE;
    }

    
    public static Context getApplicationContext() {
        return INSTANCE.appContext;
    }

    public String getApplicationName() {
        int stringId = appContext.getApplicationInfo().labelRes;
        return appContext.getString(stringId);
    }

}
