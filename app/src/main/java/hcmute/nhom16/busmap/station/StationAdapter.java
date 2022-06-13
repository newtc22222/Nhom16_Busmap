package hcmute.nhom16.busmap.station;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcmute.nhom16.busmap.R;
import hcmute.nhom16.busmap.Support;
import hcmute.nhom16.busmap.data.SavedStationDAO;
import hcmute.nhom16.busmap.model.Station;
import hcmute.nhom16.busmap.model.User;
import hcmute.nhom16.busmap.model.UserAccount;

public class StationAdapter extends RecyclerView.Adapter<StationAdapter.StationHolder> {
    private Context context;
    private List<Station> stations;
    private StationHolder preHolder = null;
    private boolean order = false;

//    StationAdapter nhận vào là context và list stations
    public StationAdapter(Context context, List<Station> stations) {
        this.stations = stations;
        this.context = context;
    }

    @NonNull
    @Override
    public StationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StationHolder(LayoutInflater.from(context).inflate(R.layout.saved_station, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StationHolder holder, int position) {
        holder.tv_name.setText(stations.get(position).getName(32));

        holder.itemView.setOnClickListener(v -> {
            holder.detail.setVisibility(View.VISIBLE);
            holder.order.setVisibility(View.VISIBLE);
            if (preHolder != null && preHolder != holder) {
                preHolder.order.setVisibility(View.INVISIBLE);
                preHolder.detail.setVisibility(View.INVISIBLE);
            }
            preHolder = holder;
        });

        holder.detail.setOnClickListener(v -> {
            onDetailClick(position);
        });

        holder.ib_delete.setOnClickListener(v -> {
            unSaveStation(position);
        });
    }


    private void unSaveStation(int position) {
        User user = UserAccount.getUser();
        if (user != null) {
            String email = user.getEmail();
            SavedStationDAO.deleteSavedStation(context, email, stations.get(position).getId());
        }
        stations.remove(position);
        notifyItemRemoved(position);
    }

//    onDetailClick sẽ chuyển hướng đến staton activity
    private void onDetailClick(int position) {
        Intent intent = new Intent(context, StationActivity.class);
        intent.putExtra("station", stations.get(position));
        context.startActivity(intent);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return stations == null ? 0 : stations.size();
    }
//  holder của recycler view
    public class StationHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        ImageButton ib_delete;
        LinearLayout detail;
        View order;

        public StationHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            ib_delete = itemView.findViewById(R.id.ib_delete);
            detail = itemView.findViewById(R.id.detail);
            order = itemView.findViewById(R.id.order);
        }
    }
}
