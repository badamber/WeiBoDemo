package demo.amber.weibodemo.model;

import android.content.Context;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

import demo.amber.weibodemo.util.AccessTokenKeeper;
import demo.amber.weibodemo.util.HttpNet;

/**
 * Created by amber on 16/11/3.
 *
 * login时的model层代码，主要用于进行密码和账号判断；
 */

public class UserModel implements IUser {

    private String usename;
    private String password;



    public UserModel() {
    }

    public void setUsename(String usename) {
        this.usename = usename;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getName() {
        return usename;
    }

    @Override
    public String getPassWd() {
        return password;
    }

    @Override
    public boolean checkVisiable(String userName, String passwd) {

        return !userName.equals("")&&!passwd.equals("") ? true : false;
    }


    //登陆
    public static void Login(SsoHandler ssoHandler,WeiboAuthListener weiboAuthListener){

        HttpNet.getInstance().load(ssoHandler,weiboAuthListener);

    }

    //model层对token进行保存
    public boolean keepLoginTokenInfo(Context context,Oauth2AccessToken accessToken){

        if(accessToken.isSessionValid()){
            AccessTokenKeeper.writeAccessToken(context ,accessToken);
            return true;
        }else{

            return false;
        }
    }



}
