package com.example.phase2.undo_activities;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.example.phase2.highabstract.ClickableList;
import com.example.phase2.dialogs.DialogFactory;
import com.example.phase2.highabstract.Dialogable;
import com.example.phase2.items.ItemManager;
import com.example.phase2.R;
import com.example.phase2.highabstract.BundleActivity;
import com.example.phase2.users.TraderManager;

import java.util.ArrayList;
import java.util.List;

public class UndoRemoveItem extends BundleActivity implements Dialogable, ClickableList {
    private ItemManager itemManager;
    private TraderManager traderManager;
    private String chosenTrader;
    private Integer chosenItem;

    /**create this activity
     * @param savedInstanceState the bundle from the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chosenTrader = getIntent().getStringExtra("chosenTrader");
        itemManager = (ItemManager) getUseCase(ITEMKEY);
        traderManager = (TraderManager) getUseCase(TRADERKEY);
        isValid();
        
    }

    /**
     * listener for the back button, return to the last menu
     */
    @Override
    public void onBackPressed() {
        replaceUseCase(itemManager);
        super.onBackPressed();
    }

    /**
     * This is a method that displays list of items that the chosenTrader has removed. When the user
     * clicks on an item, the user can add the item back into the system.
     */
    public void viewList() {
        final List<Integer> removedItems = itemManager.getRemovedItemIds(chosenTrader);
        ArrayList<String> removePresenter = new ArrayList<>();
        setContentView(R.layout.activity_undo_remove_item);
        ListView listView = findViewById(R.id.removedItem);
        for(Integer i: removedItems){
            removePresenter.add(itemManager.getItemInString(i));
        }
        ArrayAdapter<String> removeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, removePresenter);
        listView.setAdapter(removeAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openDialog();
                chosenItem = removedItems.get(i);
            }
        });
    }

    private void isValid(){
        if(traderManager.getIsFrozen(chosenTrader) || traderManager.isInactive(chosenTrader)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Alert")
                    .setMessage("The account is either frozen or inactive")
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            onBackPressed();
                        }
                    });
            builder.show();
        }else{
            viewList();
        }
    }



    /**
     * Listener for the positive button, undoRemoveItems
     */
    @Override
    public void clickPositive() {
        itemManager.undoRemoval(chosenItem);
        Toast.makeText(this,
                "Successfully undo remove", Toast.LENGTH_SHORT).show();
        viewList();

    }

    /**
     * Listener for the negative button, cancel the action
     */
    @Override
    public void clickNegative() {
        Toast.makeText(this,
                "Cancelled", Toast.LENGTH_SHORT).show();
        viewList();

    }

    /**
     * open the dialog
     */
    @Override
    public void openDialog() {
        DialogFactory dialogFactory = new DialogFactory();
        dialogFactory.getDialog("Undo")
                .show(getSupportFragmentManager(), "UndoRemove");

    }
}