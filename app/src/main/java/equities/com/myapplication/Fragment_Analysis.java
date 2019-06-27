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
import java.util.Calendar;
import java.util.Date;

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
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        graph_view.removeAllSeries();
                        ab_list.notifyDataSetChanged();

                        DataPoint [] dp = new DataPoint[7];
                        for(int z= 0; z<=7;z++){
                            Calendar calendar1 =Calendar.getInstance();
                            DateFormat dateFormat = new SimpleDateFormat("MMM, DD");
                            String d =dateFormat.format(calendar1.getTime());
                            calendar1.add(Calendar.DATE,-z);
                            Date date =calendar1.getTime();
                            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                                    new DataPoint(date, z+7)

                            });
                            graph_view.addSeries(series);

                        }
                        //graph_view.getGridLabelRenderer().setNumVerticalLabels(roundVal); // Approx 28-3
                        graph_view.getGridLabelRenderer().setGridColor(Color.TRANSPARENT);
                        graph_view.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
                        graph_view.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);

                        //graph_view.getViewport().setMinX(1);
                        //graph_view.getViewport().setMaxX(integer[0]);
                        //graph_view.getViewport().setMinY(0);
                        //graph_view.getViewport().setMaxY(Double.parseDouble((String) graph_high.get(integer[0])));

                        //graph_view.getViewport().setYAxisBoundsManual(true);
                        //graph_view.getViewport().setXAxisBoundsManual(true);

                        series.setColor(Color.GREEN);
                        break;
                    case 1:
                        graph_view.removeAllSeries();
                        ab[0].notifyDataSetChanged();
                        integer[0] =30;

                        historical_listview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        ab[0] =new Adapter_Graph_Points(getContext(), integer[0],graph_high,graph_change,graph_volume,graph_date);
                        historical_listview.setAdapter(ab[0]);
                        series = new LineGraphSeries<>();
                        for (int i= 0; i < integer[0];i++){
                            try {
                                String s= String.valueOf(graph_high.get(integer[0]-i).toString().replace(",",""));
                                xx = (int) Double.parseDouble(s.trim());
                                DataPoint point = new DataPoint(i,xx);
                                series.appendData(point,true,integer[0]);
                            } catch (NumberFormatException numberFormatException) {
                                //("Invalid date");
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
                        graph_view.removeAllSeries();
                        ab[0].notifyDataSetChanged();
                        integer[0] =90;

                        series = new LineGraphSeries<>();
                        for (int i= 0; i < integer[0];i++){
                            try {
                                String s= String.valueOf(graph_high.get(integer[0]-i).toString().replace(",",""));
                                xx = (int) Double.parseDouble(s.trim());
                                DataPoint point = new DataPoint(i,xx);
                                series.appendData(point,true,integer[0]);
                            } catch (IndexOutOfBoundsException e) {
                                //("Invalid date");
                            }
                            continue;
                        }
                        graph_view.getGridLabelRenderer().setGridColor(Color.TRANSPARENT);
                        graph_view.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
                        graph_view.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
                        series.setColor(Color.GREEN);
                        graph_view.addSeries(series);
                        historical_listview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        ab[0] =new Adapter_Graph_Points(getContext(), integer[0],graph_high,graph_change,graph_volume,graph_date);
                        historical_listview.setAdapter(ab[0]);
                        break;
                    case 3:
                        graph_view.removeAllSeries();
                        ab[0].notifyDataSetChanged();
                        if(ap_info.getMarketType().equalsIgnoreCase("stock")){
                            integer[0] =98;
                        }else{
                        integer[0] =180;}

                        series = new LineGraphSeries<>();
                        for (int i= 0; i < integer[0];i++){
                            try {
                                String s= String.valueOf(graph_high.get(integer[0]-i).toString().replace(",",""));
                                xx = (int) Double.parseDouble(s.trim());
                                DataPoint point = new DataPoint(i,xx);
                                series.appendData(point,true,integer[0]);
                            } catch (IndexOutOfBoundsException e) {
                                //("Invalid date");
                            }
                            continue;
                        }
                        graph_view.getGridLabelRenderer().setGridColor(Color.TRANSPARENT);
                        graph_view.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
                        graph_view.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
                        series.setColor(Color.GREEN);
                        graph_view.addSeries(series);
                        historical_listview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        ab[0] =new Adapter_Graph_Points(getContext(), integer[0],graph_high,graph_change,graph_volume,graph_date);
                        historical_listview.setAdapter(ab[0]);
                        break;
                    case 4:
                        graph_view.removeAllSeries();
                        ab[0].notifyDataSetChanged();
                        integer[0] =365;

                        series = new LineGraphSeries<>();
                        for (int i= 0; i < integer[0];i++){
                            try {
                                String s= String.valueOf(graph_high.get(integer[0]-i).toString().replace(",",""));
                                xx = (int) Double.parseDouble(s.trim());
                                DataPoint point = new DataPoint(i,xx);
                                series.appendData(point,true,integer[0]);
                            } catch (IndexOutOfBoundsException e) {
                                //("Invalid date");
                            }
                            continue;
                        }
                        graph_view.getGridLabelRenderer().setGridColor(Color.TRANSPARENT);
                        graph_view.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
                        graph_view.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
                        graph_view.addSeries(series);
                        series.setColor(Color.GREEN);
                        historical_listview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        ab[0] =new Adapter_Graph_Points(getContext(), integer[0],graph_high,graph_change,graph_volume,graph_date);
                        historical_listview.setAdapter(ab[0]);

                        break;
                    case 5:
                        graph_view.removeAllSeries();
                        ab[0].notifyDataSetChanged();
                        //(_AllDays.size());
                        integer[0] =_AllDays.size();
                        series = new LineGraphSeries<>();
                        for (int i= 0; i < integer[0];i++){
                            try {
                                String s= String.valueOf(graph_high.get(integer[0]-i).toString().replace(",",""));
                                xx = (int) Double.parseDouble(s.trim());
                                DataPoint point = new DataPoint(i,xx);
                                series.appendData(point,true,integer[0]);
                            } catch (IndexOutOfBoundsException e) {

                            }

                        }
                        //("SIZE of iteration"+integer[0]);
                        //("SIZE OF ALL DAYS "+_AllDays.size());
                        graph_view.getGridLabelRenderer().setGridColor(Color.TRANSPARENT);
                        graph_view.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
                        graph_view.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
                        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph_view);
                        staticLabelsFormatter.setHorizontalLabels(new String[] {"Jan", "Feb", "Mar", "Apr","May", "Jun", "Jul", "Aug","Sept"});
                        graph_view.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
                        graph_view.addSeries(series);
                        series.setColor(Color.GREEN);
                        historical_listview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        ab[0] =new Adapter_Graph_Points(getContext(), integer[0],graph_high,graph_change,graph_volume,graph_date);
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


