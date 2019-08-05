package equities.com.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.hardware.camera2.CaptureRequest;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static equities.com.myapplication.Activity_Main.ap_info;
import static equities.com.myapplication.Service_Main_Equities.*;
import static equities.com.myapplication.Service_Main_Equities.stock_winners_changelist;

public class Adapter_Main_Equities extends RecyclerView.Adapter<Adapter_Main_Equities.MyViewHolder> {


    ArrayList stockloserssymbol_list;
        ArrayList stocklosersname_list;
        ArrayList change_list;

    String type_and_case;
        Context context;
Fragment_Winners fragment_winners;
        public Adapter_Main_Equities(Context context, String type_and_case, ArrayList stock_losers_symbollist, ArrayList stock_losers_namelist, ArrayList changelist){
            this.context=context;

            this.stockloserssymbol_list = stock_losers_symbollist;
            this.stocklosersname_list =stock_losers_namelist;
            this.change_list =changelist;
            this.type_and_case =type_and_case;

        }

        public Adapter_Main_Equities(Context context, int id, Fragment_Winners fragment_winners){
            this.fragment_winners = fragment_winners;
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
            //if (lowest_integer[0] > 0|| stock_losers_symbollist.size()>0||crypto_losers_symbollist.size()>0||symbollist.size()>0||symbollist.size()>0){



                if ("Stock_Loser".equals(type_and_case)) {
                    holder.number.setText((1 + i) + ".");
                    holder.symbol.setText("" + stock_losers_symbollist.get(i));
                    holder.name.setText("" + stock_losers_namelist.get(i));
                    holder.circle.setImageResource(R.drawable.holder_red_circle);
                    holder.cPrice.setText("$ " + stock_losers_pricelist.get(i));
                    holder.value.setText("" + stock_losers_changelist.get(i));
                    holder.value.setTextColor(Color.parseColor("#ff0000"));
                    holder.types.setText("Stock");
                }
                if ("Stock_Kings".equals(type_and_case)) {
                    holder.number.setText((1 + i) + ".");
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
                 //   System.out.println(crypto_winners_changelist.get(i)+" "+crypto_winners_namelist.get(i)+" "+typelist.get(i));

                    holder.number.setText((1 + i) + ".");
                    holder.symbol.setText("" + crypto_winners_symbollist.get(i));
                    holder.name.setText("" + crypto_winners_namelist.get(i));
                    holder.cPrice.setText("" + crypto_winners_pricelist.get(i));
                    holder.circle.setImageResource(R.drawable.holder_green_circle);
                    holder.value.setText("+" + crypto_winners_changelist.get(i));
                    holder.value.setTextColor(Color.parseColor("#00cc00"));
                    holder.types.setText("+" + "Cryptocurrency");
                }
            if ("Stock_Winner".equals(type_and_case)) {
//               System.out.println(stock_winners_changelist.get(i)+" "+stock_winners_namelist.get(i)+" "+typelist.get(i)+" "+i);
                holder.number.setText((1 + i) + ".");
                holder.symbol.setText("" + stock_winners_symbollist.get(i));
                holder.name.setText("" + stock_winners_namelist.get(i));
                holder.cPrice.setText("" + stock_winners_pricelist.get(i));
                holder.circle.setImageResource(R.drawable.holder_green_circle);
                holder.value.setText("+" + stock_winners_changelist.get(i));
                holder.value.setTextColor(Color.parseColor("#00cc00"));
                holder.types.setText("+" + "Stock");
            }
                if ("Crypto_Loser".equals(type_and_case)) {
                    holder.number.setText((1 + i) + ".");
                    holder.symbol.setText("" + crypto_losers_symbollist.get(i));
                    holder.name.setText("" + crypto_losers_namelist.get(i));
                    holder.circle.setImageResource(R.drawable.holder_red_circle);
                    holder.value.setText("" + crypto_losers_changelist.get(i));
                    holder.value.setTextColor(Color.parseColor("#ff0000"));
                    holder.types.setText("Cryptocurrency");
                    holder.cPrice.setText("" + crypto_losers_pricelist.get(i));
                }
                if (("Crypto_Kings".equals(type_and_case))) {
                    holder.number.setText((1 + i) + ".");
                    holder.symbol.setText("" + "poop");
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
                    holder.types.setText("Cryptocurrency");
                }
            }
        //}


        @Override
        public int getItemCount() {
            //if (lowest_integer[0]>0){
           // return lowest_integer[0];}else{/
                return 20;
         //   }
        }





    }