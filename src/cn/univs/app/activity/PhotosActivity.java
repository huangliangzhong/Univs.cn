package cn.univs.app.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.univs.api.DownloadWebImgTask;
import cn.univs.api.MyHttpAPIControl;
import cn.univs.api.ParseUtils;
import cn.univs.api.bean.ArticleItme;
import cn.univs.api.bean.PhotosItem;
import cn.univs.api.bean.PhotosItem.ImageData;
import cn.univs.api.bean.PhotosItem.PhotoData;
import cn.univs.app.R;
import cn.univs.app.adapter.PhotosActivityAdapter;
import cn.univs.app.util.DateUtils;
import cn.univs.app.util.DialogUtils;
import cn.univs.app.util.SharedPMananger;
import cn.univs.app.widget.OnChangedListener;
import cn.univs.app.widget.SlipButton;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.weixin.controller.UMWXHandler;

/***
 * 组图
 * 
 * @author issuser
 * 
 */
public class PhotosActivity extends FragmentActivity implements
		OnChangedListener {
	private PhotosActivity mInstance;
	private ListView listview;
	private ArticleItme item;
	private PhotosItem ss;
	private PhotoData data;
	private ImageView iv_head_left, iv_head_right;
	private TextView tv_head_title, txt_title, txt_time;;
	private ImageLoader instance;
	private DisplayImageOptions options;
	protected View mView;
	private TextViewOnclick textOnclick;
	private LinearLayout ll_head;
	private PhotosActivityAdapter adapter;
	private LinearLayout ll_share;
	private TextView currentTextView;
	private TextView txt_small;
	private TextView txt_middle;
	private TextView txt_large;
	private String fontsize2;
	private ProgressDialog dialog;

	/** 夜间模式开关 **/
	private SlipButton NightMode;
	// Umeng分享
	final UMSocialService mController = UMServiceFactory
			.getUMSocialService("com.umeng.share");

	String appID = "wx8b63be803dc131b2";
	String appSecret = "75f2be6949d858ef97688338d5cd4767";
	private View view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInstance = this;
		instance = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder().cacheOnDisc(true).build();
		textOnclick = new TextViewOnclick();
		setContentView(R.layout.activity_photos);
		initView();
		getData();
		setFontSize();
	}

	// 设置字体大小
	private void setFontSize() {
		if (adapter != null) {
			if (fontsize2.equals("small")) {
				adapter.setTextSize(13);
			}
			if (fontsize2.equals("middle")) {
				adapter.setTextSize(15);
			}
			if (fontsize2.equals("large")) {
				adapter.setTextSize(18);
			}
		}
	}

	private void initView() {
		dialog = new ProgressDialog(mInstance);
		dialog.show();
		try {
			fontsize2 = SharedPMananger.getString("fontsize", "middle");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Intent intent = getIntent();
		item = (ArticleItme) intent.getSerializableExtra("SpecialActivity");
		tv_head_title = (TextView) findViewById(R.id.tv_head_title);
		tv_head_title.setText("中国大学生在线");

		iv_head_left = (ImageView) findViewById(R.id.iv_head_left);
		iv_head_right = (ImageView) findViewById(R.id.iv_head_right);
		ll_share = (LinearLayout) findViewById(R.id.ll_share);
		iv_head_left.setOnClickListener(textOnclick);
		iv_head_right.setOnClickListener(textOnclick);
		ll_share.setOnClickListener(textOnclick);
		listview = (ListView) findViewById(R.id.listview);
		ll_head = (LinearLayout) findViewById(R.id.ll_head);

	}

	private void getData() {
		MyHttpAPIControl.newInstance().getPicture(item.getContentid(),
				new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						if(dialog.isShowing()){
							dialog.dismiss();
						}
					}

					@Override
					public void onStart() {
						super.onStart();
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);
						if(dialog.isShowing()){
							dialog.dismiss();
						}
						Type tp = new TypeToken<PhotosItem>() {
						}.getType();
						ss = (PhotosItem) ParseUtils.Gson2Object(content, tp);
						if (ss != null && ss.isState()) {
							data = ss.getData();
							setListData(data);

							try {
								boolean putBoolean = SharedPMananger
										.getBoolean("NowChoose", false);
								if (putBoolean) {
									setOnNightMode();
								} else {
									setOffNightMode();
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				});

	}

	private void setListData(PhotoData data) {
		view = View.inflate(mInstance, R.layout.univs_listview_head, null);
		txt_title = (TextView) view.findViewById(R.id.txt_title);
		txt_time = (TextView) view.findViewById(R.id.txt_time);
		txt_title.setText(data.getTitle());

		txt_time.setText(data.getSource() + "  "
				+ DateUtils.formatDateCN(new Date(data.getSorttime() * 1000)));
		listview.addHeaderView(view);
		ArrayList<ImageData> images = data.getImages();
		adapter = new PhotosActivityAdapter(PhotosActivity.this);
		adapter.addData(images);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ImageData item2 = adapter.getItem(position - 1);
				showSaveDialog(item2.getImage());
			}
		});
	}

	private void showSaveDialog(String imgurl) {
		// TODO Auto-generated method stub
		mView = LayoutInflater.from(this).inflate(R.layout.dialog_save_image,
				null);
		mView.findViewById(R.id.ll_save).setTag(imgurl);
		mView.findViewById(R.id.ll_save).setOnClickListener(textOnclick);
		mView.findViewById(R.id.ll_cancle).setOnClickListener(textOnclick);
		DialogUtils.getInstance().displayDialog(this, mView, Gravity.BOTTOM);
	}

	/** 点击事件 **/
	private class TextViewOnclick implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.iv_head_left:
				finish();
				break;
			case R.id.iv_head_right:
				showShareDialog();
				break;
			case R.id.txt_small:// 字体 小
				SharedPMananger.putString("fontsize", "small");
				currentTextView.setBackgroundColor(0);
				currentTextView.setTextColor(Color.BLACK);
				currentTextView = (TextView) v;
				currentTextView.setBackgroundColor(getResources().getColor(
						R.color.mode_night_green));
				currentTextView.setTextColor(Color.WHITE);
				adapter.setTextSize(13);
				break;
			case R.id.txt_middle:// 字体 中
				SharedPMananger.putString("fontsize", "middle");
				currentTextView.setBackgroundColor(0);
				currentTextView.setTextColor(Color.BLACK);
				currentTextView = (TextView) v;
				currentTextView.setBackgroundColor(getResources().getColor(
						R.color.mode_night_green));
				currentTextView.setTextColor(Color.WHITE);
				adapter.setTextSize(15);
				break;
			case R.id.txt_large:// 字体 大
				SharedPMananger.putString("fontsize", "large");
				currentTextView.setBackgroundColor(0);
				currentTextView.setTextColor(Color.BLACK);
				currentTextView = (TextView) v;
				currentTextView.setBackgroundColor(getResources().getColor(
						R.color.mode_night_green));
				currentTextView.setTextColor(Color.WHITE);
				adapter.setTextSize(18);
				break;
			case R.id.ll_communication:// 夜间模式
				break;
			case R.id.ll_share:// 分享
				setShareContent();
				// 是否只有已登录用户才能打开分享选择页
				mController.openShare(PhotosActivity.this, false);
				break;
			case R.id.ll_save:// 保存到手机
				new DownloadWebImgTask().execute((String) v.getTag());
				DialogUtils.getInstance().dismissDialog();
				break;
			case R.id.ll_cancle:// 取消
				DialogUtils.getInstance().dismissDialog();
				break;
			default:
				break;
			}
		}
	}

	private void showShareDialog() {
		// TODO Auto-generated method stub
		mView = LayoutInflater.from(this).inflate(R.layout.dialog_share, null);
		txt_small = (TextView) mView.findViewById(R.id.txt_small);
		txt_small.setOnClickListener(textOnclick);
		txt_middle = (TextView) mView.findViewById(R.id.txt_middle);
		txt_middle.setOnClickListener(textOnclick);
		txt_large = (TextView) mView.findViewById(R.id.txt_large);
		txt_large.setOnClickListener(textOnclick);
		NightMode = (SlipButton) mView.findViewById(R.id.slipBtn);
		NightMode.SetOnChangedListener(this);
		if (fontsize2.equals("small")) {
			currentTextView = txt_small;
		}
		if (fontsize2.equals("middle")) {
			currentTextView = txt_middle;
		}
		if (fontsize2.equals("large")) {
			currentTextView = txt_large;
		}
		currentTextView.setBackgroundColor(getResources().getColor(
				R.color.mode_night_green));
		currentTextView.setTextColor(Color.WHITE);
		DialogUtils.getInstance().displayDialog(this, mView, Gravity.BOTTOM);
	}

	@Override
	public void OnChanged(boolean CheckState) {
		SharedPMananger.putBoolean("NowChoose", CheckState);
		if (CheckState) {
			setOnNightMode();
		} else {
			setOffNightMode();
		}
	}

	/**
	 * 关闭夜间模式
	 */
	private void setOffNightMode() {
		try {
			boolean putBoolean = SharedPMananger.getBoolean("NowChoose", false);
			listview.setBackgroundColor(Color.WHITE);
			adapter.setNightMode(putBoolean);
			view.setBackgroundColor(Color.WHITE);
			txt_title.setTextColor(Color.BLACK);
			txt_time.setTextColor(Color.BLACK);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 打开夜间模式
	 */
	private void setOnNightMode() {
		try {
			boolean putBoolean = SharedPMananger.getBoolean("NowChoose", false);
			listview.setBackgroundColor(Color.GRAY);
			view.setBackgroundColor(Color.GRAY);
			txt_title.setTextColor(Color.WHITE);
			txt_time.setTextColor(Color.WHITE);
			adapter.setNightMode(putBoolean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/***
	 * 分享
	 */
	private void setShareContent() {
		// 1. 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(this, appID, appSecret);
		wxHandler.addToSocialSDK();
		// 设置分享内容的链接地址
		wxHandler.setTargetUrl(ss.getData().getShareurl() + "");

		// 2. 添加微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(this, appID, appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
		// 设置分享内容的链接地址
		wxCircleHandler.setTitle(item.getTitle()+"");
		wxCircleHandler.setTargetUrl(ss.getData().getShareurl() + "");

		// 3. 设置分享内容
		mController.setShareContent(item.getTitle()
				+ ss.getData().getShareurl() + "");
		// 4. 设置分享图片, 参数2为图片的url地址
		mController.setShareMedia(new UMImage(this, item.getThumb() + ""));

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
