package cn.univs.app.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import cn.univs.app.R;
import cn.univs.app.util.ApplicationUtil;
import cn.univs.app.util.NetWorkUtils;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class registe0neActivity extends Activity implements OnClickListener {
	private ImageView imageView1, imageView2;
	private Button button1, button2;
	private TextView textView1;
	private EditText phonenumber;
	private Map<String, Object> usermessage = new HashMap<String, Object>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registebyphoneone);
		findView();
	}

	private void findView() {
		imageView1 = (ImageView) findViewById(R.id.iv_head_left);
		imageView2 = (ImageView) findViewById(R.id.iv_head_right);
		//设置右边的图片不可见
		imageView2.setVisibility(View.INVISIBLE);
		textView1 = (TextView) findViewById(R.id.tv_head_title);
		textView1.setText("手机注册");
		phonenumber = (EditText) findViewById(R.id.registerbyphoneone_phonenumber_et);
		button1 = (Button) findViewById(R.id.registerbyphoneone_tiaokuan_bt);
		button2 = (Button) findViewById(R.id.registerbyphoneone_complete_bt);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.registerbyphoneone_tiaokuan_bt:

			break;

		case R.id.registerbyphoneone_complete_bt:
			String number = phonenumber.getText().toString().trim();
			if (TextUtils.isEmpty(number)) {
				Toast.makeText(this, "手机号不能为空", 0).show();
				return;
			}

			// 验证是否同意服务条款
			// if(){}

			break;

		default:
			break;
		}

	}

	public Map getMap() {
		return usermessage;
	}

}
