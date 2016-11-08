package demo.amber.weibodemo.util;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.io.Serializable;

/**
 * Created by amber on 16/11/7.
 */

public class MyApplication extends Application implements Serializable,Cloneable {


    private AccessTokenKeeper accessTokenKeeper;

    private static MyApplication myApplication;

    private static Context context;

    public static Oauth2AccessToken accessToken;//定义了AccessToken


    public  MyApplication(){

    };

    public static MyApplication getInstance(){

        if(myApplication==null){


        }else{


        }
        return new SingleInstance().myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TAG","OnCreate");
        context = this.getApplicationContext();
    }

    //以防当OnCreate无法被调用时，未能对accessToken进行初始化
    public static Oauth2AccessToken getAccessToken() {
        if(accessToken == null){
            accessToken = AccessTokenKeeper.readAccessToken(context);
        }

        return accessToken;
    }

    public static class SingleInstance{

        private static final MyApplication myApplication = new MyApplication();
    }


}
