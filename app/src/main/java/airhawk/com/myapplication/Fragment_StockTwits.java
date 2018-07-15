package airhawk.com.myapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import static airhawk.com.myapplication.Constructor_App_Variables.stocktwits_feedItems;

public class Fragment_StockTwits extends Fragment {
    private RecyclerView stocktwits_feed;

    public Fragment_StockTwits() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stocktwits, container, false);
        stocktwits_feed=rootView.findViewById(R.id.stocktwits_feed);
        stocktwits_feed.setAdapter(new Adapter_StockTwits_Feed(getActivity(), stocktwits_feedItems));
        stocktwits_feed.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        return rootView;
    }
}
