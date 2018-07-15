package airhawk.com.myapplication;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Arrays;

import static airhawk.com.myapplication.Activity_Main.ap_info;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_date;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_high;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_volume;

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
        public MyViewHolder(View itemView) {
        super(itemView);
        symbol = itemView.findViewById(R.id.symbol);
        name = itemView.findViewById(R.id.name);
        currentchange = itemView.findViewById(R.id.current_change);
        type = itemView.findViewById(R.id.type);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ap_info.setMarketName(name.getText().toString());
                    ap_info.setMarketSymbol(symbol.getText().toString());
                    ap_info.setMarketType(type.getText().toString());
                    ((Activity_Main)context).setMarketPage();

                }
            });

        }}

    @Override
    public Adapter_Saved_Feed.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_saved_feed, parent, false);

        return new Adapter_Saved_Feed.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Adapter_Saved_Feed.MyViewHolder holder, int position) {
       Database_Local_Aequities ld =new Database_Local_Aequities(context.getApplicationContext());
        Constructor_App_Variables cav =new Constructor_App_Variables();
        System.out.println("ALL SAVED "+cav.getCpr());
        ld.deleteDuplicates();
       holder.symbol.setText(""+ ld.getSymbol().get(position));
       holder.name.setText(""+ld.getName().get(position));
       holder.currentchange.setText(""+cav.getCpr().get(position));
       if(ld.getType().get(position).equals("Cryptocurrency")){
           holder.type.setText("Crypto");
       }else{
       holder.type.setText(""+ld.getType().get(position));
    }}

    @Override
    public int getItemCount() {
       Database_Local_Aequities ld =new Database_Local_Aequities(context.getApplicationContext());

       return ld.getSymbol().size();
    }









         }

