package demo.amber.weibodemo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by amber on 16/11/7.
 *
 * 该类主要用于全局变量的恢复
 */

public class Tools {

    //保存Object
    public static final void saveObject(String path,Object saveObject){

        FileOutputStream outputStream = null ;
        ObjectOutputStream objectOutputStream = null;
        try {
            File file = new File(path);
            if(!file.exists()){
                file.mkdir();
            }
             outputStream = new FileOutputStream(file);

             objectOutputStream= new ObjectOutputStream(outputStream);

             objectOutputStream.writeObject(saveObject);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                outputStream.close();
                objectOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    //重铸Object
    public static Object restoreObject(String path){

        FileInputStream inputStream =null;
        ObjectInputStream objectInputStream = null;

        Object object = null;

        File file = new File(path);

        if(!file.exists()){
            return null;
        }
        try {
            inputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(inputStream);
            object = objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
                objectInputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return object;
    }
}
