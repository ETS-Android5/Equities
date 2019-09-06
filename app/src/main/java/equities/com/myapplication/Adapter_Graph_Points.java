package equities.com.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static equities.com.myapplication.Constructor_App_Variables.graph_change;
import static equities.com.myapplication.Constructor_App_Variables.graph_date;
import static equities.com.myapplication.Constructor_App_Variables.graph_high;
import static equities.com.myapplication.Constructor_App_Variables.graph_volume;

import java.util.ArrayList;

public class Adapter_Graph_Points extends RecyclerView.Adapter<Adapter_Graph_Points.MyViewHolder> {

    ArrayList graph_h;
    ArrayList graph_v;
    ArrayList graph_d;
    ArrayList graph_c;
    Integer integer;
    Context context;

    public Adapter_Graph_Points(Context context, Integer integer, ArrayList graph_h, ArrayList graph_c, ArrayList graph_v, ArrayList graph_d) {
        this.graph_h = graph_h;
        this.graph_c = graph_c;
        this.graph_v = graph_v;
        this.graph_d = graph_d;
        this.integer =integer;
        this.context=context;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView aequity_amount, aequity_volume, aequity_date,aequity_change;
        public MyViewHolder(View itemView) {
            super(itemView);
            aequity_amount = itemView.findViewById(R.id.aequity_amount);
            aequity_volume = itemView.findViewById(R.id.aequity_volume);
            aequity_date = itemView.findViewById(R.id.aequity_date);
            aequity_change =itemView.findViewById(R.id.aequity_change);

            /*No Listener needed for now
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
*/
        }}
            @Override
    public Adapter_Graph_Points.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.historical_row, parent, false);
        return new Adapter_Graph_Points.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Adapter_Graph_Points.MyViewHolder holder, int position) {
        Double as= 0.00;
        Double as1=0.00; try{
        holder.aequity_change.setText(graph_change.get(position)+" %");

            holder.aequity_amount.setText("$ "+graph_high.get(position));
            if(graph_high.get(position)!=null) {
                as = Double.parseDouble(String.valueOf(graph_high.get(position)).replace(",", ""));
            }else{
               as=0.00;
            }

                as1 = Double.parseDouble(String.valueOf(graph_high.get(position + 1)).replace(",",""));

            if(as>as1)
            {
                holder.aequity_amount.setTextColor(context.getResources().getColor(R.color.colorGreen));
                holder.aequity_change.setTextColor(context.getResources().getColor(R.color.colorGreen));

            }
            else
            {
                holder.aequity_amount.setTextColor(context.getResources().getColor(R.color.colorRed));
                holder.aequity_change.setTextColor(context.getResources().getColor(R.color.colorRed));

            }



            String s = (String) graph_volume.get(position);
            s=s.replace(",","");
            s=s.replace("-","");
            String sl=null;

                    sl = (String) graph_volume.get(position + 1);
                    sl = sl.replace(",", "");
                    sl = sl.replace("-", "");


            if (s!=null) {
                Double a =0.00;
                Double al=0.00;
                if(s==""){a = Double.parseDouble("0");}else{
                a = Double.parseDouble(s);}
                if(sl==""){al = Double.parseDouble("0");}else{
                al = Double.parseDouble(sl);}
                holder.aequity_volume.setText("" + graph_volume.get(position));
                if (a > al) {
                    holder.aequity_volume.setTextColor(context.getResources().getColor(R.color.colorGreen));
                } else {
                    holder.aequity_volume.setTextColor(context.getResources().getColor(R.color.colorRed));
                }
            }
            //DO NOT DELETE THIS SYSTEM MESSAGE>ANDROID GLITCH
            //("GDSIZE "+graph_date.size());
            holder.aequity_date.setText(""+graph_date.get(position));}
 catch (IndexOutOfBoundsException e){}}

    @Override
    public int getItemCount() {

        return integer;
    }






}
