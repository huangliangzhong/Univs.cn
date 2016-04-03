package cn.univs.app.activity;

import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import cn.univs.app.R;
import cn.univs.app.adapter.GuidePagerAdapter;
import cn.univs.app.util.ApplicationUtil;
import cn.univs.app.util.SharedPMananger;

public class GuideActivity extends Activity {
	private Button btn_begin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		String versionName = ApplicationUtil
//				.getVersionName();
//		SharedPMananger.putString("versionName", versionName);
		try {
			boolean boolean1 = SharedPMananger.getBoolean(
					SharedPMananger.FRIST_APP, false);
			if (boolean1) {
				Intent intent = new Intent(GuideActivity.this,
						StartActivity.class);
				startActivity(intent);
				finish();
			} else {
				setContentView(R.layout.activity_yindao);
				ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager);
				viewpager.setAdapter(new GuidePagerAdapter(new int[] {
						R.drawable.yin_dao1, R.drawable.yin_dao2,
						R.drawable.yin_dao3 }, this));
				viewpager
						.setOnPageChangeListener(new SimpleOnPageChangeListener() {

							@Override
							public void onPageSelected(int arg0) {
								if(arg0 == 2){
									btn_begin.setVisibility(View.VISIBLE);
								}else{
									btn_begin.setVisibility(View.GONE);
								}
							}
						});
				btn_begin =(Button)findViewById(R.id.btn_begin);
				btn_begin.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(GuideActivity.this,
								StartActivity.class);
						intent.putExtra("isFirst", true);
						startActivity(intent);
						// 结束掉当前的activity
						finish();
					}
				});
			}
		} catch (Exception e1) {
			e1.printStackTrace();
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