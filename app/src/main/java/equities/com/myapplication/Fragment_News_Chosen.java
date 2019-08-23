package equities.com.myapplication;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static equities.com.myapplication.Constructor_App_Variables.feedItems;


/**
 * Created by Julian Dinkins on 4/25/2018.
 */

public class Fragment_News_Chosen extends Fragment {
    private RecyclerView news_feed;
    TextView news;

    public Fragment_News_Chosen() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chosen_news, container, false);

        news_feed=rootView.findViewById(R.id.news_feed);
        news_feed.setAdapter(new Adapter_Chosen_News_Feed(getActivity(), feedItems));
        news_feed.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));



        return rootView;
    }




}

