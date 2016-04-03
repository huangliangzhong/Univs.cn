package cn.univs.app.activity;

import cn.univs.app.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class loginWaysActivity extends Activity implements OnClickListener {
	private ImageView imageView1, imageView2;
	private ImageButton imageButton1, imageButton2, imageButton3, imageButton4;
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		findView();
	}

	private void findView() {
		imageView1 = (ImageView) findViewById(R.id.iv_head_left);
		imageView2 = (ImageView) findViewById(R.id.iv_head_right);
		imageView2.setVisibility(View.INVISIBLE);
		imageButton1 = (ImageButton) findViewById(R.id.loginways_loginbyphone_ibt);
		imageButton2 = (ImageButton) findViewById(R.id.loginways_loginbyzhanghaoemail_ibt);
		imageButton3 = (ImageButton) findViewById(R.id.loginways_loginbyQQ_ibt);
		imageButton4 = (ImageButton) findViewById(R.id.loginways_loginbyweibo_ibt);
		textView = (TextView) findViewById(R.id.tv_head_title);
		textView.setText("中国大学生在线");

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.iv_head_left:

			break;
		case R.id.loginways_loginbyphone_ibt:

			break;
		case R.id.loginways_loginbyzhanghaoemail_ibt:

			break;
		case R.id.loginways_loginbyQQ_ibt:

			break;

		case R.id.loginways_loginbyweibo_ibt:

			break;

		default:
			break;
		}

	}

}
