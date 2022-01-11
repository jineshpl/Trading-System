package com.example.phase2.browse_trades_activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.highabstract.Dialogable;
import com.example.phase2.highabstract.UpdatableBundleActivity;
import com.example.phase2.items.ItemManager;
import com.example.phase2.meetings.MeetingManager;
import com.example.phase2.trades.TradeManager;
import com.example.phase2.users.TraderManager;

import java.util.Objects;

public class EditTradeActivity extends UpdatableBundleActivity implements Dialogable {
    private TradeManager tradeManager;
    private MeetingManager meetingManager;
    private TraderManager traderManager;
    private String currentTrader;
    private Integer trade;
    private ItemManager itemManager;

    private Dialog itemsDialog;

    /**
     * Updates the Manager classes in the bundle
     */
    protected void updateUseCases(){
        tradeManager = (TradeManager) getUseCase(TRADEKEY);
        meetingManager = (MeetingManager) getUseCase(MEETINGKEY);
        traderManager = (TraderManager) getUseCase(TRADERKEY);
        itemManager = (ItemManager) getUseCase(ITEMKEY);
        updateScreen();
    }
    /**
     * Called when the activity is starting.
     * @param savedInstanceState If the activity is being re-initialized after previously being
     * shut down then this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trade);

        trade = (Integer) Objects.requireNonNull(getIntent().getExtras()).getSerializable("Trade");
        tradeManager = (TradeManager) getUseCase(TRADEKEY);
        meetingManager = (MeetingManager) getUseCase(MEETINGKEY);
        traderManager = (TraderManager) getUseCase(TRADERKEY);
        currentTrader = (String) getUseCase(USERNAMEKEY);
        itemManager = (ItemManager) getUseCase(ITEMKEY);
        updateScreen();
    }

    private void updateScreen(){
        String tempTradeType = "Trade Type: " + tradeManager.getTradeType(trade);
        TextView tradeType = findViewById(R.id.tradeType);
        tradeType.setText(tempTradeType);
        String tempTradeDuration;
        if(tradeManager.isTradePermanent(trade)){
            tempTradeDuration = "Trade Duration: "+ "Permanent";
        }
        else{
            tempTradeDuration = "Trade Duration: "+ "1 Month";
        }
        TextView tradeDuration = findViewById(R.id.tradeDuration);
        tradeDuration.setText(tempTradeDuration);
        String temptradeWith = "Trade With: "+ tradeManager.getOtherTrader(trade,currentTrader);
        TextView tradeWith = findViewById(R.id.tradeWith);
        tradeWith.setText(temptradeWith);
        String tempDate = "Date: " + meetingManager.getMeetingDate(trade);
        TextView meetingDate = findViewById(R.id.meetingDate);
        meetingDate.setText(tempDate);
        String tempLocation = "Location: " + meetingManager.getMeetingLocation(trade);
        TextView meetingLocation = findViewById(R.id.meetingInformation);
        meetingLocation.setText(tempLocation);
        updateCurTraderMeetingStatus();
        updateOtherTraderMeetingStatus();
        String tempEditNumber = "You can edit " + meetingManager.getEditsLeft(trade, currentTrader) + " times.";
        TextView editInfo = findViewById(R.id.editInformation);
        editInfo.setText(tempEditNumber);
        Button button = findViewById(R.id.confirmButton);
        itemsDialog = new Dialog(this);
        if(meetingManager.bothAgreed(trade)){
            LinearLayout linearLayout = findViewById(R.id.agreeOrEditButtons);
            linearLayout.setVisibility(View.GONE);
            if(meetingManager.hasConfirmed(trade, currentTrader)){
                button.setVisibility(View.GONE);
            }
            else{
                button.setVisibility(View.VISIBLE);
            }
        }
        else{
            button.setVisibility(View.GONE);
        }
        Button declineButton = findViewById(R.id.declineButton);
        if(meetingManager.bothAgreed(trade)){
            declineButton.setVisibility(View.GONE);
        }
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDeclineTrade();
            }
        });
        if(cancelTradeCheck()){
            onBackPressed();
        }
    }

    /**
     * Declines the trade if the decline pressed.
     */
    public void onDeclineTrade(){
        removeTrade();
        Toast.makeText(EditTradeActivity.this, R.string.tradeDeclined, Toast.LENGTH_LONG).show();
        onBackPressed();
    }

