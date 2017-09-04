package hudongchuangxiang.com.jinglingzhiquan.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import hudongchuangxiang.com.jinglingzhiquan.fragment.ShouYiMXFragment;

public class ShouYiFragmentAdapter extends FragmentPagerAdapter {

    public ShouYiFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new ShouYiMXFragment();
    }

    @Override
    public int getCount() {
        return 4;
    }
}