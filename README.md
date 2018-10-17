# PaySDK
支付工具类（微信支付、支付宝支付和支付宝授权登录）

## 使用
### 1. 初始化

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 初始化支付工具类
         */
        PaySDK.init(this,"3216sd876868ds67",true);
    }

### 2.使用

#### 微信支付

        /**
         * 微信支付
         */
        PaySDK.getInstance().wxPay("此处填写json字符串")
                .pay(new PayResult.WXPayCallBack() {
                    /**
                     * 是否继续支付
                     * @param isWXAvailable 是否微信安装了
                     * @return  true:继续支付  false:停止支付
                     */
                    @Override
                    public boolean setStartPay(boolean isWXAvailable) {
                        return isWXAvailable;
                    }

                    @Override
                    public void success() {
                        PayErrorUtil.showMsg("支付成功");
                    }

                    @Override
                    public void error(int code) {
                        PayErrorUtil.showWXError(code);
                    }

                    @Override
                    public void cancel() {
                        PayErrorUtil.showCancel();
                    }

                    @Override
                    public void complete() {
                        //取消弹窗之类的进度view
                    }
                });
                
#### 支付宝支付
        /**
         * 支付宝支付
         */
        PaySDK.getInstance().aliPay(this,"params")
                .pay(new PayResult.AliPayCallBack() {
                    /**
                     * 是否继续支付
                     * @param isAliPayAvailable 是否支付宝安装了
                     * @return true:继续支付  false:停止支付
                     */
                    @Override
                    public boolean setStartPay(boolean isAliPayAvailable) {
                        return isAliPayAvailable;
                    }

                    @Override
                    public void success() {
                        PayErrorUtil.showMsg("支付成功");
                    }

                    @Override
                    public void processing() {
                        PayErrorUtil.showMsg("正在处理中...");
                    }

                    @Override
                    public void error(int code) {
                        PayErrorUtil.showAliPayError(code);
                    }

                    @Override
                    public void cancel() {
                        PayErrorUtil.showCancel();
                    }

                    @Override
                    public void complete() {

                    }
                });
#### 支付宝授权登录
        /**
         * 支付宝授权登录
         */
        PaySDK.getInstance().aliAuth(this,"params")
                .auth(new AliAuthCallBack() {
                    /**
                     * 是否继续授权
                     * @param isAliPayAvailable 支付宝是否安装
                     * @return true:继续授权  false:停止授权
                     */
                    @Override
                    public boolean setStartAuth(boolean isAliPayAvailable) {
                        return isAliPayAvailable;
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSuccess(AuthBean bean) {

                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });
