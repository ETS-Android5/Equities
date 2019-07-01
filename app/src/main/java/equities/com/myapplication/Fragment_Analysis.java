package equities.com.myapplication;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
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

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static equities.com.myapplication.Constructor_App_Variables._AllDays;
import static equities.com.myapplication.Constructor_App_Variables.graph_change;
import static equities.com.myapplication.Constructor_App_Variables.graph_date;
import static equities.com.myapplication.Constructor_App_Variables.graph_high;
import static equities.com.myapplication.Constructor_App_Variables.graph_volume;

/**
 * Created by Julian Dinkins on 4/25/2018.
 */

public class Fragment_Analysis extends Fragment {
    TextView a_price,a_price_change,a_name,a_symbol,a_type,a_supply,a_cap,sup,saved,savedd,analysis;
    ImageView save;
    LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
    int xx= 0;
    Constructor_App_Variables ap_info =new Constructor_App_Variables();
    private Database_Local_Aequities db;
    TabLayout tabchoice;
    GraphView graph_view;
    Adapter_Graph_Points ab;
    Adapter_Graph_Points ab_list;


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_analysis, container, false);
        RecyclerView historical_listview =rootView.findViewById(R.id.historical_listview);
        graph_view=rootView.findViewById(R.id.graph_view);
        String ap_pc = ap_info.getCurrent_Aequity_Price_Change();
        savedd =rootView.findViewById(R.id.savedd);
        a_name =rootView.findViewById(R.id.aequity_name);
        a_type =rootView.findViewById(R.id.aequity_type);
        a_symbol=rootView.findViewById(R.id.aequity_symbol);
        a_supply=rootView.findViewById(R.id.aequity_supply);
        a_price_change =rootView.findViewById(R.id.aequity_price_change);
        a_price=rootView.findViewById(R.id.aequity_price);
        saved =rootView.findViewById(R.id.saved);
        a_cap =rootView.findViewById(R.id.aequity_cap);
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Oregon.ttf");
        analysis =rootView.findViewById(R.id.analysis);
        analysis.setTypeface(custom_font);

        analysis.setPaintFlags(analysis.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        a_cap.setText(ap_info.getMarketCap());
        try {
            if (ap_info.getCurrent_Aequity_Price()!=null) {
                a_price.setText("$ " + ap_info.getCurrent_Aequity_Price());
            } else {
                a_price.setText("No Data");

            }
        }catch(IndexOutOfBoundsException e){

            ////("Had to start over ");
        }
        String test=a_price.getText().toString();

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
        save =rootView.findViewById(R.id.save);
        a_type.setText(app_info.getMarketType());
        db = new Database_Local_Aequities(getActivity().getApplicationContext());
        if(db.getSymbol().contains(a_symbol.getText().toString())){
            save.setImageResource(android.R.drawable.btn_star_big_on);
        }

        //dont forget shared preferences or check database if aequity is in database show big star on

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save.setImageResource(android.R.drawable.btn_star_big_on);
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle(app_info.getMarketName()+" is saved")
                        .setIcon(R.drawable.banner_ae)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {

                                        String mS=a_symbol.getText().toString();
                                        String mN=a_name.getText().toString();
                                        String mT=a_type.getText().toString();
                                        System.out.println("THIS IS THE SAVED DATA "+mS+" "+mN+" "+mT);
                                        db.add_equity_info(mS,mN,mT);
                                          db.close();
                                    }
                                }, 2000);
                            }
                        }).show();


            }
        });
        final Integer[] integer = {7};
        historical_listview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        final Adapter_Graph_Points[] ab = {new Adapter_Graph_Points(getContext(), integer[0], graph_high,graph_change, graph_volume, graph_date)};
        ab_list = new Adapter_Graph_Points(getContext(),integer[0], graph_high,graph_change, graph_volume, graph_date);
        historical_listview.setAdapter(ab_list);

        graph_view.removeAllSeries();
        ab_list.notifyDataSetChanged();
        integer[0] =7;
        historical_listview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        ab_list =new Adapter_Graph_Points(getContext(), integer[0],graph_high,graph_change,graph_volume,graph_date);
        historical_listview.setAdapter(ab_list);
        series = new LineGraphSeries<>();
        //Collections.reverse(graph_high);
        Calendar calendar = Calendar.getInstance();

        for (int i= 0; i < 7;i++){
            series.resetData(new DataPoint[] {});
            try {
                String s= String.valueOf(graph_date.get(i).toString().replace(",",""));
                Date date1= null;
                try {

                    date1 = new SimpleDateFormat("MMM DD, yyyy").parse(s);
                    xx = (int) Double.parseDouble(graph_high.get(i).toString());
                    DataPoint point = new DataPoint(date1,xx);

                    series.appendData(new DataPoint(date1, xx), true, 7);
                    System.out.println(point);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            } catch (IndexOutOfBoundsException e) {
                //("Invalid date");
            }
            continue;
        }

        graph_view.getGridLabelRenderer().setGridColor(Color.TRANSPARENT);
        graph_view.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        graph_view.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);

        //graph_view.getViewport().setMinX(0);
        //graph_view.getViewport().setMaxX((graph_date.get(integer[0])));
        //graph_view.getViewport().setMinY(0);
        //graph_view.getViewport().setMaxY((Double.parseDouble((String)graph_high.get(integer[0]))));

        //graph_view.getViewport().setYAxisBoundsManual(true);
        //graph_view.getViewport().setXAxisBoundsManual(true);

        series.setColor(Color.GREEN);
        graph_view.addSeries(series);

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
        TabLayout tabLayout = rootView.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("7D"));
        if (graph_high.size()>7){
        tabLayout.addTab(tabLayout.newTab().setText("30D"));}
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
                List<String> allDates = new ArrayList<>();
                Calendar cal = Calendar.getInstance();
                String maxDate = new SimpleDateFormat("MMM").format(cal.getTime());
                SimpleDateFormat monthDate = new SimpleDateFormat("MMM");
                String maxDayDate = new SimpleDateFormat("EEE").format(cal.getTime());
                SimpleDateFormat dayDate = new SimpleDateFormat("EEE");
                String maxYearDate = new SimpleDateFormat("YYYY").format(cal.getTime());
                SimpleDateFormat yearDate = new SimpleDateFormat("YYYY");
                StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph_view);
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        cal.clear();
                        graph_view.removeAllSeries();
                        ab[0].notifyDataSetChanged();
                        try {
                            cal.setTime(dayDate.parse(maxDayDate));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i <= 6; i++) {
                            String month_name1 = dayDate.format(cal.getTime());
                            allDates.add(month_name1);
                            cal.add(Calendar.DAY_OF_WEEK, -1);
                        }
                        Collections.reverse(allDates);
                        series = new LineGraphSeries<>();
                        for (int i= 0; i < 7;i++){
                            try {
                                String s= String.valueOf(graph_high.get(7-i).toString().replace(",",""));
                                xx = (int) Double.parseDouble(s.trim());
                                DataPoint point = new DataPoint(i,xx);
                                series.appendData(point,true,7);
                            } catch (IndexOutOfBoundsException e) {
                                //("Invalid date");
                            }
                            continue;
                        }
                        graph_view.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
                        graph_view.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
                        series.setColor(Color.GREEN);
                        series.setDrawBackground(true);

                        staticLabelsFormatter.setHorizontalLabels(allDates.toArray(new String[0]));
                        graph_view.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

                        graph_view.addSeries(series);
                        historical_listview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        ab[0] =new Adapter_Graph_Points(getContext(), 7,graph_high,graph_change,graph_volume,graph_date);
                        historical_listview.setAdapter(ab[0]);
                        break;
                    case 1:
                        cal.clear();
                        allDates.clear();
                        graph_view.removeAllSeries();
                        ab[0].notifyDataSetChanged();
                        try {
                            cal.setTime(dayDate.parse(maxDayDate));
                        } catch (ParseException e) {
                            e.printStackTrace(); }
                        for (int i = 0; i <= 3; i++) {
                            //String month_name1 = dayDate.format(cal.getTime());
                            allDates.add(i+" week");
                            //int d = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);
                            //cal.add(Calendar.WEEK_OF_MONTH, -d);
                            }
                        Collections.reverse(allDates);
                        series = new LineGraphSeries<>();
                        for (int i= 0; i < 30;i++){
                            try {
                                String s= String.valueOf(graph_high.get(30-i).toString().replace(",",""));
                                xx = (int) Double.parseDouble(s.trim());
                                DataPoint point = new DataPoint(i,xx);
                                series.appendData(point,true,30);
                            } catch (IndexOutOfBoundsException e) {
                                //("Invalid date");
                            }
                            continue; }
                        graph_view.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
                        graph_view.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
                        series.setColor(Color.GREEN);
                        series.setDrawBackground(true);
                        staticLabelsFormatter.setHorizontalLabels(allDates.toArray(new String[0]));
                        graph_view.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
                        graph_view.addSeries(series);
                        historical_listview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        ab[0] =new Adapter_Graph_Points(getContext(), 30,graph_high,graph_change,graph_volume,graph_date);
                        historical_listview.setAdapter(ab[0]);
                        break;
                    case 2:
                        cal.clear();
                        allDates.clear();
                        graph_view.removeAllSeries();
                        ab[0].notifyDataSetChanged();

                        try {
                            cal.setTime(monthDate.parse(maxDate));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i <= 3; i++) {
                            String month_name1 = monthDate.format(cal.getTime());
                            allDates.add(month_name1);
                            cal.add(Calendar.MONTH, -1);
                        }
                        Collections.reverse(allDates);
                        series = new LineGraphSeries<>();
                        for (int i= 0; i < 90;i++){
                            try {
                                String s= String.valueOf(graph_high.get(90-i).toString().replace(",",""));
                                xx = (int) Double.parseDouble(s.trim());
                                DataPoint point = new DataPoint(i,xx);
                                series.appendData(point,true,90);
                            } catch (IndexOutOfBoundsException e) {
                                //("Invalid date");
                            }
                            continue;
                        }
                        graph_view.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
                        graph_view.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
                        series.setColor(Color.GREEN);
                        series.setDrawBackground(true);

                        staticLabelsFormatter.setHorizontalLabels(allDates.toArray(new String[0]));
                        graph_view.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

                        graph_view.addSeries(series);
                        historical_listview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        ab[0] =new Adapter_Graph_Points(getContext(), 90,graph_high,graph_change,graph_volume,graph_date);
                        historical_listview.setAdapter(ab[0]);
                        break;
                    case 3:
                        cal.clear();
                        graph_view.removeAllSeries();
                        ab[0].notifyDataSetChanged();
                        try {
                            cal.setTime(monthDate.parse(maxDate));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i <= 6; i++) {
                            String month_name1 = monthDate.format(cal.getTime());
                            allDates.add(month_name1);
                            cal.add(Calendar.MONTH, -1);
                        }
                        Collections.reverse(allDates);
                        series = new LineGraphSeries<>();
                        for (int i= 0; i < 180;i++){
                            try {
                                String s= String.valueOf(graph_high.get(180-i).toString().replace(",",""));
                                xx = (int) Double.parseDouble(s.trim());
                                DataPoint point = new DataPoint(i,xx);
                                series.appendData(point,true,180);
                            } catch (IndexOutOfBoundsException e) {
                                //("Invalid date");
                            }
                            continue;
                        }
                        graph_view.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
                        graph_view.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
                        series.setColor(Color.GREEN);
                        series.setDrawBackground(true);

                        staticLabelsFormatter.setHorizontalLabels(allDates.toArray(new String[0]));
                        graph_view.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

                        graph_view.addSeries(series);
                        historical_listview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        ab[0] =new Adapter_Graph_Points(getContext(), 180,graph_high,graph_change,graph_volume,graph_date);
                        historical_listview.setAdapter(ab[0]);
                        break;
                    case 4:
                        cal.clear();
                        graph_view.removeAllSeries();
                        ab[0].notifyDataSetChanged();
                        try {
                            cal.setTime(monthDate.parse(maxDate));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i <= 11; i++) {
                            String month_name1 = monthDate.format(cal.getTime());
                            allDates.add(month_name1);
                            cal.add(Calendar.MONTH, -1);
                        }
                        Collections.reverse(allDates);
                        series = new LineGraphSeries<>();
                        for (int i= 0; i < 365;i++){
                            try {
                                String s= String.valueOf(graph_high.get(365-i).toString().replace(",",""));
                                xx = (int) Double.parseDouble(s.trim());
                                DataPoint point = new DataPoint(i,xx);
                                series.appendData(point,true,365);
                            } catch (IndexOutOfBoundsException e) {
                                //("Invalid date");
                            }
                            continue;
                        }
                        graph_view.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
                        graph_view.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
                        series.setColor(Color.GREEN);
                        series.setDrawBackground(true);

                        staticLabelsFormatter.setHorizontalLabels(allDates.toArray(new String[0]));
                        graph_view.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

                        graph_view.addSeries(series);
                        historical_listview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        ab[0] =new Adapter_Graph_Points(getContext(), 365,graph_high,graph_change,graph_volume,graph_date);
                        historical_listview.setAdapter(ab[0]);
                        break;
                    case 5:
                        cal.clear();
                        graph_view.removeAllSeries();
                        ab[0].notifyDataSetChanged();
                        try {
                            cal.setTime(yearDate.parse(maxYearDate));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i <= 5; i++) {
                            String month_name1 = yearDate.format(cal.getTime());
                            allDates.add(month_name1);
                            cal.add(Calendar.YEAR, -1);
                        }
                        Collections.reverse(allDates);
                        series = new LineGraphSeries<>();
                        for (int i= 0; i < graph_high.size();i++){
                            try {
                                String s= String.valueOf(graph_high.get(graph_high.size()-i).toString().replace(",",""));
                                xx = (int) Double.parseDouble(s.trim());
                                DataPoint point = new DataPoint(i,xx);
                                series.appendData(point,true,graph_high.size());
                            } catch (IndexOutOfBoundsException e) {
                                //("Invalid date");
                            }
                            continue;
                        }
                        graph_view.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
                        graph_view.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
                        series.setColor(Color.GREEN);
                        series.setDrawBackground(true);

                        staticLabelsFormatter.setHorizontalLabels(allDates.toArray(new String[0]));
                        graph_view.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

                        graph_view.addSeries(series);
                        historical_listview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        ab[0] =new Adapter_Graph_Points(getContext(), graph_high.size(),graph_high,graph_change,graph_volume,graph_date);
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


