package cn.univs.app.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.univs.api.MyHttpAPIControl;
import cn.univs.api.ParseUtils;
import cn.univs.api.bean.ArticleItme;
import cn.univs.api.bean.SpecialItme;
import cn.univs.api.bean.UnivsDataBaseSpecial;
import cn.univs.app.R;
import cn.univs.app.adapter.ArticleAdapter2;
import cn.univs.app.util.ScreenSizeUtil;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

/***
 * 专题
 * 
 * @author issuser
 * 
 */
public class SpecialActivity2 extends FragmentActivity implements
		OnClickListener {
	private SpecialActivity2 mInstance;
	private ArticleItme item;
	private ImageView iv_head_left;
	private ListView mListView;
	private ArticleAdapter2 articleAdapter;
	private ImageLoader instance;
	private DisplayImageOptions options;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInstance = this;
		instance = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder().cacheOnDisc(true).build();
		setContentView(R.layout.activity_spec_layout_content_list);
		Intent intent = getIntent();
		item = (ArticleItme) intent.getSerializableExtra("SpecialActivity");
		initView();
		getdata();
	}

	private void initView() {
		TextView title = (TextView) findViewById(R.id.tv_head_title);
		title.setText("中国大学生在线");
		iv_head_left = (ImageView) findViewById(R.id.iv_head_left);
		iv_head_left.setVisibility(View.VISIBLE);
		iv_head_left.setOnClickListener(this);
		findViewById(R.id.iv_head_right).setVisibility(View.GONE);
		mListView = (ListView) findViewById(R.id.mylistView);
	}

	private void setListData(ArticleItme data) {
		ArticleItme datas =data;
		
		int screenWidth = ScreenSizeUtil.getScreenWidth(this);
		android.widget.AbsListView.LayoutParams layoutParams = new android.widget.AbsListView.LayoutParams(
				screenWidth, screenWidth / 100 * 69);
		
		View view = View.inflate(mInstance, R.layout.special_image, null);
		view.setLayoutParams(layoutParams);
		ImageView image = (ImageView) view.findViewById(R.id.iv_list_head);
		instance.displayImage(datas.getImage(), image, options);
		mListView.addHeaderView(view);
		Log.e("---image----", "----" + datas.getImage());
		articleAdapter = new ArticleAdapter2(mInstance);
		mListView.setAdapter(articleAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				ArticleItme item2 = articleAdapter.getItem(position-1);
				if (item2.getModelid() == 10) {
					Intent intent = new Intent(mInstance,
							SpecialActivity2.class);
					intent.putExtra("SpecialActivity", item2);
					startActivity(intent);
				} else if (item2.getModelid() == 2) {
					Intent intent = new Intent(mInstance, PhotosActivity.class);
					intent.putExtra("SpecialActivity", item2);
					startActivity(intent);
				} else if (item2.getModelid() == 1) {
					Intent intent = new Intent(mInstance, NewsActivity.class);
					intent.putExtra("SpecialActivity", item2);
					startActivity(intent);
				}
			}
		});
	}

	private void getdata() {
		MyHttpAPIControl.newInstance().getSpecial(item.getContentid(),
				new AsyncHttpResponseHandler() {

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
								setListData(data);
								ArrayList<SpecialItme> data2 = data.getData();
								if (data2 != null && data2.size() > 0) {
									articleAdapter.addData(data2);
								}
							}
						}
					}

				});
	}

	/**
	 * 选择的Column里面的Tab
	 * */
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
