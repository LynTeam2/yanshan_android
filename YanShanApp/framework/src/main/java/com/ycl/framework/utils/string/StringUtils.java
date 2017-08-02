package com.ycl.framework.utils.string;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author LOVE
 *
 */
public final class StringUtils {


    /**
     * 连接数组
     *
     * @param array 数组
     * @param sep   分隔符
     * @return String
     */
    public static String join(String[] array, String sep) {
        if (array == null) {
            return null;
        }

        int arraySize = array.length;
        int sepSize = 0;
        if (sep != null && !sep.equals("")) {
            sepSize = sep.length();
        }

        int bufSize = (arraySize == 0 ? 0 : ((array[0] == null ? 16 : array[0]
                .length()) + sepSize) * arraySize);
        StringBuilder buf = new StringBuilder(bufSize);

        for (int i = 0; i < arraySize; i++) {
            if (i > 0) {
                buf.append(sep);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    /**
     * 转换数组为json格式
     *
     * @param array (不允许为空)
     * @return   String
     */
    public static String jsonJoin(String[] array) {
        int arraySize = array.length;
        int bufSize = arraySize * (array[0].length() + 3);
        StringBuilder buf = new StringBuilder(bufSize);
        for (int i = 0; i < arraySize; i++) {
            if (i > 0) {
                buf.append(',');
            }

            buf.append('"');
            buf.append(array[i]);
            buf.append('"');
        }
        return buf.toString();
    }

    /**
     * 将长字符从截取剩下的用...代替
     *
     * @param input
     * @param count
     * @return
     */
    public static String cutString(String input, int count) {
        return cutString(input, count, null);
    }

    /**
     * 将长字符从截取剩下的用more代替,more为空则用省略号代替
     *
     * @param input
     * @param count
     * @param more
     * @return
     */
    public static String cutString(String input, int count, String more) {
        String resultString = "";
        if (input != null) {
            if (more == null) {
                more = "...";
            }
            if (input.length() > count) {
                resultString = input.substring(0, count) + more;
            } else {
                resultString = input;
            }
        }
        return resultString;
    }

    /**
     * 获得指定中文长度对应的字符串长度，用于截取显示文字，一个中文等于两个英文
     *
     * @param chineseLengthForDisplay
     * @return
     */
    public static int chineseWidth2StringLenth(int chineseLengthForDisplay,
                                               String string) {
        int result = 0;
        int displayWidth = chineseLengthForDisplay * 2;
        if (string != null) {
            for (char chr : string.toCharArray()) {
                // 中文
                if (chr >= 0x4e00 && chr <= 0x9fbb) {
                    displayWidth -= 2;
                } else {
                    // 英文
                    displayWidth -= 1;
                }
                if (displayWidth <= 0) {
                    break;
                }
                result++;
            }
        }
        return result;
    }

    /**
     * 检测字符串中是否包含汉字
     *
     * @param sequence
     * @return
     */
    public static boolean checkChinese(String sequence) {
        final String format = "[\\u4E00-\\u9FA5\\uF900-\\uFA2D]";
        boolean result;
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(sequence);
        result = matcher.find();
        return result;
    }

    /**
     * 将本地html  通过流转换 获取List<T></T>
     *
     * @return List<T>
     */
//    public static List<ExpressEntity> getExpress(Context context) {
//        List<ExpressEntity> s_express = new ArrayList<>();
//        List<String> s_exname = new ArrayList<>();
//        List<String> s_key = new ArrayList<>();
//
//        try {
//            //提取网页并转换成inputstream
//            InputStream is = context.getAssets().open("expresslist.html");
//            int size = is.available();
//
//            // 转换成byte
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//
//            // 把btye转换成string.
//            String text = new String(buffer, "UTF-8");
//            //正则匹配名称
//            Pattern ptitle = Pattern.compile("\"expressname\":\"(.*?)\"");
//            Matcher mtitle = ptitle.matcher(text);
//            while (mtitle.find()) {
//                s_exname.add(mtitle.group(1));
//            }
//
//            //正则匹配key
//            Pattern pkey = Pattern.compile("\"expresskey\":\"(.*?)\"");
//            Matcher mkey = pkey.matcher(text);
//            while (mkey.find()) {
//                s_key.add(mkey.group(1));
//            }
//            ExpressEntity expressEntity;
//            for (int i = 0; i < s_key.size(); i++) {
//
//                expressEntity = new ExpressEntity();
//
//                expressEntity.setExpresskey(s_key.get(i));
//                expressEntity.setExpressname(s_exname.get(i));
//                s_express.add(expressEntity);
//            }
//
//            return s_express;
//        } catch (IOException e) {
//            // Should never happen!
//            throw new RuntimeException(e);
//        }
//    }
    public static String getMd5Str(Map<String, Object> maps) {
        String resultStr = "";
        for (Entry ent : maps.entrySet()) {
            String keyt = ent.getKey().toString();
            String valuet;
            if (ent.getValue() == null) {
                valuet = "";
            } else {
                valuet = ent.getValue().toString();
            }
            resultStr += keyt + valuet;
        }
        return resultStr;
    }

    public static String getFormatedDateTime(String pattern, long dateTime) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
        return sDateFormat.format(new Date(dateTime));
    }

    public static String getHour_Minute_Second() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }
}
