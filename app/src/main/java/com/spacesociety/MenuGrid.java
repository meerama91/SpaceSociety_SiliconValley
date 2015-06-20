/*
* created by Meera Mali, 2015.
*
*/
package com.spacesociety;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class MenuGrid extends Activity {

    private static final String[] MENU_INDEX = {"WEBSITE", "PDF", "MP3"};
    private static final int WEB_DATA = 0;
    private static final int PDF = 1;
    private static final int MP3 = 2;

    private static GridView mGrid;
    private static int image_id[] = {R.drawable.s3v_logo, R.drawable.bookreadericon, R.drawable.mp3icon};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_grid);
        CustomGrid adapter = new CustomGrid(MenuGrid.this, MENU_INDEX, image_id);
        mGrid = (GridView) findViewById(R.id.grid);
        mGrid.setAdapter(adapter);
        mGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent;
                switch (position) {
                    case WEB_DATA:
                        intent = new Intent(MenuGrid.this, WebActivity.class);
                        startActivity(intent);
                        break;
                    case PDF:
                        intent = new Intent(MenuGrid.this, PdfLibrary.class);
                        startActivity(intent);
                        break;
                    case MP3:
                        intent = new Intent(MenuGrid.this, Mp3Library.class);
                        startActivity(intent);
                }
            }
        });
    }

}
