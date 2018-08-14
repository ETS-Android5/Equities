package airhawk.com.myapplication;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import static airhawk.com.myapplication.Constructor_App_Variables.btc_market_cap_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.btc_market_cap_change;
import static airhawk.com.myapplication.Constructor_App_Variables.dow_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.dow_change;
import static airhawk.com.myapplication.Constructor_App_Variables.dow_name;
import static airhawk.com.myapplication.Constructor_App_Variables.nas_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.nas_change;
import static airhawk.com.myapplication.Constructor_App_Variables.nas_name;
import static airhawk.com.myapplication.Constructor_App_Variables.sp_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.sp_change;
import static airhawk.com.myapplication.Constructor_App_Variables.sp_name;


public class Adapter_Main_Markets extends RecyclerView.Adapter<Adapter_Main_Markets.MyViewHolder> {

    String[] market_list = new String[]{dow_name, sp_name, nas_name, "Bitcoin"};
    String[] int_list = new String[]{dow_amount,sp_amount,nas_amount,btc_market_cap_amount};
    String[] change_list = new String[]{dow_change,sp_change, nas_change, btc_market_cap_change};
    Constructor_App_Variables ap_info = new Constructor_App_Variables();


    public Adapter_Main_Markets(){}
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mIdView;
        TextView mPriceView;
        TextView mChangeView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mIdView = itemView.findViewById(R.id.id_text);
            mPriceView = itemView.findViewById(R.id.price);
            mChangeView = itemView.findViewById(R.id.change);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String temp = mIdView.getText().toString();
                    Activity_Main connect =new Activity_Main();
                    switch (getAdapterPosition()) {
                        case 0:
                            temp = "Dow Jones";
                            ap_info.setMarketName(mIdView.getText().toString());
                            ap_info.setMarketSymbol("%5EDJI");
                            ap_info.setMarketType("Stock");
                            connect.Launch_Chosen_Progress();
                            break;
                        case 1:
                            temp = "S P 500";
                            ap_info.setMarketName(temp);
                            ap_info.setMarketSymbol("%5EGSPC");
                            ap_info.setMarketType("Stock");
                            connect.Launch_Chosen_Progress();
                            break;
                        case 2:
                            temp = "Nasdaq";
                            ap_info.setMarketName(temp);
                            ap_info.setMarketSymbol("%5EIXIC");
                            ap_info.setMarketType("Stock");
                            connect.Launch_Chosen_Progress();
                            break;
                        case 3:
                            temp = "Bitcoin";
                            ap_info.setMarketName(temp);
                            ap_info.setMarketSymbol("BTC");
                            ap_info.setMarketType("Cryptocurrency");
                            connect.Launch_Chosen_Progress();
                            break;

                        default:
                            break;

                    }

                    }
            });
        }
    }

    @NonNull
    @Override
    public Adapter_Main_Markets.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_item_list_content, parent, false);
        return new Adapter_Main_Markets.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Main_Markets.MyViewHolder holder, int position) {
        holder.mIdView.setText("" + market_list[position]);
        holder.mPriceView.setText("$ " + int_list[position]);
        //if (mValues.get(position).change !=null && mValues.get(position).change.isEmpty()){
       /* if (change_list[position].contains("-")) {
            holder.mIdView.setTextColor(Color.parseColor("#FFFFFF"));
            holder.mPriceView.setTextColor(Color.parseColor("#FFFFFF"));
            holder.mChangeView.setTextColor(Color.parseColor("#FF0000"));
        } else {
        */
            holder.mIdView.setTextColor(Color.parseColor("#FFFFFF"));
            holder.mPriceView.setTextColor(Color.parseColor("#FFFFFF"));
            holder.mChangeView.setTextColor(Color.parseColor("#00CC00"));
            //}
            holder.mChangeView.setText(change_list[position]);
            //}

       // }
    }
    @Override
    public int getItemCount() {
        return 4;
    }
}
