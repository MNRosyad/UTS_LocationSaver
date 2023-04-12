package uts.location.saver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddItem extends AppCompatActivity {
    EditText locNameET, latitudeET, longitudeET;
    Button btnSave, btnCancel;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);

        reference = FirebaseDatabase.getInstance().getReference(LocationPoint.class.getSimpleName());

        locNameET = findViewById(R.id.input_title);
        latitudeET = findViewById(R.id.input_latitude);
        longitudeET = findViewById(R.id.input_longitude);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);

        btnSave.setOnClickListener(view -> {
            addItem();
            nextSave();
        });
        btnCancel.setOnClickListener(view -> {
            Intent back = new Intent(AddItem.this, MainActivity.class);
            back.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(back);
        });
    }

    private void addItem() {
        LocationPoint locationPoint = new LocationPoint();
        String locName = locNameET.getText().toString();
        String latitude = latitudeET.getText().toString();
        String longitude = longitudeET.getText().toString();

        try {
            locationPoint.setLocationName(locName);
            locationPoint.setLatitude(latitude);
            locationPoint.setLongitude(longitude);

            reference.push().setValue(locationPoint);
        } catch (Exception e) {
            locNameET.setError(getText(R.string.feedback_form));
            latitudeET.setError(getText(R.string.feedback_form));
            longitudeET.setError(getText(R.string.feedback_form));
        }
    }

    private void nextSave() {
        Intent save = new Intent(this, MainActivity.class);
        startActivity(save);
        Toast.makeText(getApplicationContext(), R.string.feedback_save, Toast.LENGTH_SHORT).show();
    }
}