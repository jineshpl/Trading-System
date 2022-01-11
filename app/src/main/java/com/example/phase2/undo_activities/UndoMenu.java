package com.example.phase2.undo_activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.phase2.R;
import com.example.phase2.highabstract.BundleActivity;


public class UndoMenu extends BundleActivity {

    /**create this activity
     * @param savedInstanceState the bundle from the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_undo_menu);
    }


    /**goes to the undoEditMeeting menu
     * @param view the button is clicked
     */
    public void undoEditMeeting(View view){
        Intent intent = new Intent(this, UndoEditMeeting.class);
        intent.putExtra("chosenTrader", getIntent().getStringExtra("chosenTrader"));
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

    /**goes to the undoAgreeTrade menu
     * @param view the button is clicked
     */
    public void undoAgreeTrade(View view){
        Intent intent = new Intent(this, UndoAgreeTrade.class);
        intent.putExtra("chosenTrader", getIntent().getStringExtra("chosenTrader"));
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

    /**goes to the undoConfirmTrade menu
     * @param view the button is clicked
     */
    public void undoConfirmTrade(View view){
        Intent intent = new Intent(this, UndoConfirmTrade.class);
        intent.putExtra("chosenTrader", getIntent().getStringExtra("chosenTrader"));
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }


    /**goes to the undoRemoveItem menu
     * @param view the button is clicked
     */
    public void undoRemoveItem(View view){
        Intent intent = new Intent(this, UndoRemoveItem.class);
        intent.putExtra("chosenTrader", getIntent().getStringExtra("chosenTrader"));
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }
}