package com.ycl.framework.albums;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ycl.framework.R;
import com.ycl.framework.base.AlbumEntityEBus;
import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.photoview.AlbumBrowserActivity;
import com.ycl.framework.photoview.PhotoBrowserActivity;
import com.ycl.framework.utils.string.DensityUtils;
import com.ycl.framework.utils.util.GlideProxy;
import com.ycl.framework.utils.util.SelectorUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * 图片gridview的adapter
 */
public class MyAdapter extends CommonAdapter<String> {

    /**
     * 用户选择的图片，存储为图片的完整路径（不重复LinkedList）
     */
    public static List<String> mSelectedImage = new LinkedList<>();

    /**
     * 文件夹路径
     */
    private String mDirPath;
    private int itemSize;
    private String tagName;
    private int topicId;

    public MyAdapter(Context context, List<String> mDatas, int itemLayoutId,
                     String dirPath) {

        super(context, mDatas, itemLayoutId);
        this.mDirPath = dirPath;
        itemSize = (DensityUtils.getScreenW(context) - DensityUtils.dp2px(12, context)) / 3;
//        if (AlbumActivity.MaxSelectNum != 1) {
//            for (String str : oldSelectedImage)
//                mSelectedImage.add(str);
//        } else
//            oldSelectedImage.clear();
    }

    public void setmDirPath(String dirPath) {
        mDirPath = dirPath;
    }

