package top.waws.paysdk.util;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import java.lang.ref.WeakReference;

import top.waws.paysdk.PaySDK;
import top.waws.paysdk.core.WXPay;

/**
 *  @desc: 微信支付事件回调
 *  @className: WXAPIEventHandler
 *  @author: thanatos
 *  @createTime: 2018/10/16
 *  @updateTime: 2018/10/16 上午11:05
 */
public class WXAPIEventHandler implements IWXAPIEventHandler {

    private WeakReference<Activity> activityWeakReference;

    private static class Inner{
        private static final WXAPIEventHandler HANDLER = new WXAPIEventHandler();
    }

    public static WXAPIEventHandler getDefault(){
        return Inner.HANDLER;
    }

    /**
     * 绑定activity
     * @param activity 吊起支付的活动页面
     */
    public void attachActivity(@NonNull Activity activity){
        activityWeakReference = new WeakReference<>(activity);
        if(PaySDK.getWXAPI() != null && activityWeakReference != null && activityWeakReference.get() != null) {
            PaySDK.getWXAPI().handleIntent(activityWeakReference.get().getIntent(), this);
        } else {
            if (activityWeakReference != null && activityWeakReference.get() != null){
                activityWeakReference.get().finish();
            }
        }
    }

    /**
     * 和活动页面的onNewIntent方法绑定
     * @param intent
     */
    public void attachNewIntent(Intent intent){
        if (activityWeakReference != null && activityWeakReference.get() != null){
            activityWeakReference.get().setIntent(intent);
            if(PaySDK.getWXAPI() != null) {
                PaySDK.getWXAPI().handleIntent(intent, this);
            }
        }
    }


    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if(baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            WXPay.onResponse(baseResp.errCode);
            if (activityWeakReference != null && activityWeakReference.get() != null){
                activityWeakReference.get().finish();
            }
        }
    }
}
