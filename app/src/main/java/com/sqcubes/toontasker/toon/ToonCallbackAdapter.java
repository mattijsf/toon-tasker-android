package com.sqcubes.toontasker.toon;

import android.support.annotation.Nullable;

public class ToonCallbackAdapter implements ToonAndroidClient.ToonCallback {
    @Override
    public void onSuccess() {
    }

    @Override
    public void onFailure(boolean hasException, @Nullable Exception e) {
    }
}
