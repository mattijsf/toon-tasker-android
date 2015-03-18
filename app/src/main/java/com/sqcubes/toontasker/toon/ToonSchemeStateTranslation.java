package com.sqcubes.toontasker.toon;

import android.content.Context;

import com.sqcubes.toon.api.model.ToonSchemeState;
import com.sqcubes.toontasker.R;

/**
 * Created by mattijs on 08-02-15.
 */
public class ToonSchemeStateTranslation {

    public static String getTranslatedDescription(Context context, ToonSchemeState schemeState){
        switch (schemeState){
            case AWAY:
                return context.getString(R.string.program_away);
            case HOME:
                return context.getString(R.string.program_home);
            case COMFORT:
                return context.getString(R.string.program_comfort);
            case SLEEP:
                return context.getString(R.string.program_sleep);
            default:
                return context.getString(R.string.program_unknown_error);

        }
    }
}
