package com.example.phase2.admin_activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.highabstract.BundleActivity;
import com.example.phase2.users.AdminActions;

/**
 * An activity class responsible for changing admin user's password
 */
public class ChangePasswordActivity extends BundleActivity {
    private AdminActions adminActions;
    private String currentAdmin;

    /**
     * Sets up the activity
     * @param savedInstanceState A bundle that has all the necessary objects
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminActions = (AdminActions) getUseCase(ADMINKEY);
        currentAdmin = getUsername();
        setContentView(R.layout.activity_change_password);
    }

    /**
     * Gets the new password from the EditText and updates the current admin's password.
     * @param view The View object beign clicked on
     */
    public void submitPassword(View view){
        EditText et = findViewById(R.id.enterPassword);
        if(et.getText().toString().equals("")){
            Toast.makeText(this, R.string.invalid_password, Toast.LENGTH_SHORT).show();
        }else {
            assert adminActions != null;
            adminActions.changePassword(currentAdmin, et.getText().toString());
            Toast.makeText(this, R.string.successfully_changed_password, Toast.LENGTH_SHORT).show();
        }
        et.setText("");
    }

    /**
     * Called when the user presses the back button
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        replaceUseCase(adminActions);
    }
}