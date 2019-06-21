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

/**
 * Created by Julian Dinkins on 4/25/2018.
 */

public class Fragment_Video extends Fragment {
    Activity_Main mainActivity = (Activity_Main) getActivity();
    TextView video;
    public Fragment_Video() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video, container, false);
        RecyclerView recyclerView=(RecyclerView)rootView.findViewById(R.id.list);
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Oregon.ttf");
        video =rootView.findViewById(R.id.video);
        video.setTypeface(custom_font);
        video.setPaintFlags(video.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        Adapter_Youtube adapter=new Adapter_Youtube(getActivity());
        recyclerView.setAdapter(adapter);
        return rootView;
    }
}


