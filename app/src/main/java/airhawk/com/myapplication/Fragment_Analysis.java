package airhawk.com.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

import static airhawk.com.myapplication.Activity_Main._180Days;
import static airhawk.com.myapplication.Activity_Main._1_Day_Chart;
import static airhawk.com.myapplication.Activity_Main._1_Year_Chart;
import static airhawk.com.myapplication.Activity_Main._30Days;
import static airhawk.com.myapplication.Activity_Main._7Days;
import static airhawk.com.myapplication.Activity_Main._90Days;
import static airhawk.com.myapplication.Activity_Main._AllDays;
import static airhawk.com.myapplication.Activity_Main.dataModels;
import static airhawk.com.myapplication.Activity_Main.graph_date;
import static airhawk.com.myapplication.Activity_Main.graph_high;
import static airhawk.com.myapplication.Activity_Main.graph_market_cap;
import static airhawk.com.myapplication.Activity_Main.graph_volume;
import static airhawk.com.myapplication.Activity_Main.market_name;

/**
 * Created by Julian Dinkins on 4/25/2018.
 */

public class Fragment_Analysis extends Fragment {
    private TabLayout tabLayout;
    private TextView a_name,a_marketcap,sup;
    private ListView h_listview;
    private CustomAdapter adapter;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.fragment_analysis, container, false);
        a_name =rootView.findViewById(R.id.aequity_name);
        sup=rootView.findViewById(R.id.supply);
        a_name.setText(market_name);
        a_marketcap =rootView.findViewById(R.id.aequity_market_cap);
        h_listview =rootView.findViewById(R.id.historical_listview);
        dataModels= new ArrayList<>();
        adapter= new CustomAdapter(dataModels,getContext());

            dataModels.clear();
            h_listview.invalidateViews();

        for (int i = 0; i < _AllDays.size(); i++)
        {
            dataModels.add(new Historical_Data_Model(graph_high.get(i), graph_volume.get(i), graph_date.get(i)));
            System.out.println(String.valueOf(dataModels.get(i)));}

        h_listview.setAdapter(adapter);
        tabLayout = (TabLayout)rootView.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("7D"));
        tabLayout.addTab(tabLayout.newTab().setText("1M"));
        tabLayout.addTab(tabLayout.newTab().setText("3M"));
        tabLayout.addTab(tabLayout.newTab().setText("6M"));
        tabLayout.addTab(tabLayout.newTab().setText("1Y"));
        tabLayout.addTab(tabLayout.newTab().setText("MAX"));
        tabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT);








        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:

                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        updateReceiptsList();
                        for (int i = 0; i < _AllDays.size(); i++) {

                            dataModels.add(new Historical_Data_Model((graph_high.subList(0, 7)).get(i), (graph_volume.subList(0, 7)).get(i), (graph_date.subList(0, 7)).get(i)));
                        }
                        adapter= new CustomAdapter(dataModels,getContext());
                        h_listview.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        break;
                    case 6:
                        for (int i = 0; i < _AllDays.size(); i++) {

                            dataModels.add(new Historical_Data_Model((graph_high.subList(0, 7)).get(i), (graph_volume.subList(0, 7)).get(i), (graph_date.subList(0, 7)).get(i)));
                            }
                            adapter= new CustomAdapter(dataModels,getContext());
                            h_listview.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        break;
                    default:
                    break;
                }





            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return rootView;

    }
    public void updateReceiptsList() {
        adapter.clear();
        adapter.addAll(dataModels);
        adapter.notifyDataSetChanged();
    }
}


