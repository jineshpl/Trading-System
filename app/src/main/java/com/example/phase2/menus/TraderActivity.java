package com.example.phase2.menus;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phase2.item_browsing_activities.BrowseItemsActivity;
import com.example.phase2.browse_trades_activities.BrowseTradesActivity;
import com.example.phase2.trader_activities.ChangeHomecity;
import com.example.phase2.trader_activities.ChangeTraderPassword;
import com.example.phase2.trader_activities.EditInventoryActivity;
import com.example.phase2.trader_activities.EditWishlistActivity;
import com.example.phase2.R;
import com.example.phase2.trader_activities.RequestAdminActivity;
import com.example.phase2.trader_activities.ViewMyUserInfoActivity;
import com.example.phase2.highabstract.BundleActivity;

public class TraderActivity extends BundleActivity {

    /**
     * create this activity
     * @param savedInstanceState the bundle from the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String currentTrader = getUsername();
        setContentView(R.layout.activity_trader);
        TextView textView = findViewById(R.id.textView15);
        textView.setText(currentTrader);
    }

    /**
     * This method is called when the user clicks on the Browse Available Items button. It starts
     * the BrowseItemsActivity.
     * @param view A view
     */
    public void browseAvailableItems(View view){
        Intent intent = new Intent(this, BrowseItemsActivity.class);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

    /**
     * This method is called when the user clicks on the Browse My On-Going Trades button. It starts
     * the BrowseTradesActivity.
     * @param view A view
     */
    public void browseOnGoingTrades(View view){
        Intent intent = new Intent(this, BrowseTradesActivity.class);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

    /**
     * This method is called when the user clicks on the Edit Inventory button. It starts
     * the EditInventoryActivity
     * @param view A view
     */
    public void editInventory(View view){
        Intent intent = new Intent(this, EditInventoryActivity.class);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

    /**
     * This method is called when the user clicks on the Edit Wishlist button. It starts
     * the EditWishlistActivity
     * @param view A view
     */
    public void editWishlist(View view){
        Intent intent = new Intent(this, EditWishlistActivity.class);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

    /**
     * This method is called when the user presses the View User Info button. Starts the Request
     * ViewMyUserInfoActivity
     * @param view A view
     */
    public void viewUserInfo(View view){
        Intent intent = new Intent(this, ViewMyUserInfoActivity.class);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }
    /**
     * This method is called when the user presses the Request Admin button. Starts the Request
     * AdminActivity
     * @param view A view
     */
    public void requestAdmin(View view){
        Intent intent =  new Intent(this, RequestAdminActivity.class);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

    /**
     * This method is called when the user presses the Change Password button. Starts the Change
     * TraderPassword Activity
     * @param view A view
     */
    public void changeTraderPassword(View view){
        Intent i =  new Intent(this, ChangeTraderPassword.class);
        putBundle(i);
        startActivityForResult(i, RESULT_FIRST_USER);
    }

    /**
     * This method is called when the user presses the Change Homecity button. Starts the
     * ChangeHomecity Activity
     * @param view A view
     */
    public void changeHomecity(View view){
        Intent i =  new Intent(this, ChangeHomecity.class);
        putBundle(i);
        startActivityForResult(i, RESULT_FIRST_USER);
    }

    /**
     * This method is called when the user clicks the logout button. It lets the user to logout
     * from the system. It also updates the use case classes.
     * @param view A view
     */
    public void onLogoutClicked(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        putBundle(intent);
        startActivity(intent);
        finish();
    }

    /**
     * This is called when the user presses the back button. It forbids user from going back to
     * the previous activity and displays a toast that notifies the user that you cannot go back.
     */
    @Override
    public void onBackPressed() {
        Toast.makeText(this,
                "You have reached the main menu!", Toast.LENGTH_SHORT).show();
    }

}