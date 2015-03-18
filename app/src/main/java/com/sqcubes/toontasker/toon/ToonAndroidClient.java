package com.sqcubes.toontasker.toon;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.sqcubes.toon.api.ToonClient;
import com.sqcubes.toon.api.exception.ToonException;
import com.sqcubes.toon.api.exception.ToonLoginFailedException;
import com.sqcubes.toon.api.model.ToonSchemeState;

import org.apache.http.impl.client.DefaultHttpClient;

import java.math.BigDecimal;

public class ToonAndroidClient extends ToonClient {
    public ToonAndroidClient(Context context) {
        super(new DefaultHttpClient(), new ToonSharedPreferencePersistenceHandler(context));
    }

    public void setSchemeStateAsync(ToonSchemeState state, @Nullable final ToonCallback callback) {
        new ToonSetSchemeStateTask(callback).execute(state);
    }

    public void setTemperatureAsync(float temperatureInCelsius, @Nullable final ToonCallback callback) {
        new ToonSetTemperatureTask(callback).execute(temperatureInCelsius);
    }

    public interface ToonCallback{
        void onSuccess();
        void onFailure(boolean hasException, @Nullable Exception e);
    }
    private class ToonSetSchemeStateTask extends ToonAsyncTask<ToonSchemeState> {
        public ToonSetSchemeStateTask(ToonCallback callback) {
            super(callback);
        }

        @Override
        protected Boolean doInBackground(ToonSchemeState... schemeStates) {
            try {
                return ToonAndroidClient.this.setSchemeState(schemeStates[0]);
            } catch (ToonException e) {
                this.error = e;
            }

            return false;
        }
    }

    private class ToonSetTemperatureTask extends ToonAsyncTask<Float> {
        public ToonSetTemperatureTask(ToonCallback callback) {
            super(callback);
        }

        @Override
        protected Boolean doInBackground(Float... temperatures) {
            try {
                return ToonAndroidClient.this.setTemperature(temperatures[0]);
            } catch (ToonException e) {
                this.error = e;
            }

            return false;
        }
    }

    private abstract class ToonAsyncTask <T> extends AsyncTask<T, Void, Boolean> {

        private final ToonCallback callback;
        public ToonException error;

        public ToonAsyncTask(@Nullable ToonCallback callback) {
            this.callback = callback;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success && callback != null){
                callback.onSuccess();
            }
            else if (callback != null){
                callback.onFailure(error != null, error);
            }

        }
    }
}
