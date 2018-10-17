package top.waws.paysdk.core;

import android.app.Activity;

/**
 *  功能描述:
 *  @className: PayFactory
 *  @author: thanatos
 *  @createTime: 2018/6/14
 *  @updateTime: 2018/6/14 15:10
 */
public class PayFactory {

    /**
     * 当前类唯一实类对象
     */
    private static class Inner{
        private static final PayFactory FACTORY = new PayFactory();
    }

    public static PayFactory getDefault(){
        return Inner.FACTORY;
    }

    /**
     * 获取支付宝支付对象
     * @param activity
     * @param params
     * @return
     */
    public AliPay getAliPay(Activity activity, String params){
        return new AliPay(activity,params);
    }

    /**
     * 获取微信支付对象
     * @param params
     * @return
     */
    public WXPay getWXPay(String params){
        return new WXPay(params);
    }

    /**
     * 获取支付宝授权登录对象
     * @param activity
     * @param params
     * @return
     */
    public AliAuth getAliAuth(Activity activity, String params){
        return new AliAuth(activity,params);
    }

}
