package cn.gov.bjys.onlinetrain.bean;

/**
 * Created by Administrator on 2017/12/30 0030.
 */
public  class SingleExamBean {

        public  static class Judgment{
            public static String question = "行车中从其他道路汇入车流前，应注意观察侧后方车辆的动态。";
            public static String[] answers = {
                    "正确",
                    "错误",
            };

            public  static int isTrue = 0;

            public static String fx = "行车中从其他道路线汇入车流前，应注意观察侧后方车辆的动态，确保行车安全。";
        }

        public static class SingleChoose{
            public static String question = "驾驶机动车在道路上违反道路交通安全法的行为，属于什么行为？";
            public static String[] answers = {
              "违章行为",
                    "违法行为",
                    "过失行为",
                    "违规行为"

            };
            public  static int isTrue = 1;

            public static  String fx = "违反《道路交通安全法》，属于违法行为。";
        }

    public static class MultiChoose{
            public static String question = "林某驾车以110公里/小时的速度在城市道路行驶，与一辆机动车追尾后弃车逃离被群众拦下。经鉴定，事发时林某血液中的酒精浓度为135.8毫克/百毫升。林某的主要违法行为是什么？";
            public static String[] answers = {
              "醉酒驾驶",
                    "肇事逃逸",
                    "疲劳驾驶",
                    "超速驾驶"

            };
            public static int[] isTrue = {0,1,3};

            public static String fx = "违法行为有：\n" +
                    "一、“以110公里/小时的速度在城市道路行驶”属于超速驾驶；\n" +
                    "二、“与一辆机动车追尾后弃车逃离”属于肇事逃逸；\n" +
                    "三、“血液中的酒精浓度为135.8毫克/百毫升”是醉酒驾驶。";
        }

}
