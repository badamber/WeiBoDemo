package demo.amber.weibodemo.view;

import android.content.Context;

import demo.amber.weibodemo.bean.UserBean;

/**
 * Created by amber on 16/11/3.
 */

public interface LoginView extends BaseView {

    //展示dialog框
    void showProgressDialog();
    //隐藏Dialog
    void hideProgressDialog();

    //显示user信息
    void showUser(UserBean userBean);

    Context getCurContext();
}
