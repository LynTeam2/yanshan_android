package cn.gov.bjys.onlinetrain.customview;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cleveroad.slidingtutorial.Direction;
import com.cleveroad.slidingtutorial.IndicatorOptions;
import com.cleveroad.slidingtutorial.OnTutorialPageChangeListener;
import com.cleveroad.slidingtutorial.PageOptions;
import com.cleveroad.slidingtutorial.TransformItem;
import com.cleveroad.slidingtutorial.TutorialOptions;
import com.cleveroad.slidingtutorial.TutorialPageOptionsProvider;
import com.cleveroad.slidingtutorial.TutorialPageProvider;
import com.cleveroad.slidingtutorial.TutorialSupportFragment;
import com.ycl.framework.utils.helper.ContextHelper;
import com.ycl.framework.utils.sp.SavePreference;

import cn.gov.bjys.onlinetrain.BaseApplication;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.LoginActivity;
import cn.gov.bjys.onlinetrain.utils.YSConst;

public class CustomTutorialFragment extends TutorialSupportFragment
        implements OnTutorialPageChangeListener {

    private static final String TAG = "CustomTutorialFragment";
    private static final int TOTAL_PAGES = 4;
    private static final int ACTUAL_PAGES_COUNT = 4;

    private final View.OnClickListener mOnSkipClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            Toast.makeText(getActivity(), "Skip button clicked", Toast.LENGTH_SHORT).show();
            SavePreference.save(BaseApplication.getBaseApplication(), YSConst.NOT_FIRST_LOGIN, true);
            ContextHelper.getRequiredActivity(getActivity()).startAct(LoginActivity.class);
            ContextHelper.getRequiredActivity(getActivity()).finish();
        }
    };

    private final TutorialPageOptionsProvider mTutorialPageOptionsProvider = new TutorialPageOptionsProvider() {
        @NonNull
        @Override
        public PageOptions provide(int position) {
            @LayoutRes int pageLayoutResId;
            TransformItem[] tutorialItems;
            position %= ACTUAL_PAGES_COUNT;
            switch (position) {
                case 0: {
                    pageLayoutResId = R.layout.fragment_page_first;
                    tutorialItems = new TransformItem[]{
                            TransformItem.create(R.id.img, Direction.LEFT_TO_RIGHT, 0.50f),
                    };
                    break;
                }
                case 1: {
                    pageLayoutResId = R.layout.fragment_page_second;
                    tutorialItems = new TransformItem[]{
                            TransformItem.create(R.id.img, Direction.RIGHT_TO_LEFT, 0.5f),
                    };
                    break;
                }
                case 2: {
                    pageLayoutResId = R.layout.fragment_page_third;
                    tutorialItems = new TransformItem[]{
                            TransformItem.create(R.id.img, Direction.RIGHT_TO_LEFT, 0.50f),
                    };
                    break;
                }
                case 3: {
                    pageLayoutResId = R.layout.fragment_page_fourth;
                    tutorialItems = new TransformItem[]{
                            TransformItem.create(R.id.img, Direction.RIGHT_TO_LEFT, 0.50f),
                    };
                    break;
                }
 /*               case 4: {
                    pageLayoutResId = R.layout.fragment_page_fifth;
                    tutorialItems = new TransformItem[]{
                            TransformItem.create(R.id.img, Direction.RIGHT_TO_LEFT, 0.50f),
                    };
                    break;
                }*/

                default: {
                    throw new IllegalArgumentException("Unknown position: " + position);
                }
            }

            return PageOptions.create(pageLayoutResId, position, tutorialItems);
        }
    };

    private final TutorialPageProvider<Fragment> mTutorialPageProvider = new TutorialPageProvider<Fragment>() {
        @NonNull
        @Override
        public Fragment providePage(int position) {
            position %= ACTUAL_PAGES_COUNT;
            switch (position) {
                case 0:
//                    return new FirstCustomPageFragment();
                case 1:
//                    return new SecondCustomPageFragment();
                case 2:
//                    return new ThirdCustomPageFragment();
                default:
                    throw new IllegalArgumentException("Unknown position: " + position);
            }
        }
    };

    private int[] pagesColors;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (pagesColors == null) {
            pagesColors = new int[]{
                    ContextCompat.getColor(getActivity(), android.R.color.darker_gray),
                    ContextCompat.getColor(getActivity(), android.R.color.holo_green_dark),
                    ContextCompat.getColor(getActivity(), android.R.color.holo_red_dark),
                    ContextCompat.getColor(getActivity(), android.R.color.holo_blue_dark),
                    ContextCompat.getColor(getActivity(), android.R.color.holo_purple),
                    ContextCompat.getColor(getActivity(), android.R.color.holo_orange_dark),
            };
        }
        addOnTutorialPageChangeListener(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.custom_tutorial_layout_ids_example;
    }

    @Override
    protected int getIndicatorResId() {
        return R.id.indicatorCustom;
    }

    @Override
    protected int getSeparatorResId() {
        return R.id.separatorCustom;
    }

    @Override
    protected int getButtonSkipResId() {
        return R.id.tvSkipCustom;
    }

    @Override
    protected int getViewPagerResId() {
        return R.id.viewPagerCustom;
    }

    @Override
    protected TutorialOptions provideTutorialOptions() {
        final IndicatorOptions indicatorOptions = IndicatorOptions.newBuilder(getActivity())
                .build();
        return newTutorialOptionsBuilder(getActivity())
                .setUseInfiniteScroll(true)
                .setPagesColors(pagesColors)
                .setPagesCount(TOTAL_PAGES)
                //.setTutorialPageProvider(mTutorialPageOptionsProvider)
                .setIndicatorOptions(indicatorOptions)
                .setTutorialPageProvider(mTutorialPageOptionsProvider)
                .setOnSkipClickListener(mOnSkipClickListener)
                .build();
    }

    @Override
    public void onPageChanged(int position) {
        Log.i(TAG, "onPageChanged: position = " + position);
        if (position == TutorialSupportFragment.EMPTY_FRAGMENT_POSITION) {
            Log.i(TAG, "onPageChanged: Empty fragment is visible");
        }
        if (position == TOTAL_PAGES - 1) {
            mSkipButton.setVisibility(View.VISIBLE);
        } else {
            mSkipButton.setVisibility(View.GONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initViews(view);
        return view;
    }


    TextView mSkipButton;

    public void initViews(View rootView) {
        mSkipButton = (TextView) rootView.findViewById(R.id.tvSkipCustom);
    }
}
