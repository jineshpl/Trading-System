package com.example.phase2.menus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.highabstract.UpdatableBundleActivity;
import com.example.phase2.users.AdminActions;
import com.example.phase2.users.TraderManager;

import java.io.IOException;

public class LoginActivity extends UpdatableBundleActivity {
    private AdminActions adminActions;
    private TraderManager traderManager;

    /**
     * Called when the activity is starting.
     * @param savedInstanceState If the activity is being re-initialized after previously being
     * shut down then this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateUseCases();
        setContentView(R.layout.activity_login);
    }

    /**
     * Called when the login button is clicked.
     * @param view The View Object that is clicked
     */
    public void onLoginClicked(View view){
        EditText userEditText = findViewById(R.id.editTextTextPersonName);
        EditText passEditText = findViewById(R.id.editTextTextPassword);
        String username = userEditText.getText().toString();
        String password = passEditText.getText().toString();

        if (traderManager.login(username, password)){
            Intent intent = new Intent(this, TraderActivity.class);
            replaceUsername(username);
            putBundle(intent);
            startActivityForResult(intent, RESULT_FIRST_USER);
        }

        else if (adminActions.login(username, password)){
            Intent intent = new Intent(this, AdminActivity.class);
            replaceUsername(username);
            putBundle(intent);
            startActivityForResult(intent, RESULT_FIRST_USER);
        }
        else{
            Toast.makeText(this, R.string.login_error, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Called when the Signup button is clicked
     * @param view The View object that is clicked
     */
    public void onSignupClicked(View view){
        Intent intent = new Intent(this, SignupActivity.class);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

    /**
     * Called when the Tutorial button is clicked. It starts TutorialActivity.
     * @param view The View object clicked
     */
    public void onTutorialClicked(View view){
        Intent intent = new Intent(this, TutorialActivity.class);
        putBundle(intent);
        startActivity(intent);
    }

    /**
     * Called when the activity has detected the user's press of the back key.
     */
    @Override
    public void onBackPressed(){}

    /**
     * Updates the Manager classes in the bundle
     */
    @Override
    protected void updateUseCases() {
        traderManager = (TraderManager) getUseCase(TRADERKEY);
        adminActions = (AdminActions) getUseCase(ADMINKEY);
        try {
            saveBundle();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
