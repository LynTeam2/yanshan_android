package com.ycl.framework.view.recycleview.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.ycl.framework.view.recycleview.FrameViewHolder;
import com.ycl.framework.view.recycleview.animation.AlphaInAnimation;
import com.ycl.framework.view.recycleview.animation.BaseAnimation;
import com.ycl.framework.view.recycleview.animation.ScaleInAnimation;
import com.ycl.framework.view.recycleview.animation.SlideInBottomAnimation;
import com.ycl.framework.view.recycleview.animation.SlideInLeftAnimation;
import com.ycl.framework.view.recycleview.animation.SlideInRightAnimation;

/**
 * Created by yuchaoliang on 17/2/7.
 */

public abstract class AnimationAdapter<K extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<K> {


    private int mLastPosition = -1;

    private boolean mOpenAnimationEnable = false;
    private boolean mFirstOnlyEnable = true; // 默认 第一次加载 有动画  标志

    private BaseAnimation mCustomAnimation;
    private BaseAnimation mSelectAnimation = new SlideInLeftAnimation();

    //添加动画
    @Override
    public void onViewAttachedToWindow(K holder) {
        super.onViewAttachedToWindow(holder);
        int type = holder.getItemViewType();
        if (type == MultiRecycleTypesAdapter.VIEW_TYPES.CUSTOM_FOOTER || type == MultiRecycleTypesAdapter.VIEW_TYPES.TYPE_FOOTER || type == MultiRecycleTypesAdapter.VIEW_TYPES.TYPE_HEADER || type == MultiRecycleTypesAdapter.VIEW_TYPES.EMPTY_FOOTER) {
            setFullSpan(holder);
        } else {
            addAnimation(holder);
        }
    }

    //解决 瀑布流的 填充布局
    protected void setFullSpan(RecyclerView.ViewHolder holder) {
        if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            params.setFullSpan(true);
        }
    }

    public void isFirstOnly(boolean firstOnly) {
        this.mFirstOnlyEnable = firstOnly;
    }

    /**
     * add animation when you want to show time
     *
     * @param holder
     */
    private void addAnimation(RecyclerView.ViewHolder holder) {
        if (mOpenAnimationEnable) {
            if (!mFirstOnlyEnable || holder.getLayoutPosition() > mLastPosition) {
                BaseAnimation animation;
                if (mCustomAnimation != null) {
                    animation = mCustomAnimation;
                } else {
                    animation = mSelectAnimation;
                }
                for (Animator anim : animation.getAnimators(holder.itemView)) {
                    startAnim(anim, holder.getLayoutPosition());
                }
                mLastPosition = holder.getLayoutPosition();
            }
        }
    }

    protected void startAnim(Animator anim, int index) {
        anim.setDuration(300).start();
        anim.setInterpolator(new LinearInterpolator());
    }


    public void openLoadAnimation(int animationType) {
        this.mOpenAnimationEnable = true;
        mCustomAnimation = null;
        switch (animationType) {
            case 0:
                mSelectAnimation = new AlphaInAnimation();
                break;
            case 1:
                mSelectAnimation = new ScaleInAnimation();
                break;
            case 2:
                mSelectAnimation = new SlideInBottomAnimation();
                break;
            case 3:
                mSelectAnimation = new SlideInLeftAnimation();
                break;
            case 4:
                mSelectAnimation = new SlideInRightAnimation();
                break;
            case 5:
                mSelectAnimation = new BaseAnimation() {

                    @Override
                    public Animator[] getAnimators(View view) {
                        PropertyValuesHolder valuesHolderY = PropertyValuesHolder.ofFloat("scaleY", 1, 1.1f, 1);
                        PropertyValuesHolder valuesHolderX = PropertyValuesHolder.ofFloat("scaleX", 1, 1.1f, 1);
                        return new Animator[]{
                                ObjectAnimator.ofPropertyValuesHolder(view, valuesHolderY, valuesHolderX)
                        };
                    }
                };
                break;
            default:
                break;
        }
    }

    public void setmFirstOnlyEnable(boolean mFirstOnlyEnable) {
        this.mFirstOnlyEnable = mFirstOnlyEnable;
    }

    public void setmOpenAnimationEnable(boolean mOpenAnimationEnable) {
        this.mOpenAnimationEnable = mOpenAnimationEnable;
    }

    public void setNotDoAnimationCount(int mLastPosition) {
        this.mLastPosition = mLastPosition;
    }

    public void startAnimationDefault() {
        mFirstOnlyEnable = false;
        mOpenAnimationEnable = true;
        this.mLastPosition = 4;
    }
}
