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


/* Using the radio button template from
https://medium.com/@suragch/adding-a-list-to-an-android-alertdialog-e13c1df6cf00*/

public class TradeType  extends AppCompatDialogFragment {
    private Bundle bundle;
    private Dialogable dialogable;
    protected final String[] ways = {"One way", "Two way"};
    protected final String[] status = {"Permanent", "Temporary"};
    protected final String[] meetingPlace = {"Online", "In person"};
    private Boolean isOneWay;
    private Boolean isPermanent;
    private Boolean isOnline;
    private AlertDialog choseLastTime;
    private AlertDialog choseMeetingPlace;

    /**create this dialog
     * @param savedInstanceState the bundle from the activity
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = new Bundle();
        isPermanent = false;
        isOneWay = false;
        isOnline = false;
        choseLastTime = (AlertDialog) createLastTimeDialog();
        choseMeetingPlace = (AlertDialog) createMeetingPlaceDialog();
    }



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
    public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("TradeType")
                .setSingleChoiceItems(ways, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        isOneWay = i == 0;
                        AlertDialog dialog = (AlertDialog) getDialog();
                        assert dialog != null;
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    }
                })
                .setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bundle.putBoolean("isOneWay", isOneWay);
                        dismiss();
                        choseLastTime.show();
                        choseLastTime.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogable.clickNegative();
                    }
                });

            return builder.create();


    }

    private Dialog createLastTimeDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("TradeType")
                .setSingleChoiceItems(status, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        isPermanent = i == 0;
                        choseLastTime.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    }
                })
                .setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bundle.putBoolean("isPermanent", isPermanent);
                        dismiss();
                        choseMeetingPlace.show();
                        choseMeetingPlace.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogable.clickNegative();
                    }
                });

        return builder.create();

    }

    private Dialog createMeetingPlaceDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("TradeType")
                .setSingleChoiceItems(meetingPlace, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        isOnline = i == 0;
                        choseMeetingPlace.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    }
                })
                .setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bundle.putBoolean("isOnline", isOnline);
                        setArguments(bundle);
                        dialogable.clickPositive();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogable.clickNegative();
                    }
                });

        return builder.create();

    }


    /**
     * the action when the fragment is on resume
     */
    @Override
    public void onResume()
    {
        super.onResume();
        AlertDialog dialog = (AlertDialog) getDialog();
        assert dialog != null;
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
    }


}
