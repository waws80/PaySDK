package top.waws.paysdk.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import top.waws.paysdk.R;

/**
 *  @desc: 微信支付回调活动页面基类
 *  @className: BaseWXPayEntryActivity
 *  @author: thanatos
 *  @createTime: 2018/10/16
 *  @updateTime: 2018/10/16 上午11:16
 */
public abstract class BaseWXPayEntryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpay_call_back);

        WXAPIEventHandler.getDefault().attachActivity(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        WXAPIEventHandler.getDefault().attachNewIntent(intent);
    }
}
