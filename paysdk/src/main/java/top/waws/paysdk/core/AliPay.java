package top.waws.paysdk.core;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;

import top.waws.paysdk.PaySDK;
import top.waws.paysdk.callback.*;

import java.util.Map;

/**
 *  功能描述: 支付宝支付
 *  @className: AliPay
 *  @author: thanatos
 *  @createTime: 2018/6/14
 *  @updateTime: 2018/6/14 14:47
 */
public final class AliPay {

    private String mParams;
    private PayTask mPayTask;
    private PayHandler mHandler = new PayHandler();

    AliPay(Activity context, String params) {
        mParams = params;
        mPayTask = new PayTask(context);
    }

    /**
     * 开始支付
     * @param callBack 支付回调
     */
    public void pay(final PayResult.AliPayCallBack callBack) {
        if (callBack == null) return;
        //是否开始支付拦截 true：开始支付  false：终止支付
        if (!callBack.setStartPay(PaySDK.isAliPayAvailable())){
            callBack.complete();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Map<String, String> pay_result = mPayTask.payV2(mParams,true);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.complete();
                        if(pay_result == null) {
                            callBack.error(PayResult.AliPayCallBack.ERROR_RESULT);
                            return;
                        }
                        String resultStatus = pay_result.get("resultStatus");
                        if(TextUtils.equals(resultStatus, "9000")) {    //支付成功
                            callBack.success();
                        } else if(TextUtils.equals(resultStatus, "8000")) { //支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                            callBack.processing();
                        } else if(TextUtils.equals(resultStatus, "6001")) {		//支付取消
                            callBack.cancel();
                        } else if(TextUtils.equals(resultStatus, "6002")) {     //网络连接出错
                            callBack.error(PayResult.AliPayCallBack.ERROR_NETWORK);
                        } else if(TextUtils.equals(resultStatus, "4000")) {        //支付错误
                            callBack.error(PayResult.AliPayCallBack.ERROR_PAY);
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * handler
     */
    static final class PayHandler extends Handler{
        PayHandler(){
            super(Looper.getMainLooper());
        }
    }
}
