package uts.location.saver;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {
    DatabaseReference databaseReference;

    RecyclerView recyclerView;
    private static ArrayList<LocationPoint> locationList = new ArrayList<>();
    static LocationAdapter locationAdapter;
    static ArrayList<String> keys = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        locationList.clear();
        keys.clear();

        databaseReference = FirebaseDatabase.getInstance().getReference(LocationPoint.class.getSimpleName());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for (DataSnapshot currentData : snapshot.getChildren()) {
                        String key = currentData.getKey();
                        keys.add(key);

                        LocationPoint locationPoint = new LocationPoint();
                        locationPoint = currentData.getValue(LocationPoint.class);
                        locationList.add(locationPoint);
                    }
                }

                locationAdapter = new LocationAdapter(getContext(), locationList);
                recyclerView = getActivity().findViewById(R.id.recycler_list);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(locationAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Failed to collect data", error.toException());
            }
        });

        setHasOptionsMenu(true);

        FloatingActionButton fab = rootView.findViewById(R.id.btn_addLocation);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(requireActivity(), AddItem.class);
            startActivity(intent);
        });

        return rootView;
    }
}