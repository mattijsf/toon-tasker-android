package com.sqcubes.toontasker.toon;

import android.content.Context;
import android.content.SharedPreferences;

import com.sqcubes.toon.api.persistence.ToonPersistenceHandler;

import java.io.Serializable;

public class ToonSharedPreferencePersistenceHandler implements ToonPersistenceHandler {

    final SharedPreferences prefs;

    public ToonSharedPreferencePersistenceHandler(Context context) {
        prefs = sharedPreferences(context);
    }

    @Override
    public void persistKeyValuePair(String key, Serializable serializable) {
        throw new IllegalStateException("Not yet implemented");
    }

    @Override
    public void persistKeyValuePair(String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    @Override
    public String getPersistedKeyValue(String key) {
        return prefs.getString(key, null);
    }

    @Override
    public <T extends Serializable> T getPersistedKeyValue(String key, Class<T> tClass) {
        throw new IllegalStateException("Not yet implemented");
    }

    private static SharedPreferences sharedPreferences(Context context) {
        return context.getSharedPreferences(ToonSharedPreferencePersistenceHandler.class.getSimpleName(), Context.MODE_MULTI_PROCESS);
    }
}
