package cn.univs.app.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.univs.api.MyHttpAPIControl;
import cn.univs.api.ParseUtils;
import cn.univs.api.bean.ArticleItme;
import cn.univs.api.bean.SpecialItme;
import cn.univs.api.bean.UnivsDataBaseSpecial;
import cn.univs.app.R;
import cn.univs.app.adapter.SpecialFragmentPagerAdapter;
import cn.univs.app.util.ResourceUtil;
import cn.univs.app.util.ScreenSizeUtil;
import cn.univs.app.widget.ColumnHorizontalScrollView;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

/***
 * 专题
 * 
 * @author issuser
 * 
 */
public class SpecialActivity extends FragmentActivity implements
		OnClickListener {
	private SpecialActivity mInstance;
	private ViewPager mViewpager;
	private ColumnHorizontalScrollView mNaviga_scroll;
	private LinearLayout mNavigation;
	private int columnSelectIndex = 0;
	private int mScreenWidth = 0;
	private ArticleItme item;
	private ImageView iv_head_left;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInstance = this;
		mScreenWidth = ScreenSizeUtil.getScreenWidth(mInstance);
		setContentView(R.layout.activity_main);
		Intent intent = getIntent();
		item = (ArticleItme) intent.getSerializableExtra("SpecialActivity");
		initView();
		getdata();
	}

	private void initView() {
		TextView title = (TextView) findViewById(R.id.tv_head_title);
		title.setText(item.getTitle());
		iv_head_left = (ImageView) findViewById(R.id.iv_head_left);
		iv_head_left.setVisibility(View.VISIBLE);
		iv_head_left.setOnClickListener(this);
		findViewById(R.id.iv_head_right).setVisibility(View.GONE);
		mNaviga_scroll = (ColumnHorizontalScrollView) findViewById(R.id.naviga_scroll);
		findViewById(R.id.add_naviga_itme_bt).setVisibility(View.GONE);
		mNavigation = (LinearLayout) findViewById(R.id.naviga_view);
		mViewpager = (ViewPager) findViewById(R.id.viewpager);
	}

	private void getdata() {
		MyHttpAPIControl.newInstance().getSpecial(item.getContentid(),
				new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
					}

					@Override
					public void onStart() {
						super.onStart();
					}

					@SuppressWarnings({ "unchecked", "deprecation" })
					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);
						Type tp = new TypeToken<UnivsDataBaseSpecial>() {
						}.getType();
						UnivsDataBaseSpecial ss = (UnivsDataBaseSpecial) ParseUtils
								.Gson2Object(content, tp);
						if (ss != null && ss.isState()) {
							ArticleItme data = ss.getData();
							if (data != null) {
								ArrayList<SpecialItme> data2 = data.getData();
								if (data2 != null && data2.size() > 0) {
									setViewPagerV(data2);
									setNavigation(data2);
								}
							}
						}
					}

				});
	}

	private void setNavigation(ArrayList<SpecialItme> naviga) {
		int count = naviga.size();
		for (int i = 0; i < count; i++) {
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					ScreenSizeUtil.Dp2Px(mInstance, LayoutParams.WRAP_CONTENT),
					LayoutParams.WRAP_CONTENT);
			params.leftMargin = 10;
			params.rightMargin = 10;
			TextView localTextView = new TextView(this);
			localTextView.setTextAppearance(this,
					android.R.style.TextAppearance_DeviceDefault_Medium);
			localTextView
					.setBackgroundResource(R.drawable.selector_navigation_btn);
			localTextView.setGravity(Gravity.CENTER);
			localTextView.setPadding(10, 5, 10, 5);
			localTextView.setId(i);
			localTextView.setText(naviga.get(i).getCatname());
			localTextView
					.setTextColor(ResourceUtil
							.getColorStateList(R.color.top_category_scroll_text_color_day));
			localTextView.setTextSize(16);
			if (columnSelectIndex == i) {
				localTextView.setSelected(true);
			}
			localTextView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					for (int i = 0; i < mNavigation.getChildCount(); i++) {
						View localView = mNavigation.getChildAt(i);
						if (localView != v)
							localView.setSelected(false);
						else {
							localView.setSelected(true);
							mViewpager.setCurrentItem(i / 2);
						}
					}
				}
			});
			mNavigation.addView(localTextView, params);
			if (i != count - 1) {
				ImageView imageView = new ImageView(this);
				imageView.setImageDrawable(ResourceUtil
						.getDrawable(R.drawable.nav_line));
				LinearLayout.LayoutParams split = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				mNavigation.addView(imageView, split);
			}
		}
	}

	private void setViewPagerV(ArrayList<SpecialItme> naviga) {
		mViewpager.setAdapter(new SpecialFragmentPagerAdapter(
				getSupportFragmentManager(), naviga));
		mViewpager.setOnPageChangeListener(new SimpleOnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				selectTab(arg0);
			}

		});
	}

	/**
	 * 选择的Column里面的Tab
	 * */
	private void selectTab(int tab_postion) {
		columnSelectIndex = tab_postion * 2;
		// for (int i = 0; i < mNavigation.getChildCount(); i++) {
		View checkView = mNavigation.getChildAt(tab_postion * 2);
		int k = checkView.getMeasuredWidth();
		int l = checkView.getLeft();
		int i2 = l + k / 2 - mScreenWidth / 2;
		// rg_nav_content.getParent()).smoothScrollTo(i2, 0);
		mNaviga_scroll.smoothScrollTo(i2, 0);
		// mColumnHorizontalScrollView.smoothScrollTo((position - 2) *
		// mItemWidth , 0);
		// }
		// 判断是否选中
		for (int j = 0; j < mNavigation.getChildCount(); j++) {
			View checkView1 = mNavigation.getChildAt(j);
			boolean ischeck;
			if (j == tab_postion * 2) {
				ischeck = true;
			} else {
				ischeck = false;
			}
			checkView1.setSelected(ischeck);
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.iv_head_left:
			finish();
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	
	
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	
}
