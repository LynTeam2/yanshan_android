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

        public final static String KEY_USER_ID = "key_user_id";

        public final static String USER_AVATAR_PATH = "user_avatar_path";
        public final static String USER_SAVE_NICK = "user_save_nick";


        public final static String USER_COLLECTION_IDS = "user_collection_ids";//用户收藏试题列表 1,2,3...如此格式
        public final static String USER_ERROR_IDS = "user_error_ids";//用户错题列表 1,2,3...如此格式

    }

    public static class BaseUrl{
        public final static String BASE_URL = "http://39.104.118.75/";
    }


}
