package demo.amber.weibodemo.presenters;

import demo.amber.weibodemo.view.BaseView;

/**
 * Created by amber on 16/11/2.
 */

public interface BasePresenter<T extends BaseView> {

    //和View进行绑定；
    void attachView(T mView);
    //和View解除绑定
    void detachView(T mView);
}
