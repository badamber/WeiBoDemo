package demo.amber.weibodemo.baseTool;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import demo.amber.weibodemo.R;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initView(savedInstanceState);
        initData();
    }

    //初始化view
    protected abstract void initView(Bundle savedInstanceState);
    //初始化data
    protected abstract void initData();

    public void Toast(String toastString){

        Toast.makeText(this,toastString,Toast.LENGTH_SHORT).show();
    }

}
