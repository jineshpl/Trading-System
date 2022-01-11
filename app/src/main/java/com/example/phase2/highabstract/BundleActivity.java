package com.example.phase2.highabstract;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;


public abstract class BundleActivity extends AppCompatActivity {
    private Bundle bundle;
    protected final String ADMINKEY = "AdminActions";
    protected final String ITEMKEY = "ItemManager";
    protected final String TRADERKEY = "TraderManager";
    protected final String TRADEKEY = "TradeManager";
    protected final String MEETINGKEY = "MeetingManager";
    protected final String USERNAMEKEY = "Username";

    private final List<String> strings = Arrays.asList(ADMINKEY, ITEMKEY, TRADEKEY, TRADERKEY,
            MEETINGKEY, USERNAMEKEY);

    /**
     * Called when the activity is starting.
     * @param savedInstanceState If the activity is being re-initialized after previously being
     * shut down then this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = filterExtras();
    }

    /**
     * Called when an activity you launched exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     * @param requestCode The integer request code originally supplied to startActivityForResult(),
     * allowing you to identify who this result came from.
     * @param resultCode The integer result code returned by the child activity through its
     * setResult().
     * @param data An Intent, which can return result data to the caller (various data can be
     * attached to Intent "extras").
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        bundle = data.getExtras();
    }

    /**
     * Called when the activity has detected the user's press of the back key.
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_FIRST_USER, intent);
        finish();
    }

    /**
     * Puts the extras in the bundle into the intent
     * @param intent the Intent that requires extras from the bundle
     */
    protected void putBundle(Intent intent){
        intent.putExtras(bundle);
    }

    /**
     * Replaces the Manager class in the bundle
     * @param manager the Manager that needs to be replaced
     */
    protected void replaceUseCase(Manager manager){
        bundle.putSerializable(manager.getIdentifier(), manager);
    }

    /**
     * Replaces the username in the bundle
     * @param username the new username
     */
    protected void replaceUsername(String username){
        bundle.putString(USERNAMEKEY, username);
    }

    /**
     * Getter for the Manager class as a Serializable
     * @param key the key for the Serializable object
     * @return the Serializable object
     */
    protected Serializable getUseCase(String key){
        return bundle.getSerializable(key);
    }

    /**
     * Getter for the String username
     * @return A string representing the username of the user
     */
    protected String getUsername(){
        return bundle.getString(USERNAMEKEY);
    }


    private Bundle filterExtras(){
        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            ConfigGateway configGateway = new ConfigGateway(getApplicationContext().getFilesDir());
            try {
                bundle = configGateway.getBundle();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        else {
            for (String key : bundle.keySet()) {
                if (!strings.contains(key)) {
                    bundle.remove(key);
                }
            }
        }
        return bundle;
    }

    /**
     * Saves the bundle of use cases
     * @throws IOException from the saveBundle method in ConfigGateway
     */
    protected void saveBundle() throws IOException {
        ConfigGateway configGateway = new ConfigGateway(getApplicationContext().getFilesDir());
        configGateway.saveBundle(bundle);
    }

}
