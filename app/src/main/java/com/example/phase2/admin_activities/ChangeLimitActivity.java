package com.example.phase2.admin_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.phase2.R;
import com.example.phase2.highabstract.BundleActivity;

/**
 * An activity class responsible for changing the limits in the Trading System.
 */
public class ChangeLimitActivity extends BundleActivity {

    /**
     * Sets up the activity
     * @param savedInstanceState A bundle storing all the necessary objects
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_limit);
    }

    /**
     * This method is called when the user clicks on the Weekly Limit button. It starts
     * the DisplayChangeLimitActivity
     * @param view A view
     */
    public void changeWeeklyLimit(View view){
        Intent i = new Intent(this, DisplayChangeLimitActivity.class);
        putBundle(i);
        i.putExtra(LimitType.class.getName(), LimitType.WEEKLY_LIMIT);
        startActivityForResult(i, RESULT_FIRST_USER);
    }

    /**
     * This method is called when the user clicks on the Max Incomplete button. It starts
     * the DisplayChangeLimitActivity
     * @param view A view
     */
    public void changeMaxIncomplete(View view){
        Intent i = new Intent(this, DisplayChangeLimitActivity.class);
        putBundle(i);
        i.putExtra(LimitType.class.getName(), LimitType.MAX_INCOMPLETE);
        startActivityForResult(i, RESULT_FIRST_USER);

    }

    /**
     * This method is called when the user clicks on the More Lend button. It starts
     * the DisplayChangeLimitActivity
     * @param view A view
     */
    public void changeAtLeast(View view){
        Intent i = new Intent(this, DisplayChangeLimitActivity.class);
        putBundle(i);
        i.putExtra(LimitType.class.getName(), LimitType.MORE_LEND);
        startActivityForResult(i, RESULT_FIRST_USER);
    }

}