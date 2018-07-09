package airhawk.com.myapplication;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class Adapter_Chosen_Aequity extends FragmentPagerAdapter {

    private Context mContext;

    public Adapter_Chosen_Aequity(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }


    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new Fragment_Analysis();
        } else if (position == 1){
            return new Fragment_News();
        } else if (position == 2){
            return new Fragment_Video();
        } else if (position == 3){
            return new Fragment_Exchanges();
        }

        return null;
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 4;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return mContext.getString(R.string.title_analysis);
            case 1:
                return mContext.getString(R.string.title_news);
            case 2:
                return mContext.getString(R.string.title_video);
            case 3:
                return mContext.getString(R.string.title_buy);
            default:
                return null;
        }
    }

}