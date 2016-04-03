package cn.univs.app.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @author ArticleFragment 头部viewpager adapter
 * 
 */
public class GuidePagerAdapter extends PagerAdapter {

	int[] ints;
	Context context;
	DisplayImageOptions options;
	ImageLoader imageLoader;

	public GuidePagerAdapter(int[] ints, Context context) {
		this.ints = ints;
		this.context = context;
	}

	@Override
	public int getCount() {
		return ints.length;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView image = new ImageView(context);
		image.setScaleType(ImageView.ScaleType.FIT_XY);
		image.setImageResource(ints[position]);
		((ViewPager) container).addView(image);
		return image;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}

}
