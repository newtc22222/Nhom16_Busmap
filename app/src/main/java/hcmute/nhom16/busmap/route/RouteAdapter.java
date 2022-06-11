package hcmute.nhom16.busmap.route;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcmute.nhom16.busmap.R;
import hcmute.nhom16.busmap.Support;
import hcmute.nhom16.busmap.model.Route;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.RouteHolder> {
    private Context context;
    private List<Route> routes;

    public RouteAdapter(Context context, List<Route> routes) {
        this.routes = routes;
        this.context = context;
    }

    @NonNull
    @Override
    public RouteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RouteHolder(LayoutInflater.from(context).inflate(R.layout.route, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RouteHolder holder, int position) {
        holder.tv_id.setText(routes.get(position).getId());
        holder.tv_name.setText(routes.get(position).getName());
        holder.tv_time.setText(routes.get(position).getOperation_time());
        holder.tv_money.setText(routes.get(position).getMoney());
        holder.ib_saved.setOnClickListener(v -> {
            saveRoute(position);
        });
        holder.itemView.setOnClickListener(v -> {
            toRouteDetail(position);
        });
    }

    private void toRouteDetail(int position) {
        Intent intent = new Intent(context, RouteDetailActivity.class);
        intent.putExtra("route", routes.get(position));
        context.startActivity(intent);
    }

    private void saveRoute(int position) {
        Support.saveRoute(context, routes.get(position).getId());
        Toast.makeText(context, "Đã lưu", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return routes == null ? 0 : routes.size();
    }

    public class RouteHolder extends RecyclerView.ViewHolder {
        TextView tv_id, tv_name, tv_time, tv_money;
        ImageButton ib_saved;
        public RouteHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_money = itemView.findViewById(R.id.tv_money);
            tv_name = itemView.findViewById(R.id.tv_name);
            ib_saved = itemView.findViewById(R.id.ib_saved);
        }
    }
}
