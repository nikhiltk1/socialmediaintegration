package nikhil.com.facebooksdk.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by Sharath on 6/25/2016.
 */
public class App extends Application
{
    private static Context context;


    @Override
    public void onCreate()
    {
        super.onCreate();

        context = this.getApplicationContext();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

    public static Context getContext()
    {
        return context;
    }

}
