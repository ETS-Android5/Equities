package airhawk.com.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static airhawk.com.myapplication.Constructor_App_Variables.Simg_url;
import static airhawk.com.myapplication.Constructor_App_Variables.Smessage;
import static airhawk.com.myapplication.Constructor_App_Variables.Smessage_time;
import static airhawk.com.myapplication.Constructor_App_Variables.Suser_name;
import static airhawk.com.myapplication.Constructor_App_Variables.exchange_image;
import static airhawk.com.myapplication.Constructor_App_Variables.exchange_text;

public class Adapter_Exchanges_Feed extends RecyclerView.Adapter<Adapter_Exchanges_Feed.MyViewHolder> {

   private static Context context;
    public ArrayList<String> e_text=exchange_text;
    public ArrayList<String> e_image=exchange_image;


    public Adapter_Exchanges_Feed(Context context, List<Constructor_Exchanges> items){
     this.context=context;  
   }


    @Override
   public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     LayoutInflater inflater = LayoutInflater.from(parent.getContext());
     final View view = inflater.inflate(R.layout.view_exchanges, parent, false);

     MyViewHolder mvh = new MyViewHolder(view, new MyViewHolder.myClickWebView() {  
       @Override  
       public void gotoWebView(String textlink) {  
         //Intent intent=new Intent(view.getContext(),Activity_Web_View.class);
         //intent.putExtra("url",textlink);


           //view.getContext().startActivity(intent);
       }  
     });  
     return mvh;  
   }  
   
   @Override  
   public void onBindViewHolder(MyViewHolder holder, int position) {
     holder.exchange_text.setText(""+ exchange_text.get(position));
     if(e_image !=null){
     Picasso.with(context).load(String.valueOf(e_image.get(position))).memoryPolicy(MemoryPolicy.NO_CACHE).fit().centerInside().noFade().into(holder.exchange_image);}
     else{Picasso.with(context).load(R.drawable.socialmedia).memoryPolicy(MemoryPolicy.NO_CACHE).noFade().into(holder.exchange_image);}
   }
   
   @Override  
   public int getItemCount() {  
     return exchange_text.size();
   }  
   
   public static class MyViewHolder extends RecyclerView.ViewHolder{

       TextView exchange_text;
       ImageView exchange_image;
       myClickWebView myClickWebView;
   
     public MyViewHolder(View itemView, final myClickWebView myClickWebView) {  
       super(itemView);
       exchange_image =itemView.findViewById(R.id.exchange_image);
       exchange_text=itemView.findViewById(R.id.exchange_text);
       this.myClickWebView=myClickWebView;  
   
       itemView.setOnClickListener(new View.OnClickListener() {  
         @Override  
         public void onClick(View view) {  
          //myClickWebView.gotoWebView(textlink);
         }  
       });



   
     }  
   
     //interface to handle onclick event and send some extras to another activity  
     public interface myClickWebView{

       void gotoWebView(String textlink);

     }  
   }  
   
 }