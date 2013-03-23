package com.example.layouttest;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
		TabHost tabHost = getTabHost();
        
        TabSpec patternSpec = tabHost.newTabSpec("Patterns").setIndicator("Patterns", getResources().getDrawable(R.drawable.ic_launcher));
        //patternSpec.setIndicator("Patterns");
        Intent patternIntent = new Intent().setClass(this, PatternActivity.class);
        patternSpec.setContent(patternIntent);
 
        TabSpec trackSpec = tabHost.newTabSpec("Track").setIndicator("Track");
        //trackSpec.setIndicator("Track");
        Intent trackIntent = new Intent().setClass(this, TrackActivity.class); 
        trackSpec.setContent(trackIntent);

        
        tabHost.addTab(patternSpec);
        tabHost.addTab(trackSpec);
        tabHost.setCurrentTab(0);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
}
