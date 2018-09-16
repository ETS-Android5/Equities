package airhawk.com.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_News_Feed extends RecyclerView.Adapter<Adapter_News_Feed.MyViewHolder> {

   private List<Constructor_News_Feed> feedItems;
   private static Context context;

   public Adapter_News_Feed(Context context, List<Constructor_News_Feed> items){
     this.context=context;  
     this.feedItems = items;  
   }  
   
   @Override  
   public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     LayoutInflater inflater = LayoutInflater.from(parent.getContext());
     final View view = inflater.inflate(R.layout.recyclerview_news, parent, false);

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
     Constructor_News_Feed item = feedItems.get(position);
     holder.tv_pubdate.setText(item.getPubDate());  
     holder.tv_title.setText(item.getTitle());
     if(item.getThumbnailUrl() !=null){
     Picasso.with(context).load(item.getThumbnailUrl()).memoryPolicy(MemoryPolicy.NO_CACHE).noFade().into(holder.tv_image);}
     else{Picasso.with(context).load(R.drawable.news).memoryPolicy(MemoryPolicy.NO_CACHE).noFade().into(holder.tv_image);}
     holder.textlink=item.getLink();
   }  
   
   @Override  
   public int getItemCount() {  
     return feedItems==null?0:feedItems.size();  
   }  
   
   public static class MyViewHolder extends RecyclerView.ViewHolder{
   
       TextView tv_title;
       TextView tv_pubdate;
       ImageView tv_image;
       String textlink;
       myClickWebView myClickWebView;
   
     public MyViewHolder(View itemView, final myClickWebView myClickWebView) {  
       super(itemView);

       tv_title=itemView.findViewById(R.id.news_title);
       tv_pubdate=itemView.findViewById(R.id.news_pubdate);
       tv_image=itemView.findViewById(R.id.news_image);
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