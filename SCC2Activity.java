package com.scc2.cano.scc2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SCC2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scc2);
        Button serviceBtn = (Button) findViewById(R.id.button);

        serviceBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startService(new Intent(SCC2Activity.this, SCC2.class));
            }
        });
    }
}
