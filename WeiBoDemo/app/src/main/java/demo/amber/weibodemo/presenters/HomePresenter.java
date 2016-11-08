package demo.amber.weibodemo.presenters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import demo.amber.weibodemo.model.UserModel;
import demo.amber.weibodemo.util.AccessTokenKeeper;
import demo.amber.weibodemo.util.HttpNet;
import demo.amber.weibodemo.view.BaseView;
import demo.amber.weibodemo.view.LoginView;

/**
 * Created by amber on 16/11/2.
 */

public class HomePresenter implements BasePresenter {


    private LoginView mView;
    private String userName,passWords;
    private Context context ;

    //Model中的userModel
    private UserModel userModel;

    public HomePresenter() {
        userModel = new UserModel();
    }

    //进行登陆
    public void loadUser(String userName, String passWords, SsoHandler ssoHandler){

        if(!userModel.checkVisiable(userName,passWords)){

            userModel.Login(ssoHandler,new AuthListener());
        }else
            mView.showErrorMessage("账号或者密码不符合规范");

    };


    public class AuthListener implements WeiboAuthListener {

        private Oauth2AccessToken mAccessToken ;
        @Override
        public void onComplete(Bundle bundle) {
            mAccessToken = Oauth2AccessToken.parseAccessToken(bundle);
            if(mAccessToken.isSessionValid()){
                AccessTokenKeeper.writeAccessToken(mView.getCurContext(),mAccessToken);
                Log.d("TAG","load success");
                HttpNet.getInstance().getUserInfor();

            }else{
                mView.showErrorMessage("哎呦喂，失败了，好尴尬:"+bundle.getString("code"));
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Log.d("TAG","login:"+e.getMessage());

        }

        @Override
        public void onCancel() {
            Log.d("TAG","cancel");

        }
    }


    @Override
    public void attachView(BaseView mView) {

        this.mView = (LoginView) mView;

    }

    @Override
    public void detachView(BaseView mView) {
        this.mView = null;
    }
}
