package airhawk.com.myapplication;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class Adapter_Pager_Winners_Losers_Volume extends FragmentPagerAdapter {
    private Context mContext;
    public Adapter_Pager_Winners_Losers_Volume(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new Fragment_Winners();
        } else if (position == 1) {
            return new Fragment_Losers();

       /* } else if (position == 2){
            return new Fragment_Volume();

        */
        }
        return null;
    }
    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return mContext.getString(R.string.winners);
            case 1:
                return mContext.getString(R.string.losers);
            case 2:
                return mContext.getString(R.string.volume);
            default:
                return null;
        }
    }

}