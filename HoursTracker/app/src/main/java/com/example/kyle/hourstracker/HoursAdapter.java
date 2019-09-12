package com.example.kyle.hourstracker;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;


import java.util.List;
/* Still working to fix
public class HoursAdapter extends RecyclerView.Adapter<HoursAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView hourDate;
        public TextView hourLength;
        public TextView lunchDuration;
        public TextView jobName;
        public CheckBox chkBoxSelect;

        public ViewHolder(View itemView) {
            super (itemView);

            // get textview references here
        }
    }

    private List<Hours> hourList;

    public HoursAdapter(List<Hours> hours) {
        hourList = hours;
    }

    @Override
    public HoursAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View hourView = inflater.inflate(R.layout.hour_entry, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(hourView);
        return viewHolder;
    }

    // Involves populating data into the item through holder

    @Override
    public void onBindViewHolder(HoursAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Hours hour = hourList.get(position);

        // Set item views based on your views and data model
        TextView hourDate = viewHolder.hourDate;
        hourDate.setText(hour.getDate());

        TextView hourLength = viewHolder.hourLength;
        hourLength.setText(hour.getWorkedHours());

        TextView lunchDuration = viewHolder.lunchDuration;
        lunchDuration.setText(hour.getLunchDuration());

        TextView jobName = viewHolder.jobName;
        jobName.setText(hour.getJobName());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return hourList.size();
    }

}
*/
