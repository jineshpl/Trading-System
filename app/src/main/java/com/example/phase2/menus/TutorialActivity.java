package com.example.phase2.menus;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.phase2.R;
import com.example.phase2.highabstract.BundleActivity;
import com.example.phase2.tutorial_activities.TutorialBrowseItemsActivity;

/**
 * An activity class responsible for providing a tutorial for a trader the Trading System.
 */
public class TutorialActivity extends BundleActivity {

    /**
     * Sets up the activity
     * @param savedInstanceState A bundle storing all the necessary objects
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
    }

    /**
     * Called when the back button is pressed
     */
    @Override
    public void onBackPressed(){
        finish();
    }

    /**
     * Called when browse items button is pressed. It starts TutorialBrowseItemsActivity.
     * @param view A view
     */
    public void onBrowseItems(View view){
        Intent intent = new Intent(this, TutorialBrowseItemsActivity.class);
        putBundle(intent);
        startActivity(intent);
    }

    /**
     * Called when browse trades button is pressed. It uses a dialog to explain what a trader
     * could do using an account.
     * @param view A view
     */
    public void onBrowseTrades(View view){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("You need an account to view or modify your trade  \n ")
                .append("- You could see all your trades' information  \n")
                .append("-You could only edit your trade 3 times before the conformation  \n");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("BrowseTradePreview")
                .setMessage(stringBuilder);
        builder.show();
    }

    /**
     * Called when edit inventory button is pressed. It uses a dialog to explain what a trader
     * could do using an account.
     * @param view A view
     */
    public void onEditInventory(View view){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("You need an account to edit your inventory  \n ")
                .append("- You could view your items' information \n")
                .append("-You could remove items from your inventory \n")
                .append("-You could add items to your inventory \n")
                .append("-items need to be reviewed by an admin");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("InventoryPreview")
                .setMessage(stringBuilder);
        builder.show();
    }

    /**
     * Called when edit wish list button is pressed. It uses a dialog to explain what a trader
     * could do using an account.
     * @param view A view
     */
    public void onEditWishList(View view){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("You need an account to edit your wishList   \n")
                .append("- You could view items in your wish list  \n")
                .append("-You could remove items from your wish list");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("WishListPreview")
                .setMessage(stringBuilder);
        builder.show();

    }

    /**
     * Called when view my user info button is pressed. It uses a dialog to explain what a trader
     * could do using an account.
     * @param view A view
     */
    public void onViewInfo(View view){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("You need an account to view your information \n")
                .append("- You could check your recent trades  \n")
                .append("-You could check traders you frequently have trades with");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ViewInfoPreview")
                .setMessage(stringBuilder);
        builder.show();
    }

    /**
     * Called when request admin button is pressed. It uses a dialog to explain what a trader
     * could do using an account.
     * @param view A view
     */
    public void onRequestUnfreeze(View view){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("You need an account to request to unfreeze your account \n")
                .append("- Only do it when you account gets flagged or frozen");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("UnfreezePreview")
                .setMessage(stringBuilder);
        builder.show();
    }

    /**
     * Called when change password button is pressed. It uses a dialog to explain what a trader
     * could do using an account.
     * @param view A view
     */
    public void onChangePassword(View view){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("You need an account to change your password");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert")
                .setMessage(stringBuilder);
        builder.show();
    }

    /**
     * Called when change home city button is pressed. It uses a dialog to explain what a trader
     * could do using an account.
     * @param view A view
     */
    public void onChangeHomeCity (View view){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("You need an account to change your home city");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change Home City")
                .setMessage(stringBuilder);
        builder.show();
    }
}