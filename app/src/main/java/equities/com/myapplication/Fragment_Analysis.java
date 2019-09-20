package equities.com.myapplication;


import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.google.android.gms.internal.zzagz.runOnUiThread;
import static equities.com.myapplication.Activity_Markets_Main.ap_info;
import static equities.com.myapplication.Constructor_App_Variables.*;
import static equities.com.myapplication.Constructor_App_Variables.market_type;

/**
 * Created by Julian Dinkins on 4/25/2018.
 */

public class Fragment_Analysis extends Fragment {
    TextView a_price,a_price_change,chosen_price_change;
    ImageView save;
    LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
    int xx= 0;
    Constructor_App_Variables ap_info =new Constructor_App_Variables();
    List<Double> labels = new ArrayList<Double>();
    private Database_Local_Aequities db;
    TabLayout tabchoice;
    GraphView graph_view;
    Adapter_Graph_Points ab;
    Adapter_Graph_Points ab_list;
    List<Double> Points7 = new ArrayList<>();
    List<Double> Points30 = new ArrayList<>();
    List<Double> Points90 = new ArrayList<>();
    List<Double> Points180 = new ArrayList<>();
    List<Double> Points365 = new ArrayList<>();
    List<Double> PointsAll = new ArrayList<>();
    RecyclerView historical_listview;
    List<String> allDates = new ArrayList<>();
    Calendar cal = Calendar.getInstance();
    String maxDate = new SimpleDateFormat("MMM").format(cal.getTime());
    SimpleDateFormat monthDate = new SimpleDateFormat("MMM");
    String maxDayDate = new SimpleDateFormat("EEE").format(cal.getTime());
    SimpleDateFormat dayDate = new SimpleDateFormat("EEE");
    String maxYearDate = new SimpleDateFormat("yyyy").format(cal.getTime());
    SimpleDateFormat yearDate = new SimpleDateFormat("yyyy");
    List<Integer> years= new ArrayList<Integer>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_analysis, container, false);
        //Set text null or delete stock from list if not found online(LIKE IPCI)
        historical_listview =rootView.findViewById(R.id.historical_listview);
        chosen_price_change = rootView.findViewById(R.id.chosenpricechange);
        graph_view=rootView.findViewById(R.id.graph_view);
        a_price_change =rootView.findViewById(R.id.aequity_price_change);
        a_price=rootView.findViewById(R.id.aequity_price);
        graph_view.setVisibility(View.GONE);
        graph_view.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        graph_view.getGridLabelRenderer().setGridColor(getResources().getColor(R.color.colorPrimary));
        if (graph_high.size()>6){
            graph_view.setVisibility(View.VISIBLE);
            Points7.clear();
            Points7.addAll(graph_high.subList(0,7));
            Collections.reverse(Points7);
            getGraphData(Points7,7,7,Calendar.DAY_OF_WEEK,dayDate,maxDayDate);
        }
        historical_listview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //PointsAll.addAll(graph_high);
        //Collections.reverse(PointsAll);
        //getGraphData(PointsAll,0,PointsAll.size(),Calendar.DAY_OF_WEEK,dayDate,maxDayDate);
        ab =new Adapter_Graph_Points(getContext(), graph_high.size(),graph_high,graph_change,graph_volume,graph_date);
        historical_listview.setAdapter(ab);
        historical_listview.findViewHolderForAdapterPosition(0);
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
        TabLayout tabLayout = rootView.findViewById(R.id.chosen_tabs);
        tabLayout.addTab(tabLayout.newTab().setText("7D"));
        if (graph_high.size()>30){
        tabLayout.addTab(tabLayout.newTab().setText("30D"));}
        if (graph_high.size()>90){
        tabLayout.addTab(tabLayout.newTab().setText("3M"));}
        if (graph_high.size()>180){
        tabLayout.addTab(tabLayout.newTab().setText("6M"));}
        if (graph_high.size()>365){
        tabLayout.addTab(tabLayout.newTab().setText("1Y"));}
        //if (graph_high.size()>365){
        //tabLayout.addTab(tabLayout.newTab().setText("MAX"));}
        tabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        Points7.clear();
                        Points7.addAll(graph_high.subList(0,7));
                        Collections.reverse(Points7);
                        getGraphData(Points7,7,7,Calendar.DAY_OF_WEEK,dayDate,maxDayDate);
                        break;
                    case 1:
                        Points30.clear();
                        Points30.addAll(graph_high.subList(0,31));
                        Collections.reverse(Points30);
                        getGraphData(Points30,3,31,Calendar.WEEK_OF_MONTH,dayDate, maxDayDate);
                        break;
                    case 2:
                        Points90.clear();
                        Points90.addAll(graph_high.subList(0,90));
                        Collections.reverse(Points90);
                        getGraphData(Points90,3,90,Calendar.MONTH,monthDate,maxDate);
                        break;
                    case 3:
                        if(market_type.equals("Crypto")|| market_type.equals("Cryptocurrency")){
                            Points180.clear();
                            Points180.addAll(graph_high.subList(0,180));
                            Collections.reverse(Points180);
                            getGraphData(Points180,6,180,Calendar.MONTH,monthDate,maxDate);}else {
                            Points180.clear();
                            Points180.addAll(graph_high);
                            Collections.reverse(Points180);
                            getGraphData(Points180,6,graph_high.size(),Calendar.MONTH,monthDate,maxDate);
                        }
                        break;
                    case 4:
                        Points365.clear();
                        Points365.addAll(graph_high.subList(0,364));
                        Collections.reverse(Points365);
                        getGraphData(Points365,7,365,Calendar.MONTH,monthDate,maxDate);
                        break;
                    case 5:
                        PointsAll.clear();
                        PointsAll.addAll(graph_high);
                        Set<String> set =new HashSet<>(graph_date);
                        graph_date.clear();
                        graph_date.addAll(set);
                        for (int c=0;c<graph_date.size();c++) {
                            String s = String.valueOf(graph_date.get(c));
                            String s1 = s.substring(s.length() - 4);
                            int d = Integer.parseInt(s1);
                            years.add(d); }
                        int lowest_year = Collections.min(years);
                        int current_year =Calendar.getInstance().get(Calendar.YEAR);
                        Collections.reverse(PointsAll);

                        getGraphData(PointsAll,current_year-lowest_year,graph_high.size(),Calendar.YEAR,yearDate,maxYearDate);
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


