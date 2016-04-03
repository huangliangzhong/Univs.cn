package cn.univs.app;

import java.io.File;

import android.app.Application;
import android.os.Build;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

@SuppressWarnings("deprecation")
public class UnivsApplication extends Application {
	private static UnivsApplication mApp;

	public static UnivsApplication getInstance() {
		return mApp;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		initImageLoad();
	}

	public UnivsApplication() {
		mApp = this;
	}

	public File getExternalStorageDirectory() {
		File externalStorageDirectory = Environment
				.getExternalStorageDirectory();
		int sdk = Integer.valueOf(Build.VERSION.SDK);
		if (sdk >= 8 && externalStorageDirectory == null) {
			externalStorageDirectory = getExternalFilesDir(null);
		}
		if (externalStorageDirectory != null) {
			externalStorageDirectory = new File(
					externalStorageDirectory.getAbsolutePath(),
					Constants.APP_FOLDER);
			if (!externalStorageDirectory.exists()) {
				if (!externalStorageDirectory.mkdirs()) {
					externalStorageDirectory = null;
				}
			}
		}
		return externalStorageDirectory;
	}

	private void initImageLoad() {
		File externalStorageDirectory = getExternalStorageDirectory();
		if (externalStorageDirectory != null) {
			externalStorageDirectory = new File(
					externalStorageDirectory.getAbsolutePath(), "imageCach");
			if (!externalStorageDirectory.exists()) {
				if (!externalStorageDirectory.mkdirs()) {
					externalStorageDirectory = null;
				}
			}
		}
		Builder config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.threadPriority(Thread.NORM_PRIORITY - 1)
				.memoryCache(new LruMemoryCache(10 * 1024 * 1024))
				.memoryCacheSize(10 * 1024 * 1024)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO);
		if (externalStorageDirectory != null) {
			config.discCache(new UnlimitedDiscCache(externalStorageDirectory));
		}
		ImageLoaderConfiguration build = config.build();
		ImageLoader.getInstance().init(build);
	}

	public File getCachDateeDir() {
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		if (!sdCardExist) {
			return getCacheDir();
		}
		File externalStorageDirectory = getExternalStorageDirectory();
		File cachDateeDir = new File(
				externalStorageDirectory.getAbsoluteFile(),
				Constants.APP_CACH_DATA_FOLDER);
		if (!cachDateeDir.exists()) {
			if (!cachDateeDir.mkdirs()) {
				cachDateeDir = null;
			}
		}
		return cachDateeDir;
	}

}
