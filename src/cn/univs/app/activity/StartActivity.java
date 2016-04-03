package cn.univs.app.activity;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import cn.univs.api.MyHttpAPIControl;
import cn.univs.api.ParseUtils;
import cn.univs.api.bean.Category;
import cn.univs.api.bean.Splash;
import cn.univs.api.bean.Splash.App;
import cn.univs.api.bean.UnivsDataBase;
import cn.univs.app.R;
import cn.univs.app.UnivsApplication;
import cn.univs.app.util.ApplicationUtil;
import cn.univs.app.util.SharedPMananger;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.umeng.analytics.MobclickAgent;

public class StartActivity extends FragmentActivity {
	private StartActivity mInstance;
	private DisplayImageOptions options;
	private ImageLoader instance;
	private ImageView start_image;
	private String imageurl;
	private ProgressDialog dialog;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			startActivity(new Intent(mInstance, MainActivity.class));
			finish();
		};
	};
	private boolean boo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInstance = this;
		setContentView(R.layout.activity_start);
		Intent intent = getIntent();
		boo = intent.getBooleanExtra("isFirst", false);
		try {
			boolean boolean1 = SharedPMananger.getBoolean(
					SharedPMananger.FRIST_APP, false);
			if (!boolean1) {
				SharedPMananger.putBoolean(SharedPMananger.FRIST_APP, true);
			}

			start_image = (ImageView) findViewById(R.id.start_image);
			instance = ImageLoader.getInstance();
			options = new DisplayImageOptions.Builder().cacheOnDisc(true)
					.build();
			imageurl = SharedPMananger.getString(
					SharedPMananger.UNIVS_START_IMAGE, "");
			getChanneldata();
			if (!"".equals(imageurl)) {
				instance.displayImage(imageurl, start_image, options);
				getdata(true);
				startThread();
			} else {
				dialog = new ProgressDialog(mInstance);
				dialog.setMessage("正初始化....");
				getdata(false);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void getChanneldata() {
		MyHttpAPIControl.newInstance().getNewsCategory(
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
						Type tp = new TypeToken<UnivsDataBase<Category>>() {
						}.getType();
						UnivsDataBase<Category> ss = (UnivsDataBase<Category>) ParseUtils
								.Gson2Object(content, tp);
						if (ss != null && ss.isState()) {
							try {
								String channels = SharedPMananger.getString(
										SharedPMananger.UNIVS_CHANNEL_LIST, "");
								if (!channels.equals("")) {
									UnivsDataBase<Category> sss = (UnivsDataBase<Category>) ParseUtils
											.Gson2Object(channels, tp);
									if (sss.getData().size() != ss.getData()
											.size()) {
										ArrayList<Category> naviga = ss
												.getData();
										for (int i = 0; i < naviga.size(); i++) {
											if (i < 5) {
												naviga.get(i).setSelect(true);
											}
										}
										String object2Json = ParseUtils
												.Object2Json(ss);
										SharedPMananger
												.putString(
														SharedPMananger.UNIVS_CHANNEL_LIST,
														object2Json);
									}
								} else {
									ArrayList<Category> naviga = ss.getData();
									for (int i = 0; i < naviga.size(); i++) {
										if (i < 5) {
											naviga.get(i).setSelect(true);
										}
									}
									String object2Json = ParseUtils
											.Object2Json(ss);
									SharedPMananger.putString(
											SharedPMananger.UNIVS_CHANNEL_LIST,
											object2Json);

								}

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}

				});
	}

	private void startThread() {
		new Thread() {

			@Override
			public void run() {
				try {
					if (boo) {
						// Thread.sleep(0000);
					} else {
						Thread.sleep(3000);
					}
					handler.sendEmptyMessage(0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				super.run();
			}

		}.start();
	}

	private void getdata(final boolean isload) {
		MyHttpAPIControl.newInstance().getSplashInfo(this,
				new AsyncHttpResponseHandler() {
					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
					}

					@Override
					public void onStart() {
						super.onStart();
						if (!isload) {
							dialog.show();
						}
					}

					@Override
					public void onFailure(Throwable error) {
						super.onFailure(error);
						if (!isload) {
							dialog.dismiss();
							handler.sendEmptyMessage(0);
						}
					}

					@Override
					public void onSuccess(String content) {
						super.onSuccess(content);
						Type tp = new TypeToken<Splash>() {
						}.getType();
						Splash splash = (Splash) ParseUtils.Gson2Object(
								content, tp);
						try {
							App app = splash.getData().getApp();

							String version = app.getVersion();
							 String versionName = ApplicationUtil
							.getVersionName();
							//String versionName = ApplicationUtil.getVersion()
							//		+ "";
							Log.e("test", "server版本号----" + version);
							Log.e("test", "local版本号++++" + versionName);
							if (!versionName.equals(version)) {
								showUpdataDialog(app);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (splash != null && splash.isState()) {
							String image = splash.getData().getImage();
							if (isload) {
								if (!image.equals(imageurl)) {
									SharedPMananger.putString(
											SharedPMananger.UNIVS_START_IMAGE,
											image);
									instance.loadImage(imageurl, options, null);
								}
							} else {
								SharedPMananger.putString(
										SharedPMananger.UNIVS_START_IMAGE,
										image);
								instance.displayImage(imageurl, start_image,
										options,
										new SimpleImageLoadingListener() {
											@Override
											public void onLoadingComplete(
													String imageUri, View view,
													Bitmap loadedImage) {
												super.onLoadingComplete(
														imageUri, view,
														loadedImage);
												if (dialog.isShowing()) {
													dialog.dismiss();
													startThread();
												}
											}

										});
							}
						}
					}

				});

	}

	/*
	 * 
	 * 弹出对话框通知用户更新程序
	 * 
	 * 弹出对话框的步骤： 1.创建alertDialog的builder. 2.要给builder设置属性, 对话框的内容,样式,按钮
	 * 3.通过builder 创建一个对话框 4.对话框show()出来
	 */
	protected void showUpdataDialog(final App app) {
		AlertDialog.Builder builer = new Builder(getApplicationContext());
		builer.setTitle("版本升级");
		builer.setMessage(app.getDescription());
		// 当点确定按钮时从服务器上下载 新的apk 然后安装
		builer.setPositiveButton("确定", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(app
						.getUrl()));
				startActivity(i);
			}
		});
		// 当点取消按钮时进行登录
		builer.setNegativeButton("取消", null);
		AlertDialog dialog = builer.create();
		dialog.getWindow()
				.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		dialog.show();
	}

	/*
	 * 从服务器中下载APK
	 */

	// 安装apk
	protected void installApk(File file) {
		Intent intent = new Intent();
		// 执行动作
		intent.setAction(Intent.ACTION_VIEW);
		// 执行的数据类型
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");// 编者按：此处Android应为android，否则造成安装不了
		startActivity(intent);
	}

	/*
	 * 进入程序的主界面
	 */
	private void LoginMain() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		// 结束掉当前的activity
		this.finish();
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
