package equities.com.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class Adapter_Main_Markets extends RecyclerView.Adapter<Adapter_Main_Markets.MyViewHolder> {

    String[] market_list;
    String[] int_list;
    String[] change_list;
    Constructor_App_Variables ap_info = new Constructor_App_Variables();
    Context context;
    public Adapter_Main_Markets(Context context, String [] ml,String[] il,String [] cl)
    {
        this.market_list = ml;
        this.int_list =il;
        this.change_list =cl;
        this.context = context;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mIdView;
        TextView mPriceView;
        TextView mChangeView;
        ImageView circle;


        public MyViewHolder(View itemView) {
            super(itemView);
            mIdView = itemView.findViewById(R.id.symbol);
            mPriceView = itemView.findViewById(R.id.name);
            mChangeView = itemView.findViewById(R.id.cPrice);
            circle=itemView.findViewById(R.id.circle);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ap_info.getCurrent_Aequity_Price();
                    String temp = mIdView.getText().toString();
                    Activity_Markets_Main activity = new Activity_Markets_Main();
                    switch (getAdapterPosition()) {
                        case 0:
                            temp = "Dow Jones";
                            ap_info.setMarketName(temp);
                            ap_info.setMarketSymbol("%5EDJI");
                            ap_info.setMarketType("Stock");
                            new AsyncChosenData((Activity_Markets_Main) context)
                                    .execute();
                            break;
                        case 1:
                            temp = "S P 500";
                            ap_info.setMarketName(temp);
                            ap_info.setMarketSymbol("%5EGSPC");
                            ap_info.setMarketType("Stock");
                            new AsyncChosenData((Activity_Markets_Main)context)
                                    .execute();
                            break;
                        case 2:
                            temp = "Nasdaq";
                            ap_info.setMarketName(temp);
                            ap_info.setMarketSymbol("%5EIXIC");
                            ap_info.setMarketType("Stock");
                           new AsyncChosenData((Activity_Markets_Main) context)
                                    .execute();
                            break;
                        case 3:
                            temp = "Bitcoin";
                            ap_info.setMarketName(temp);
                            ap_info.setMarketSymbol("BTC");
                            ap_info.setMarketType("Cryptocurrency");
                            new AsyncChosenData((Activity_Markets_Main) context)
                                    .execute();
                            break;
                        case 4:
                            temp = "FTSE 100";
                            ap_info.setMarketName(temp);
                            ap_info.setMarketSymbol("%5EFTSE");
                            ap_info.setMarketType("Index");
                            new AsyncChosenData((Activity_Markets_Main) context)
                                    .execute();
                            break;
                        case 5:
                            temp = "CAC 40";
                            ap_info.setMarketName(temp);
                            ap_info.setMarketSymbol("%5EFCHI");
                            ap_info.setMarketType("Index");
                            new AsyncChosenData((Activity_Markets_Main) context)
                                    .execute();
                            break;

                        case 6:
                            temp = "DAX";
                            ap_info.setMarketName(temp);
                            ap_info.setMarketSymbol("%5EGDAXI");
                            ap_info.setMarketType("Index");
                            new AsyncChosenData((Activity_Markets_Main) context)
                                    .execute();
                            break;
                        case 7:
                            temp = "SHANGHAI SE";
                            ap_info.setMarketName(temp);
                            ap_info.setMarketSymbol("000001.ss");
                            ap_info.setMarketType("Index");
                            new AsyncChosenData((Activity_Markets_Main) context)
                                    .execute();
                            break;
                        case 8:
                            temp = "HANG SENG";
                            ap_info.setMarketName(temp);
                            ap_info.setMarketSymbol("%5EHSI");
                            ap_info.setMarketType("Index");
                            new AsyncChosenData((Activity_Markets_Main) context)
                                    .execute();
                            break;
                        case 9:
                            temp = "NIKKEI 225";
                            ap_info.setMarketName(temp);
                            ap_info.setMarketSymbol("%5EN225");
                            ap_info.setMarketType("Index");
                            new AsyncChosenData((Activity_Markets_Main) context)
                                    .execute();
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_world_view, parent, false);



        return new Adapter_Main_Markets.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Adapter_Main_Markets.MyViewHolder holder, int position) {

        holder.mIdView.setText("" + market_list[position]);
        holder.mPriceView.setText("$ " + int_list[position]);
        holder.mChangeView.setText(change_list[position]);
        if (change_list[position] !=null && change_list[position].contains("-")){
            holder.circle.setImageResource(R.drawable.holder_red_circle);
            holder.mChangeView.setTextColor(Color.parseColor("#FF0000"));
        } else {
            holder.circle.setImageResource(R.drawable.holder_green_circle);
            holder.mChangeView.setTextColor(Color.parseColor("#00cc00"));

        }


    }
    @Override
    public int getItemCount() {
        return market_list.length;
    }





}