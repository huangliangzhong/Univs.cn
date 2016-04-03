package cn.univs.app.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.univs.api.MyHttpAPIControl;
import cn.univs.api.ParseUtils;
import cn.univs.api.bean.Category;
import cn.univs.api.bean.UnivsDataBase;
import cn.univs.app.R;
import cn.univs.app.adapter.NewsFragmentPagerAdapter;
import cn.univs.app.util.ResourceUtil;
import cn.univs.app.util.ScreenSizeUtil;
import cn.univs.app.util.SharedPMananger;
import cn.univs.app.widget.ColumnHorizontalScrollView;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

public class MainActivity extends FragmentActivity {
	private static final int MA_REQUESTCODE = 1001;
	private MainActivity mInstance;
	private ViewPager mViewpager;
	private ColumnHorizontalScrollView mNaviga_scroll;
	private LinearLayout mNavigation;
	private int columnSelectIndex = 0;
	private int mScreenWidth = 0;
	private View mContentView;
	private View mFailure_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInstance = this;
		mScreenWidth = ScreenSizeUtil.getScreenWidth(mInstance);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		TextView title = (TextView) findViewById(R.id.tv_head_title);
		title.setText("中国大学生在线");
		findViewById(R.id.iv_head_left).setVisibility(View.GONE);
		findViewById(R.id.iv_head_right).setVisibility(View.GONE);
		findViewById(R.id.add_naviga_itme_bt).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						startActivityForResult(new Intent(mInstance,
								ChannelActivity.class), MA_REQUESTCODE);
					}
				});

		mContentView = findViewById(R.id.content);
		mFailure_view = findViewById(R.id.failure_view);
		findViewById(R.id.reload).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getChanneldata();
			}
		});
		mNaviga_scroll = (ColumnHorizontalScrollView) findViewById(R.id.naviga_scroll);
		mNaviga_scroll = (ColumnHorizontalScrollView) findViewById(R.id.naviga_scroll);
		mNavigation = (LinearLayout) findViewById(R.id.naviga_view);
		mViewpager = (ViewPager) findViewById(R.id.viewpager);
		initChannel();
	}

	private void initChannel() {
		try {
			String channels = SharedPMananger.getString(
					SharedPMananger.UNIVS_CHANNEL_LIST, "");
			columnSelectIndex = 0;
			if ("".equals(channels)) {
				getChanneldata();
			} else {
				initNaigation(channels);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initNaigation(String json) {
		try {
			Type tp = new TypeToken<UnivsDataBase<Category>>() {
			}.getType();
			UnivsDataBase<Category> ss = (UnivsDataBase<Category>) ParseUtils
					.Gson2Object(json, tp);
			if (ss != null && ss.isState()) {
				ArrayList<Category> naviga = ss.getData();
				ArrayList<Category> myChannel = new ArrayList<Category>();
				for (Category category : naviga) {
					if (category.isSelect()) {
						myChannel.add(category);
					}
				}
				setViewPagerV(myChannel);
				setNavigation(myChannel);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void getChanneldata() {
		MyHttpAPIControl.newInstance().getNewsCategory(
				new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						mFailure_view.setVisibility(View.VISIBLE);
					}

					@Override
					public void onStart() {
						super.onStart();
						mFailure_view.setVisibility(View.GONE);
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);
						Type tp = new TypeToken<UnivsDataBase<Category>>() {
						}.getType();
						UnivsDataBase<Category> ss = (UnivsDataBase<Category>) ParseUtils
								.Gson2Object(content, tp);
						if (ss != null && ss.isState()) {
							ArrayList<Category> naviga = ss.getData();
							for (int i = 0; i < naviga.size(); i++) {
								if (i < 5) {
									naviga.get(i).setSelect(true);
								}
							}
							String object2Json = ParseUtils.Object2Json(ss);
							SharedPMananger.putString(
									SharedPMananger.UNIVS_CHANNEL_LIST,
									object2Json);
							initNaigation(object2Json);
						}
					}

				});
	}

	private void setNavigation(ArrayList<Category> naviga) {
		int count = naviga.size();
		int dp2Px = ScreenSizeUtil.Dp2Px(this, 20);
		int width = (ScreenSizeUtil.getScreenWidth(mInstance) - 12 * 10 - 4
				- dp2Px - 19) / 5;
		mNavigation.removeAllViews();
		for (int i = 0; i < count; i++) {
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					width, LayoutParams.WRAP_CONTENT);
			params.leftMargin = 12;
			params.rightMargin = 12;
			TextView localTextView = new TextView(this);
			localTextView
					.setBackgroundResource(R.drawable.selector_navigation_btn);
			localTextView.setGravity(Gravity.CENTER);
			localTextView.setPadding(0, 5, 0, 5);
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

	private void setViewPagerV(ArrayList<Category> naviga) {
		mViewpager.setAdapter(new NewsFragmentPagerAdapter(
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
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == MA_REQUESTCODE
				&& arg1 == SettingChannelActivity.SCA_RESULTCODE) {
			columnSelectIndex = 0;
			initChannel();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle("提示");
		builder.setMessage("确认退出?");
		builder.setPositiveButton("确  认", new AlertDialog.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		builder.setNegativeButton("取  消", new AlertDialog.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();
		return super.onKeyUp(keyCode, event);
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
