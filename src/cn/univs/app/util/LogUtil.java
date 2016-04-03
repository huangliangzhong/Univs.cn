package cn.univs.app.util;

import android.util.Log;

public class LogUtil {
	private final static String tag = "LogUtil";

	public static void d(Class<?> clazz, String msg, int lineNum) {
		Log.d(tag, new LogUtil().new LogContent(clazz, msg, lineNum).toString());
	}

	public static void e(Class<?> clazz, String msg, int lineNum) {
		Log.e(tag, new LogUtil().new LogContent(clazz, msg, lineNum).toString());
	}

	public static void v(Class<?> clazz, String msg, int lineNum) {
		Log.v(tag, new LogUtil().new LogContent(clazz, msg, lineNum).toString());
	}

	public static void i(Class<?> clazz, String msg, int lineNum) {
		Log.i(tag, new LogUtil().new LogContent(clazz, msg, lineNum).toString());
	}

	public static void w(Class<?> clazz, String msg, int lineNum) {
		Log.w(tag, new LogUtil().new LogContent(clazz, msg, lineNum).toString());
	}

	class LogContent {
		private String tag;
		private String msg;
		private int lineNum;

		public LogContent(Class<?> clazz, String msg, int lineNum) {
			super();
			this.tag = clazz.getName();
			this.msg = msg;
			this.lineNum = lineNum;
		}

		@Override
		public String toString() {
			return "LogContent [tag=" + tag + ", msg=" + msg + ", lineNum="
					+ lineNum + "]";
		}
	}
}