    public void setDatas(List<String> datas) {
        mDatas = datas;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public int getTopicId() {
        return topicId;
    }

    @Override
    public void convert(final ViewHolder helper, final String item) {
        //设置no_pic
        RelativeLayout rlRoot = helper.getView(R.id.rl_dir_photo_root);
        rlRoot.getLayoutParams().width = itemSize;
        rlRoot.getLayoutParams().height = itemSize;
        final ImageView mImageView = helper.getView(R.id.id_item_image);
        final TextView mSelect = helper.getView(R.id.id_item_select);

        if (AlbumActivity.MaxSelectNum != 1) {
            //设置no_selected
            mSelect.setBackgroundResource(R.drawable.picture_unselected_2);
            mSelect.setText("");
            mSelect.setVisibility(View.VISIBLE);
            if (AlbumActivity.ALL_IMG.equals(mDirPath) && helper.getPosition() == 0) {
                mSelect.setVisibility(View.GONE);
            }
            /**
             * 已经选择过的图片，显示出选择过的效果
             */
            String path = AlbumActivity.ALL_IMG.equals(mDirPath) ? item : mDirPath + "/" + item;
            if (mSelectedImage.contains(path)) {
                mSelect.setBackground(SelectorUtil.getShape(0xFFee5948, DensityUtils.dp2px(15, mContext), 0, 0));
                mImageView.setColorFilter(Color.parseColor("#57000000"));
                mSelect.setText((mSelectedImage.indexOf(path) + 1) + "");
            }
            mSelect.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 已经选择过该图片
                    if (mSelectedImage.contains(AlbumActivity.ALL_IMG.equals(mDirPath) ? item : mDirPath + "/" + item)) {
                        mSelectedImage.remove(AlbumActivity.ALL_IMG.equals(mDirPath) ? item : mDirPath + "/" + item);
                        notifyDataSetChanged();
// mSelect.setBackground(ContextCompat.getDrawable(mContext.getApplicationContext(), R.drawable.picture_unselected_2));
//                        mImageView.setColorFilter(null);
                    } else
                    // 未选择该图片
                    {
                        if (AlbumActivity.MaxSelectNum - mSelectedImage.size() == 0) {
                            ((AlbumActivity) mContext).showWarmToast("最多选择" + mSelectedImage.size() + "张!");
                            return;
                        }
                        if (((AlbumActivity) mContext).isFilterSize(AlbumActivity.ALL_IMG.equals(mDirPath) ? item : mDirPath + "/" + item)) {
                            mSelectedImage.add(AlbumActivity.ALL_IMG.equals(mDirPath) ? item : mDirPath + "/" + item);
//                            mSelect.setBackground(SelectorUtil.getShape(0xFFee5948, DensityUtils.dp2px(15, mContext), 0, 0));
//                            mImageView.setColorFilter(Color.parseColor("#57000000"));
                            notifyDataSetChanged();
                        } else {
                            ((AlbumActivity) mContext).showWarmToast("图片太小了!");
                            return;
                        }
                    }
                    ((AlbumActivity) mContext).refreshTitleBtn();
                }
            });

        } else {
            mSelect.setVisibility(View.GONE);
        }
        mImageView.setScaleType(ScaleType.CENTER_CROP);
        //设置图片
        if (AlbumActivity.ALL_IMG.equals(mDirPath)) {
            if (helper.getPosition() == 0) {
                rlRoot.setBackgroundColor(0xffD8D8D8);
                //防止缓存
                int padding = DensityUtils.dp2px(25, mContext);
                rlRoot.setPadding(padding, padding, padding, padding);
                GlideProxy.loadImgForRes(mImageView, R.drawable.photo_camera);
            } else {
                rlRoot.setPadding(0, 0, 0, 0);
                rlRoot.setBackground(null);
                helper.setImageRes(mImageView, item);
            }
        } else {
            rlRoot.setPadding(0, 0, 0, 0);
            rlRoot.setBackground(null);
            helper.setImageByUrl(R.id.id_item_image, mDirPath + "/" + item);
        }
        mImageView.setColorFilter(null);
        //设置ImageView的点击事件
        mImageView.setOnClickListener(new OnClickListener() {
                                          //选择，则将图片变暗，反之则反之
                                          @SuppressWarnings("unchecked")
                                          @Override
                                          public void onClick(View v) {
                                              if (AlbumActivity.CAMERA_TAG.equals(item)) {
                                                  Bundle mBundle = new Bundle();
                                                  mBundle.putInt("topicId", topicId);
                                                  mBundle.putString("tagName", tagName);

                                                  ((FrameActivity) mContext).startAct(CameraTempActivity.class, mBundle);
                                                  return;
                                              }

                                              if (AlbumActivity.MaxSelectNum == 1) {
                                                  if (((AlbumActivity) mContext).isFilterSize(AlbumActivity.ALL_IMG.equals(mDirPath) ? item : mDirPath + "/" + item)) {
                                                      EventBus.getDefault().post(new AlbumEntityEBus<String>(AlbumActivity.ALL_IMG.equals(mDirPath) ? item : mDirPath + "/" + item));
                                                      ((AlbumActivity) mContext).finish();
                                                  } else
                                                      ((AlbumActivity) mContext).showWarmToast("图片太小了!");
                                                  return;
                                              } else {
                                                  //是否是全部图片相册
                                                  int indexId = helper.getPosition();
                                                  Bundle mBundle = new Bundle();
                                                  ArrayList<String> photos = new ArrayList<String>();
                                                  if (AlbumActivity.ALL_IMG.equals(mDirPath)) {
                                                      for (String items : mDatas) {
                                                          photos.add(items);
                                                      }
                                                      if (photos.size() > 0) {
                                                          indexId--;
                                                          photos.remove(0);
                                                      }
                                                  } else {
                                                      for (String items : mDatas) {
                                                          photos.add(mDirPath + "/" + items);
                                                      }
                                                  }
                                                  mBundle.putInt(PhotoBrowserActivity.PHOTO_INDEX, indexId);
                                                  mBundle.putStringArrayList(PhotoBrowserActivity.PHOTO_BROWSER, photos);
                                                  mBundle.putInt("topicId", topicId);
                                                  mBundle.putString("tagName", tagName);
                                                  try {
                                                      //具体数据过大  TransactionTooLargeException  （bundle 限制大小）
                                                      ((FrameActivity) v.getContext()).startActForResultBundle(AlbumBrowserActivity.class, mBundle, 101);
                                                  } catch (Exception ignore) {
                                                  }
                                              }
                                          }
                                      }
        );
    }

    //还原上次选择的图片数据
//    public void refreshSelectedPic() {
//        mSelectedImage.clear();
//        for (String str : oldSelectedImage)
//            mSelectedImage.add(str);
//    }
}