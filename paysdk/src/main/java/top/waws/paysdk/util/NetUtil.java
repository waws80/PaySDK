package top.waws.paysdk.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 *  功能描述: 工具类
 *  @className: Util
 *  @author: thanatos
 *  @createTime: 2018/6/14
 *  @updateTime: 2018/6/14 14:32
 */
public final class NetUtil {

    /**
     * 获取客户端ip
     * @return
     * 成功 ip
     * 失败 ""
     */
    public static String getIpAddress() {
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()){
                NetworkInterface intf = en.nextElement();
                Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
                while (enumIpAddr.hasMoreElements()){
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 判断网络是否连接
     *
     * @param context 上下文对象
     * @return true: 网络已连接  false: 网络断开
     */
    public static boolean isConnected(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != connectivity) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                return info.getState() == NetworkInfo.State.CONNECTED;
            }
        }
        return false;
    }


}
