package cn.univs.app.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import cn.univs.api.bean.PhotosItem.ImageData;
import cn.univs.app.R;
import cn.univs.app.util.ScreenSizeUtil;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

/****
 * 组图适配器
 * 
 * @author issuser
 * 
 */
public class PhotosActivityAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<ImageData> imagedata = new ArrayList<ImageData>();
	private ImageLoader instance;
	private DisplayImageOptions options;
	private float textSize = 15;
	private boolean CheckState;

	public PhotosActivityAdapter(Context context) {
		this.context = context;
		instance = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.def_image)
				.showImageForEmptyUri(R.drawable.def_image)
				.showImageOnFail(R.drawable.def_image).cacheInMemory(true)
				.cacheOnDisc(true).build();
	}

	public void addData(ArrayList<ImageData> imagedata) {
		this.imagedata.addAll(imagedata);
		this.notifyDataSetChanged();
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
		this.notifyDataSetChanged();
	}

	public void setNightMode(boolean CheckState) {
		this.CheckState = CheckState;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return imagedata.size();
	}

	@Override
	public ImageData getItem(int position) {
		return imagedata.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHodler viewholder = null;
		if (convertView == null) {
			viewholder = new ViewHodler();
			convertView = View.inflate(context, R.layout.univs_itme_photos,
					null);
			viewholder.iv_icon = (ImageView) convertView
					.findViewById(R.id.iv_icon);
			viewholder.txt_content = (TextView) convertView
					.findViewById(R.id.txt_content);
			convertView.setTag(viewholder);
		} else {
			viewholder = (ViewHodler) convertView.getTag();
		}
		ImageData item = getItem(position);
		instance.displayImage(item.getImage(), viewholder.iv_icon, options,
				new SimpleImageLoadingListener() {
					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						super.onLoadingComplete(imageUri, view, loadedImage);
						float width = loadedImage.getWidth();
						int screenWidth = ScreenSizeUtil
								.getScreenWidth(context);
						if (width > screenWidth) {
							view.setLayoutParams(new LayoutParams(
									screenWidth,
									(int) (loadedImage.getHeight() / (width / screenWidth))));
						} else {
							view.setLayoutParams(new LayoutParams(
									screenWidth,
									(int) (loadedImage.getHeight() * (screenWidth / width))));
						}

					}

				});
		viewholder.txt_content.setTextSize(textSize);
		viewholder.txt_content.setText(item.getNote() + "");

		if (CheckState) {
			viewholder.txt_content.setTextColor(Color.WHITE);
		} else {
			viewholder.txt_content.setTextColor(Color.BLACK);
		}
		return convertView;
	}

	class ViewHodler {
		public ImageView iv_icon;
		public TextView txt_content;
	}

}
