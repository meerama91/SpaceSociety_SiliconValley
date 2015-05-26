/*
* created by Meera Mali, 2015.
*
*/
package com.spacesociety;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FirstScreen extends Activity {

    private Button mExplore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_screen);
        mExplore = (Button) findViewById(R.id.explore);
        mExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstScreen.this, MenuGrid.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
