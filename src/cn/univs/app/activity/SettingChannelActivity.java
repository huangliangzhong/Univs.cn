package cn.univs.app.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import cn.univs.api.ParseUtils;
import cn.univs.api.bean.Category;
import cn.univs.api.bean.UnivsDataBase;
import cn.univs.app.R;
import cn.univs.app.util.ApplicationUtil;
import cn.univs.app.util.SharedPMananger;

import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;

/***
 * 频道设置页面
 * 
 * @author issuser
 * 
 */
public class SettingChannelActivity extends FragmentActivity implements
		OnClickListener {
	public static final int SCA_RESULTCODE = 1002;
	private SettingChannelActivity mInstance;
	private GridView my_channel;
	private GridView un_my_channel;
	private MyChannelAdapter myChannelAdapter, unmyChannelAdapter;
	private UnivsDataBase<Category> mUnivsDataBase;
	private boolean changeChannels = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInstance = this;
		setContentView(R.layout.activity_setting_channel_main);
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
			myChannelAdapter.addData(myChannel);
			unmyChannelAdapter.addData(unmyChannel);
		}

	}

	private void initView() {
		TextView title = (TextView) findViewById(R.id.tv_head_title);
		title.setText("中国大学生在线");
		ImageView back = (ImageView) findViewById(R.id.iv_head_left);
		findViewById(R.id.iv_head_right).setVisibility(View.GONE);
		back.setOnClickListener(this);
		my_channel = (GridView) findViewById(R.id.my_channel);
		un_my_channel = (GridView) findViewById(R.id.un_my_channel);
		myChannelAdapter = new MyChannelAdapter();
		my_channel.setAdapter(myChannelAdapter);
		unmyChannelAdapter = new MyChannelAdapter();
		un_my_channel.setAdapter(unmyChannelAdapter);
		my_channel.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				changeChannels = true;
				Category item = myChannelAdapter.getItem(position);
				myChannelAdapter.removeItem(item);
				item.setSelect(false);
				unmyChannelAdapter.addItem(item);

			}
		});
		un_my_channel.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				changeChannels = true;
				Category item = unmyChannelAdapter.getItem(position);
				unmyChannelAdapter.removeItem(item);
				item.setSelect(true);
				myChannelAdapter.addItem(item);

			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_head_left:
			goBack();
			break;
		default:
			break;
		}
	}

	private void goBack() {
		if (mUnivsDataBase != null) {
			ArrayList<Category> data = mUnivsDataBase.getData();
			data.clear();
			data.addAll(myChannelAdapter.getData());
			data.addAll(unmyChannelAdapter.getData());
			String object2Json = ParseUtils.Object2Json(mUnivsDataBase);
			SharedPMananger.putString(SharedPMananger.UNIVS_CHANNEL_LIST,
					object2Json);
		}
		if (changeChannels) {
			setResult(SCA_RESULTCODE);
		}
		finish();
	}

	@Override
	public void onBackPressed() {
		goBack();
	}

	class MyChannelAdapter extends BaseAdapter {
		private ArrayList<Category> mChannels = new ArrayList<Category>();

		public MyChannelAdapter() {
		}

		@Override
		public int getCount() {
			return mChannels.size();
		}

		public void addData(ArrayList<Category> channels) {
			if (channels != null && channels.size() > 0) {
				mChannels.addAll(channels);
				notifyDataSetChanged();
			}
		}

		@Override
		public Category getItem(int position) {
			return mChannels.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public void addItem(Category category) {
			mChannels.add(category);
			notifyDataSetChanged();
		}

		public void removeItem(Category category) {
			mChannels.remove(category);
			notifyDataSetChanged();
		}

		public ArrayList<Category> getData() {
			return mChannels;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView inflate = (TextView) View.inflate(mInstance,
					R.layout.univs_itme_channel_view, null);
			inflate.setText(getItem(position).getCatname());
			return inflate;
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
