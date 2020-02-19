package edu.cs.wm.rateeverythingatwm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button toNearYou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toNearYou = findViewById(R.id.nearyoubutton);




        toNearYou.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startNearYouActivity();
            }
        });

    }

    private void startNearYouActivity() {
        Intent intent = new Intent(this, NearYouActivity.class);
        startActivity(intent);
    }

}

