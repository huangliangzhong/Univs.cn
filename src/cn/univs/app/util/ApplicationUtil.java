package cn.univs.app.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;
import cn.univs.app.UnivsApplication;

public class ApplicationUtil {

	private static final String USER_AGENT_FORMAT = "Android/%s/%s/%d/%s";

	public static final UnivsApplication getApplication() {
		return UnivsApplication.getInstance();
	}

	public static final String getPhoenAgent() {
		return "android" + " " + android.os.Build.MODEL + " "
				+ android.os.Build.VERSION.RELEASE;
	}

	/**
	 * 获取设备唯一标识码
	 * 
	 * @param context
	 * @return
	 */
	public static String getDeviceId() {
		TelephonyManager tm = (TelephonyManager) getApplication()
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}

	public static final String getAPKChannel() {
		ApplicationInfo appInfo;
		try {
			appInfo = getApplication().getPackageManager().getApplicationInfo(
					getApplication().getPackageName(),
					PackageManager.GET_META_DATA);
			return appInfo.metaData.getString("UMENG_CHANNEL");
		} catch (NameNotFoundException e) {
			return null;
		}
	}

	public static final int getSdkVersion() {
		return Integer.valueOf(Build.VERSION.SDK).intValue();
	}

	public static final int getVersion() {
		try {
			return getApplication().getPackageManager().getPackageInfo(
					getApplication().getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			return 0;
		}
	}

	public static final String getVersionName() {
		try {
			return getApplication().getPackageManager().getPackageInfo(
					getApplication().getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			return null;
		}
	}

	public static final String getScreenResolution() {
		WindowManager wm = (WindowManager) getApplication().getSystemService(
				Context.WINDOW_SERVICE);
		if (wm != null && wm.getDefaultDisplay() != null) {
			return String.format("%dx%d", wm.getDefaultDisplay().getWidth(), wm
					.getDefaultDisplay().getHeight());
		}
		return "";
	}

	public static final String getUserAgent() {
		return String.format(USER_AGENT_FORMAT, android.os.Build.MODEL,
				android.os.Build.VERSION.SDK, getVersion(),
				getScreenResolution());
	}

	public static final NotificationManager getNotificationService() {
		return (NotificationManager) getApplication().getSystemService(
				Context.NOTIFICATION_SERVICE);
	}

	public static final String getCurrentLocale() {
		return ResourceUtil.getConfiguration().locale.toString().replace('_',
				'-');
	}

	public static void showToastInLogin(String text) {
		Toast.makeText(getApplication(), text, 0).show();
	}

	public static Bitmap getBitmaptoScrollView(ScrollView scrollView) {
		int h = 0;
		for (int i = 0; i < scrollView.getChildCount(); i++) {
			h += scrollView.getChildAt(i).getHeight();
		}
		Bitmap bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
				Bitmap.Config.ARGB_8888);
		final Canvas c = new Canvas(bitmap);
		scrollView.draw(c);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		return bitmap;
	}

	public static void writeBitmap(Bitmap photo, String fPath) {
		try {
			File sdCard = new File(fPath);
			FileOutputStream outStreamz = new FileOutputStream(sdCard);
			photo.compress(Bitmap.CompressFormat.PNG, 100, outStreamz);
			outStreamz.flush();
			outStreamz.close();
			if (photo.isRecycled()) {
				photo.recycle();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void showSoftKeyboard(final Context context,
			final EditText editText) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				((InputMethodManager) context
						.getSystemService(Context.INPUT_METHOD_SERVICE))
						.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}, 50);
		if (editText == null) {
			return;
		}
		editText.requestFocus();
	}

	public static void hideSoftKeyboard(final Context context, EditText editText) {
		final InputMethodManager inputMethodManager = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (inputMethodManager.isActive()) {
			inputMethodManager.hideSoftInputFromWindow(
					editText.getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	public static void hideSoftKeyboard(final Context context) {
		((InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(((Activity) context).getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
