package com.example.phase2.admin_activities;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.phase2.highabstract.ClickableList;
import com.example.phase2.highabstract.Dialogable;
import com.example.phase2.R;
import com.example.phase2.dialogs.DialogFactory;
import com.example.phase2.highabstract.BundleActivity;
import com.example.phase2.users.AdminActions;

import java.util.ArrayList;


public class ApproveAdminActivity extends BundleActivity implements ClickableList, Dialogable {
    private AdminActions adminActions;
    private boolean approved;
    private String approvedUser;

    /**
     * Called when the activity is starting.
     * @param savedInstanceState If the activity is being re-initialized after previously being
     * shut down then this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_admin);
        adminActions = (AdminActions) getUseCase(ADMINKEY);
        viewList();
    }

    /**
     * Called when the activity has detected the user's press of the back key.
     */
    @Override
    public void onBackPressed(){
        replaceUseCase(adminActions);
        super.onBackPressed();
    }

    /**
     * Updates the ListView object in the XML file
     */
    public void viewList(){
        final ArrayList<String> adminRequests = adminActions.getRequestedAdmins();
        setContentView(R.layout.activity_approve_admin);
        ListView listView= findViewById(R.id.requested_admins);
        ArrayAdapter<String> adminAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, adminRequests);
        listView.setAdapter(adminAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openDialog();
                approvedUser = adminRequests.get(i);
            }
        });
    }

    /**
     * Called when the positive button is clicked
     */
    @Override
    public void clickPositive() {
        approved = false;
        approveReject();

    }

    /**
     * Called when the negative button is clicked
     */
    @Override
    public void clickNegative() {
        approved = true;
        approveReject();

    }

    /**
     * Creates the Dialog object for this activity
     */
    @Override
    public void openDialog() {
        DialogFactory dialogFactory = new DialogFactory();
        dialogFactory.getDialog("Approve")
                .show(getSupportFragmentManager(), "ApproveAdmin");
    }

    private void approveReject(){
        adminActions.approveAdmin(approvedUser, approved);
        String message = "Successfully ";
        if (approved){
            message += "approved!";
        }
        else{
            message += "denied!";
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        viewList();
    }
}