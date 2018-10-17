package top.waws.paysdk.core;

import android.app.Activity;
import android.text.TextUtils;

import com.alipay.sdk.app.AuthTask;

import java.util.Map;

import top.waws.paysdk.PaySDK;
import top.waws.paysdk.callback.*;
import top.waws.paysdk.util.*;

/**
 *  功能描述: 支付宝授权登录获取用户id
 *  @className: AliAuth
 *  @author: thanatos
 *  @createTime: 2018/6/27
 *  @updateTime: 2018/6/27 14:47
 */
public final class AliAuth {

    private String mParams;
    private AuthTask mAuthTask;
    private AliPay.PayHandler mHandler = new AliPay.PayHandler();

    AliAuth(Activity context, String params) {
        mParams = params;
        mAuthTask = new AuthTask(context);
    }

    /**
     * 开始授权登录
     * @param callBack 支付回调
     */
    public void auth(final AliAuthCallBack callBack) {
        if (callBack == null) return;
        //是否开始授权拦截 true：开始授权  false：终止授权
        if (!callBack.setStartAuth(PaySDK.isAliPayAvailable())){
            callBack.onComplete();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Map<String, String> auth_result = mAuthTask.authV2(mParams,true);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onComplete();
                        if(auth_result == null) {
                            callBack.onFailure("授权结果解析出错");
                            return;
                        }
                        String resultStatus = auth_result.get("resultStatus");
                        if(TextUtils.equals(resultStatus, "9000")) {    //授权回调成功
                            String result = auth_result.get("result");
                            if (result == null || result.isEmpty()){
                                callBack.onFailure("授权失败");
                            }else {
                                AuthBean bean = getBean(result);
                                if (bean != null && bean.resultCode.equals("200")){//授权真正成功
                                    callBack.onSuccess(bean);
                                }else {
                                    callBack.onFailure("授权失败");
                                }
                            }

                        } else if(TextUtils.equals(resultStatus, "4000")) { //授权异常
                            callBack.onFailure("系统异常");
                        } else if(TextUtils.equals(resultStatus, "6001")) {		//授权取消
                            callBack.onFailure("取消授权");
                        } else if(TextUtils.equals(resultStatus, "6002")) {     //网络连接出错
                            callBack.onFailure("当前网络不可用");
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * 将认证信息转换成实体类
     * @param result
     * @return
     */
    private AuthBean getBean( String result){
        String[] entries = result.split("&");
        if (entries.length == 0) return null;
        AuthBean bean = new AuthBean();
        for (String e : entries) {
            String[] entry = e.split("=");
            switch (entry[0]) {
                case "success":
                    bean.success = entry[1].equals("true");
                    break;
                case "auth_code":
                    bean.authCode = entry[1];
                    break;
                case "result_code":
                    bean.resultCode = entry[1];
                    break;
                case "alipay_open_id":
                    bean.openId = entry[1];
                    break;
                case "user_id":
                    bean.userId = entry[1];
                    break;
            }
        }
        return bean;
    }
}
