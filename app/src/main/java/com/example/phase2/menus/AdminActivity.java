package com.example.phase2.menus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phase2.admin_activities.ApproveAdminActivity;
import com.example.phase2.admin_activities.ApproveItems;
import com.example.phase2.admin_activities.ChangeLimitActivity;
import com.example.phase2.admin_activities.ChangePasswordActivity;
import com.example.phase2.admin_activities.ManageFrozenAccount;
import com.example.phase2.admin_activities.ViewTradersActivity;
import com.example.phase2.R;
import com.example.phase2.highabstract.BundleActivity;
import com.example.phase2.undo_activities.UndoActivity;
import com.example.phase2.users.TraderManager;

public class AdminActivity extends BundleActivity {

    /**
     * Called when the activity is starting.
     * @param savedInstanceState If the activity is being re-initialized after previously being
     * shut down then this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String currentAdmin = getUsername();
        setContentView(R.layout.activity_admin);

        TextView textView = findViewById(R.id.textView14);
        textView.setText(currentAdmin);
    }

    /**
     * Method that listens for a click on Log Out and sends the user to the LoginActivity
     * @param view the View object that is being clicked
     */
    public void onLogoutClicked(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        putBundle(intent);
        startActivity(intent);
        finish();
    }

    /**
     * Listens for a click on the Add/Remove Admin button and sends the user to the
     * ApproveAdminActivity activity
     * @param view the View object that is clicked
     */
    public void addRemoveAdmin(View view){
        Intent intent = new Intent(this, ApproveAdminActivity.class);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

    /**
     * Listens for a click on the Manage Accounts button and sends the user to the
     * ManageFrozenAccount activity
     * @param view the View object that is clicked
     */
    public void manageAccounts(View view){
        Intent intent = new Intent(this, ManageFrozenAccount.class);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

    /**
     * Listens for a click on the Add/Remove Items button and sends the user to the ApproveItems
     * activity
     * @param view the View object being clicked
     */
    public void addRemoveItems(View view){
        Intent intent = new Intent(this, ApproveItems.class);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

    /**
     * Listens for a click on the View Status button and sends the user to the ViewTraderActivity
     * activity
     * @param view the View object being clicked
     */
    public void viewStatus(View view){
        TraderManager traderManager = (TraderManager) getUseCase(TRADERKEY);
        Intent intent = new Intent(this, ViewTradersActivity.class);
        intent.putExtra(TRADERKEY, traderManager);
        startActivity(intent);
    }

    /**
     * Listens for a click on the Change Limits button and sends the user to the ChangeLimitActivity
     * activity
     * @param view the View object being clicked
     */
    public void changeLimits(View view){
        Intent i = new Intent(this, ChangeLimitActivity.class);
        putBundle(i);
        startActivityForResult(i, RESULT_FIRST_USER);
    }

    /**
     * Listens for a click on the Change Password button and sends the user to the ChangePassword
     * activity
     * @param view the View object being clicked
     */
    public void changePassword(View view){
        Intent i =  new Intent(this, ChangePasswordActivity.class);
        putBundle(i);
        startActivityForResult(i, RESULT_FIRST_USER);
    }

    /**
     * Listens for a click on the Undo Menu button and sends the user to the UndoActivity activity
     * @param view the View object being clicked
     */
    public void undoMenu(View view) {
        Intent i = new Intent(this, UndoActivity.class);
        putBundle(i);
        startActivityForResult(i, RESULT_FIRST_USER);
    }

    /**
     * Listens for a back button press and notifies the user that they've reached the menu for Admins
     */
    @Override
    public void onBackPressed() {
        Toast.makeText(this,
                "You have reached the main menu!", Toast.LENGTH_SHORT).show();
    }
}
