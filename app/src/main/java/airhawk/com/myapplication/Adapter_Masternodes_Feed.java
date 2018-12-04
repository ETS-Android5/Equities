package airhawk.com.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import org.w3c.dom.Text;

import java.util.List;

import static airhawk.com.myapplication.Activity_Main.ap_info;
import static airhawk.com.myapplication.Constructor_App_Variables.*;

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
           changes=itemView.findViewById(R.id.changes);
           name=itemView.findViewById(R.id.name);
           count=itemView.findViewById(R.id.count);
           cost_to_own=itemView.findViewById(R.id.cost_to_own);
           cap=itemView.findViewById(R.id.cap);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ap_info.setMarketSymbol(symbol.getText().toString());
                    ap_info.setMarketName(name.getText().toString());
                    ap_info.setMarketType("Crypto");
                    ap_info.setMarketChange(changes.getText().toString());
                    Activity_Main activity_main = new Activity_Main();
                    activity_main.new setAsyncChosenData((Activity_Main) context)
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
     holder.symbol.setText(""+masternode_symbol.get(position));
     if (masternode_percent_change.get(position).toString().contains("-")){
         holder.changes.setTextColor(Color.parseColor("#ff0000"));
     }else{
         holder.changes.setTextColor(Color.parseColor("#00cc00"));
     }
     holder.changes.setText(""+masternode_percent_change.get(position)+"%");
     holder.count.setText(""+masternode_node_count.get(position));
     holder.cost_to_own.setText("$ "+masternode_purchase_value.get(position));
     holder.cap.setText(""+masternode_marketcap.get(position));
     holder.name.setText(""+masternode_name.get(position));
   }
   
   @Override  
   public int getItemCount() {  
     return masternode_name.size();
   }  
   



   
     }  

