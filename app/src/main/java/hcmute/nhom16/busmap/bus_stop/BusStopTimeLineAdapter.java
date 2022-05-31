package hcmute.nhom16.busmap.bus_stop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalTime;
import java.util.List;

import hcmute.nhom16.busmap.R;

public class BusStopTimeLineAdapter extends RecyclerView.Adapter<BusStopTimeLineAdapter.TimeLineHolder> {
    List<LocalTime> time_lines;
    Context context;

    public BusStopTimeLineAdapter(Context context, List<LocalTime> time_lines) {
        this.time_lines = time_lines;
        this.context = context;
    }
    @NonNull
    @Override
    public TimeLineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TimeLineHolder(LayoutInflater.from(context).inflate(R.layout.time_line, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineHolder holder, int position) {
        holder.tv_time.setText(time_lines.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return time_lines == null ? 0 : time_lines.size();
    }

    public class TimeLineHolder extends RecyclerView.ViewHolder {
        TextView tv_time;
        public TimeLineHolder(@NonNull View itemView) {
            super(itemView);
            tv_time = itemView.findViewById(R.id.tv_time);
        }
    }
}
