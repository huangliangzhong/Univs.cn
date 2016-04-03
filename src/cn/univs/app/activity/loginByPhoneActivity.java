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

public class loginByPhoneActivity extends Activity implements OnClickListener {
	private ImageView imageView1, imageView2;
	private TextView textView1;
	private EditText phonenumber, password;
	private Button button1, button2, button3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		findView();
	}

	private void findView() {
		imageView1.findViewById(R.id.iv_head_left);
		imageView2.findViewById(R.id.iv_head_right);
		textView1.findViewById(R.id.tv_head_title);
		textView1.setText("中国大学生在线");
		phonenumber = (EditText) findViewById(R.id.login_phonrnumber_et);
		password = (EditText) findViewById(R.id.login_password_et);
		button1 = (Button) findViewById(R.id.login_register_bt);
		button2 = (Button) findViewById(R.id.login_forgetpassword_bt);
		button3 = (Button) findViewById(R.id.login_login_bt);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.login_register_bt:

			break;
		case R.id.login_forgetpassword_bt:

			break;
		case R.id.login_login_bt:

			break;

		default:
			break;
		}

	}

}
