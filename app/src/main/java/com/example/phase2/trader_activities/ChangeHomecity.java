package com.example.phase2.trader_activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.highabstract.BundleActivity;
import com.example.phase2.users.TraderManager;

public class ChangeHomecity extends BundleActivity {

    private TraderManager tm;
    private String username;

    /**
     * Sets up the activity.
     * @param savedInstanceState A bundle that has all the necessary objects
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_homecity);
        tm = (TraderManager) getUseCase(TRADERKEY);
        username = getUsername();
    }

    /**
     * This method is called when the user presses the enter button. It updates the user's homecity
     * to the new change that user has entered.
     * @param view A view
     */
    public void submitTraderHomecity(View view){
        EditText editText = findViewById(R.id.editTextTextPersonName2);
        String newHomeCity = editText.getText().toString();

        if(newHomeCity.equals("") || newHomeCity.equals(tm.getHomeCity(username))){
            Toast.makeText(this, "Invalid homecity.", Toast.LENGTH_SHORT).show();
        }else {
            tm.setHomeCity(username, newHomeCity);
            Toast.makeText(this, "Successfully changed homecity", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        editText.setText("");

    }

    /**
     * Called when the user presses the back button. Updates use case class.
     */
    @Override
    public void onBackPressed() {
        replaceUseCase(tm);
        super.onBackPressed();
    }

}