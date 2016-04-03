package cn.univs.app.activity;

import cn.univs.app.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class huifureplyActivity extends Activity implements OnClickListener {
	private ImageView imageView1, imageView2;
	private TextView textView;
	private EditText editText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_huifureply);
		findView();
	}

	private void findView() {
		imageView1 = (ImageView) findViewById(R.id.iv_head_left);
		imageView2 = (ImageView) findViewById(R.id.iv_head_right);
		textView = (TextView) findViewById(R.id.tv_head_title);
		textView.setText("评论");
		editText = (EditText) findViewById(R.id.huifureply_reply_et);

	}

	@Override
	public void onClick(View arg0) {

	}

}
