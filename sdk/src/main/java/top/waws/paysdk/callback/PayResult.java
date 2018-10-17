package top.waws.paysdk.callback;

/**
 *  功能描述:
 *  @className: AliPayResultCallBack
 *  @author: thanatos
 *  @createTime: 2018/6/14
 *  @updateTime: 2018/6/14 14:38
 */
public interface PayResult {

    /**
     *  功能描述: 支付宝支付回调接口
     *  @className: PayResult
     *  @author: thanatos
     *  @createTime: 2018/6/14
     *  @updateTime: 2018/6/14 14:43
     */
    interface AliPayCallBack{

        int ERROR_RESULT = 1;   //支付结果解析错误
        int ERROR_PAY = 2;  //支付失败
        int ERROR_NETWORK = 3;  //网络连接错误

        /**
         * 是否继续吊起支付
         * @param isAliPayAvailable 是否支付宝安装了
         * @return
         */
        boolean setStartPay(boolean isAliPayAvailable);

        /**
         * 支付成功回调
         */
        void success();

        /**
         * 支付正在处理中
         */
        void processing();

        /**
         * 支付失败
         * @param code 见当前接口常量
         */
        void error(int code);

        /**
         * 支付取消
         */
        void cancel();

        /**
         * 支付流程走完了
         */
        void complete();
    }

    interface WXPayCallBack{

        int NO_OR_LOW_WX = 1;   //未安装微信或微信版本过低
        int ERROR_PAY_PARAM = 2;  //支付参数错误
        int ERROR_PAY = 3;  //支付失败

        /**
         * 是否继续吊起支付
         * @param isWXAvailable 是否微信安装了
         * @return
         */
        boolean setStartPay(boolean isWXAvailable);

        /**
         * 支付成功回调
         */
        void success();

        /**
         * 支付失败回调
         * @param code
         */
        void error(int code);

        /**
         * 支付取消回调
         */
        void cancel();

        /**
         * 支付流程走完了
         */
        void complete();
    }
}
