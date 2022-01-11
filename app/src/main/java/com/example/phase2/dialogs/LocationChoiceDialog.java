package com.example.phase2.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.phase2.highabstract.Dialogable;
import com.example.phase2.R;
/*
Code modified from https://developer.android.com/guide/topics/ui/dialogs#PassingEvents
 */

public class LocationChoiceDialog extends AppCompatDialogFragment {
    private Dialogable dialogable;


    /**attach an activity's context to this fragment
     * @param context the context of the attached activity
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dialogable = (Dialogable) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement Dialogable");
        }
    }

    /**create the dialog for this fragment
     * @param savedInstanceState the bundle from the activity
     * @return the dialog attached to this fragment
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("LocationChoice")
                .setMessage(R.string.search_home_city)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogable.clickPositive();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogable.clickNegative();
                    }
                });

        return builder.create();
    }
}
