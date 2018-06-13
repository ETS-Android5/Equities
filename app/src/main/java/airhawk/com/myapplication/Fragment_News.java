package airhawk.com.myapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import static airhawk.com.myapplication.App_Variables.feedItems;
import static airhawk.com.myapplication.Activity_Main.progress;

/**
 * Created by Julian Dinkins on 4/25/2018.
 */

public class Fragment_News extends Fragment {
    private RecyclerView news_feed;

    public Fragment_News() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        news_feed=rootView.findViewById(R.id.news_feed);
        news_feed.setAdapter(new News_FeedAdapter(getActivity(), feedItems));
        news_feed.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));



        return rootView;
    }




}

