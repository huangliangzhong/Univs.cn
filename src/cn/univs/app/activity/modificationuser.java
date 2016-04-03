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

public class modificationuser extends Activity implements OnClickListener {
	private ImageView imageView1, imageView2;
	private TextView textView;
	private EditText newpassword, newpasswordagain;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modificationuser);
		findView();
	}

	private void findView() {
		imageView1 = (ImageView) findViewById(R.id.iv_head_left);
		imageView2 = (ImageView) findViewById(R.id.iv_head_right);
		imageView2.setVisibility(View.INVISIBLE);
		textView = (TextView) findViewById(R.id.tv_head_title);
		textView.setText("修改账号");
		newpassword = (EditText) findViewById(R.id.modificationuser_newpassword_et);
		newpasswordagain = (EditText) findViewById(R.id.modificationuser_querennewpassword_et);
		button = (Button) findViewById(R.id.modificationuser_save_bt);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.modificationuser_save_bt:

			break;

		default:
			break;
		}
	}

}
