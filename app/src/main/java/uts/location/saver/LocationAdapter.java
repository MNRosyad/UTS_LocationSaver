package uts.location.saver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    private ArrayList<LocationPoint> values;
    private LayoutInflater inflater;

    DatabaseReference reference;

    public LocationAdapter(Context context, ArrayList<LocationPoint> values) {
        this.values = values;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new LocationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.ViewHolder holder, int position) {
        int pos = position;
        LocationPoint locationPoint = values.get(position);

        holder.locName.setText(locationPoint.getLocationName());
        holder.latitude.setText(locationPoint.getLatitude());
        holder.longitude.setText(locationPoint.getLongitude());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String key = DashboardFragment.keys.get(pos);
                reference = FirebaseDatabase.getInstance().getReference(LocationPoint.class.getSimpleName());
                reference.child(key).removeValue();

                Toast.makeText(view.getContext(), R.string.feedback_remove, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView locName, latitude, longitude;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            locName = itemView.findViewById(R.id.title_item);
            latitude = itemView.findViewById(R.id.latitude);
            longitude = itemView.findViewById(R.id.longitude);
        }
    }
}
