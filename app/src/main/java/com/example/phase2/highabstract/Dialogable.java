package com.example.phase2.highabstract;

public interface Dialogable {

    /**
     * Listener for a positive button click
     */
    void clickPositive();

    /**
     * Listener for a negative button click
     */
    void clickNegative();

    /**
     * Opens the Dialog
     */
    void openDialog();
}
