package demo.amber.weibodemo.model;

/**
 * Created by amber on 16/11/3.
 */

public interface IUser {

    public String getName();
    public String getPassWd();

    boolean checkVisiable(String userName,String passwd);
}
