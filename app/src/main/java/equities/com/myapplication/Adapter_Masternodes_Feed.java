package equities.com.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static equities.com.myapplication.Activity_Markets_Main.ap_info;

public class Adapter_Masternodes_Feed extends RecyclerView.Adapter<Adapter_Masternodes_Feed.MyViewHolder> {

   List<Constructor_Masternodes> masternode_feedItems;
   static Context context;
   public Adapter_Masternodes_Feed(Context context, List<Constructor_Masternodes> items){
     this.context=context;
     this.masternode_feedItems = items;
   }


   public class MyViewHolder extends RecyclerView.ViewHolder{
       TextView symbol;
       TextView changes;
       TextView name;
       TextView count;
       TextView cost_to_own;
       TextView cap;

       public MyViewHolder(View itemView) {
           super(itemView);

           symbol=itemView.findViewById(R.id.symbol);
           changes=itemView.findViewById(R.id.cPrice);
           name=itemView.findViewById(R.id.name);
           count=itemView.findViewById(R.id.count);
           cost_to_own=itemView.findViewById(R.id.cost_to_own);
           cap=itemView.findViewById(R.id.cap);


            symbol.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ap_info.setMarketSymbol(symbol.getText().toString());
                    ap_info.setMarketName(name.getText().toString());
                    ap_info.setMarketType("Crypto");
                    ap_info.setMarketChange(changes.getText().toString());
                    Activity_Markets_Main activity_Markets_main = new Activity_Markets_Main();
                    new AsyncOnClickEquity((Activity_Markets_Main) context)
                            .execute();}
            });
        }
    }
   @Override
   public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     LayoutInflater inflater = LayoutInflater.from(parent.getContext());
     View view = inflater.inflate(R.layout.recyclerview_masternodes, parent, false);


     return new Adapter_Masternodes_Feed.MyViewHolder(view);
   }

   @Override
   public void onBindViewHolder(Adapter_Masternodes_Feed.MyViewHolder holder, int position) {
       try{
     Constructor_Masternodes constructor_masternodes = masternode_feedItems.get(position);
     holder.symbol.setText(constructor_masternodes.getMasternode_symbol());
     if (constructor_masternodes.getMasternode_percent_change().contains("-")){
         holder.changes.setTextColor(context.getResources().getColor(R.color.colorRed));
     }else{
         holder.changes.setTextColor(context.getResources().getColor(R.color.colorGreen));
     }
     holder.changes.setText(constructor_masternodes.getMasternode_percent_change());
     holder.count.setText(constructor_masternodes.getMasternode_node_count());
     holder.cost_to_own.setText(constructor_masternodes.getMasternode_purchase_value());
     holder.cap.setText(""+constructor_masternodes.getMasternode_marketcap());
     holder.name.setText(""+constructor_masternodes.getMasternode_name());
   }catch (Exception e) {
           holder.symbol.setText("" + "Updating");
           holder.changes.setText("" + "Updating");
           holder.count.setText("" + "Updating");
           holder.cost_to_own.setText("" + "Updating");
           holder.cap.setText("" + "Updating");
           holder.name.setText("" + "Updating");


       }}
   
   @Override  
   public int getItemCount() {  
     return masternode_feedItems.size();
   }  
   



   
     }  

