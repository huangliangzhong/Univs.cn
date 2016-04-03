package cn.univs.app.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;
import cn.univs.api.bean.SpecialItme;
import cn.univs.app.fragment.SpecialPageFragment;

public class SpecialFragmentPagerAdapter extends FragmentStatePagerAdapter {
	private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
	private FragmentManager fm;

	public SpecialFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		this.fm = fm;
	}

	public SpecialFragmentPagerAdapter(FragmentManager fm,
			ArrayList<SpecialItme> mSpecial) {
		super(fm);
		this.fm = fm;
		SpecialPageFragment newInstance = null;
		for (SpecialItme itme : mSpecial) {
			newInstance = SpecialPageFragment.newInstance(itme.getData());
			fragments.add(newInstance);
		}
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	public void setFragments(ArrayList<Fragment> fragments) {
		if (this.fragments != null) {
			FragmentTransaction ft = fm.beginTransaction();
			for (Fragment f : this.fragments) {
				ft.remove(f);
			}
			ft.commit();
			ft = null;
			fm.executePendingTransactions();
		}
		this.fragments = fragments;
		notifyDataSetChanged();
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		Object obj = super.instantiateItem(container, position);
		return obj;
	}

}
