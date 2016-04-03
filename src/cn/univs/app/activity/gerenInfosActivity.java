package cn.univs.app.activity;

import cn.univs.app.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class gerenInfosActivity extends Activity implements OnClickListener {
	private ImageView imageView1, imageView2, imageView3;
	private TextView textView;
	private EditText name, nicheng, sex;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gereninfos);
		findView();
	}

	private void findView() {
		imageView1 = (ImageView) findViewById(R.id.iv_head_left);
		imageView2 = (ImageView) findViewById(R.id.iv_head_right);
		imageView3 = (ImageView) findViewById(R.id.gereninfos_touxiang_iv);
		imageView3.setOnClickListener(this);
		imageView2.setVisibility(View.INVISIBLE);
		textView = (TextView) findViewById(R.id.tv_head_title);
		textView.setText("个人信息");
		name = (EditText) findViewById(R.id.gereninfos_name_et);
		nicheng = (EditText) findViewById(R.id.gereninfos_nicheng_et);
		sex = (EditText) findViewById(R.id.gereninfos_sex_et);
		button = (Button) findViewById(R.id.gereninfos_save_bt);
		button.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.gereninfos_touxiang_iv:

			break;
		case R.id.gereninfos_save_bt:

			break;

		default:
			break;
		}
	}

}
