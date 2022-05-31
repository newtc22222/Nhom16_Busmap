package hcmute.nhom16.busmap;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressHolder> implements SpinnerAdapter {
    private List<Address> addresses;
    private Context context;
    public AddressAdapter(Context context, List<Address> addresses) {
        this.addresses = addresses;
        this.context = context;
    }

    @NonNull
    @Override
    public AddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.address_spinner, parent, false);
        return new AddressHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressHolder holder, int position) {
        holder.tv_city_name.setText(addresses.get(position).getName());
        holder.iv_country.setImageResource(addresses.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }

    @Override
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.address_spinner, viewGroup, false);
        TextView tv_city_name = view.findViewById(R.id.tv_city_name);
        ImageView iv_country = view.findViewById(R.id.iv_country);
        tv_city_name.setText(addresses.get(i).getName());
        iv_country.setImageResource(addresses.get(i).getImage());
        return view;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return addresses.size();
    }

    @Override
    public Object getItem(int i) {
        return addresses.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.address_spinner, viewGroup, false);
        TextView tv_city_name = view.findViewById(R.id.tv_city_name);
        ImageView iv_country = view.findViewById(R.id.iv_country);
        tv_city_name.setText(addresses.get(i).getName());
        iv_country.setImageResource(addresses.get(i).getImage());
        return view;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return addresses.isEmpty();
    }

    public class AddressHolder extends RecyclerView.ViewHolder {
        private TextView tv_city_name;
        private ImageView iv_country;

        public AddressHolder(@NonNull View itemView) {
            super(itemView);
            tv_city_name = itemView.findViewById(R.id.tv_city_name);
            iv_country = itemView.findViewById(R.id.iv_country);
        }
    }
}
