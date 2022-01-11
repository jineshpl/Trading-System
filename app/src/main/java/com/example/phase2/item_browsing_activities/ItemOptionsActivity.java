package com.example.phase2.item_browsing_activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.items.ItemManager;
import com.example.phase2.highabstract.BundleActivity;
import com.example.phase2.users.TraderManager;

import java.time.LocalDate;
import java.util.Objects;

public class ItemOptionsActivity extends BundleActivity {

    private ItemManager itemManager;
    private TraderManager traderManager;
    private String currentTrader;
    private Integer chosenItem;

    /**
     * Called when the activity is starting.
     * @param savedInstanceState If the activity is being re-initialized after previously being
     * shut down then this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chosenItem = Objects.requireNonNull(getIntent().getExtras()).getInt("ChosenItem");
        itemManager = (ItemManager) getUseCase(ITEMKEY);
        traderManager = (TraderManager) getUseCase(TRADERKEY);
        currentTrader = getUsername();
        viewStart();
    }

    /**
     * Sets up the starting functionality of the Activity
     */
    public void viewStart() {
        setContentView(R.layout.activity_item_options);

        String tempItemName = itemManager.getItemName(chosenItem);
        TextView itemName=findViewById(R.id.itemName);
        itemName.setText(tempItemName);

        String tempItemDescription = itemManager.getItemDescription(chosenItem);
        TextView itemDescription = findViewById(R.id.descriptionText);
        itemDescription.setText(tempItemDescription);

        String tempItemRating = "Item Quality Rating: " + itemManager.getItemQuality(chosenItem);
        TextView itemRating = findViewById(R.id.itemRating);
        itemRating.setText(tempItemRating);

        String tempOwner = "Owned by: " + itemManager.getItemOwner(chosenItem);
        TextView itemOwner = findViewById(R.id.ownedBy);
        itemOwner.setText(tempOwner);
    }

    /**
     * Adds item to wishlist after a button click
     * @param view The View object being clicked
     */
    public void addToWishList(View view) {
        if(!traderManager.getWishlistIds(currentTrader).contains(chosenItem)) {
            traderManager.addToWishlist(currentTrader, chosenItem);
            Toast.makeText(this, "Item added to your wishlist!", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "This item is already in your wishlist.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Proposes a Trade after a button click
     * @param view The View object being clicked
     */
    public void proposeTrade(View view) {
        if (canProposeTrade()) {
            displayTradeOptions(view);
        } else if (traderManager.isInactive(currentTrader)) {
            Toast.makeText(this, "Your account is inactive," +
                    " you cannot trade.", Toast.LENGTH_LONG).show();
        } else if (traderManager.exceedWeeklyLimit(currentTrader, LocalDate.now())) {
            Toast.makeText(this, "You have exceeded your " +
                    "weekly trade limit", Toast.LENGTH_SHORT).show();
        } else if (traderManager.needMoreLend(currentTrader)) {
            Toast.makeText(this, "You need to lend more " +
                    "items before you may initiate a new trade.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Your account is frozen," +
                    " you cannot trade.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Helper method for proposeTrade which checks if the currentTrader is allowed to intiate a new
     * trade.
     * @return true iff currentTrader can propose a new trade.
     */
    private boolean canProposeTrade() {
        return !traderManager.getIsFrozen(currentTrader) &&
                !traderManager.isInactive(currentTrader) &&
                !traderManager.exceedWeeklyLimit(currentTrader, LocalDate.now()) &&
                !traderManager.needMoreLend(currentTrader);
    }

    /**
     * Sends the user to the next activity
     * @param view The View object beign clicked
     */
    public void displayTradeOptions(View view) {
        Intent intent = new Intent(this, DisplayTradeOptionsActivity.class);
        intent.putExtra("ChosenItem", chosenItem);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

    /**
     * Listener for negative button, go back to the last menu
     */
    @Override
    public void onBackPressed() {
        replaceUseCase(traderManager);
        super.onBackPressed();
    }
}