package cn.kellygod.schoolclient.connection;

/**
 * @author kellygod on 2016/11/4.
 */
public class CommonName {
    private static final String 	TAG					=   "CommonName";
    public static final String      Domain_URL          =   "https://202.101.111.206/";
    public static final String 		Base_URL			=   Domain_URL+"web/1/http/0/";
    public static final String 		User_Agent_Desktop	=   "Mozilla/5.0  AppleWebKit/537.36 "
                                                                + "(KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";
    public static final String 		User_Agent			=   "(Linux; U; Android 6.0; en-us;)"+User_Agent_Desktop;
    public static final String 		SSL_VPN_URL 		=   Domain_URL+"por/login_psw.csp";
    public static final String 		ZHI_NET_URL 		=   Base_URL+"www.cnki.net/";
    public static final String 		EDUCATION_SYSTEM 	=   Base_URL+"172.16.8.201/";
    public static final String      EDUCATION_DEFAULT   =   Base_URL+"172.16.8.201/default3.aspx";
    public static final String 		SSL_VPN_SFRNd		=   "?sfrnd=2346912324982305&encrypt=1";
    public static final String      SSL_VPN_LOGIN       =   SSL_VPN_URL+SSL_VPN_SFRNd;

    public static final String      EDUCATION_CHECKCODE =   EDUCATION_SYSTEM+"CheckCode.aspx";

    //Handler接收码
    public static final int         STATUS_COMMON_ERROR         =   0;
    public static final int         STATUS_COMMON_OK            =   1;
    public static final int         STATUS_CHECK_CODE_OK        =   2;
    public static final int         STATUS_CHECK_CODE_ERROR     =   3;
    public static final int         STATUS_GET_RESOURCE_ERROR   =   4;
    public static final int         STATUS_GET_RESOURCE_OK      =   5;
    public static final int         STATUS_RESOURCE_Table       =   6;
    //使用学生模式登陆
    public static final int         STUDENT_MODE        =   1;

}
