package equities.com.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static equities.com.myapplication.Activity_Markets_Main.ap_info;

public class Adapter_Crypto_Equities extends RecyclerView.Adapter<Adapter_Crypto_Equities.MyViewHolder> {

        private List<Constructor_Crypto_Equities> crypto_loser_equities ;
        private List<Constructor_Crypto_Equities> crypto_winner_equities ;
        private List<Constructor_Crypto_Equities> crypto_king_equities ;

    String type_and_case;
        Context context;
        public Adapter_Crypto_Equities(Context context, String type_and_case, ArrayList<Constructor_Crypto_Equities> losers_items, ArrayList<Constructor_Crypto_Equities> winners_items, ArrayList<Constructor_Crypto_Equities> kings_items){
            this.context=context;
            this.crypto_loser_equities = losers_items;
            this.crypto_winner_equities = winners_items;
            this.crypto_king_equities = kings_items;
            this.type_and_case =type_and_case;
        }





    public class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView number, symbol,name, value, types, cPrice;
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
                        Activity_Markets_Main activity_Markets_main = new Activity_Markets_Main();
                        new AsyncOnClickEquity((Activity_Markets_Main) context)
                                .execute();}
                });
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_aequity_view, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int i) {
//            Constructor_Crypto_Equities loser_crypto_equities = crypto_loser_equities.get(i);
//            Constructor_Crypto_Equities winner_crypto_equities = crypto_winner_equities.get(i);
//            Constructor_Crypto_Equities king_crypto_equities =crypto_king_equities.get(i);

            try {
/*
                if ("Crypto_Winner".equals(type_and_case)) {
                    holder.number.setText((1 + i) + ".");
                    holder.symbol.setText(winner_crypto_equities.getCrypto_winner_symbol());
                    holder.name.setText(winner_crypto_equities.getCrypto_kings_name());
                    holder.cPrice.setText(winner_crypto_equities.getCrytpo_winner_price());
                    holder.circle.setImageResource(R.drawable.holder_green_circle);
                    holder.value.setText("+" + winner_crypto_equities.getCrytpo_winner_percent_change());
                    holder.value.setTextColor(Color.parseColor("#00cc00"));
                    holder.types.setText("+" + "Cryptocurrency");
                }

                if ("Crypto_Loser".equals(type_and_case)) {
                    holder.number.setText((1 + i) + ".");
                    holder.symbol.setText(loser_crypto_equities.getCrypto_losers_symbol());
                    holder.name.setText(loser_crypto_equities.getCrypto_losers_name());
                    holder.circle.setImageResource(R.drawable.holder_red_circle);
                    holder.value.setText(loser_crypto_equities.getCrypto_losers_percent_change());
                    holder.value.setTextColor(Color.parseColor("#ff0000"));
                    holder.types.setText("Cryptocurrency");
                    holder.cPrice.setText(loser_crypto_equities.getCrypto_losers_price());
                }

                if (("Crypto_Kings".equals(type_and_case))) {
                    holder.number.setText((1 + i) + ".");
                    holder.symbol.setText(king_crypto_equities.getCrypto_kings_symbol());
                    holder.name.setText(king_crypto_equities.getCrypto_kings_name());
                    holder.cPrice.setText("$" + king_crypto_equities.getCrytpo_kings_price());
                    if (king_crypto_equities.getCrytpo_kings_percent_change().contains("-")) {
                        holder.circle.setImageResource(R.drawable.holder_red_circle);
                        holder.value.setTextColor(Color.parseColor("#ff0000"));
                    } else {
                        holder.value.setTextColor(Color.parseColor("#00cc00"));
                        holder.circle.setImageResource(R.drawable.holder_green_circle);
                    }
                    holder.value.setText("" + king_crypto_equities.getCrytpo_kings_price());
                    holder.types.setText("Cryptocurrency");
                }
*/
            } catch (Exception e) {
                holder.name.setText("" + "Updating");
                holder.value.setText("" + "Updating");
                holder.cPrice.setText("" + "Updating");
            }
        }



        @Override
        public int getItemCount() {

                return 20;


        }





    }