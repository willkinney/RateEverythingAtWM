package edu.cs.wm.rateeverythingatwm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("sampleData/reviews");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void postToDB(View view) {
        String testTitle = "This is the test title";
        String testBody = "This is the test text for the body";

        Map<String, Object> dataToSave = new HashMap<String, Object>();

        dataToSave.put("Title", testTitle);
        dataToSave.put("Body", testBody);
        mDocRef.set(dataToSave).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("Success", "Successfully pushed");
                } else {
                    Log.d("Fail", "Failed to push");

                }
            }
        });
    }
}
