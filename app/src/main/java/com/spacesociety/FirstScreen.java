/*
* created by Meera Mali, 2015.
*
*/
package com.spacesociety;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FirstScreen extends Activity {

    public void exploreMore(View v) {
        startActivity(new Intent(FirstScreen.this, MenuGrid.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_screen);
    }

}
