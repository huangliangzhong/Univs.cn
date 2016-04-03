package cn.univs.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.os.Environment;
import cn.univs.app.UnivsApplication;
import cn.univs.app.util.ApplicationUtil;

public class DownloadWebImgTask extends AsyncTask<String, Integer, Boolean> {
	public static final String TAG = "DownloadImgTask";
	private String path;

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected Boolean doInBackground(String... params) {
		String mUrl = params[0];
		if (mUrl == null || mUrl.equals("") || mUrl.equals("null")) {
			return false;
		}
		URL url = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		HttpURLConnection urlCon = null;
		try {
			path = Environment.getExternalStorageDirectory().getPath()
					+ "/中大在线/image/";
			Environment.getExternalStoragePublicDirectory(path);
			final File fileDirectory = new File(path);
			if (!fileDirectory.exists()) {
				fileDirectory.mkdirs();
			}
			final File file = new File(fileDirectory, mUrl.substring(mUrl
					.lastIndexOf(File.separator)));
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
			url = new URL(mUrl);
			urlCon = (HttpURLConnection) url.openConnection();
			urlCon.setRequestMethod("GET");
			urlCon.setDoInput(true);
			urlCon.connect();
			inputStream = urlCon.getInputStream();
			outputStream = new FileOutputStream(file);
			byte buffer[] = new byte[1024];
			int bufferLength = 0;
			int i = 0;
			while ((bufferLength = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, bufferLength);
				i++;
				publishProgress(i);
			}
			outputStream.flush();
			return true;
		} catch (final Exception e) {
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;

	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if (result) {
			ApplicationUtil.showToastInLogin("保存路径：" + path);
		} else {
			ApplicationUtil.showToastInLogin("保存失败");
		}
	}
}