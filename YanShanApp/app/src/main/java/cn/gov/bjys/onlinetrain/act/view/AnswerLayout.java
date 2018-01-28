package cn.gov.bjys.onlinetrain.act.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ycl.framework.db.entity.ExamBean;

import java.util.ArrayList;
import java.util.List;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.bean.SingleExamBean;

/**
 * 答题界面
 * 1、处理方式UI变换交给Layout处理
 * 2、选择的结果逻辑交给上层Fragment实现
 */
public class AnswerLayout extends LinearLayout implements View.OnClickListener {

    public interface ClickResult {
        public void clickRet(ExamBean bean);
    }

    public void registerClickResult(ClickResult cr) {
        this.mClickResult = cr;
    }

    public void removeAllLis() {
        this.mClickResult = null;
    }

    private ClickResult mClickResult;
    private Context mContext;

    public AnswerLayout(Context context) {
        super(context);
        mContext = context;
        initViews();
    }

    public AnswerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initViews();
    }

    public AnswerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initViews();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AnswerLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initViews();
    }

    private List<SingleAnswerLayout> mLayoutList;

    private void initViews() {
        if (mLayoutList == null) {
            mLayoutList = new ArrayList<>();
        }
        this.setOrientation(LinearLayout.VERTICAL);
    }


    /**
     * UI线程 避免超时任务
     *
     * @param bean
     */
    private ExamBean mExamBean;

    public void bindDatas(ExamBean bean) {
        mExamBean = bean;
        Handler handler = new Handler(getContext().getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                AnswerLayout.this.removeAllViews();
                mLayoutList.clear();
                switch (mExamBean.getQuestionType()) {
                    case ExamBean.TEXT_JUDGMENT_EXAM:
                        gotoJudgment();
                        break;
                    case ExamBean.TEXT_SINGLE_EXAM:
                        gotoSingle();
                        break;
                    case ExamBean.TEXT_MULTIPLE_EXAM:
                        gotoMulti();
                        break;
                }
/*                for(int i=0; i < 4; i++){
                    String s = "this is "+i;
                    SingleAnswerLayout layout =  new SingleAnswerLayout(mContext);
//                    RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                    rlp.setMargins(AutoUtils.getPercentWidthSize(70),AutoUtils.getPercentWidthSize(70),AutoUtils.getPercentWidthSize(70),0);
//                    layout.setLayoutParams(rlp);
                 //如果使用LayoutInflater来加载，要把数据attach到一个Parent上去，
                    // 这样才能保留LayouParam一样的信息。
                    // 如果单纯的使用inflater而不设置Parent，则会丢失LayouParam信息

                    layout.setContent(s);
                    switch (i){
                        case 0:
                            layout.setTextById("A",R.id.text_choice);
                            break;
                        case 1:
                            layout.setTextById("B", R.id.text_choice);
                            break;
                        case 2:
                            layout.setTextById("C",R.id.text_choice);
                            break;
                        case 3:
                            layout.setTextById("D",R.id.text_choice);
                            break;
                    }
                    layout.setTag(i);
                    layout.setOnClickListener(AnswerLayout.this);
                    AnswerLayout.this.addView(layout);
                    mLayoutList.add(layout);
                }*/
            }
        });
    }


    public void gotoJudgment() {
        for (int i = 0; i < 2; i++) {
            String s;
            if(i == 0){
                s = "正确";
            }else{
                s = "错误";
            }
            SingleAnswerLayout layout = new SingleAnswerLayout(mContext);
            layout.setContent(s);
            switch (i) {
                case 0:
                    layout.setTextById("A", R.id.text_choice);
                    break;
                case 1:
                    layout.setTextById("B", R.id.text_choice);
                    break;
                case 2:
                    layout.setTextById("C", R.id.text_choice);
                    break;
                case 3:
                    layout.setTextById("D", R.id.text_choice);
                    break;
            }
            layout.setTag(i);
            layout.setOnClickListener(AnswerLayout.this);
            AnswerLayout.this.addView(layout);
            mLayoutList.add(layout);
        }

        if (mExamBean.isDeal()) {

            String as = mExamBean.getAnswer();
            int j;
            if("true".equals(as)){
                j = 0;
            }else{
                j = 1;
            }
            SingleAnswerLayout layout = mLayoutList.get(j);
            TextView textChoice = (TextView) layout.findViewById(R.id.text_choice);
            textChoice.setBackground(getResources().getDrawable(R.drawable.dati_right));
            textChoice.setText("");
            if (mExamBean.getmChoose().get(0) != j) {
                SingleAnswerLayout layout1 = mLayoutList.get(mExamBean.getmChoose().get(0));
                TextView textChoice1 = (TextView) layout1.findViewById(R.id.text_choice);
                textChoice1.setBackground(getResources().getDrawable(R.drawable.dati_error));
                textChoice1.setText("");
            }
        }
    }

    public void gotoSingle() {
        for (int i = 0; i < 4; i++) {
//            String s = SingleExamBean.SingleChoose.answers[i];
            String s= "";
            switch (i){
                case 0:
                    s = mExamBean.getChoiceA();
                    break;
                case 1:
                    s = mExamBean.getChoiceB();
                    break;
                case 2:
                    s = mExamBean.getChoiceC();
                    break;
                case 3:
                    s = mExamBean.getChoiceD();
                    break;
            }
            SingleAnswerLayout layout = new SingleAnswerLayout(mContext);
            layout.setContent(s);
            switch (i) {
                case 0:
                    layout.setTextById("A", R.id.text_choice);
                    break;
                case 1:
                    layout.setTextById("B", R.id.text_choice);
                    break;
                case 2:
                    layout.setTextById("C", R.id.text_choice);
                    break;
                case 3:
                    layout.setTextById("D", R.id.text_choice);
                    break;
            }
            layout.setTag(i);
            layout.setOnClickListener(AnswerLayout.this);
            AnswerLayout.this.addView(layout);
            mLayoutList.add(layout);
        }

        if (mExamBean.isDeal()) {

            String as = mExamBean.getAnswer();
            int j =0;
            if("choiceA".equals(as)){
                j=0;
            }else if("choiceB".equals(as)){
                j=1;
            }
            else if("choiceC".equals(as)){
                j=2;
            }
            else if("choiceD".equals(as)){
                j=3;
            }

            SingleAnswerLayout layout = mLayoutList.get(j);
            TextView textChoice = (TextView) layout.findViewById(R.id.text_choice);
            textChoice.setBackground(getResources().getDrawable(R.drawable.dati_right));
            textChoice.setText("");
            if (mExamBean.getmChoose().get(0) != j) {
                SingleAnswerLayout layout1 = mLayoutList.get(mExamBean.getmChoose().get(0));
                TextView textChoice1 = (TextView) layout1.findViewById(R.id.text_choice);
                textChoice1.setBackground(getResources().getDrawable(R.drawable.dati_error));
                textChoice1.setText("");
            }
        }
    }

    public void gotoMulti() {
        for (int i = 0; i < 4; i++) {
            String s= "";
            switch (i){
                case 0:
                    s = mExamBean.getChoiceA();
                    break;
                case 1:
                    s = mExamBean.getChoiceB();
                    break;
                case 2:
                    s = mExamBean.getChoiceC();
                    break;
                case 3:
                    s = mExamBean.getChoiceD();
                    break;
            }
            SingleAnswerLayout layout = new SingleAnswerLayout(mContext);
            layout.setContent(s);
            switch (i) {
                case 0:
                    layout.setTextById("A", R.id.text_choice);
                    break;
                case 1:
                    layout.setTextById("B", R.id.text_choice);
                    break;
                case 2:
                    layout.setTextById("C", R.id.text_choice);
                    break;
                case 3:
                    layout.setTextById("D", R.id.text_choice);
                    break;
            }
            layout.setTag(i);
            layout.setOnClickListener(AnswerLayout.this);
            AnswerLayout.this.addView(layout);
            mLayoutList.add(layout);
        }

        if (mExamBean.isDeal()) {
            //正确答案
//            int[] j = cn.gov.bjys.onlinetrain.bean.SingleExamBean.MultiChoose.isTrue;


            List<Integer> cc = new ArrayList<>();
            String[] res = mExamBean.getAnswer().split(",");
            for(String s : res){
                int k;
                if(s.equals("choiceA")){
                    k = 0;
                }else if (s.equals("choiceB")) {
                    k = 1;
                }else if(s.equals("choiceC")){
                    k = 2;
                }else if(s.equals("choiceD")){
                    k = 3;
                }else{
                    k=0;
                }
                cc.add(k);
            }
            //用户答案
            List<Integer> user = mExamBean.getmChoose();
            for (int k = 0; k < 4; k++) {
                int type = 0;//-1默认 正确答案1  错误0
                for (int right : cc) {
                    if (right == k) {
                        type = 1;
                        break;
                    }
                }
                int userT = 0;//选择1 未选择0
                for (Integer userC : user) {
                    if (userC == k) {
                        userT = 1;
                        break;
                    }
                }
                if (type == 1 && userT == 1) {
                    SingleAnswerLayout layout = mLayoutList.get(k);
                    TextView textChoice = (TextView) layout.findViewById(R.id.text_choice);
                    textChoice.setBackground(getResources().getDrawable(R.drawable.dati_right));
                    textChoice.setText("");
                } else if (type == 1 && userT == 0) {
                    SingleAnswerLayout layout = mLayoutList.get(k);
                    TextView textChoice = (TextView) layout.findViewById(R.id.text_choice);
                    textChoice.setBackground(getResources().getDrawable(R.drawable.bg_exam_choice_select));
                    textChoice.setText("");
                } else if (type == 0 && userT == 1) {
                    SingleAnswerLayout layout = mLayoutList.get(k);
                    TextView textChoice = (TextView) layout.findViewById(R.id.text_choice);
                    textChoice.setBackground(getResources().getDrawable(R.drawable.dati_error));
                    textChoice.setText("");
                } else {

                }
            }


        }
    }

    public void addOneOrderData() {


    }

    boolean isClicked = false;

    @Override
    public void onClick(View v) {
        int i = (int) v.getTag();
//        if(isClicked){
//            return;
//        }

        if (mExamBean.isDeal()) {
            return;//用户已经做过这个题目了
        }

        switch (mExamBean.getQuestionType()) {
            case ExamBean.TEXT_SINGLE_EXAM:
                resolveSingleExamClick(i);
                break;

            case ExamBean.TEXT_JUDGMENT_EXAM:
                resolveJudgementExamClick(i);
                break;
            case ExamBean.TEXT_MULTIPLE_EXAM:
                resolveMultiExamClick(i);
                break;
        }
    }

    public void resolveSingleExamClick(int i) {
        mExamBean.setDeal(true);
        List<Integer> datas = new ArrayList<>();
        datas.add(i);
        mExamBean.setmChoose(datas);

        int j = cn.gov.bjys.onlinetrain.bean.SingleExamBean.SingleChoose.isTrue;

        SingleAnswerLayout layout = mLayoutList.get(j);
        TextView textChoice = (TextView) layout.findViewById(R.id.text_choice);
        textChoice.setBackground(getResources().getDrawable(R.drawable.dati_right));
        textChoice.setText("");
        if (i != j) {
            SingleAnswerLayout layout1 = mLayoutList.get(i);
            TextView textChoice1 = (TextView) layout1.findViewById(R.id.text_choice);
            textChoice1.setBackground(getResources().getDrawable(R.drawable.dati_error));
            textChoice1.setText("");
        }
        if (mClickResult != null) {
            mClickResult.clickRet(mExamBean);
        }

    }

    public void resolveJudgementExamClick(int i) {
        mExamBean.setDeal(true);
        List<Integer> datas = new ArrayList<>();
        datas.add(i);
        mExamBean.setmChoose(datas);
        int j = 0; //= cn.gov.bjys.onlinetrain.bean.SingleExamBean.Judgment.isTrue;
        if(mExamBean.getAnswer().equals("true")){
            j = 0;
        }else{
            j = 1;
        }
        SingleAnswerLayout layout = mLayoutList.get(j);
        TextView textChoice = (TextView) layout.findViewById(R.id.text_choice);
        textChoice.setBackground(getResources().getDrawable(R.drawable.dati_right));
        textChoice.setText("");
        if (i != j) {
            SingleAnswerLayout layout1 = mLayoutList.get(i);
            TextView textChoice1 = (TextView) layout1.findViewById(R.id.text_choice);
            textChoice1.setBackground(getResources().getDrawable(R.drawable.dati_error));
            textChoice1.setText("");
        }
        if (mClickResult != null) {
            mClickResult.clickRet(mExamBean);
        }

    }

    public void resolveMultiExamClick(int i) {
        List<Integer> datas = mExamBean.getmChoose();
        if (datas.contains(i)) {
            datas.remove(i);
            SingleAnswerLayout layout = mLayoutList.get(i);
            TextView textChoice = (TextView) layout.findViewById(R.id.text_choice);
            textChoice.setBackground(getResources().getDrawable(R.drawable.bg_exam_choice_normal));
        } else {
            datas.add(i);
            SingleAnswerLayout layout = mLayoutList.get(i);
            TextView textChoice = (TextView) layout.findViewById(R.id.text_choice);
            textChoice.setBackground(getResources().getDrawable(R.drawable.bg_exam_choice_select));
        }
    }

    public void sureMulti(ExamBean bean) {
            //正确答案
//            int[] j = cn.gov.bjys.onlinetrain.bean.SingleExamBean.MultiChoose.isTrue;
        List<Integer> cc = new ArrayList<>();
        String[] res = mExamBean.getAnswer().split(",");
        for(String s : res){
            int k;
            if(s.equals("choiceA")){
                k = 0;
            }else if (s.equals("choiceB")) {
                k = 1;
            }else if(s.equals("choiceC")){
                k = 2;
            }else if(s.equals("choiceD")){
                k = 3;
            }else{
                k=0;
            }
            cc.add(k);
        }

            //用户答案
            List<Integer> user = bean.getmChoose();
            for (int k = 0; k < SingleExamBean.MultiChoose.answers.length; k++) {
                int type = 0;//-1默认 正确答案1  错误0
                for (int right : cc) {
                    if (right == k) {
                        type = 1;
                        break;
                    }
                }
                int userT = 0;//选择1 未选择0
                for (Integer userC : user) {
                    if (userC == k) {
                        userT = 1;
                        break;
                    }
                }
                if (type == 1 && userT == 1) {
                    SingleAnswerLayout layout = mLayoutList.get(k);
                    TextView textChoice = (TextView) layout.findViewById(R.id.text_choice);
                    textChoice.setBackground(getResources().getDrawable(R.drawable.dati_right));
                    textChoice.setText("");
                } else if (type == 1 && userT == 0) {
                    SingleAnswerLayout layout = mLayoutList.get(k);
                    TextView textChoice = (TextView) layout.findViewById(R.id.text_choice);
                    textChoice.setBackground(getResources().getDrawable(R.drawable.bg_exam_choice_select));
                } else if (type == 0 && userT == 1) {
                    SingleAnswerLayout layout = mLayoutList.get(k);
                    TextView textChoice = (TextView) layout.findViewById(R.id.text_choice);
                    textChoice.setBackground(getResources().getDrawable(R.drawable.dati_error));
                    textChoice.setText("");
                } else {

                }
        }
        if(mClickResult != null){
            mClickResult.clickRet(mExamBean);
        }
    }
}
