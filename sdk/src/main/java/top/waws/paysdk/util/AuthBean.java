package top.waws.paysdk.util;

/**
 *  功能描述: 用户认证实体类
 *  @className: AuthBean
 *  @author: thanatos
 *  @createTime: 2018/6/27
 *  @updateTime: 2018/6/27 13:48
 */
public class AuthBean {

    public boolean success; //是否成功
    public String authCode; //授权码
    public String resultCode;//返回值 当且仅当 为200 时，为授权成功
    public String openId;
    public String userId; //用户id
}
