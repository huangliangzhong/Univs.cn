package cn.univs.app.fragment;

import java.util.ArrayList;

import com.loopj.android.http.AsyncHttpResponseHandler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import cn.univs.api.MyHttpAPIControl;
import cn.univs.api.bean.ArticleItme;
import cn.univs.app.Constants;
import cn.univs.app.R;
import cn.univs.app.activity.NewsActivity;
import cn.univs.app.activity.PhotosActivity;
import cn.univs.app.activity.SpecialActivity2;
import cn.univs.app.adapter.ArticleAdapter;
import cn.univs.pulltorefresh.library.PullToRefreshBase.Mode;
import cn.univs.pulltorefresh.library.PullToRefreshListView;

public class SpecialPageFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	private String mTag_id;
	private View mContentView;
	private PullToRefreshListView mPullListView;
	private ArticleAdapter articleAdapter;
	private ArrayList<ArticleItme> mArticles;

	public static SpecialPageFragment newInstance(
			ArrayList<ArticleItme> mArticles) {
		SpecialPageFragment articleFragment = new SpecialPageFragment();
		Bundle args = new Bundle();
		args.putSerializable(Constants.BundleKey.SPECIAL_LIST, mArticles);
		articleFragment.setArguments(args);
		return articleFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Bundle bundle = getArguments();
		mArticles = (ArrayList<ArticleItme>) bundle
				.getSerializable(Constants.BundleKey.SPECIAL_LIST);	
		mContentView = inflater.inflate(R.layout.fragment_layout_content_list,
				null);
		initView();
		return mContentView;
	}

	private void initView() {
		mPullListView = (PullToRefreshListView) mContentView
				.findViewById(R.id.mylistView);
		mPullListView.setMode(Mode.DISABLED);
		articleAdapter = new ArticleAdapter(getActivity());
		mPullListView.setAdapter(articleAdapter);
		articleAdapter.addData(mArticles);
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

	}

}
