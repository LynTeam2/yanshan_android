package com.ycl.framework.albums;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ycl.framework.R;

import java.util.List;


public class ListImageDirPopupWindow extends BasePopupWindowForListView<ImageFloder> {
    private ListView mListDir;
    private int currentSelectIndex = 0;
    private CommonAdapter adapter;

    public ListImageDirPopupWindow(int width, int height,
                                   List<ImageFloder> datas, View convertView) {
        super(convertView, width, height, true, datas);
    }

    @Override
    public void initViews() {
        mListDir = (ListView) findViewById(R.id.id_list_dir);
        adapter = new CommonAdapter<ImageFloder>(context, mDatas,
                R.layout.list_dir_item) {
            @Override
            public void convert(ViewHolder helper, ImageFloder item) {
                helper.setText(R.id.id_dir_item_name, item.getName());
                helper.setImageByUrl(R.id.id_dir_item_image,
                        item.getFirstImagePath());
                helper.setText(R.id.id_dir_item_count, item.getCount() + "张");
                helper.setVisible(R.id.img_dir_item_select, currentSelectIndex == helper.getPosition() ? View.VISIBLE : View.GONE);
            }
        };
        mListDir.setAdapter(adapter);
    }

    public interface OnImageDirSelected {
        void selected(ImageFloder floder);
    }

    private OnImageDirSelected mImageDirSelected;

    public void setOnImageDirSelected(OnImageDirSelected mImageDirSelected) {
        this.mImageDirSelected = mImageDirSelected;
    }

    @Override
    public void initEvents() {
        mListDir.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                currentSelectIndex = position;
                //刷新Pop的Adapter
                adapter.notifyDataSetChanged();
                if (mImageDirSelected != null) {
                    mImageDirSelected.selected(mDatas.get(position));
                }

            }
        });
    }

    @Override
    public void init() {

    }

    @Override
    protected void beforeInitWeNeedSomeParams(Object... params) {
    }

}
