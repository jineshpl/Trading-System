package com.example.phase2.item_browsing_activities;

import androidx.appcompat.app.AppCompatDialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.dialogs.DialogFactory;
import com.example.phase2.highabstract.BundleActivity;
import com.example.phase2.highabstract.Dialogable;
import com.example.phase2.items.ItemManager;

public class DisplayTradeOptionsActivity extends BundleActivity implements Dialogable {

    private Integer chosenItem;
    private String currentTrader;
    private boolean oneWay;
    private boolean permanent;
    private boolean online;
    private ItemManager itemManager;
    private AppCompatDialogFragment dialogFragment;

    /**create the activity
     * @param savedInstanceState the bundle from the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        currentTrader = getUsername();
        itemManager = (ItemManager) getUseCase(ITEMKEY);
        chosenItem = bundle.getInt("ChosenItem");
        setContentView(R.layout.activity_display_trade_options);
        DialogFactory dialogFactory = new DialogFactory();
        dialogFragment = dialogFactory.getDialog("TradeType");
        openDialog();
    }

    /**
     * Continues to the next activity based on user input.
     */
    private void continuing() {
        Intent intent;
        if (oneWay) {
            intent = new Intent(this, EnterInfoProposeTradeActivity.class);
            intent.putExtra("MyItem", -1);
        }
        else {
            intent = new Intent(this, SelectItemActivity.class);
        }
        intent.putExtra("ChosenItem", chosenItem);
        intent.putExtra("Permanent", permanent);
        intent.putExtra("OneWay", oneWay);
        intent.putExtra("Online", online);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

    /**
     * Listener for positive button, prompt the trader to edit the proposedTrade
     */
    @Override
    public void clickPositive() {
        assert dialogFragment.getArguments() != null;
        oneWay = dialogFragment.getArguments().getBoolean("isOneWay");
        if (itemManager.getApprovedItemsIDs(currentTrader).size() == 0 && !oneWay) {
            Toast.makeText(this, "You have no items, so you " +
                    "must commence a one-way trade.", Toast.LENGTH_LONG).show();
            onBackPressed();
        } else {
            permanent = dialogFragment.getArguments().getBoolean("isPermanent");
            online = dialogFragment.getArguments().getBoolean("isOnline");
            if (!permanent && online) {
                Toast.makeText(this, "All online trades must " +
                        "be permanent.", Toast.LENGTH_LONG).show();
                onBackPressed();
            } else { continuing(); }
        }
    }

    /**
     * Listener for negative button, go back to the last menu
     */
    @Override
    public void clickNegative() {
        onBackPressed();
    }

    /**
     * Opens the Dialog
     */
    @Override
    public void openDialog() {
        dialogFragment.show(getSupportFragmentManager(), "TradeType");
    }
}