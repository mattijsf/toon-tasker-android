package com.sqcubes.toontasker.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.NumberPicker;

import com.sqcubes.toon.api.ToonClient;
import com.sqcubes.toontasker.Intent;
import com.sqcubes.toontasker.R;

import java.util.Arrays;
import java.util.List;

public class ToonEditTemperatureActivity extends Activity {

    private final List<Float> supportedTemperatures = Arrays.asList(ToonClient.SUPPORTED_TEMPERATURES);
    private final String[] supportedTemperatureValues;

    public ToonEditTemperatureActivity() {

        supportedTemperatureValues = new String[supportedTemperatures.size()];
        for (int i = 0; i < supportedTemperatures.size(); i++){
            supportedTemperatureValues[i] = String.valueOf(supportedTemperatures.get(i));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_edit_toon_temperature);

        final Bundle localeBundle = getIntent().getBundleExtra(com.twofortyfouram.locale.Intent.EXTRA_BUNDLE);
        NumberPicker np = (NumberPicker) findViewById(R.id.numberPicker);

        np.setMaxValue(supportedTemperatureValues.length - 1);
        np.setMinValue(0);
        np.setWrapSelectorWheel(false);
        np.setDisplayedValues(supportedTemperatureValues);
        np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setSelectedTemperatureValue(20);

        if (null == savedInstanceState){
            selectTemperatureFromBundle(localeBundle);
        }
    }

    private void selectTemperatureFromBundle(Bundle bundle) {
        if (bundle != null && bundle.containsKey(Intent.BUNDLE_EXTRA_TOONTEMP)){
            float temperature = bundle.getFloat(Intent.BUNDLE_EXTRA_TOONTEMP, 20);
            setSelectedTemperatureValue(temperature);
        }
    }

    public void okButtonClicked(View view) {
        final Bundle resultBundle = new Bundle();
        resultBundle.putFloat(Intent.BUNDLE_EXTRA_TOONTEMP, getSelectedTemperatureValue());

        final android.content.Intent resultIntent = new android.content.Intent();
        resultIntent.putExtra(com.twofortyfouram.locale.Intent.EXTRA_BUNDLE, resultBundle);

        /*
         * The blurb is concise status text to be displayed in the host's UI.
         */
        final String blurb = String.valueOf(getSelectedTemperatureValue() + " °C");
        resultIntent.putExtra(com.twofortyfouram.locale.Intent.EXTRA_STRING_BLURB, blurb);

        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private Float getSelectedTemperatureValue() {
        NumberPicker np = (NumberPicker) findViewById(R.id.numberPicker);
        return supportedTemperatures.get(np.getValue());
    }

    private void setSelectedTemperatureValue(float temperature){
        NumberPicker np = (NumberPicker) findViewById(R.id.numberPicker);
        np.setValue(supportedTemperatures.indexOf(temperature));
    }

    public void cancelButtonClicked(View view) {
        setResult(RESULT_CANCELED, null);
        finish();
    }
}
