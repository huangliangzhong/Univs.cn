package cn.univs.app.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class BVBitmapUtil {
	public static Bitmap scaleImg(Bitmap bm, int newWidth, int newHeight) {
		// 图片源
		// Bitmap bm = BitmapFactory.decodeStream(getResources()
		// .openRawResource(id));
		// 获得图片的宽高
		int width = bm.getWidth();
		int height = bm.getHeight();
		// 设置想要的大小
		int newWidth1 = newWidth;
		int newHeight1 = newHeight;
		// 计算缩放比例
		float scaleWidth = ((float) newWidth1) / width;
		float scaleHeight = ((float) newHeight1) / height;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 得到新的图片
		Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
				true);
		return newbm;
	}

	public static Bitmap scaleImg(Bitmap bm, int newWidth) {
		// 图片源
		// Bitmap bm = BitmapFactory.decodeStream(getResources()
		// .openRawResource(id));
		// 获得图片的宽高
		int width = bm.getWidth();
		int height = bm.getHeight();
		// 设置想要的大小
		int newWidth1 = newWidth;
		// 计算缩放比例
		float scaleWidth = ((float) newWidth1) / width;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, newWidth1);
		// 得到新的图片
		Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
				true);
		return newbm;
	}

	public static Bitmap setBitmapAttr(Bitmap bitmap, int tarWidth,
			int tarHeigth) {
		Bitmap scaleBmp = null;
		Bitmap tarBmp = null;
		int bmpW = bitmap.getWidth();
		int bmpH = bitmap.getHeight();
		// 根据屏幕宽度设置图片大小
		// tarHeigth = bmpH * GlobalConst.SCREEN_WIDTH / bmpW;
		float scaleH = (float) tarHeigth / bmpH;
		float scaleW = (float) tarWidth / bmpW;

		try {
			int destW = (int) (bmpW * scaleW);
			scaleBmp = Bitmap
					.createScaledBitmap(bitmap, destW, tarHeigth, true);
			tarBmp = Bitmap.createBitmap(scaleBmp, 0, 0, tarWidth, tarHeigth);

			int destH = (int) (bmpH * scaleH);
			scaleBmp = Bitmap.createScaledBitmap(bitmap, tarWidth, destH, true);
			tarBmp = Bitmap.createBitmap(scaleBmp, 0, 0, tarWidth, tarHeigth);

			if (scaleBmp != null && !scaleBmp.isRecycled()) {
				scaleBmp.recycle();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tarBmp;
	}

	public static InputStream bitmaptoIO(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		InputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		return isBm;
	}
}
