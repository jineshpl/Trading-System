package com.example.phase2.item_browsing_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.items.ItemManager;
import com.example.phase2.meetings.MeetingManager;
import com.example.phase2.trades.TradeManager;
import com.example.phase2.highabstract.BundleActivity;
import com.example.phase2.menus.TraderActivity;
import com.example.phase2.users.TraderManager;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class EnterInfoProposeTradeActivity extends BundleActivity {
    private ItemManager itemManager;
    private TradeManager tradeManager;
    private MeetingManager meetingManager;
    private TraderManager traderManager;
    private String currentTrader;
    private Integer chosenItem;
    private Integer myItem;
    private boolean oneWay;
    private boolean permanent;
    private boolean online;
    private String location = null;
    private LocalDate date = null;

    /**
     * Called when the activity is starting.
     * @param savedInstanceState If the activity is being re-initialized after previously being
     * shut down then this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemManager = (ItemManager) getUseCase(ITEMKEY);
        tradeManager = (TradeManager) getUseCase(TRADEKEY);
        meetingManager = (MeetingManager) getUseCase(MEETINGKEY);
        traderManager = (TraderManager) getUseCase(TRADERKEY);
        currentTrader = getUsername();

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        chosenItem = bundle.getInt("ChosenItem");
        myItem = bundle.getInt("MyItem");
        oneWay = bundle.getBoolean("OneWay");
        permanent = bundle.getBoolean("Permanent");
        online = bundle.getBoolean("Online");
        viewStart();
        checkOnline();
    }

    /**
     * Sets the layout of the screen and actually displays things to the screen.
     */
    public void viewStart() { setContentView(R.layout.activity_enter_info_propose_trade); }

    /**
     * Listener for the 'set' button next to the location EditTextField
     * @param view the context the button was pressed in.
     */
    public void setLocation(View view) {
        EditText locationEditText = findViewById(R.id.editTextLocation);
        location =  locationEditText.getText().toString();
        if(location.isEmpty()){
            Toast.makeText(this, "Please enter a location", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Location set!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Listener for the 'set' button next to the date EditTextField
     * @param view the context the button was pressed in.
     */
    public void setDate(View view) {
        EditText dateEditText = findViewById(R.id.editTextDate);
        try {
            date = LocalDate.parse(dateEditText.getText().toString());
            Toast.makeText(this, "Date set!", Toast.LENGTH_SHORT).show();

        }
        catch (DateTimeParseException e) {
            Toast.makeText(this, "Please enter the date in the format YYYY-MM-DD",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Finishes the creation of the trade and moves to the next menu.
     * @param view the context the button was pressed in.
     */
    public void continuing(View view){
        if(checkNotNull()){
            String receiver = itemManager.getOwner(chosenItem);
            int i;
            List<Integer> items = new ArrayList<>();
            items.add(chosenItem);
            if(!oneWay){
                items.add(myItem);
            }
            i = tradeManager.createTrade(currentTrader, receiver, tradeType(), permanent, items);
            traderManager.addNewTrade(currentTrader, i, LocalDate.now());
            traderManager.addNewTrade(receiver, i, LocalDate.now());
            for (Integer j: items) {
                itemManager.changeStatusToUnavailable(j);
            }
            meetingManager.createMeeting(i, currentTrader, receiver, permanent);
            if(permanent){
                meetingManager.setMeetingInfo(i, date, null, location, null);
            }else{
                LocalDate returnDate = date.plusMonths(1);
                meetingManager.setMeetingInfo(i, date, returnDate, location, location);
            }

            Toast.makeText(this, "Trade Created!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, TraderActivity.class);
            putBundle(intent);
            startActivity(intent);
            finish();
        }
    }

    private void checkOnline(){
        if(online){
            EditText locationEditText = findViewById(R.id.editTextLocation);
            locationEditText.setVisibility(View.INVISIBLE);
            Button setLocationButton = findViewById(R.id.setLocationButton);
            setLocationButton.setVisibility(View.INVISIBLE);
            location = "ONLINE";
        }
    }

    private boolean checkNotNull(){
        if (location == null || date == null) {
            Toast.makeText(this, "Please enter a location and/or date.", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }

    }

    private String tradeType(){
        StringBuilder builder = new StringBuilder();
        if(online){
            builder.append("ONLINE-");
        }

        if(oneWay){
            builder.append("ONEWAY");
        }else{
            builder.append("TWOWAY");
        }

        return builder.toString();
    }

    /**
     * Puts the extras in the bundle into the intent
     * @param intent the Intent that requires extras from the bundle
     */
    @Override
    protected void putBundle(Intent intent) {
        replaceUseCase(itemManager);
        replaceUseCase(meetingManager);
        replaceUseCase(tradeManager);
        super.putBundle(intent);
    }

    /**
     * Listener for the cancel button.
     * @param view the context the button was pressed in.
     */
    public void cancel(View view){
        Toast.makeText(this, "Action cancelled.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,TraderActivity.class);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }
}
