package top.waws.paysdk.callback;


import top.waws.paysdk.util.*;

/**
 *  功能描述: 支付宝授权登录回调
 *  @className: AliAuthCallBack
 *  @author: thanatos
 *  @createTime: 2018/6/27
 *  @updateTime: 2018/6/27 11:52
 */
public interface AliAuthCallBack {

    /**
     * 是否继续授权
     * @param isAliPayAvailable 支付宝是否安装
     * @return
     */
    boolean setStartAuth(boolean isAliPayAvailable);

    /**
     * 完成
     */
    void onComplete();

    /**
     * 授权成功
     * @param bean
     */
    void onSuccess(AuthBean bean);

    /**
     * 授权失败
     * @param msg
     */
    void onFailure(String msg);
}
