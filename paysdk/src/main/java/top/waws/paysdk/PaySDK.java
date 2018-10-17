package top.waws.paysdk;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;

import top.waws.paysdk.core.*;
import top.waws.paysdk.util.*;

/**
 *  功能描述: 支付工具类
 *  @className: PaySDK
 *  @author: thanatos
 *  @createTime: 2018/6/14
 *  @updateTime: 2018/6/14 14:31
 */
public class PaySDK {

    private static IWXAPI WXAPI;

    private static boolean DEBUG = false;

    private static Application application;

    private static class Builder{
        private static final PaySDK DEVOTE_PAY = new PaySDK();
    }

    /**
     * 初始化微信支付参数
     * @param context
     * @param wx_appid
     * @param debug
     */
    public static void init( Application context, String wx_appid, boolean debug){
        WXAPI = WXAPIFactory.createWXAPI(context, null);
        WXAPI.registerApp(wx_appid);
        application = context;
        DEBUG = debug;
    }


    /**
     * 获取 IWXAPI
     * @return 获取之前请务必先调用 init
     */
    public static IWXAPI getWXAPI(){
        checkedInit();
        return WXAPI;
    }

    /**
     * 获取程序上下文对象
     * @return
     */
    public static Application getContext(){
        checkedInit();
        return application;
    }

    /**
     * 是否是debug模式
     * @return
     */
    public static boolean isDEBUG(){
        return DEBUG;
    }

    /**
     * 单例
     * @return
     */
    public static PaySDK getInstance(){
        return Builder.DEVOTE_PAY;
    }

    /**
     * 微信支付
     */
    public WXPay wxPay(String params){
        checkedInit();
        return PayFactory.getDefault().getWXPay(params);
    }


    /**
     * 支付宝支付
     * @param activity
     * @param params
     * @return
     */
    public AliPay aliPay(Activity activity, String params){
        checkedInit();
        return PayFactory.getDefault().getAliPay(activity, params);
    }


    /**
     * 支付宝授权登录
     * @param activity
     * @param params
     * @return
     */
    public AliAuth aliAuth(Activity activity, String params){
        checkedInit();
        return PayFactory.getDefault().getAliAuth(activity, params);
    }


    /**
     * 获取客户端ip
     * @return
     */
    public String getIp(){
        return NetUtil.getIpAddress();
    }


    /**
     * 判断微信是否安装
     * @return
     */
    public static boolean isWeixinAvilible() {
        checkedInit();
        final PackageManager packageManager = getContext().getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 判断qq是否可用
     * @return
     */
    public static boolean isQQClientAvailable() {
        checkedInit();
        final PackageManager packageManager = getContext().getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 检测是否安装支付宝
     * @return
     */
    public static boolean isAliPayAvailable() {
        checkedInit();
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(getContext().getPackageManager());
        return componentName != null;
    }


    /**
     * 判断是否初始化 PaySDK
     */
    private static void checkedInit(){
        if (WXAPI == null){
            if (DEBUG){
                Log.e("PaySDK", "请先调用 PaySDK 的 init 方法" );
            }
            throw new NullPointerException("请先调用 PaySDK 的 init 方法");
        }
    }
}