    private void updateCurTraderMeetingStatus(){
        String tempAgreeStatus;
        if(!meetingManager.bothAgreed(trade)) {
            if (meetingManager.hasAgreed(trade, currentTrader)) {
                tempAgreeStatus = "You have agreed to the appointment.";
            } else {
                tempAgreeStatus = "You have not agreed to the appointment.";
            }
        }
        else{
            if(meetingManager.hasConfirmed(trade,currentTrader)){
                tempAgreeStatus = "You have confirmed the appointment.";
            }else{
                tempAgreeStatus = "You have not confirmed the appointment.";
            }

        }
        TextView selfTraderAgree = findViewById(R.id.selfTraderAgree);
        selfTraderAgree.setText(tempAgreeStatus);
    }

    private void updateOtherTraderMeetingStatus(){
        String tempAgreeStatus2;
        if(!meetingManager.bothAgreed(trade)) {
            if (meetingManager.hasAgreed(trade, tradeManager.getOtherTrader(trade, currentTrader))) {
                tempAgreeStatus2 = tradeManager.getOtherTrader(trade, currentTrader) + " has agreed to the appointment.";
            } else {
                tempAgreeStatus2 = tradeManager.getOtherTrader(trade, currentTrader) + " has not agreed to the appointment.";
            }
        }else {
            if (meetingManager.hasConfirmed(trade, tradeManager.getOtherTrader(trade, currentTrader))) {
                tempAgreeStatus2 = tradeManager.getOtherTrader(trade, currentTrader) + " has confirmed the appointment.";
            } else {
                tempAgreeStatus2 = tradeManager.getOtherTrader(trade, currentTrader) + " has not confirmed the appointment.";
            }
        }
        TextView selfTraderAgree2 = findViewById(R.id.opposingTradeAgree);
        selfTraderAgree2.setText(tempAgreeStatus2);
    }

    /**
     * Opens the edit meeting activity if the button is clicked
     * @param view The view of the editMeetingClicked
     */
    public void onEditMeetingClicked(View view){
        if(meetingManager.isValid(currentTrader, trade)){
            Intent intent = new Intent(this, EditMeetingActivity.class);
            intent.putExtra("Trade", trade);
            if(tradeManager.getTradeType(trade).contains("ONLINE")){
                intent.putExtra("Online", true);
            }
            else{
                intent.putExtra("Online", false);
            }
            replaceUseCases();
            putBundle(intent);
            startActivityForResult(intent, RESULT_FIRST_USER);
        }
        else{
            Toast.makeText(EditTradeActivity.this, R.string.cannotEdit, Toast.LENGTH_LONG).show();
        }

    }

    private void replaceUseCases(){
        replaceUseCase(tradeManager);
        replaceUseCase(meetingManager);
        replaceUseCase(traderManager);
        replaceUseCase(itemManager);
    }

    /**
     * Goes back to browseTradesActivity if the back button is pressed
     */
    @Override
    public void onBackPressed() {
        replaceUseCases();
        super.onBackPressed();
    }

    /**
     * Agrees to the meeting and updates the trade
     * @param view The agree button view
     */
    public void onAgreeMeetingClicked(View view){
        if(meetingManager.hasAgreed(trade, currentTrader)){
            Toast.makeText(this, R.string.already_agreed, Toast.LENGTH_LONG).show();
        }
        else{
            meetingManager.agreeOnTrade(trade, currentTrader);
            String tempAgreeStatus = "You have agreed to the appointment.";
            TextView selfTraderAgree = findViewById(R.id.selfTraderAgree);
            selfTraderAgree.setText(tempAgreeStatus);
        }
        if(meetingManager.bothAgreed(trade)){
            LinearLayout linearLayout = findViewById(R.id.agreeOrEditButtons);
            linearLayout.setVisibility(View.GONE);
            Button button = findViewById(R.id.confirmButton);
            button.setVisibility(View.VISIBLE);
            updateCurTraderMeetingStatus();
            updateOtherTraderMeetingStatus();
        }

    }

    private void updateNumLentBorrowed(){
        if(tradeManager.getTradeType(trade).contains("ONEWAY")){
            if(itemManager.getItemOwner(tradeManager.getItems(trade).get(0)).equals(currentTrader)){
                traderManager.increaseNumBorrowed(tradeManager.getOtherTrader(trade,currentTrader));
                traderManager.increaseNumLent(currentTrader);
            }
            else{
                traderManager.increaseNumBorrowed(currentTrader);
                traderManager.increaseNumLent(tradeManager.getOtherTrader(trade,currentTrader));
            }
        }
    }

