package equities.com.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import static equities.com.myapplication.Activity_Main.ap_info;
import static equities.com.myapplication.Service_Main_Equities.*;

public class Adapter_Main_Equities extends RecyclerView.Adapter<Adapter_Main_Equities.MyViewHolder> {

        ArrayList stockloserssymbol_list;
        ArrayList stocklosersname_list;
        ArrayList stockloserschange_list;
        String type_and_case;
        Context context;
    private WeakReference<Activity_Main> activityReference;
    Adapter_Main_Equities(Activity_Main context) {
        activityReference = new WeakReference<>(context);
    }
        public Adapter_Main_Equities(Context context, String type_and_case, ArrayList stock_losers_symbollist, ArrayList stock_losers_namelist, ArrayList stock_losers_changelist){
            this.context=context;

            this.stockloserssymbol_list = stock_losers_symbollist;
            this.stocklosersname_list =stock_losers_namelist;
            this.stockloserschange_list =stock_losers_changelist;
            this.type_and_case =type_and_case;

        }
        public class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView number, symbol, name, value, types, cPrice;
            public ImageView circle;

            public MyViewHolder(View itemView) {
                super(itemView);
                cPrice =itemView.findViewById(R.id.cPrice);
                number=itemView.findViewById(R.id.number);
                circle =itemView.findViewById(R.id.circle);
                symbol=itemView.findViewById(R.id.symbol);
                name=itemView.findViewById(R.id.name);
                value=itemView.findViewById(R.id.value);
                types=itemView.findViewById(R.id.types);



                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ap_info.setMarketSymbol(symbol.getText().toString());
                        ap_info.setMarketName(name.getText().toString());
                        ap_info.setMarketType(types.getText().toString());
                        ap_info.setMarketChange(value.getText().toString());
                        Activity_Main activity_main = new Activity_Main();
                        new AsyncChosenData((Activity_Main) context)
                                .execute();}
                });
            }
        }

        @Override
        public Adapter_Main_Equities.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_aequity_view, parent, false);
            return new Adapter_Main_Equities.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(Adapter_Main_Equities.MyViewHolder holder, int i) {
            //.i=lowest_integer[0];
            if (lowest_integer[0] > 0) {

                if ("Stock_Winner".equals(type_and_case)) {

                    holder.symbol.setText("" + stock_win_symbollist.get(i));
                    holder.circle.setImageResource(R.drawable.holder_green_circle);
                    holder.name.setText("" + stock_win_namelist.get(i));
                    holder.value.setText("" + stock_win_changelist.get(i));
                    holder.value.setTextColor(Color.parseColor("#00cc00"));
                    holder.cPrice.setText("$ " + stock_win_pricelist.get(i));
                    holder.types.setText("Stock");
                }


                if ("Stock_Loser".equals(type_and_case)) {
                    holder.symbol.setText("" + stock_losers_symbollist.get(i));
                    holder.name.setText("" + stock_losers_namelist.get(i));
                    holder.circle.setImageResource(R.drawable.holder_red_circle);
                    holder.cPrice.setText("$ " + stock_losers_pricelist.get(i));
                    holder.value.setText("" + stock_losers_changelist.get(i));
                    holder.value.setTextColor(Color.parseColor("#ff0000"));
                    holder.types.setText("Stock");
                }
                if ("Stock_Kings".equals(type_and_case)) {
                    holder.symbol.setText("" + stock_kings_symbollist.get(i));
                    holder.name.setText("" + stock_kings_namelist.get(i));
                    holder.value.setText("" + stock_kings_ipdown.get(i));
                    String poo = String.valueOf(stock_kings_ipdown.get(i));
                    if (poo.contains("-")) {
                        holder.circle.setImageResource(R.drawable.holder_red_circle);
                        holder.value.setTextColor(Color.parseColor("#ff0000"));
                    } else {
                        holder.value.setTextColor(Color.parseColor("#00cc00"));
                        holder.circle.setImageResource(R.drawable.holder_green_circle);
                    }
                    holder.types.setText("Stock");
                    holder.cPrice.setText("" + stock_kings_changelist.get(i));
                }

                if ("Crypto_Winner".equals(type_and_case)) {
                    holder.symbol.setText("" + crypto_winners_symbollist.get(i));
                    holder.name.setText("" + crypto_winners_namelist.get(i));
                    holder.cPrice.setText("" + crypto_win_pricelist.get(i));
                    holder.circle.setImageResource(R.drawable.holder_green_circle);
                    holder.value.setText("+" + crypto_winners_changelist.get(i));
                    holder.value.setTextColor(Color.parseColor("#00cc00"));
                    holder.types.setText("Cryptocurrency");
                }
                if ("Crypto_Loser".equals(type_and_case)) {
                    holder.symbol.setText("" + crypto_losers_symbollist.get(i));
                    holder.name.setText("" + crypto_losers_namelist.get(i));
                    holder.circle.setImageResource(R.drawable.holder_red_circle);
                    holder.value.setText("" + crypto_losers_changelist.get(i));
                    holder.value.setTextColor(Color.parseColor("#ff0000"));
                    holder.types.setText("Cryptocurrency");
                    holder.cPrice.setText("" + crypto_losers_pricelist.get(i));
                }
                if (("Crypto_Kings".equals(type_and_case))) {
                    holder.symbol.setText("" + crypto_kings_symbolist.get(i));
                    holder.name.setText("" + crypto_kings_namelist.get(i));
                    holder.cPrice.setText("$" + crypto_kings_pricelist.get(i));
                    String poo = String.valueOf(crypto_kings_changelist.get(i));
                    if (poo.contains("-")) {
                        holder.circle.setImageResource(R.drawable.holder_red_circle);
                        holder.value.setTextColor(Color.parseColor("#ff0000"));
                    } else {
                        holder.value.setTextColor(Color.parseColor("#00cc00"));
                        holder.circle.setImageResource(R.drawable.holder_green_circle);
                    }
                    holder.value.setText("" + crypto_kings_changelist.get(i));
                    //holder.cPrice.setText(""+crypto_kings_marketcaplist.get(position));
                    //if (crypto_kings_changelist.get(position)>0){
                    //    holder.circle.setImageResource(R.drawable.holder_green_circle);
                    //holder.value.setTextColor(Color.parseColor("#00ff00"));
                    //}
                    //else{
                    //holder.circle.setImageResource(R.drawable.holder_red_circle);
                    //holder.value.setTextColor(Color.parseColor("#00ff00"));}


                    holder.types.setText("Cryptocurrency");
                }
            }
        }



        @Override
        public int getItemCount() {
            //if (lowest_integer[0]>0){
           // return lowest_integer[0];}else{/
                return 20;
         //   }
        }





    }