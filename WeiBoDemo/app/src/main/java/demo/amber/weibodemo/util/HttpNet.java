package demo.amber.weibodemo.util;


import android.util.Log;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import demo.amber.weibodemo.bean.UserBean;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by amber on 16/11/3.
 */

public class HttpNet {

    private HttpNet httpNet;


    private static final String URL = "https://api.weibo.com/2/";
    //TimeOut时间
    private static final int DEFAULT_TIMEOUT =2;

    private static Oauth2AccessToken accessToken;

    //retrofit http接口
    private WeiBoService weiBoService;


    public static HttpNet getInstance(){
        return new SingleInstance().singleInstance;
    }

    private static class SingleInstance{
        private static final HttpNet singleInstance = new HttpNet();
    }


    private  HttpNet() {

//        OkHttpClient okhttp3 = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//
//                Request request = chain.request();
//                HttpUrl httpUrl = request.url();
//                HttpUrl newHttpUrl = httpUrl.newBuilder()
//                        .addQueryParameter("access_token",MyApplication.getAccessToken().getToken())
//                        .addQueryParameter("uid","")
//                        .build();
//                Request newRequest = request.newBuilder()
//                        .url(newHttpUrl)
//                        .build();
//                Response response = chain.proceed(newRequest);
//
//                return response;
//            }
//        }).build();
        OkHttpClient.Builder okHttp3 = new OkHttpClient.Builder();
        okHttp3.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttp3.build())
                .build();

         weiBoService = retrofit.create(WeiBoService.class);




    }


    private SsoHandler mSsoHandler;
    private Oauth2AccessToken mAccessToken;
    private AuthInfo mAuthInfo;

    //model层进行登陆界面
    public void load(SsoHandler mSsoHandler,WeiboAuthListener weiboAuthListener){
        this.mSsoHandler = mSsoHandler;
        Log.d("TAG","load");
        mSsoHandler.authorize(weiboAuthListener);

    }

    //获取用户信息
    public void getUserInfor(){


        Map<String ,String> map = new HashMap<>();
        map.put("access_token",MyApplication.getAccessToken().getToken());
        map.put("uid",MyApplication.getAccessToken().getUid());
        Log.d("TAG","Token:"+MyApplication.getAccessToken().getToken());
        weiBoService.getUserInfo(map)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG","ERROR："+e.getMessage());
                    }

                    @Override
                    public void onNext(UserBean userBean) {
                            Log.d("TAG","user:"+userBean.getName()+" "+userBean.getGender()+" "+userBean.getLocation());

                    }
                });

    }


    private interface WeiBoService{


        @GET("users/show.json")
        Observable<UserBean> getUserInfo(@QueryMap(encoded = true) Map<String,String> options);


    }
}
