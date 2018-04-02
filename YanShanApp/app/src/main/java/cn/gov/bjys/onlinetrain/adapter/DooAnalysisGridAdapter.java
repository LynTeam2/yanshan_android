package cn.gov.bjys.onlinetrain.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ycl.framework.adapter.SimpleBaseAdapter;
import com.ycl.framework.db.entity.ExamBean;

import java.util.List;

import cn.gov.bjys.onlinetrain.R;

/**
 * Created by Administrator on 2018/4/1 0001.
 */
public class DooAnalysisGridAdapter extends SimpleBaseAdapter {

    public DooAnalysisGridAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    public int getItemResource() {
        return R.layout.item_analysis_look_item_layout;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {
        ErrorLookBean bean = (ErrorLookBean) data.get(position);

        ImageView img = (ImageView) holder.getView(R.id.img);
        img.setImageResource(bean.getImgRes());

        TextView title = (TextView) holder.getView(R.id.title);
        title.setText(bean.getTitle());


        TextView hint = (TextView) holder.getView(R.id.hint);
        hint.setText(bean.getHint());

        return convertView;
    }
    public static class ErrorLookBean{
        private int imgRes;
        private String title;
        private String hint;
        private List<ExamBean> datas;

        public List<ExamBean> getDatas() {
            return datas;
        }

        public void setDatas(List<ExamBean> datas) {
            this.datas = datas;
        }

        public int getImgRes() {
            return imgRes;
        }

        public void setImgRes(int imgRes) {
            this.imgRes = imgRes;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }
    }
}
