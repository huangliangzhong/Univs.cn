package cn.univs.app.util;

import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import cn.univs.app.UnivsApplication;

/**
 * @author Resource
 */
public class ResourceUtil {

	public static final Resources getResources() {
		return UnivsApplication.getInstance().getResources();
	}

	public static final String getString(int id) {
		return getResources().getString(id);
	}

	public static final Drawable getDrawable(int id) {
		return getResources().getDrawable(id);
	}

	public static final String[] getStringArray(int id) {
		return getResources().getStringArray(id);
	}

	public static final int[] getIntArray(int id) {
		return getResources().getIntArray(id);
	}

	public static DisplayMetrics getDisplayMetrics() {
		return getResources().getDisplayMetrics();
	}

	public static int getColor(int colorId) {
		return getResources().getColor(colorId);
	}

	public static ColorStateList getColorStateList(int colorId) {
		return getResources().getColorStateList(colorId);
	}

	public static final int getPixelDimension(int dip) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dip, getDisplayMetrics());
	}

	public static final Configuration getConfiguration() {
		return getResources().getConfiguration();
	}

}
