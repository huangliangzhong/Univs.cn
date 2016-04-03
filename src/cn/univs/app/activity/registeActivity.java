package cn.univs.app.activity;

import cn.univs.app.R;
import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class registeActivity extends Activity implements OnClickListener {

	private ImageView imageView1, imageView2;
	private TextView textView;
	private EditText editText1, editText2, editText3;
	private Button button1, button2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registerbyphone);
		findView();
	}

	private void findView() {
		imageView1 = (ImageView) findViewById(R.id.iv_head_left);
		imageView2 = (ImageView) findViewById(R.id.iv_head_right);
		imageView2.setVisibility(View.INVISIBLE);
		textView = (TextView) findViewById(R.id.tv_head_title);
		textView.setText("手机注册");
		editText1 = (EditText) findViewById(R.id.registerbyphone_yanzhengma_et);
		button1 = (Button) findViewById(R.id.registerbyphone_againyanzhengma_bt);
		editText2 = (EditText) findViewById(R.id.registerbyphone_password_et);
		editText3 = (EditText) findViewById(R.id.registerbyphone_againpassword_et);
		button2 = (Button) findViewById(R.id.registerbyphone_complete_bt);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.registerbyphone_againyanzhengma_bt:

			break;

		case R.id.registerbyphone_complete_bt:

			break;

		default:
			break;
		}

	}

}
