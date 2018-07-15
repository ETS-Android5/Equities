package airhawk.com.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static airhawk.com.myapplication.Activity_Main.ap_info;
import static airhawk.com.myapplication.Service_Main_Aequities.crypto_kings_marketcaplist;
import static airhawk.com.myapplication.Service_Main_Aequities.crypto_kings_namelist;
import static airhawk.com.myapplication.Service_Main_Aequities.crypto_kings_symbolist;
import static airhawk.com.myapplication.Service_Main_Aequities.crypto_losers_changelist;
import static airhawk.com.myapplication.Service_Main_Aequities.crypto_losers_namelist;
import static airhawk.com.myapplication.Service_Main_Aequities.crypto_losers_symbollist;
import static airhawk.com.myapplication.Service_Main_Aequities.crypto_winners_changelist;
import static airhawk.com.myapplication.Service_Main_Aequities.crypto_winners_namelist;
import static airhawk.com.myapplication.Service_Main_Aequities.crypto_winners_symbollist;
import static airhawk.com.myapplication.Service_Main_Aequities.stock_kings_changelist;
import static airhawk.com.myapplication.Service_Main_Aequities.stock_kings_namelist;
import static airhawk.com.myapplication.Service_Main_Aequities.stock_kings_symbollist;
import static airhawk.com.myapplication.Service_Main_Aequities.stock_losers_changelist;
import static airhawk.com.myapplication.Service_Main_Aequities.stock_losers_namelist;
import static airhawk.com.myapplication.Service_Main_Aequities.stock_losers_symbollist;
import static airhawk.com.myapplication.Service_Main_Aequities.stock_win_changelist;
import static airhawk.com.myapplication.Service_Main_Aequities.stock_win_namelist;
import static airhawk.com.myapplication.Service_Main_Aequities.stock_win_symbollist;

public class Adapter_Main_Equities extends RecyclerView.Adapter<Adapter_Main_Equities.MyViewHolder> {

        ArrayList stock_losers_symbol_list;
        ArrayList stock_losers_name_list;
        ArrayList stock_losers_change_list;
        String type_and_case;
        Context context;

        public Adapter_Main_Equities(Context context, String type_and_case, ArrayList stock_losers_symbollist, ArrayList stock_losers_namelist, ArrayList stock_losers_changelist){
            this.context=context;
            this.stock_losers_symbol_list = stock_losers_symbollist;
            this.stock_losers_name_list =stock_losers_namelist;
            this.stock_losers_change_list =stock_losers_changelist;
            this.type_and_case =type_and_case;

        }
        public class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView number, symbol, name, value, types;
            public ImageView circle;

            public MyViewHolder(View itemView) {
                super(itemView);
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
                        ((Activity_Main) context).Launch_Progress();}
                });
            }
        }

        @Override
        public Adapter_Main_Equities.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.aequity_view, parent, false);
            return new Adapter_Main_Equities.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(Adapter_Main_Equities.MyViewHolder holder, int position) {

            if ("Stock_Winner".equals(type_and_case)){
                holder.number.setText((1+position)+".");
                holder.symbol.setText(""+stock_win_symbollist.get(position));
                holder.circle.setImageResource(R.drawable.green_circle);
                holder.name.setText(""+stock_win_namelist.get(position));
                holder.value.setText(""+stock_win_changelist.get(position));
                holder.value.setTextColor(Color.parseColor("#00cc00"));
                holder.types.setText("Stock");
            }
            if ("Stock_Loser".equals(type_and_case)){
                holder.number.setText((1+position)+".");
                holder.symbol.setText(""+stock_losers_symbollist.get(position));
                holder.name.setText(""+stock_losers_namelist.get(position));
                holder.circle.setImageResource(R.drawable.red_circle);
                holder.value.setText(""+stock_losers_changelist.get(position));
                holder.value.setTextColor(Color.parseColor("#ff0000"));
                holder.types.setText("Stock");
            }
            if ("Stock_Kings".equals(type_and_case)){
                holder.number.setText((1+position)+".");
                holder.symbol.setText(""+stock_kings_symbollist.get(position));
                holder.name.setText(""+stock_kings_namelist.get(position));
                holder.value.setText(""+stock_kings_changelist.get(position));
                holder.value.setTextColor(Color.parseColor("#00cc00"));
                holder.types.setText("Stock");
            }

            if ("Crypto_Winner".equals(type_and_case)){
                holder.number.setText((1+position)+".");
                holder.symbol.setText(""+crypto_winners_symbollist.get(position));
                holder.name.setText(""+crypto_winners_namelist.get(position));
                holder.circle.setImageResource(R.drawable.green_circle);
                holder.value.setText("+"+crypto_winners_changelist.get(position));
                holder.value.setTextColor(Color.parseColor("#00cc00"));
                holder.types.setText("Cryptocurrency");}
            if ("Crypto_Loser".equals(type_and_case)){
                holder.number.setText((1+position)+".");
                holder.symbol.setText(""+crypto_losers_symbollist.get(position));
                holder.name.setText(""+crypto_losers_namelist.get(position));
                holder.circle.setImageResource(R.drawable.red_circle);
                holder.value.setText(""+crypto_losers_changelist.get(position));
                holder.value.setTextColor(Color.parseColor("#ff0000"));
                holder.types.setText("Cryptocurrency");}
            if (("Crypto_Kings".equals(type_and_case))){
                holder.number.setText((1+position)+".");
                holder.symbol.setText(""+crypto_kings_symbolist.get(position));
                holder.name.setText(""+crypto_kings_namelist.get(position));
                holder.value.setText(""+crypto_kings_marketcaplist.get(position));
                holder.value.setTextColor(Color.parseColor("#00cc00"));
                holder.types.setText("Cryptocurrency");}


        }

        @Override
        public int getItemCount() {

            return 10 ;
        }




    }