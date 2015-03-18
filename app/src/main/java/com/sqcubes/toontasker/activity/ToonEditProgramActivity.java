package com.sqcubes.toontasker.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.sqcubes.toon.api.model.ToonSchemeState;
import com.sqcubes.toontasker.Intent;
import com.sqcubes.toontasker.R;

public class ToonEditProgramActivity extends Activity {
    private final static int defaultProgramButtonId = R.id.rbToonProgramHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_edit_toon_program);


        final Bundle localeBundle = getIntent().getBundleExtra(com.twofortyfouram.locale.Intent.EXTRA_BUNDLE);

        RadioButton defaultButton = (RadioButton) findViewById(defaultProgramButtonId);
        defaultButton.setChecked(true);

        if (null == savedInstanceState){
            selectProgramFromBundle(localeBundle);
        }
    }

    public void okButtonClicked(View view) {
        final Bundle resultBundle = new Bundle();
        resultBundle.putInt(Intent.BUNDLE_EXTRA_TOONPROGRAM, getSelectedToonState().schemeStateCode());

        final android.content.Intent resultIntent = new android.content.Intent();
        resultIntent.putExtra(com.twofortyfouram.locale.Intent.EXTRA_BUNDLE, resultBundle);

        /*
         * The blurb is concise status text to be displayed in the host's UI.
         */
        RadioButton selectedProgramRadioButton = (RadioButton) findViewById(getSelectedProgramRadioButtonId());
        final String blurb = String.valueOf(selectedProgramRadioButton.getText());
        resultIntent.putExtra(com.twofortyfouram.locale.Intent.EXTRA_STRING_BLURB, blurb);

        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void cancelButtonClicked(View view) {
        setResult(RESULT_CANCELED, null);
        finish();
    }

    private void selectProgramFromBundle(Bundle bundle){
        if (bundle != null && bundle.containsKey(Intent.BUNDLE_EXTRA_TOONPROGRAM)){
            // extract the ToonSchemeState from the bundle
            int schemeStateCode = bundle.getInt(Intent.BUNDLE_EXTRA_TOONPROGRAM, ToonSchemeState.HOME.schemeStateCode());
            ToonSchemeState schemeState = ToonSchemeState.fromSchemeStateCode(schemeStateCode);

            // select the corresponding radio button
            int programButtonId = getProgramButtonIdFromToonSchemeState(schemeState);
            RadioButton programButton = (RadioButton) findViewById(programButtonId);
            programButton.setChecked(true);
        }
    }

    private ToonSchemeState toonSchemeStateFromName(String name) {
        for (ToonSchemeState state : ToonSchemeState.values()){
            if (state.name().equals(name)){
                return state;
            }
        }

        throw new IllegalStateException("Could not find ToonSchemeState from name " + name);
    }

    private int getSelectedProgramRadioButtonId(){
        RadioGroup programRadioGroup = (RadioGroup) findViewById(R.id.rgToonProgram);
        return programRadioGroup.getCheckedRadioButtonId();
    }
    private ToonSchemeState getSelectedToonState() {
        return getToonStateFromButton(getSelectedProgramRadioButtonId());
    }

    private ToonSchemeState getToonStateFromButton(int buttonId){
        switch (buttonId){
            case R.id.rbToonProgramHome:
                return ToonSchemeState.HOME;
            case R.id.rbToonProgramAway:
                return ToonSchemeState.AWAY;
            case R.id.rbToonProgramComfort:
                return ToonSchemeState.COMFORT;
            case R.id.rbToonProgramSleep:
                return ToonSchemeState.SLEEP;
        }

        throw new IllegalStateException("Could not find ToonSchemeState for button " + buttonId);
    }

    private int getProgramButtonIdFromToonSchemeState(ToonSchemeState state){
        switch (state){
            case AWAY:
                return R.id.rbToonProgramAway;
            case HOME:
                return R.id.rbToonProgramHome;
            case SLEEP:
                return R.id.rbToonProgramSleep;
            case COMFORT:
                return R.id.rbToonProgramComfort;
        }

        throw new IllegalStateException("Could not find button for ToonSchemeState " + state);
    }
}
