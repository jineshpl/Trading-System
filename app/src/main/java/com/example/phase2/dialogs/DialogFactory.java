package com.example.phase2.dialogs;

import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogFactory {

    /**get the dialog based on the dialogType
     * @param dialogType the type of the dialog that client wants
     * @return the dialog based on the type client inputs
     */
    public AppCompatDialogFragment getDialog(String dialogType){

        switch (dialogType){
            case "Approve":
                return new ApproveDialog();
            case "Freeze":
                return new FreezeDialog();
            case "Undo":
                return new UndoDialog();
            case "Unfreeze":
                return new UnFreezeDialog();
            case"LocationChoice":
                return new LocationChoiceDialog();
            case"TradeType":
                return new TradeType();
            case"Recommend":
                return new RecommendedItemDialog();
            default:
                return new AppCompatDialogFragment();
        }
    }

}
