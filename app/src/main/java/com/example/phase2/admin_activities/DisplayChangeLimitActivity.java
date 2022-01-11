package com.example.phase2.admin_activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.highabstract.BundleActivity;
import com.example.phase2.users.TraderManager;

/**
 * This activity class displays the screen that allows admin users to type in new limit.
 */
public class DisplayChangeLimitActivity extends BundleActivity {
    private TraderManager tm;
    private int currLimit;
    private TextView displayLimit;

    /**
     * Sets up the class
     * @param savedInstanceState A bundle storing all the necesssary objects
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_change_limit);
        displayLimit = findViewById(R.id.textView7);
        LimitType limitToChange = getLimitToChange();

        tm = (TraderManager) getUseCase(TRADERKEY);

        switch (limitToChange){
            case WEEKLY_LIMIT:
                currLimit = tm.getWeeklyLimit();
                break;
            case MORE_LEND:
                currLimit = tm.getMoreLend();
                break;
            default:
                currLimit = tm.getMaxInComplete();
        }


        updateCurrLimit(displayLimit, currLimit);

    }

    /**
     * This method is called when the user clicks the enter. The method gets the new limit
     * from the EditText and updates it. If the user doesn't type in anything, then it will
     * notify the user that it is invalid.
     * @param view The View object being clicked
     */
    public void enterNewLimit(View view){
        EditText editText = findViewById(R.id.editNewLimit);

        if(editText.getText().toString().equals("")){
            Toast.makeText(this, "Invalid value.", Toast.LENGTH_SHORT).show();
            editText.setText("");
        }else {
            int newLimit = Integer.parseInt(editText.getText().toString());
            LimitType limitToChange = getLimitToChange();
            currLimit = newLimit;

            assert tm != null;
            switch (limitToChange) {
                case WEEKLY_LIMIT:
                    tm.setWeeklyLimit(newLimit);
                    break;
                case MORE_LEND:
                    tm.setMoreLend(newLimit);
                    break;
                default:
                    tm.setMaxInComplete(newLimit);

            }
            editText.setText("");
            updateCurrLimit(displayLimit, currLimit);
            Toast.makeText(this, R.string.successfully_changed_limit, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Called when the user presses the back button. Updates the Trader Manager class
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        replaceUseCase(tm);
    }

    private LimitType getLimitToChange(){
        return (LimitType) getIntent().getSerializableExtra(LimitType.class.getName());
    }

    private void updateCurrLimit(TextView tv, int currLimit) {
        this.currLimit = currLimit;
        String displayLimit = "The current limit is "+ this.currLimit;
        tv.setText(displayLimit);
    }
}