package top.waws.paysdk.core;

import android.text.TextUtils;
import android.util.Log;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;

import top.waws.paysdk.PaySDK;
import top.waws.paysdk.callback.*;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *  功能描述: 微信支付
 *  @className: WXPay
 *  @author: thanatos
 *  @createTime: 2018/6/14
 *  @updateTime: 2018/6/14 14:59
 */
public final class WXPay {

    private String mPayParam;
    private static PayResult.WXPayCallBack mCallback;

    WXPay(String pay_param) {
        this.mPayParam = pay_param;
    }
    /**
     * 发起微信支付
     */
    public void pay(PayResult.WXPayCallBack callback) {
        if (callback == null) return;
        if (PaySDK.getWXAPI() == null){
            if (PaySDK.isDEBUG()){
                Log.e("PaySDK", "请先调用 PaySDK 的 init 方法" );
            }
            return;
        }
        mCallback = callback;

        //是否开始支付拦截 true：开始支付  false：终止支付
        if (!mCallback.setStartPay(PaySDK.isWeixinAvilible())){
            mCallback.complete();
            return;
        }

        if(!check()) {
            mCallback.error(PayResult.WXPayCallBack.NO_OR_LOW_WX);
            mCallback.complete();
            return;
        }
        JSONObject param = null;
        try {
            param = new JSONObject(mPayParam);
        } catch (JSONException e) {
            e.printStackTrace();
            if(mCallback != null) {
                mCallback.error(PayResult.WXPayCallBack.ERROR_PAY_PARAM);
                mCallback.complete();
            }
            return;
        }
        if(TextUtils.isEmpty(param.optString("appid"))
                || TextUtils.isEmpty(param.optString("partnerid"))
                || TextUtils.isEmpty(param.optString("prepayid"))
                || TextUtils.isEmpty(param.optString("package"))
                || TextUtils.isEmpty(param.optString("noncestr"))
                || TextUtils.isEmpty(param.optString("timestamp"))
                || TextUtils.isEmpty(param.optString("sign"))) {
            if(mCallback != null) {
                mCallback.error(PayResult.WXPayCallBack.ERROR_PAY_PARAM);
            }
            return;
        }
        PayReq req = new PayReq();
        req.appId = param.optString("appid");
        req.partnerId = param.optString("partnerid");
        req.prepayId = param.optString("prepayid");
        req.packageValue = param.optString("package");
        req.nonceStr = param.optString("noncestr");
        req.timeStamp = param.optString("timestamp");
        req.sign = param.optString("sign");

        PaySDK.getWXAPI().sendReq(req);
    }

    /**
     * 支付回调响应
     * @param code
     */
    public static void onResponse(int code) {
        if(mCallback == null) {
            return;
        }
        mCallback.complete();
        if(code == 0) {   //成功
            mCallback.success();
        } else if(code == -1) {   //错误
            mCallback.error(PayResult.WXPayCallBack.ERROR_PAY);
        } else if(code == -2) {   //取消
            mCallback.cancel();
        }

        mCallback = null;
    }

    /**
     * 检测是否支持微信支付
     * @return
     */
    private boolean check() {
        return PaySDK.getWXAPI().isWXAppInstalled()
                && PaySDK.getWXAPI().getWXAppSupportAPI()
                >= Build.PAY_SUPPORTED_SDK_INT;
    }
}
