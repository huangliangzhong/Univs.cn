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

public class findpasswordActivity extends Activity implements OnClickListener {
	private ImageView imageView1, imageView2;
	private TextView textView;
	private EditText email;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_findpassword);
		findView();
	}

	private void findView() {
		imageView1 = (ImageView) findViewById(R.id.iv_head_left);
		imageView2 = (ImageView) findViewById(R.id.iv_head_right);
		imageView2.setVisibility(View.INVISIBLE);
		textView = (TextView) findViewById(R.id.tv_head_title);
		textView.setText("修改密码");
		email = (EditText) findViewById(R.id.findpassword_email_et);
		button = (Button) findViewById(R.id.findpassword_next_bt);
		button.setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.iv_head_left:

			break;

		case R.id.findpassword_next_bt:

			break;

		default:
			break;
		}
	}

}
