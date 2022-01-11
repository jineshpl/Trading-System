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

public class UndoConfirmTrade extends BundleActivity implements Dialogable {
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

    private void viewList() {
        List<Integer> incompleteTrades =
                meetingManager.getOnGoingMeetings(traderManager.getTrades(chosenTrader));
        final List<Integer> undoableMeetingConfirmations = new ArrayList<>();
        for (Integer i : incompleteTrades) {
            if (meetingManager.canUndoConfirm(i, chosenTrader)) {
                undoableMeetingConfirmations.add(i);
            }
        }
        ArrayList<String> confirmationPresenter = new ArrayList<>();
        for (Integer i : undoableMeetingConfirmations) {
            confirmationPresenter.add(meetingManager.getMeetingDescription(i));
        }
        setContentView(R.layout.activity_undo_confirm_trade);
        ListView listView = findViewById(R.id.confirmedTrade);
        ArrayAdapter<String> confirmAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, confirmationPresenter);
        listView.setAdapter(confirmAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openDialog();
                chosenTrade = undoableMeetingConfirmations.get(i);
            }
        });

    }

    /**
     * Listener for the positive button, undoConfirm
     */
    @Override
    public void clickPositive() {
        meetingManager.undoConfirm(chosenTrade, chosenTrader);
        Toast.makeText(this, "Successfully undo confirm", Toast.LENGTH_SHORT).show();
        viewList();

    }

    /**
     * Listener for the negative button, cancel the action
     */
    @Override
    public void clickNegative() {
        Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
        viewList();


    }

    /**
     * open the dialog
     */
    @Override
    public void openDialog() {
        DialogFactory dialogFactory = new DialogFactory();
        dialogFactory.getDialog("Undo")
                .show(getSupportFragmentManager(), "UndoConfirm");

    }
}