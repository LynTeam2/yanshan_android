package cn.gov.bjys.onlinetrain.utils;

import android.text.TextUtils;


//用来处理一些数据问题
public class DataHelper {
    /**
     *
     * @param input  "1,,2,,3"
     * @return  output "1,2,3"
     */
    public static String clearEmptyString(String input){
        StringBuilder sb = new StringBuilder();
        String[] inputs = input.split(",");
        for(int i=0; i<inputs.length; i++){
            String s = inputs[i];
            if(TextUtils.isEmpty(s)){
                continue;
            }
            if(i == inputs.length -1) {
                sb.append(s);
            }else {
                sb.append(s+",");
            }
        }
        return sb.toString();
    }
}
