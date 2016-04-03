package cn.univs.app.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.univs.api.ParseUtils;
import cn.univs.api.bean.Category;
import cn.univs.api.bean.UnivsDataBase;
import cn.univs.app.R;
import cn.univs.app.adapter.DragAdapter;
import cn.univs.app.adapter.OtherAdapter;
import cn.univs.app.util.ApplicationUtil;
import cn.univs.app.util.SharedPMananger;
import cn.univs.app.widget.DragGrid;
import cn.univs.app.widget.DragGrid.Exchangelistenter;
import cn.univs.app.widget.OtherGridView;

import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;

/**
 * 频道管理
 * 
 * @Author RA
 * @Blog http://blog.csdn.net/vipzjyno1
 */
public class ChannelActivity extends Activity implements OnItemClickListener,
		OnClickListener {
	/** 用户栏目的GRIDVIEW */
	private DragGrid userGridView;
	/** 其它栏目的GRIDVIEW */
	private OtherGridView otherGridView;
	/** 用户栏目对应的适配器，可以拖动 */
	DragAdapter userAdapter;
	/** 其它栏目对应的适配器 */
	OtherAdapter otherAdapter;
	/** 其它栏目列表 */
	ArrayList<Category> otherChannelList = new ArrayList<Category>();
	/** 用户栏目列表 */
	ArrayList<Category> userChannelList = new ArrayList<Category>();
	/** 是否在移动，由于这边是动画结束后才进行的数据更替，设置这个限制为了避免操作太频繁造成的数据错乱。 */
	boolean isMove = false;
	private UnivsDataBase<Category> mUnivsDataBase;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subscribe_activity);
		initView();
		try {
			String channels = SharedPMananger.getString(
					SharedPMananger.UNIVS_CHANNEL_LIST, "");
			if ("".equals(channels)) {
				ApplicationUtil.showToastInLogin("频道列表初始化失败");
			} else {
				initNaigation(channels);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	
	/** 初始化数据 */
	@SuppressWarnings("unchecked")
	private void initNaigation(String json) {
		Type tp = new TypeToken<UnivsDataBase<Category>>() {
		}.getType();
		mUnivsDataBase = (UnivsDataBase<Category>) ParseUtils.Gson2Object(json,
				tp);
		if (mUnivsDataBase != null && mUnivsDataBase.isState()) {
			ArrayList<Category> naviga = mUnivsDataBase.getData();
			ArrayList<Category> myChannel = new ArrayList<Category>();
			ArrayList<Category> unmyChannel = new ArrayList<Category>();
			for (Category category : naviga) {
				if (category.isSelect()) {
					myChannel.add(category);
				} else {
					unmyChannel.add(category);
				}
			}

			userAdapter = new DragAdapter(this, myChannel);
			userGridView.setAdapter(userAdapter);
			userGridView.setonExchangelistenter(new Exchangelistenter() {

				@Override
				public void exchange(int dragPostion, int dropPostion) {
					changeChannels = true;
				}
			});
			otherAdapter = new OtherAdapter(this, unmyChannel);
			otherGridView.setAdapter(this.otherAdapter);
			// 设置GRIDVIEW的ITEM的点击监听
			otherGridView.setOnItemClickListener(this);
			userGridView.setOnItemClickListener(this);
		}

	}

	/** 初始化布局 */
	private void initView() {
		TextView title = (TextView) findViewById(R.id.tv_head_title);
		title.setText("中国大学生在线");
		ImageView back = (ImageView) findViewById(R.id.iv_head_left);
		findViewById(R.id.iv_head_right).setVisibility(View.GONE);
		userGridView = (DragGrid) findViewById(R.id.userGridView);
		otherGridView = (OtherGridView) findViewById(R.id.otherGridView);
		back.setOnClickListener(this);
	}

	/** GRIDVIEW对应的ITEM点击监听接口 */
	@Override
	public void onItemClick(AdapterView<?> parent, final View view,
			final int position, long id) {
		// 如果点击的时候，之前动画还没结束，那么就让点击事件无效
		if (isMove) {
			return;
		}
		switch (parent.getId()) {
		case R.id.userGridView:
			// position为 0，1 的不可以进行任何操作
			// if (position != 0 && position != 1) {
			final ImageView moveImageView = getView(view);
			if (moveImageView != null) {
				TextView newTextView = (TextView) view;
				final int[] startLocation = new int[2];
				newTextView.getLocationInWindow(startLocation);
				final Category channel = ((DragAdapter) parent.getAdapter())
						.getItem(position);// 获取点击的频道内容
				otherAdapter.setVisible(false);
				// 添加到最后一个
				otherAdapter.addItem(channel);
				new Handler().postDelayed(new Runnable() {
					public void run() {
						try {
							int[] endLocation = new int[2];
							// 获取终点的坐标
							otherGridView.getChildAt(
									otherGridView.getLastVisiblePosition())
									.getLocationInWindow(endLocation);
							MoveAnim(moveImageView, startLocation, endLocation,
									channel, userGridView);
							userAdapter.setRemove(position);
						} catch (Exception localException) {
						}
					}
				}, 50L);
			}
			// }
			break;
		case R.id.otherGridView:
			final ImageView omoveImageView = getView(view);
			if (omoveImageView != null) {
				TextView newTextView = (TextView) view;
				final int[] startLocation = new int[2];
				newTextView.getLocationInWindow(startLocation);
				final Category channel = ((OtherAdapter) parent.getAdapter())
						.getItem(position);
				userAdapter.setVisible(false);
				// 添加到最后一个
				userAdapter.addItem(channel);
				new Handler().postDelayed(new Runnable() {
					public void run() {
						try {
							int[] endLocation = new int[2];
							// 获取终点的坐标
							userGridView.getChildAt(
									userGridView.getLastVisiblePosition())
									.getLocationInWindow(endLocation);
							MoveAnim(omoveImageView, startLocation,
									endLocation, channel, otherGridView);
							otherAdapter.setRemove(position);
						} catch (Exception localException) {
						}
					}
				}, 50L);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 点击ITEM移动动画
	 * 
	 * @param moveView
	 * @param startLocation
	 * @param endLocation
	 * @param moveChannel
	 * @param clickGridView
	 */
	private void MoveAnim(View moveView, int[] startLocation,
			int[] endLocation, final Category moveChannel,
			final GridView clickGridView) {
		int[] initLocation = new int[2];
		// 获取传递过来的VIEW的坐标
		moveView.getLocationInWindow(initLocation);
		// 得到要移动的VIEW,并放入对应的容器中
		final ViewGroup moveViewGroup = getMoveViewGroup();
		final View mMoveView = getMoveView(moveViewGroup, moveView,
				initLocation);
		// 创建移动动画
		TranslateAnimation moveAnimation = new TranslateAnimation(
				startLocation[0], endLocation[0], startLocation[1],
				endLocation[1]);
		moveAnimation.setDuration(300L);// 动画时间
		// 动画配置
		AnimationSet moveAnimationSet = new AnimationSet(true);
		moveAnimationSet.setFillAfter(false);// 动画效果执行完毕后，View对象不保留在终止的位置
		moveAnimationSet.addAnimation(moveAnimation);
		mMoveView.startAnimation(moveAnimationSet);
		moveAnimationSet.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				isMove = true;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				moveViewGroup.removeView(mMoveView);
				// instanceof 方法判断2边实例是不是一样，判断点击的是DragGrid还是OtherGridView
				if (clickGridView instanceof DragGrid) {
					changeChannels = true;
					otherAdapter.setVisible(true);
					otherAdapter.notifyDataSetChanged();
					userAdapter.remove();
				} else {
					changeChannels = true;
					userAdapter.setVisible(true);
					userAdapter.notifyDataSetChanged();
					otherAdapter.remove();
				}
				isMove = false;
			}
		});
	}

	/**
	 * 获取移动的VIEW，放入对应ViewGroup布局容器
	 * 
	 * @param viewGroup
	 * @param view
	 * @param initLocation
	 * @return
	 */
	private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
		int x = initLocation[0];
		int y = initLocation[1];
		viewGroup.addView(view);
		LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		mLayoutParams.leftMargin = x;
		mLayoutParams.topMargin = y;
		view.setLayoutParams(mLayoutParams);
		return view;
	}

	/**
	 * 创建移动的ITEM对应的ViewGroup布局容器
	 */
	private ViewGroup getMoveViewGroup() {
		ViewGroup moveViewGroup = (ViewGroup) getWindow().getDecorView();
		LinearLayout moveLinearLayout = new LinearLayout(this);
		moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		moveViewGroup.addView(moveLinearLayout);
		return moveLinearLayout;
	}

	/**
	 * 获取点击的Item的对应View，
	 * 
	 * @param view
	 * @return
	 */
	private ImageView getView(View view) {
		view.destroyDrawingCache();
		view.setDrawingCacheEnabled(true);
		Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
		view.setDrawingCacheEnabled(false);
		ImageView iv = new ImageView(this);
		iv.setImageBitmap(cache);
		return iv;
	}

	private boolean changeChannels = false;

	/** 退出时候保存选择后数据库的设置 */
	private void saveChannel() {
		if (mUnivsDataBase != null) {
			ArrayList<Category> data = mUnivsDataBase.getData();
			data.clear();
			data.addAll(userAdapter.getChannnelLst());
			data.addAll(otherAdapter.getChannnelLst());
			String object2Json = ParseUtils.Object2Json(mUnivsDataBase);
			SharedPMananger.putString(SharedPMananger.UNIVS_CHANNEL_LIST,
					object2Json);
		}
		if (changeChannels) {
			setResult(SettingChannelActivity.SCA_RESULTCODE);
		}
	}

	@Override
	public void onBackPressed() {
		saveChannel();
		super.onBackPressed();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_head_left:
			saveChannel();
			finish();
			break;
		default:
			break;
		}
	}
}
