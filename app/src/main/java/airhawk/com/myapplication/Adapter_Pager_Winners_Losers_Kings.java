package airhawk.com.myapplication;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class Adapter_Pager_Winners_Losers_Kings extends FragmentPagerAdapter {
    private Context mContext;
    public Adapter_Pager_Winners_Losers_Kings(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }
    @Override
    public Fragment getItem(int position) {
        if (position == 0)
        {
            return new Fragment_Saved();
        }else if (position == 1)
        {
            return new Fragment_App_News();
        }
         else if (position == 2)
        {
            return new Fragment_Winners();
        }
         else if (position == 3)
        {
            return new Fragment_Losers();
        }
        else if (position == 4)
        {
            return new Fragment_Market_Kings();
        }
        /*
        else if (position == 5)
        {
            return new Fragment_ICO();
        }
        else if (position == 6)
        {
            return new Fragment_IPO();
        }
*/
        return null;
    }
    @Override
    public int getCount() {
        return 5;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {

            case 0:
                return mContext.getString(R.string.saved);
            case 1:
                return mContext.getString(R.string.news);
            case 2:
                return mContext.getString(R.string.leaders);
            case 3:
                return mContext.getString(R.string.losers);
            case 4:
                return mContext.getString(R.string.market_kings);
                /*
            case 5:
                return mContext.getString(R.string.ico);
            case 6:
                return mContext.getString(R.string.ipo);
*/


            default:
                return null;
        }
    }

}