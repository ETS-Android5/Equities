package airhawk.com.myapplication;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Historical_Data_Model> implements View.OnClickListener{

    private ArrayList<Historical_Data_Model> dataSet;
    protected Context mContext;

    @Override
    public void onClick(View v) {

    }

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtVolume;
        TextView txtDate;
    }

    public CustomAdapter(ArrayList<Historical_Data_Model> data, Context context) {
        super(context, R.layout.historical_row, data);
        this.dataSet = data;
        this.mContext=context;

    }


    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Historical_Data_Model dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.historical_row, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.aequity_name);
            viewHolder.txtVolume = (TextView) convertView.findViewById(R.id.aequity_volume);
            viewHolder.txtDate = (TextView) convertView.findViewById(R.id.aequity_date);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        lastPosition = position;

        viewHolder.txtName.setText("$ "+dataModel.getName());
        viewHolder.txtVolume.setText("$ "+dataModel.getVolume());
        viewHolder.txtDate.setText(dataModel.getDate());

        // Return the completed view to render on screen
        return convertView;
    }
}