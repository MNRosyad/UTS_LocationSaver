package uts.location.saver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;

import uts.location.saver.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.dashboard:
                    replaceFragment(new DashboardFragment());
                    break;
                case R.id.maps:
                    replaceFragment(new MapFragment());
                    break;
                case R.id.settings:
                    replaceFragment(new SettingsFragment());
                    break;
            }
            return true;
        });

        binding.bottomNav.setSelectedItemId(R.id.dashboard);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }
}