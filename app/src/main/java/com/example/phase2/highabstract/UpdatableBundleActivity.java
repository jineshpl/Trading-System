package com.example.phase2.highabstract;

import android.content.Intent;

import androidx.annotation.Nullable;

import com.example.phase2.highabstract.BundleActivity;

public abstract class UpdatableBundleActivity extends BundleActivity {

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
        updateUseCases();
    }

    /**
     * Updates the Manager classes in the bundle
     */
    protected abstract void updateUseCases();
}
