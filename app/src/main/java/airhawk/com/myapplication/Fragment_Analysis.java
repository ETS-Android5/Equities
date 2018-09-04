package airhawk.com.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Arrays;
import java.util.Collections;

import static airhawk.com.myapplication.Constructor_App_Variables.graph_date;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_high;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_volume;

/**
 * Created by Julian Dinkins on 4/25/2018.
 */

public class Fragment_Analysis extends Fragment {
    TextView a_price,a_price_change,a_name,a_symbol,a_type,a_supply,a_cap,sup,saved;
    ImageView save;
    double xx= 0;
    Constructor_App_Variables ap_info =new Constructor_App_Variables();
    private Database_Local_Aequities db;
    TabLayout tabchoice;
    GraphView graph_view;
    Adapter_Graph_Points ab;
    static Integer integer;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_analysis, container, false);
        RecyclerView historical_listview =rootView.findViewById(R.id.historical_listview);
        graph_view=rootView.findViewById(R.id.graph_view);
        String ap_pc = ap_info.getCurrent_Aequity_Price_Change();
        a_name =rootView.findViewById(R.id.aequity_name);
        a_type =rootView.findViewById(R.id.aequity_type);
        a_symbol=rootView.findViewById(R.id.aequity_symbol);
        a_supply=rootView.findViewById(R.id.aequity_supply);
        a_price_change =rootView.findViewById(R.id.aequity_price_change);
        a_price=rootView.findViewById(R.id.aequity_price);
        saved =rootView.findViewById(R.id.saved);
        a_cap =rootView.findViewById(R.id.aequity_cap);
        a_cap.setText(ap_info.getMarketCap());
        a_price.setText("$ "+ap_info.getCurrent_Aequity_Price());
        a_price_change.setText(ap_pc);


        if (ap_pc !=null && ap_pc.contains("-")){
            a_price_change.setTextColor(Color.parseColor("#ff0000"));
        }else{a_price_change.setTextColor(Color.parseColor("#00ff00"));}
        sup=rootView.findViewById(R.id.supply);
        if(ap_info.getMarketType().equals("Cryptocurrency")){
            sup.setText(getString(R.string.supply));
        }else{
            sup.setText(getString(R.string.shares));}

            a_supply.setText(ap_info.getMarketSupply());



        Constructor_App_Variables app_info =new Constructor_App_Variables();
        a_name.setText(app_info.getMarketName());
        String correct =app_info.getMarketSymbol();
        boolean  b = correct.startsWith("%5E");
        if(b)
        {
            String scorrect=correct.substring(0,3);
            correct =correct.replaceAll(scorrect,"");
            a_symbol.setText(correct);
        }
        else
            {
        a_symbol.setText(correct);
            }
        a_type.setText(app_info.getMarketType());


        save =rootView.findViewById(R.id.save);
        //dont forget shared preferences or check database if aequity is in database show big star on

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save.setBackgroundResource(android.R.drawable.btn_star_big_on);
                db = new Database_Local_Aequities(getActivity().getApplicationContext());
                db.add_equity_info(app_info.getMarketSymbol(), ap_info.getMarketName(), app_info.getMarketType());
                ((Activity_Main)getActivity()).setSavedMarketPage();

            }
        });
        final Integer[] integer = {7};
        historical_listview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        final Adapter_Graph_Points[] ab = {new Adapter_Graph_Points(getContext(), integer[0], graph_high, graph_volume, graph_date)};
        final Adapter_Graph_Points ab_list = new Adapter_Graph_Points(getContext(),integer[0], graph_high, graph_volume, graph_date);
        historical_listview.setAdapter(ab_list);
        //Collections.reverse(graph_high);



        TabLayout tabLayoutchoice = (TabLayout)rootView.findViewById(R.id.tabchoice);
        tabLayoutchoice.addTab(tabLayoutchoice.newTab().setText("LIST"));
        tabLayoutchoice.addTab(tabLayoutchoice.newTab().setText("GRAPH"));
        tabLayoutchoice.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
//SHOW LIST
                        historical_listview.setVisibility(View.VISIBLE);
                        graph_view.setVisibility(View.GONE);
                        break;
                    case 1:
