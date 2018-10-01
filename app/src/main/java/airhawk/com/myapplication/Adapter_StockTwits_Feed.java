package airhawk.com.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import static airhawk.com.myapplication.Activity_Main.fullScreen;
import static airhawk.com.myapplication.Constructor_App_Variables.Simg_url;
import static airhawk.com.myapplication.Constructor_App_Variables.Smessage;
import static airhawk.com.myapplication.Constructor_App_Variables.Smessage_link;
import static airhawk.com.myapplication.Constructor_App_Variables.Smessage_time;
import static airhawk.com.myapplication.Constructor_App_Variables.Suser_name;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter_StockTwits_Feed extends RecyclerView.Adapter<Adapter_StockTwits_Feed.MyViewHolder> {

   private static Context context;
    private ArrayList<String> timeList=Smessage_time;
    private ArrayList<String> linkList=Smessage_link;
    public ArrayList<String> nameList=Suser_name;
    public ArrayList<String> messsageList=Smessage;
    public ArrayList<String> imagelist=Simg_url;


    public Adapter_StockTwits_Feed(Context context, List<Constructor_Stock_Twits> items){
     this.context=context;  
   }


    @Override
   public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     LayoutInflater inflater = LayoutInflater.from(parent.getContext());
     final View view = inflater.inflate(R.layout.recyclerview_stocktwits, parent, false);

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
     holder.tv_message_time.setText(""+Smessage_time.get(position));
     holder.tv_user_name.setText(""+Suser_name.get(position));
     holder.tv_message.setText(""+Smessage.get(position));
     if(imagelist !=null){
     Picasso.with(context).load(imagelist.get(position)).memoryPolicy(MemoryPolicy.NO_CACHE).fit().centerInside().noFade().into(holder.tv_image);}
     else{Picasso.with(context).load(R.drawable.direction_socialmedia).memoryPolicy(MemoryPolicy.NO_CACHE).noFade().into(holder.tv_image);}
     holder.textlink= String.valueOf(Smessage.get(position));
   }
   
   @Override  
   public int getItemCount() {  
     return Smessage.size();
   }  
   
   public static class MyViewHolder extends RecyclerView.ViewHolder{
   
       TextView tv_user_name;
       TextView tv_message;
       TextView tv_message_time;
       ImageView tv_image;
       String textlink;
       myClickWebView myClickWebView;
   
     public MyViewHolder(View itemView, final myClickWebView myClickWebView) {  
       super(itemView);

       tv_user_name=itemView.findViewById(R.id.user_name);
       tv_message=itemView.findViewById(R.id.message);
       tv_message_time=itemView.findViewById(R.id.message_time);
       tv_image=itemView.findViewById(R.id.user_image);
       this.myClickWebView=myClickWebView;
       fullScreen ="go";
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