package com.example.phase2.undo_activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.highabstract.BundleActivity;
import com.example.phase2.users.TraderManager;

public class UndoActivity extends BundleActivity {


    /**create this activity
     * @param savedInstanceState the bundle from the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_undo);
    }

    /**submit the trader that the admin wants to undo action
     * @param view the textView that allows the admin to input the trader
     */
    public void submit(View view){
        EditText undoTraderText = findViewById(R.id.undoUser);
        String chosenTrader = undoTraderText.getText().toString();
        TraderManager traderManager = (TraderManager) getUseCase(TRADERKEY);

        assert traderManager != null;
        if(traderManager.containTrader(chosenTrader)){
            Intent intent = new Intent(this, UndoMenu.class);
            intent.putExtra("chosenTrader", chosenTrader);
            putBundle(intent);
            startActivityForResult(intent, RESULT_FIRST_USER);
        }else{
            Toast.makeText(this, "User doesn't exist", Toast.LENGTH_SHORT).show();
        }

    }
}