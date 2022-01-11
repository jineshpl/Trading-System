package com.example.phase2.admin_activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.items.ItemManager;
import com.example.phase2.dialogs.DialogFactory;
import com.example.phase2.highabstract.BundleActivity;
import com.example.phase2.highabstract.ClickableList;
import com.example.phase2.highabstract.Dialogable;
import com.example.phase2.users.TraderManager;

import java.util.List;

public class RequestedUnfrozenMenu extends BundleActivity implements ClickableList, Dialogable {
    private TraderManager traderManager;
    private ItemManager itemManager;
    private String unfreezeRequest;

    /**
     * Called when the activity is starting.
     * @param savedInstanceState If the activity is being re-initialized after previously being
     * shut down then this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        traderManager = (TraderManager) getUseCase(TRADERKEY);
        itemManager = (ItemManager) getUseCase(ITEMKEY);

        viewList();
    }

    /**
     * Called when the activity has detected the user's press of the back key.
     */
    @Override
    public void onBackPressed() {
        replaceUseCase(traderManager);
        super.onBackPressed();
    }

    /**
     * Updates the ListView object in the XML file
     */
    public void viewList(){
        final List<String> allUnfreezeRequests = traderManager.getAllRequestsToUnfreeze();
        setContentView(R.layout.activity_requested_unfrozen_menu);
        ListView listView = findViewById(R.id.unfreeze);
        ArrayAdapter<String> allTraderAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, allUnfreezeRequests);
        listView.setAdapter(allTraderAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openDialog();
                unfreezeRequest = allUnfreezeRequests.get(i);
            }
        });
    }

    /**
     * Called when the positive button is clicked.
     */
    @Override
    public void clickPositive() {
        if(traderManager.unfreezeAccount(unfreezeRequest)){
            itemManager.setStatusForRegularUser(unfreezeRequest);
            Toast.makeText(this, "Successfully", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this,
                    "Fail: the account is already unfrozen", Toast.LENGTH_SHORT).show();
        }
        viewList();

    }

    /**
     * Called when the negative button is clicked
     */
    @Override
    public void clickNegative() {
        Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
        viewList();

    }

    /**
     * Opens the itemsDialog.
     */
    @Override
    public void openDialog() {
        DialogFactory dialogFactory = new DialogFactory();
        dialogFactory.getDialog("Unfreeze")
                .show(getSupportFragmentManager(), "Unfreeze");

    }
}