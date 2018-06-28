package airhawk.com.myapplication;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static airhawk.com.myapplication.Activity_Main.get_current_aequity_price;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_date;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_high;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_volume;

/**
 * Created by Julian Dinkins on 4/25/2018.
 */

public class Fragment_Analysis extends Fragment {
    private TabLayout tabLayout;
    private TextView a_price,a_name,a_symbol,a_type,a_supply,a_marketcap,sup;
    private RecyclerView historical_listview;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_analysis, container, false);
        a_name =rootView.findViewById(R.id.aequity_name);
        a_type =rootView.findViewById(R.id.aequity_type);
        a_symbol=rootView.findViewById(R.id.aequity_symbol);
        a_supply=rootView.findViewById(R.id.aequity_supply);
        a_price=rootView.findViewById(R.id.aequity_price);
        a_price.setText("$ "+get_current_aequity_price);
        sup=rootView.findViewById(R.id.supply);
        Constructor_App_Variables app_info =new Constructor_App_Variables();
        a_name.setText(app_info.getMarketName());
        a_symbol.setText(app_info.getMarketSymbol());
        a_type.setText(app_info.getMarketType());
        if(app_info.getMarketType().equals("Cryptocurrency")){
            sup.setText(getString(R.string.supply));
        }else{sup.setText(getString(R.string.shares));}

        historical_listview =rootView.findViewById(R.id.historical_listview);
        historical_listview.setAdapter(new Adapter_Graph_Points_Crypto(getActivity(),graph_high,graph_volume,graph_date));



       // for (int i = 0; i < _AllDays.size(); i++)
        //{
        //    dataModels.add(new Historical_Data_Model(graph_high.get(i), graph_volume.get(i), graph_date.get(i)));
        //    System.out.println(Arrays.asList(dataModels.get(i)));}


        /* This to be added in version 2 with graphs
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
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        updateReceiptsList();
                        for (int i = 0; i < _AllDays.size(); i++) {

                        }

                        break;
                    case 7:
                        for (int i = 0; i < _AllDays.size(); i++) {

                        }
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
*/
        return rootView;

    }
    public void updateReceiptsList() {
        graph_date.clear();
        graph_high.clear();
        graph_volume.clear();
    }
}


