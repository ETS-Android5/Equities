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
import static airhawk.com.myapplication.Activity_Main.db_exist;
import static airhawk.com.myapplication.Constructor_App_Variables.current_percentage_change;
import static airhawk.com.myapplication.Constructor_App_Variables.ipo_date;

public class Adapter_Saved_Feed extends RecyclerView.Adapter<Adapter_Saved_Feed.MyViewHolder> {

    String name;
    String symbol;
    String type;
    ArrayList currentchange;
    Context context;


    public Adapter_Saved_Feed(Context context, String symbol, String name, ArrayList currentchange, String type) {
        this.symbol = symbol;
        this.name = name;
        this.currentchange =currentchange;
        this.type = type;
        this.context=context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView symbol, name, currentchange, type;
        public ImageView circle;
        public MyViewHolder(View itemView) {
        super(itemView);
        circle =itemView.findViewById(R.id.circle);
        symbol = itemView.findViewById(R.id.symbol);
        name = itemView.findViewById(R.id.name);
        currentchange = itemView.findViewById(R.id.current_change);

        type = itemView.findViewById(R.id.type);





        }}

    @Override
    public Adapter_Saved_Feed.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_saved_feed, parent, false);

        return new Adapter_Saved_Feed.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Adapter_Saved_Feed.MyViewHolder holder, int position) {
       Database_Local_Aequities ld =new Database_Local_Aequities(context);

        System.out.println("SYMBOL  = "+ld.getSymbol().get(position));
        System.out.println("NAME  = "+ld.getName().get(position));
        System.out.println("CHANGE  = "+current_percentage_change.get(position));

       holder.symbol.setText(""+ ld.getSymbol().get(position));
       holder.name.setText(""+ld.getName().get(position));
       holder.currentchange.setText(""+current_percentage_change.get(position));
        String cc = String.valueOf(current_percentage_change.get(position));
        if(cc.contains("-")){
            cc.replace("-","");
            holder.currentchange.setTextColor(Color.parseColor("#ff0000"));
            holder.circle.setImageResource(R.drawable.holder_red_circle);
        }
        if(cc.contains("+")){
            cc.replace("+","").replace("(","").replace(")","");
            holder.currentchange.setTextColor(Color.parseColor("#00ff00"));
            holder.circle.setImageResource(R.drawable.holder_green_circle);
        }
        String l =ld.getType(holder.name.getText().toString());
if (l.equalsIgnoreCase("Cryptocurrency")){
    holder.type.setText("Crypto");
}else{
           holder.type.setText(l);}
           ld.close();



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ap_info.setMarketName(holder.name.getText().toString());
                ap_info.setMarketSymbol(holder.symbol.getText().toString());
                ap_info.setMarketType(holder.type.getText().toString());
                System.out.println("THIS SI I INFO "+ap_info.getMarketName()+" "+ap_info.getMarketSymbol()+" "+ap_info.getMarketType());
                db_exist=true;
                ((Activity_Main)context).new setAsyncChosenData((Activity_Main)context).execute();


            }
        });}

    @Override
    public int getItemCount() {
        Database_Local_Aequities ld =new Database_Local_Aequities(context);

        return ld.getSymbol().size();
    }









         }