    /**
     * Confirms and updates the trade once the confirm button is clicked.
     * @param view The confirm button's view
     */
    public void onConfirmMeetingClicked(View view){
        //Date for checking confirm time is set
        //For Brandon's convenience I have commented this line out for easier testing.
//        if(meetingManager.getReturnLocation(trade).equals("N/A")){
//            if(!meetingManager.dateIsAfterMeeting(trade, LocalDate.now())){
//                Toast.makeText(this, R.string.cannotConfirm, Toast.LENGTH_LONG).show();
//                return;
//            }
//        }else{
//            if(!meetingManager.dateIsAfterReturnMeeting(trade, LocalDate.now())){
//                Toast.makeText(this, R.string.cannotConfirm, Toast.LENGTH_LONG).show();
//                return;
//            }
//        }
            if(meetingManager.hasConfirmed(trade,tradeManager.getOtherTrader(trade,currentTrader) )){
                if(traderManager.exceedMaxIncomplete(currentTrader)){
                    traderManager.decreaseNumIncomplete(currentTrader);
                    if(!traderManager.exceedMaxIncomplete(currentTrader)){
                        traderManager.unFlagUser(currentTrader);
                    }
                }
                else{
                    traderManager.decreaseNumIncomplete(currentTrader);
                }

            }
            meetingManager.confirmMeeting(trade, currentTrader);
            Button button = findViewById(R.id.confirmButton);
            button.setVisibility(View.GONE);
            updateCurTraderMeetingStatus();
            updateOtherTraderMeetingStatus();
            if(meetingManager.bothConfirmed(trade)){
                if(!tradeManager.isTradePermanent(trade)){
                    if(meetingManager.getReturnLocation(trade).equals("N/A")){
                        meetingManager.setReturnInfo(trade);
                        String tempDate = "Date: " + meetingManager.getReturnDate(trade);
                        TextView meetingDate = findViewById(R.id.meetingDate);
                        meetingDate.setText(tempDate);
                        updateWishlistAndBorrowed();
                        Toast.makeText(this, R.string.return_meeting_popup, Toast.LENGTH_LONG).show();
                        button.setVisibility(View.VISIBLE);
                        updateCurTraderMeetingStatus();
                        updateOtherTraderMeetingStatus();
                    }
                    else{
                        for(Integer i: tradeManager.getItems(trade)){
                            if(traderManager.getIsFrozen(itemManager.getItemOwner(i))){
                                itemManager.changeStatusToFrozen(i);
                            }
                            else if(traderManager.isInactive(itemManager.getItemOwner(i))){
                                itemManager.changeStatusToInactiveAva(i);
                            }
                            else{
                                itemManager.changeStatusToAvailable(i);
                            }
                        }
                        updateNumLentBorrowed();
                        completeTrade();
                    }
                }
                else{
                    updateNumLentBorrowed();
                    updateItemOwnerAndStatus();
                    completeTrade();
                }
            }else{
                traderManager.increaseNumbIncomplete(tradeManager.getOtherTrader(trade,currentTrader));
                if(traderManager.exceedMaxIncomplete(tradeManager.getOtherTrader(trade,currentTrader))){
                    traderManager.flagUser(tradeManager.getOtherTrader(trade,currentTrader));
                }
            }
    }

    private void completeTrade(){
        meetingManager.setMeetingCompleted(trade);
        tradeManager.setTradeCompleted(trade);
        Toast.makeText(this, R.string.trade_completed, Toast.LENGTH_LONG).show();
        onBackPressed();
    }
    private void updateItemOwnerAndStatus(){
        for(Integer i: tradeManager.getItems(trade)){
            if(itemManager.getOwner(i).equals(currentTrader)){
                itemManager.setItemOwner(i, tradeManager.getOtherTrader(trade,currentTrader));
            }
            else{
                itemManager.setItemOwner(i, currentTrader);
            }
            if(traderManager.getIsFrozen(itemManager.getItemOwner(i))){
                itemManager.changeStatusToFrozen(i);
            }
            else if(traderManager.isInactive(itemManager.getItemOwner(i))){
                itemManager.changeStatusToInactiveAva(i);
            }
            else{
                itemManager.changeStatusToAvailable(i);
            }
        }
    }
    private void updateWishlistAndBorrowed(){
        for(Integer i: tradeManager.getItems(trade)){
            itemManager.changeStatusToUnavailable(i);
            if(itemManager.getOwner(i).equals(currentTrader)){
                traderManager.removeFromWishlist(tradeManager.getOtherTrader(trade,currentTrader), i);
            }
            else{
                traderManager.removeFromWishlist(currentTrader, i);
            }
        }
    }

    /**
     * Shows the item(s) information
     * @param view The view
     */
    public void onViewItemInformationClicked(View view){
        openDialog();
    }

