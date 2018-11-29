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

import static airhawk.com.myapplication.Constructor_App_Variables.*;

public class Adapter_Masternodes_Feed extends RecyclerView.Adapter<Adapter_Masternodes_Feed.MyViewHolder> {

   private List<Constructor_Masternodes> masternode_feedItems;
   private static Context context;
   private Bitmap bitmap;
   public Adapter_Masternodes_Feed(Context context, List<Constructor_Masternodes> items){
     this.context=context;  
     this.masternode_feedItems = items;
   }  
   
   @Override  
   public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     LayoutInflater inflater = LayoutInflater.from(parent.getContext());
     final View view = inflater.inflate(R.layout.recyclerview_masternodes, parent, false);

     MyViewHolder mvh = new MyViewHolder(view, new MyViewHolder.myClickWebView() {  
       @Override  
       public void gotoWebView(String textlink) {  
         Intent intent=new Intent(view.getContext(),Activity_Web_View.class);
         intent.putExtra("url",textlink);

           System.out.println("NEWS "+textlink);
           view.getContext().startActivity(intent);
       }  
     });  
     return mvh;  
   }  
   
   @Override  
   public void onBindViewHolder(MyViewHolder holder, int position) {  
     holder.symbol.setText(""+masternode_symbol.get(position));
     if (masternode_percent_change.get(position).toString().contains("-")){
         holder.changes.setTextColor(Color.parseColor("#ff0000"));
     }else{
         holder.changes.setTextColor(Color.parseColor("#00ff00"));
     }
     holder.changes.setText(""+masternode_percent_change.get(position)+"%");
     holder.count.setText(""+masternode_node_count.get(position));
     holder.cost_to_own.setText("$ "+masternode_purchase_value.get(position));
     holder.cap.setText(""+masternode_marketcap.get(position));
     holder.name.setText(""+masternode_name.get(position));
     //holder.textlink=item.getLink();
   }  
   
   @Override  
   public int getItemCount() {  
     return masternode_name.size();
   }  
   
   public static class MyViewHolder extends RecyclerView.ViewHolder{
   
       TextView symbol;
       TextView changes;
       TextView name;
       TextView count;
       TextView cost_to_own;
       TextView cap;
       String textlink;
       myClickWebView myClickWebView;
   
     public MyViewHolder(View itemView, final myClickWebView myClickWebView) {  
       super(itemView);

       symbol=itemView.findViewById(R.id.symbol);
       changes=itemView.findViewById(R.id.changes);
       name=itemView.findViewById(R.id.name);
       count=itemView.findViewById(R.id.count);
       cost_to_own=itemView.findViewById(R.id.cost_to_own);
       cap=itemView.findViewById(R.id.cap);


         this.myClickWebView=myClickWebView;
   
       itemView.setOnClickListener(new View.OnClickListener() {  
         @Override  
         public void onClick(View view) {  
           myClickWebView.gotoWebView(textlink);  
         }  
       });



   
     }  
   
     //interface to handle onclick event and send some extras to another activity  
     public interface myClickWebView{

       void gotoWebView(String textlink);

     }  
   }

 }
