package top.waws.paysdk.util;

import android.view.Gravity;
import android.widget.Toast;

import top.waws.paysdk.PaySDK;
import top.waws.paysdk.callback.*;

/**
 *  功能描述: 错误码显示工具类
 *  @className: ErrorUtil
 *  @author: thanatos
 *  @createTime: 2018/6/27
 *  @updateTime: 2018/6/27 14:05
 */
public class PayErrorUtil {

    private static Toast sToast = null;


    public static void showMsg(CharSequence s){
        if (s == null) return;
        showMsg(s,Gravity.BOTTOM);
    }

    public static void showMsg(CharSequence s, int gravity){
        if (s == null) return;
        if (sToast == null){
            sToast = Toast.makeText(PaySDK.getContext(),s,Toast.LENGTH_SHORT);
            sToast.setGravity(gravity,0,0);
        }else {
            sToast.setText(s);
        }
        sToast.show();
    }

    /**
     * 显示微信支付失败信息提示
     * @param code
     */
    public static void showWXError(int code){
        switch (code) {
            case PayResult.WXPayCallBack.NO_OR_LOW_WX:
                showMsg("未安装微信或微信版本过低");
                break;
            case PayResult.WXPayCallBack.ERROR_PAY_PARAM:
                showMsg("参数错误");
                break;
            case PayResult.WXPayCallBack.ERROR_PAY:
                showMsg("支付失败");
                break;
        }
    }


    /**
     * 显示支付宝支付失败信息提示
     * @param code
     */
    public static void showAliPayError(int code){
        switch (code) {
            case PayResult.AliPayCallBack.ERROR_PAY:
                showMsg("支付失败");
                break;
            case PayResult.AliPayCallBack.ERROR_NETWORK:
                showMsg("网络连接错误");
                break;
            case PayResult.AliPayCallBack.ERROR_RESULT:
                showMsg("支付结果解析错误");
                break;
        }
    }

    /**
     * 显示取消支付
     */
    public static void showCancel(){
        showMsg("支付取消");
    }



}