    private boolean cancelTradeCheck(){
            if (meetingManager.isMaxEditsReached(trade)) {
                removeTrade();
                Toast.makeText(EditTradeActivity.this, R.string.maxEditsReached, Toast.LENGTH_LONG).show();
                return true;
            }
            return false;
    }

    private void removeTrade(){
        for(Integer i: tradeManager.getItems(trade)){
            if(traderManager.getIsFrozen(itemManager.getItemOwner(i))){
                itemManager.changeStatusToFrozen(i);
            }
            else if(traderManager.isInactive(itemManager.getItemOwner(i))){
                itemManager.changeStatusToInactiveAva(i);
            }
            else{
                itemManager.changeStatusToAvailable(i);
            }
        }
        traderManager.removeTradeFromTraders(trade, currentTrader, tradeManager.getOtherTrader(trade, currentTrader));
        tradeManager.removeFromInventory(trade);
        meetingManager.removeMeeting(trade);

    }
    private void updateDialogs(){
        if(tradeManager.getTradeType(trade).contains("ONEWAY")){
            itemsDialog.setContentView(R.layout.activity_one_item);
            setUpOneItem();
        }
        else{
            itemsDialog.setContentView(R.layout.activity_two_item);
            setUpTwoItems();
        }

    }
    private void setUpOneItem(){
        String tempItemName = itemManager.getItemName(tradeManager.getItems(trade).get(0));
        TextView itemName = itemsDialog.findViewById(R.id.itemName);
        itemName.setText(tempItemName);

        String tempItemDescription = itemManager.getItemDescription(tradeManager.getItems(trade).get(0));
        TextView itemDescription = itemsDialog.findViewById(R.id.descriptionText);
        itemDescription.setText(tempItemDescription);

        String tempItemRating = "Item Quality Rating: " + itemManager.getItemQuality(tradeManager.getItems(trade).get(0));
        TextView itemRating = itemsDialog.findViewById(R.id.itemRating);
        itemRating.setText(tempItemRating);

        String tempOwner = "Owned by: " + itemManager.getItemOwner(tradeManager.getItems(trade).get(0));
        TextView itemOwner = itemsDialog.findViewById(R.id.ownedBy);
        itemOwner.setText(tempOwner);
    }
    private void setUpTwoItems(){
        String tempItemName = itemManager.getItemName(tradeManager.getItems(trade).get(0));
        TextView itemName=itemsDialog.findViewById(R.id.itemName2);
        itemName.setText(tempItemName);

        String tempItemDescription = itemManager.getItemDescription(tradeManager.getItems(trade).get(0));
        TextView itemDescription = itemsDialog.findViewById(R.id.descriptionText2);
        itemDescription.setText(tempItemDescription);

        String tempItemRating = "Rating: " + itemManager.getItemQuality(tradeManager.getItems(trade).get(0));
        TextView itemRating = itemsDialog.findViewById(R.id.itemRating2);
        itemRating.setText(tempItemRating);

        String tempItemName2 = itemManager.getItemName(tradeManager.getItems(trade).get(1));
        TextView itemName2=itemsDialog.findViewById(R.id.secondItem);
        itemName2.setText(tempItemName2);

        String tempItemDescription2 = itemManager.getItemDescription(tradeManager.getItems(trade).get(1));
        TextView itemDescription2 = itemsDialog.findViewById(R.id.descriptionText3);
        itemDescription2.setText(tempItemDescription2);

        String tempItemRating2 = "Rating: "+itemManager.getItemQuality(tradeManager.getItems(trade).get(1));
        TextView itemRating2 = itemsDialog.findViewById(R.id.itemRating3);
        itemRating2.setText(tempItemRating2);

        String tempOwner = "Owned by: " + itemManager.getItemOwner(tradeManager.getItems(trade).get(0));
        TextView itemOwner = itemsDialog.findViewById(R.id.ownedBy2);
        itemOwner.setText(tempOwner);

        String tempOwner2 = "Owned by: " + itemManager.getItemOwner(tradeManager.getItems(trade).get(1));
        TextView itemOwner2 = itemsDialog.findViewById(R.id.ownedBy3);
        itemOwner2.setText(tempOwner2);
    }

    /**
     * Called when the positive button is clicked.
     */
    @Override
    public void clickPositive() {
    }

    /**
     * Called when the negative button is clicked
     */
    @Override
    public void clickNegative() {
    }

    /**
     * Opens the itemsDialog.
     */
    @Override
    public void openDialog() {
        updateDialogs();
        itemsDialog.show();
    }
}