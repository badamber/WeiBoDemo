package demo.amber.weibodemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import demo.amber.weibodemo.R;
import demo.amber.weibodemo.baseTool.BaseActivity;
import demo.amber.weibodemo.bean.UserBean;
import demo.amber.weibodemo.presenters.HomePresenter;
import demo.amber.weibodemo.util.AccessTokenKeeper;
import demo.amber.weibodemo.util.Constants;
import demo.amber.weibodemo.util.HttpNet;
import demo.amber.weibodemo.view.LoginView;

public class LoginActivity extends BaseActivity implements LoginView {

    private HomePresenter homePresenter;
    private SsoHandler mSsoHandler;

    //账号和密码
    @BindView(R.id.login_userName)
     EditText userName;


    @BindView(R.id.login_passwords)
     EditText passWord;



    @Override
    protected void initView(Bundle savedInstanceState) {
    setContentView(R.layout.activity_login);
    ButterKnife.bind(this);

    homePresenter  = new HomePresenter();
    homePresenter.attachView(this);


    }

    @Override
    protected void initData() {
        AuthInfo authInfo = new AuthInfo(this, Constants.APP_KEY,Constants.REDIRECT_URL,Constants.COPE);

        mSsoHandler = new SsoHandler(this,authInfo);
    }

    @Override
    public void showErrorMessage(String errorText) {
        Toast.makeText(this,errorText,Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.login_load)
    void login(View view){

        homePresenter.loadUser(userName.getText().toString(),passWord.getText().toString(),mSsoHandler);


    }




    @Override
    public void showProgressDialog() {
    }

    @Override
    public void hideProgressDialog() {


    }

    @Override
    public void showUser(UserBean userBean) {

    }

    public Context getCurContext(){
        return this;
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        homePresenter.detachView(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //SSO发出请求时，所需要重写的activityResults

            Log.d("TAG","ActivityResult");
            mSsoHandler.authorizeCallBack(requestCode,resultCode,data);

    }
}
