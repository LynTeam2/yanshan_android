package cn.gov.bjys.onlinetrain.utils;

/**
 * Created by Administrator on 2017/8/2 0002.
 */
public class YSConst {

    public static final int USERNAME_LENGTH_MAX = 11;
    public static final int PASSWORD_LENGTH_MIN = 6;
    public static final int PASSWORD_LENGTH_MAX = 20;

    //是否为首次登陆app
    public final static String NOT_FIRST_LOGIN = "not_first_login";

    //assets文件目录下的 update文件夹名字
    public final static String UPDATE_ZIP = "update";
    public static class UserInfo{
        public final static String KEY_USER_TOKEN  = "key_user_token";
        public final static String USER_AVATAR_PATH = "user_avatar_path";
        public final static String USER_SAVE_NICK = "user_save_nick";

     }
}
