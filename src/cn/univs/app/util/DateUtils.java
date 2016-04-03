package cn.univs.app.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("deprecation")
public class DateUtils {
	private static long DAYTICKS = 24 * 60 * 60 * 1000;
	private static long HOUR = 60 * 60 * 1000;

	private static Date String2Date(String date) {
		long time = Long.parseLong(date);
		Date da = new Date(time * 1000);
		return da;
	}

	public static boolean isToday(String sdate) {
		Date date = String2Date(sdate);
		Date now = new Date(System.currentTimeMillis());
		Date todayEnd = new Date(now.getYear(), now.getMonth(), now.getDate(),
				23, 59);
		return todayEnd.getTime() - date.getTime() > 0
				&& (todayEnd.getTime() - date.getTime()) / DAYTICKS == 0;
	}

	public static String formatDate(final Date pDate) {
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return dateFormat.format(pDate);
	}

	public static String formatDate(final Date date, DateFormat dateFormat) {
		return dateFormat.format(date);
	}

	public static String formatDateL(final Date pDate) {
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(pDate);
	}

	public static String formatDateF(final Date pDate) {
		final DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
		return dateFormat.format(pDate);
	}

	public static String formatDateM(final Date pDate) {
		final DateFormat dateFormat = new SimpleDateFormat("MM/dd");
		return dateFormat.format(pDate);
	}

	public static String formatDateY(final Date pDate) {
		final DateFormat dateFormat = new SimpleDateFormat("yyyy");
		return dateFormat.format(pDate);
	}

	public static String formatDateTime(Date date) {
		String retStr = "";
		retStr = date.getHours() + ":" + date.getMinutes();
		return retStr;
	}

//	public static String formatDateCN(Date pDate) {
//		final DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
//		return dateFormat.format(pDate);
//	}
	
	public static String formatDateCN(Date pDate) {
		final DateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
		return dateFormat.format(pDate);
	}

	public static String formatDateDifference(final Date pDate) {
		// X秒前
		final long differenceTime = (System.currentTimeMillis() - pDate
				.getTime()) / 1000;
		// X分钟前
		final long minute = differenceTime / 60;
		// X小时前
		final long hour = minute / 60;
		// X天前
		final long day = hour / 24;

		if (differenceTime < 60) {
			return "1分钟前";
		} else if (minute < 60) {
			return minute + "分钟前";
		} else if (hour < 24) {
			return hour + "小时前";
		} else {
			return day + "天前";
		}
	}
}