package com.example.phase2.undo_activities;



import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;
import android.widget.Toast;

import com.example.phase2.dialogs.DialogFactory;
import com.example.phase2.highabstract.Dialogable;
import com.example.phase2.meetings.MeetingManager;
import com.example.phase2.R;
import com.example.phase2.highabstract.BundleActivity;
import com.example.phase2.users.TraderManager;

import java.util.ArrayList;
import java.util.List;

public class UndoAgreeTrade extends BundleActivity implements Dialogable {
    private String chosenTrader;
    private TraderManager traderManager;
    private MeetingManager meetingManager;
    private Integer chosenTrade;

    /**create this activity
     * @param savedInstanceState the bundle from the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chosenTrader = getIntent().getStringExtra("chosenTrader");
        traderManager = (TraderManager) getUseCase(TRADERKEY);
        meetingManager = (MeetingManager) getUseCase(MEETINGKEY);
        viewList();
    }

    /**
     * listener for the back button, return to the last menu
     */
    @Override
    public void onBackPressed() {
        replaceUseCase(meetingManager);
        replaceUseCase(traderManager);
        super.onBackPressed();
    }

    /**
     * This is a method that displays list of trade that the chosenTrader has agreed to. When the user
     * clicks on an item, the user can undo the chosenTrader's agreement.
     */
    public void viewList(){
        List<Integer> incompleteTrades = meetingManager
                .getOnGoingMeetings(traderManager.getTrades(chosenTrader));
        final List<Integer> undoableMeetingAgreements = new ArrayList<>();
        for (Integer i : incompleteTrades) {
            if (meetingManager.canUndoAgree(i, chosenTrader)) {
                undoableMeetingAgreements.add(i);
            }
        }
        ArrayList<String> undoPresentation = new ArrayList<>();
        for (Integer i : undoableMeetingAgreements) {
            undoPresentation.add(meetingManager.getMeetingDescription(i));
        }
        setContentView(R.layout.activity_undo_agree_trade);
        ListView listView = findViewById(R.id.agreedTrade);
        ArrayAdapter<String> agreeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, undoPresentation);
        listView.setAdapter(agreeAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openDialog();
                chosenTrade = undoableMeetingAgreements.get(i);
            }
        });
    }


    /**
     * Listener for the positive button, undoAgree
     */
    @Override
    public void clickPositive() {
        meetingManager.undoAgree(chosenTrade, chosenTrader);
        Toast.makeText(this, "Successfully undo agree!", Toast.LENGTH_SHORT).show();
        viewList();

    }

    /**
     * Listener for the negative button, cancel the action
     */
    @Override
    public void clickNegative() {
        Toast.makeText(this, "Cancelled!", Toast.LENGTH_SHORT).show();
        viewList();

    }

    /**
     * open the dialog
     */
    @Override
    public void openDialog() {
        DialogFactory dialogFactory = new DialogFactory();
        dialogFactory.getDialog("Undo")
                .show(getSupportFragmentManager(), "UndoAgree");

    }
}