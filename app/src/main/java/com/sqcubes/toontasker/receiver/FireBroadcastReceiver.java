package com.sqcubes.toontasker.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.sqcubes.toon.api.exception.ToonLoginFailedException;
import com.sqcubes.toon.api.model.ToonSchemeState;
import com.sqcubes.toontasker.Intent;
import com.sqcubes.toontasker.R;
import com.sqcubes.toontasker.notification.NotificationHelper;
import com.sqcubes.toontasker.toon.ToonAndroidClient;
import com.sqcubes.toontasker.toon.ToonCallbackAdapter;

public class FireBroadcastReceiver extends BroadcastReceiver {
    public FireBroadcastReceiver() {
    }

    @Override
    public void onReceive(final Context context, android.content.Intent intent) {
        if (!com.twofortyfouram.locale.Intent.ACTION_FIRE_SETTING.equals(intent.getAction())) {
            return;
        }

        final Bundle bundle = intent.getBundleExtra(com.twofortyfouram.locale.Intent.EXTRA_BUNDLE);
        if (bundle != null) {
            if (bundle.containsKey(Intent.BUNDLE_EXTRA_TOONPROGRAM)) {
                int schemeStateCode = bundle.getInt(Intent.BUNDLE_EXTRA_TOONPROGRAM, ToonSchemeState.HOME.schemeStateCode());
                final ToonSchemeState schemeState = ToonSchemeState.fromSchemeStateCode(schemeStateCode);

                setToonSchemeState(context, schemeState);
            }
            else if (bundle.containsKey(Intent.BUNDLE_EXTRA_TOONTEMP)) {
                float temperature = bundle.getFloat(Intent.BUNDLE_EXTRA_TOONTEMP, 20.0f);
                setToonTemperature(context, temperature);
            }
        }
    }

    private void setToonTemperature(final Context context, final float temperature) {
        ToonAndroidClient toon = new ToonAndroidClient(context);
        if (!toon.hasCredentials()){
            NotificationHelper.notifyForLoginRequired(context);
        }
        else {
            toon.setTemperatureAsync(temperature, new ToonCallbackAdapter(){
                @Override
                public void onSuccess() {
                    StringBuilder sb = new StringBuilder();
                    sb.append(context.getString(R.string.toast_message_toon_temperature_set)).append(": ");
                    sb.append(temperature).append(" °C");
                    Toast.makeText(context, sb, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(boolean hasException, @Nullable Exception e) {
                    if (hasException && e instanceof ToonLoginFailedException) {
                        NotificationHelper.notifyForLoginFailed(context, (ToonLoginFailedException) e);
                    }
                }
            });
        }
    }

    private void setToonSchemeState(final Context context, final ToonSchemeState schemeState) {
        ToonAndroidClient toon = new ToonAndroidClient(context);
        if (!toon.hasCredentials()){
            NotificationHelper.notifyForLoginRequired(context);
        }
        else{
            toon.setSchemeStateAsync(schemeState, new ToonCallbackAdapter() {
                @Override
                public void onSuccess() {
                    StringBuilder sb = new StringBuilder();
                    sb.append(context.getString(R.string.toast_message_toon_set_to)).append(": ");
                    sb.append(schemeState.name());
                    Toast.makeText(context, sb, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(boolean hasException, @Nullable Exception e) {
                    if (hasException && e instanceof ToonLoginFailedException) {
                        NotificationHelper.notifyForLoginFailed(context, (ToonLoginFailedException) e);
                    }
                }
            });
        }
    }


}