public void getGraphData(List<Double> array, int xDates, int xPoints, int calendar, SimpleDateFormat date, String maximumDate){
    graph_view.setVisibility(View.VISIBLE);
    StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph_view);
    allDates.clear();
    cal.clear();
    graph_view.removeAllSeries();
    //labels.clear();

    Comparable mi = Collections.min(array);
    double min = Double.parseDouble(String.valueOf(mi).replace(",",""));
    Comparable ma = Collections.max(array);
    double max = Double.parseDouble(String.valueOf(ma).replace(",",""));
    double avg = (min+max)/2;
    double bottom = min*.9;
    double top = max *1.1;
    DecimalFormat df = new DecimalFormat("##.##");
    if (bottom>1.00) {
        labels.add(Double.valueOf(df.format(bottom)));
        labels.add(Double.valueOf(df.format(min)));
        labels.add(Double.valueOf(df.format(avg)));
        labels.add(Double.valueOf(df.format(max)));
        labels.add(Double.valueOf(df.format(top)));
    }
    if (bottom<1.00){
        labels.add(bottom);
        labels.add(min);
        labels.add(avg);
        labels.add(max);
        labels.add(top);
    }
    //ab.notifyDataSetChanged();
    try {
        cal.setTime(date.parse(maximumDate));
    } catch (ParseException e) {
        e.printStackTrace();
    }

    if (calendar == 0){
    for (int i=0;i<=3;i++){
        allDates.add(i+ " week");
    }
    }
    else{
        System.out.println("ARRAY SIZE AND DATES SIZE "+array.size()+" "+xDates);
    for (int i = 0; i < array.size(); i++) {
        String month_name1 = date.format(cal.getTime());
        allDates.add(month_name1);
        cal.add(calendar, -1);
    }}
    Collections.reverse(allDates);
    //Collections.reverse(array);
    series = new LineGraphSeries<>();
    List<Double> lastArray =new ArrayList<>();
    DataPoint point = null;
    DataPoint volume_point = null;
    for (int i= 0; i < array.size();i++){
        try {
            point = new DataPoint(i,Double.parseDouble(String.valueOf(array.get(i))));
            volume_point = new DataPoint(i,Double.parseDouble(String.valueOf(array.get(i))));
            series.appendData(point,true,array.size());
            //System.out.println("THIS IS THE POINT "+point);
        } catch (IndexOutOfBoundsException e) {
            //("Invalid date");
        }
        continue;
    }
    //Collections.reverse(Arrays.asList(series));
    //for (int i= 0; i < graph_high.subList(0,(xPoints)).size();i++) {
    //    point = new DataPoint(i,lastArray.get(i));
    //    series.appendData(point,true,xPoints);
    //}
    graph_view.addSeries(series);
    graph_view.getGridLabelRenderer().setHorizontalLabelsColor(Color.TRANSPARENT);
    graph_view.getGridLabelRenderer().setHorizontalLabelsVisible(false);
    graph_view.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
    //graph_view.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
    Double graph_end = Double.parseDouble(String.valueOf(array.get(array.size() - 1)));
    Double graph_start = Double.parseDouble(String.valueOf(array.get(0)));
    Double result = ((graph_end-graph_start)/graph_start)*100;
    if(graph_end>graph_start){
    series.setColor(Color.GREEN);
        chosen_price_change.setTextColor(getResources().getColor(R.color.colorGreen));
    }
    else{series.setColor(Color.RED);
        chosen_price_change.setTextColor(getResources().getColor(R.color.colorRed));}
    chosen_price_change.setText(((df.format(result))+" %"));
    staticLabelsFormatter.setHorizontalLabels(allDates.toArray(new String[0]));
    Collections.sort(labels);
    //Collections.reverse(labels);
    List<String> finalList = new ArrayList<>();
    for(int s =0;s<labels.size();s++){
        finalList.add(String.valueOf(labels.get(s)));
    }
    NumberFormat nf = NumberFormat.getInstance();
    nf.setMinimumFractionDigits(1);
    nf.setMinimumIntegerDigits(3);
    graph_view.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(nf, nf));
    //staticLabelsFormatter.setVerticalLabels(finalList.toArray(new String[0]));
    graph_view.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
    //graph_view.getGridLabelRenderer().setHumanRounding(false);
    historical_listview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    ab =new Adapter_Graph_Points(getContext(), xPoints,graph_high,graph_change,graph_volume,graph_date);
    historical_listview.setAdapter(ab);
    historical_listview.findViewHolderForAdapterPosition(0);

}






}


