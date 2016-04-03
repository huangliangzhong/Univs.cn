package cn.univs.app.adapter;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.univs.api.bean.ArticleItme;
import cn.univs.api.bean.SpecialItme;
import cn.univs.api.bean.Type;
import cn.univs.app.R;
import cn.univs.app.util.DateUtils;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ArticleAdapter2 extends BaseAdapter {
	private Context context;
	private ArrayList<ArticleItme> mArticles = new ArrayList<ArticleItme>();
	private ImageLoader instance;
	private DisplayImageOptions options;
	private ArrayList<SpecialItme> mSpecialItme = new ArrayList<SpecialItme>();;
	private int[] tag = { 0 };

	public ArticleAdapter2(Context context) {
		this.context = context;
		instance = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.def_image)
				.showImageForEmptyUri(R.drawable.def_image)
				.showImageOnFail(R.drawable.def_image).cacheInMemory(true)
				.cacheOnDisc(true).build();

	}

	public void addData(ArrayList<SpecialItme> specialItmes) {
		if (specialItmes != null) {
			mSpecialItme = specialItmes;
			tag = new int[mSpecialItme.size()];
			for (int i = 0; i < specialItmes.size(); i++) {
				ArrayList<ArticleItme> data = specialItmes.get(i).getData();
				this.mArticles.addAll(data);
				tag[i] = mArticles.size() - data.size();
			}
			this.notifyDataSetChanged();
		}
	}

	public ArrayList<ArticleItme> getData() {
		return mArticles;
	}

	@Override
	public int getCount() {
		return mArticles.size();
	}

	@Override
	public ArticleItme getItem(int position) {
		return mArticles.get(position);
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
			convertView = View.inflate(context,
					R.layout.univs_itme_article_view2, null);
			viewholder.sepcial_itme_title = (TextView) convertView
					.findViewById(R.id.sepcial_itme_title);
			viewholder.image_icon = (ImageView) convertView
					.findViewById(R.id.article_itme_icon);
			viewholder.type = (TextView) convertView
					.findViewById(R.id.article_itme_type);
			viewholder.time = (TextView) convertView
					.findViewById(R.id.article_itme_time);
			viewholder.title = (TextView) convertView
					.findViewById(R.id.article_itme_title);
			viewholder.content = (TextView) convertView
					.findViewById(R.id.article_itme_content);
			convertView.setTag(viewholder);
		} else {
			viewholder = (ViewHodler) convertView.getTag();
		}
		ArticleItme item = getItem(position);
		instance.displayImage(item.getThumb(), viewholder.image_icon, options);
		int tag2 = isTag(position);
		if (tag2 != -1) {
			viewholder.sepcial_itme_title.setText(mSpecialItme.get(tag2)
					.getCatname());
			viewholder.sepcial_itme_title.setVisibility(View.VISIBLE);
		} else {
			viewholder.sepcial_itme_title.setVisibility(View.GONE);
		}

		viewholder.type.setText(new Type(item.getModelid()).toString());
		viewholder.title.setText(item.getTitle());
		viewholder.content.setText(item.getDescription());
		viewholder.time.setText(DateUtils.formatDateDifference(new Date(item
				.getSorttime() * 1000)));
		return convertView;
	}

	private int isTag(int position) {
		if (tag.length > 0) {
			for (int i = 0; i < tag.length; i++) {
				if (position == tag[i]) {
					return i;
				}
			}
		}
		return -1;
	}

	class ViewHodler {
		public ImageView image_icon;
		public TextView sepcial_itme_title, type, time, title, content;
	}
}
