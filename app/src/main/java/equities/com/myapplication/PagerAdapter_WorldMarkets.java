package equities.com.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static equities.com.myapplication.Activity_Markets_Main.pager;

public class PagerAdapter_WorldMarkets extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public PagerAdapter_WorldMarkets(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
    if (pager.getVisibility()== View.VISIBLE){
        Fragment fragment = null;
        switch (position) {
            case 0: {
                fragment = new Fragment_Markets();
                break;
            }
            case 1: {
                fragment = new Fragment_App_News();
                break;
            }
            case 2: {
                fragment = new Fragment_Video();
                break;
            }
        }

        return fragment;}else{
        //System.out.println("PAGER IS NOT VISIBLE" );
        return mFragmentList.get(position);

    }
    }

    @Override
    public int getCount() {

        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    public void removeFrag(Fragment fragment, String title) {
        mFragmentList.remove(fragment);
        mFragmentTitleList.remove(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}