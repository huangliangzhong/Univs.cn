package cn.univs.app.activity;

import cn.univs.app.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class exitLoginActivity extends Activity implements OnClickListener {
	private ImageView imageView1, imageView2;
	private EditText editText1, editText2, editText3;
	private ImageButton imageButton1, imageButton2, imageButton3, imageButton4,
			imageButton5, imageButton6;
	private TextView textView1, textView2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exitlogin);
		findView();
	}

	private void findView() {
		imageView1 = (ImageView) findViewById(R.id.iv_head_left);
		imageView2 = (ImageView) findViewById(R.id.iv_head_right);
		textView1 = (TextView) findViewById(R.id.tv_head_title);
		textView1.setText("中国大学生在线");
		textView2 = (TextView) findViewById(R.id.exitlogin_renzhneg_tv);
		editText1 = (EditText) findViewById(R.id.exitlogin_zhanghao_et);
		editText2 = (EditText) findViewById(R.id.exitlogin_nicheng_et);
		editText3 = (EditText) findViewById(R.id.exitlogin_xuexiao_et);
		imageButton1 = (ImageButton) findViewById(R.id.exitlogin_image_ibt);
		imageButton2 = (ImageButton) findViewById(R.id.exitlogin_renzhneg_ibt);
		imageButton3 = (ImageButton) findViewById(R.id.exitlogin_xiaoneixinxi_ibt);
		imageButton4 = (ImageButton) findViewById(R.id.exitlogin_userandsecurity_ibt);
		imageButton5 = (ImageButton) findViewById(R.id.exitlogin_xitongshezhi_ibt);
		imageButton6 = (ImageButton) findViewById(R.id.exitlogin_yijianfankui_ibt);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.iv_head_left:

			break;
		case R.id.exitlogin_image_ibt:

			break;
		case R.id.exitlogin_renzhneg_ibt:

			break;
		case R.id.exitlogin_xiaoneixinxi_ibt:

			break;
		case R.id.exitlogin_userandsecurity_ibt:

			break;
		case R.id.exitlogin_xitongshezhi_ibt:

			break;
		case R.id.exitlogin_yijianfankui_ibt:

			break;

		default:
			break;
		}
	}

}