//SHOW GRAPH

                        historical_listview.setVisibility(View.GONE);
                        graph_view.setVisibility(View.VISIBLE);


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
        //This to be added in version 2 with graphs
        TabLayout tabLayout = (TabLayout)rootView.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("1D"));
        if (graph_high.size()>1){
        tabLayout.addTab(tabLayout.newTab().setText("1W"));}
        if (graph_high.size()>7){
        tabLayout.addTab(tabLayout.newTab().setText("1M"));}
        if (graph_high.size()>30){
        tabLayout.addTab(tabLayout.newTab().setText("3M"));}
        if (graph_high.size()>90){
        tabLayout.addTab(tabLayout.newTab().setText("6M"));}
        if (graph_high.size()>180){
        tabLayout.addTab(tabLayout.newTab().setText("1Y"));}
        if (graph_high.size()>365){
        tabLayout.addTab(tabLayout.newTab().setText("MAX"));}
        tabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        graph_view.removeAllSeries();
                        ab[0].notifyDataSetChanged();
                        integer[0] =1;
                        historical_listview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        ab[0] =new Adapter_Graph_Points(getContext(), integer[0],graph_high,graph_volume,graph_date);
                        historical_listview.setAdapter(ab[0]);
                        historical_listview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        ab[0] =new Adapter_Graph_Points(getContext(), integer[0],graph_high,graph_volume,graph_date);
                        historical_listview.setAdapter(ab[0]);
                        if (integer[0] <=graph_high.size())
                    {}
                        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                        for (int i= 0; i < integer[0];i++){
                            try {
                                xx = Double.parseDouble(String.valueOf(graph_high.get(i)));
                                DataPoint point = new DataPoint(i,xx);
                                series.appendData(point,true,integer[0]);
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("Invalid date");
                            }
                            continue;


                        }
                        graph_view.getGridLabelRenderer().setGridColor(Color.TRANSPARENT);
                        graph_view.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
                        graph_view.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
                        series.setColor(Color.RED);
                        graph_view.addSeries(series);
                        break;
                    case 1:
                        //updateReceiptsList();
                        graph_view.removeAllSeries();
                        ab[0].notifyDataSetChanged();
                        integer[0] =7;
                        historical_listview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        ab[0] =new Adapter_Graph_Points(getContext(), integer[0],graph_high,graph_volume,graph_date);
                        historical_listview.setAdapter(ab[0]);
                        //Collections.reverse(graph_high);
                        //System.out.println(Arrays.asList(graph_high));
                        series = new LineGraphSeries<>();
                        for (int i= 0; i < integer[0];i++){
                            try {
                                xx = Double.parseDouble(String.valueOf(graph_high.get(i)));
                                DataPoint point = new DataPoint(i,xx);
                                series.appendData(point,true,integer[0]);
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("Invalid date");
                            }
                            continue;
                        }
                        graph_view.getGridLabelRenderer().setGridColor(Color.TRANSPARENT);
                        graph_view.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
                        graph_view.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
                        series.setColor(Color.GREEN);
                        graph_view.addSeries(series);
                        break;
                    case 2:
                        //updateReceiptsList();
                        graph_view.removeAllSeries();
                        ab[0].notifyDataSetChanged();
                        integer[0] =30;
                        historical_listview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        ab[0] =new Adapter_Graph_Points(getContext(), integer[0],graph_high,graph_volume,graph_date);
                        historical_listview.setAdapter(ab[0]);
                        //Collections.reverse(graph_high);
                        series = new LineGraphSeries<>();
                        for (int i= 0; i < integer[0];i++){
                            try {
                                xx = Double.parseDouble(String.valueOf(graph_high.get(i)));
                                DataPoint point = new DataPoint(i,xx);
                                series.appendData(point,true,integer[0]);
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("Invalid date");
                            }
                            continue;
                        }
                        graph_view.getGridLabelRenderer().setGridColor(Color.TRANSPARENT);
                        graph_view.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
                        graph_view.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
                        series.setColor(Color.GREEN);
                        //series.setDrawDataPoints(true);
                        graph_view.addSeries(series);
                        break;
                    case 3:
                        graph_view.removeAllSeries();
                        ab[0].notifyDataSetChanged();
                        integer[0] =90;
                        //Collections.reverse(graph_high);
                        series = new LineGraphSeries<>();
                        for (int i= 0; i < integer[0];i++){
                            try {
                                xx = Double.parseDouble(String.valueOf(graph_high.get(i)));
                                DataPoint point = new DataPoint(i,xx);
                                series.appendData(point,true,integer[0]);
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("Invalid date");
                            }
                            continue;
                        }
                        graph_view.getGridLabelRenderer().setGridColor(Color.TRANSPARENT);
                        graph_view.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
                        graph_view.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
                        series.setColor(Color.GREEN);
                        //series.setDrawDataPoints(true);
                        graph_view.addSeries(series);
                        historical_listview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        ab[0] =new Adapter_Graph_Points(getContext(), integer[0],graph_high,graph_volume,graph_date);
                        historical_listview.setAdapter(ab[0]);
                        break;
                    case 4:
                        graph_view.removeAllSeries();
                        ab[0].notifyDataSetChanged();
                        //if integer is not null
                        integer[0] =180;

                        //Collections.reverse(graph_high);
                        series = new LineGraphSeries<>();
                        for (int i= 0; i < integer[0];i++){
                            try {
                                xx = Double.parseDouble(String.valueOf(graph_high.get(i)));
                                DataPoint point = new DataPoint(i,xx);
                                series.appendData(point,true,integer[0]);
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("Invalid date");
                            }
                            continue;
                        }
                        graph_view.getGridLabelRenderer().setGridColor(Color.TRANSPARENT);
                        graph_view.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
                        graph_view.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
                        series.setColor(Color.GREEN);
                        //series.setDrawDataPoints(true);
                        graph_view.addSeries(series);
                        historical_listview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        ab[0] =new Adapter_Graph_Points(getContext(), integer[0],graph_high,graph_volume,graph_date);
                        historical_listview.setAdapter(ab[0]);
                        break;
                    case 5:
                        graph_view.removeAllSeries();
                        ab[0].notifyDataSetChanged();
                        integer[0] =365;
                        //Collections.reverse(graph_high);
                        series = new LineGraphSeries<>();
                        for (int i= 0; i <integer[0];i++){
                            try {
                                xx = Double.parseDouble(String.valueOf(graph_high.get(i)));
                                DataPoint point = new DataPoint(i,xx);
                                series.appendData(point,true,integer[0]);
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("Invalid date");
                            }
                            continue;
                        }
                        graph_view.getGridLabelRenderer().setGridColor(Color.TRANSPARENT);
                        graph_view.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
                        graph_view.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
                        graph_view.addSeries(series);
                        series.setColor(Color.GREEN);
                        historical_listview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        ab[0] =new Adapter_Graph_Points(getContext(), integer[0],graph_high,graph_volume,graph_date);
                        historical_listview.setAdapter(ab[0]);

                        break;
                    case 6:
                        graph_view.removeAllSeries();
                        ab[0].notifyDataSetChanged();
                        integer[0] =graph_high.size();
                        //Collections.reverse(graph_high);
                        series = new LineGraphSeries<>();

                        for (int i= 0; i < integer[0];i++){
                            try {
                                xx = Double.parseDouble(String.valueOf(graph_high.get(i)));
                                DataPoint point = new DataPoint(i,xx);
                                series.appendData(point,true,integer[0]);
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("Invalid date");
                            }
                            continue;
                        }
                        graph_view.getGridLabelRenderer().setGridColor(Color.TRANSPARENT);
                        graph_view.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
                        graph_view.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
                        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph_view);
                        //staticLabelsFormatter.setHorizontalLabels(new String[] {"Jan", "Feb", "Mar", "Apr","May", "Jun", "Jul", "Aug","Sept"});
                        graph_view.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
                        graph_view.addSeries(series);
                        series.setColor(Color.GREEN);
                        historical_listview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        ab[0] =new Adapter_Graph_Points(getContext(), integer[0],graph_high,graph_volume,graph_date);
                        historical_listview.setAdapter(ab[0]);
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
        graph_date.clear();
        graph_high.clear();
        graph_volume.clear();
    }


}


