package cn.univs.app.fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;

import org.apache.http.Header;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import cn.univs.api.MyHttpAPIControl;
import cn.univs.api.ParseUtils;
import cn.univs.api.bean.ArticleItme;
import cn.univs.api.bean.UnivsDataBase;
import cn.univs.app.Constants;
import cn.univs.app.R;
import cn.univs.app.activity.NewsActivity;
import cn.univs.app.activity.PhotosActivity;
import cn.univs.app.activity.SpecialActivity2;
import cn.univs.app.adapter.ArticleAdapter;
import cn.univs.app.adapter.PictureScrollPagerAdapter;
import cn.univs.app.util.ResourceUtil;
import cn.univs.app.util.ScreenSizeUtil;
import cn.univs.pulltorefresh.library.PullToRefreshBase;
import cn.univs.pulltorefresh.library.PullToRefreshBase.Mode;
import cn.univs.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import cn.univs.pulltorefresh.library.PullToRefreshListView;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class ArticlePageFragment extends Fragment {
	private int page = 1;
	private long article_last_time = 0;
	private long slide_last_time = 0;
	private LayoutInflater mLayoutInflater;
	private String mTag_id;
	private View mContentView;
	private PullToRefreshListView mPullListView;
	private ArticleAdapter articleAdapter;
	private ViewPager head_img;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public static ArticlePageFragment newInstance(String tag_id) {
		ArticlePageFragment articleFragment = new ArticlePageFragment();
		Bundle args = new Bundle();
		args.putString(Constants.BundleKey.PAGE_TAG_ID, tag_id);
		articleFragment.setArguments(args);
		return articleFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Bundle bundle = getArguments();
		mLayoutInflater = inflater;
		mTag_id = bundle.getString(Constants.BundleKey.PAGE_TAG_ID);
		mContentView = inflater.inflate(R.layout.fragment_layout_content_list,
				null);
		initView(savedInstanceState);
		return mContentView;
	}

	@SuppressWarnings("unchecked")
	private void initView(Bundle savedInstanceState) {
		mPullListView = (PullToRefreshListView) mContentView
				.findViewById(R.id.mylistView);
		mPullListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				page = 0;
				getdata(true, false);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				getdata(false, false);
			}
		});
		mPullListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ArticleItme item = articleAdapter.getItem(position - 2);
				MyHttpAPIControl.newInstance().getTongji(item.getContentid(),
						new AsyncHttpResponseHandler() {
					//要调用统计接口进行统计
				});
				if (item.getModelid() == 10) {
					Intent intent = new Intent(getActivity(),
							SpecialActivity2.class);
					intent.putExtra("SpecialActivity", item);
					startActivity(intent);
				} else if (item.getModelid() == 2) {
					Intent intent = new Intent(getActivity(),
							PhotosActivity.class);
					intent.putExtra("SpecialActivity", item);
					startActivity(intent);
				} else if (item.getModelid() == 1) {
					Intent intent = new Intent(getActivity(),
							NewsActivity.class);
					intent.putExtra("SpecialActivity", item);
					startActivity(intent);
				}
			}
		});
		initHeadView();
		getNewsListSlide();
		if (savedInstanceState != null) {
			ArrayList<ArticleItme> data = (ArrayList<ArticleItme>) savedInstanceState
					.getSerializable("data");
			page = savedInstanceState.getInt("page");
			articleAdapter = new ArticleAdapter(getActivity());
			mPullListView.setAdapter(articleAdapter);
			articleAdapter.addData(data);
		} else {
			getdata(true, true);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (articleAdapter != null) {
			ArrayList<ArticleItme> data = articleAdapter.getData();
			outState.putSerializable("data", data);
			outState.putInt("page", page);
		}

	}

	private void initHeadView() {
		int screenWidth = ScreenSizeUtil.getScreenWidth(getActivity());
		android.widget.AbsListView.LayoutParams layoutParams = new android.widget.AbsListView.LayoutParams(
				screenWidth, screenWidth / 100 * 63);
		View mHeadView = mLayoutInflater.inflate(
				R.layout.univs_layout_list_head_view, null);
		head_img = (ViewPager) mHeadView.findViewById(R.id.viewpager);
		mradios = (LinearLayout) mHeadView.findViewById(R.id.radio);
		mHeadView.setLayoutParams(layoutParams);
		ListView refreshableListVieww = mPullListView.getRefreshableView();
		mPullListView.setInterceptView(mHeadView);
		refreshableListVieww.addHeaderView(mHeadView);
	}

	private void getdata(final boolean isRefresh, final boolean frist) {
		MyHttpAPIControl.newInstance().getNewsList(mTag_id, page,
				article_last_time, new AsyncHttpResponseHandler() {
					private ProgressDialog dialog;

					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						mPullListView.onRefreshComplete();
					}

					@Override
					public void onStart() {
						super.onStart();
						if (isRefresh && isAdded() && frist) {
							dialog = new ProgressDialog(getActivity());
							dialog.show();
						}
					}

					@SuppressWarnings("unchecked")
					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);
						if (dialog != null) {
							dialog.dismiss();
						}
						mPullListView.onRefreshComplete();
						Type tp = new TypeToken<UnivsDataBase<ArticleItme>>() {
						}.getType();
						UnivsDataBase<ArticleItme> ss = (UnivsDataBase<ArticleItme>) ParseUtils
								.Gson2Object(content, tp);
						if (ss != null && ss.isState()) {
							article_last_time = ss.getTime();
							ArrayList<ArticleItme> articles = ss.getData();
							if (isRefresh) {
								mPullListView.setMode(Mode.BOTH);
								articleAdapter = new ArticleAdapter(
										getActivity());
								mPullListView.setAdapter(articleAdapter);
							}
							if (articles != null && articles.size() > 0) {
								page++;
								articleAdapter.addData(articles);
							}
						}

					}

				});
	}

	private PictureScrollPagerAdapter pictureScrollPagerAdapter;
	private LinearLayout mradios;

	private void getNewsListSlide() {
		MyHttpAPIControl.newInstance().getNewsListSlide(mTag_id,
				slide_last_time, new AsyncHttpResponseHandler() {
					@SuppressWarnings("deprecation")
					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);
						if (isAdded()) {
							Type tp = new TypeToken<UnivsDataBase<ArticleItme>>() {
							}.getType();
							@SuppressWarnings("unchecked")
							UnivsDataBase<ArticleItme> ss = (UnivsDataBase<ArticleItme>) ParseUtils
									.Gson2Object(content, tp);

							if (ss != null && ss.isState()) {
								slide_last_time = ss.getTime();
								ArrayList<ArticleItme> data = ss.getData();
								pictureScrollPagerAdapter = new PictureScrollPagerAdapter(
										ss.getData(), getActivity());
								ImageView imageview = null;
								for (int i = 0; i < data.size(); i++) {
									LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
											LayoutParams.WRAP_CONTENT,
											LayoutParams.WRAP_CONTENT);
									params.leftMargin = 12;
									params.rightMargin = 12;
									imageview = new ImageView(getActivity());
									imageview.setBackgroundDrawable(ResourceUtil
											.getDrawable(R.drawable.selector_radoi_btn));
									mradios.addView(imageview);
									if (i == 0) {
										mradios.setSelected(true);
									}
									imageview.setLayoutParams(params);
								}
								head_img.setAdapter(pictureScrollPagerAdapter);
								head_img.setOnPageChangeListener(new SimpleOnPageChangeListener() {

									@Override
									public void onPageSelected(int arg0) {
										int childCount = mradios
												.getChildCount();
										for (int i = 0; i < childCount; i++) {
											View childAt = mradios
													.getChildAt(i);
											if (i == arg0) {
												childAt.setSelected(true);
											} else {
												childAt.setSelected(false);
											}
										}

									}

								});
							}
						}
					}
				});
	}
}
