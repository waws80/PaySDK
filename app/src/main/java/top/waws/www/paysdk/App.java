package top.waws.www.paysdk;

import android.app.Application;

import top.waws.paysdk.PaySDK;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PaySDK.init(this,"3216sd876868ds67",true);
    }
}
