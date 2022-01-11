package com.example.phase2.admin_activities;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.phase2.R;
import com.example.phase2.highabstract.BundleActivity;
import com.example.phase2.highabstract.ClickableList;
import com.example.phase2.users.TraderManager;

import java.util.ArrayList;

public class ViewTradersActivity extends BundleActivity implements ClickableList {
    private Dialog dialog;
    private TraderManager traderManager;
    private String userInfo;

    /**
     * Called when the activity is starting.
     * @param savedInstanceState If the activity is being re-initialized after previously being
     * shut down then this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        traderManager = (TraderManager) getUseCase(TRADERKEY);
        setContentView(R.layout.activity_view_traders);
        dialog = new Dialog(this);
        viewList();
    }

    /**
     * Updates the ListView object in the XML file
     */
    public void viewList(){
        final ArrayList<String> traders = traderManager.getTraders();
        ListView listView = findViewById(R.id.traders);
        ArrayAdapter<String> allTraderAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, traders);
        listView.setAdapter(allTraderAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                userInfo = traderManager.getTraderInfo(traders.get(i));
                displayDialog();
            }
        });
    }

    /**
     * Updates the Dialog object
     */
    public void displayDialog() {
        dialog.setContentView(R.layout.fragment_info);
        TextView close = dialog.findViewById(R.id.trader_info);
        close.setText(userInfo);
        dialog.show();
    }
}