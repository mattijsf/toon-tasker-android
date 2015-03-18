package com.sqcubes.toontasker;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.sqcubes.toon.api.ToonClient;
import com.sqcubes.toon.api.exception.ToonAuthenticationFailedException;
import com.sqcubes.toon.api.exception.ToonException;
import com.sqcubes.toon.api.exception.ToonLoginFailedException;

import org.apache.http.impl.client.DefaultHttpClient;

import java.math.BigDecimal;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testToonClient() throws ToonException {
        ToonClient client = new ToonClient(new DefaultHttpClient());

        boolean authenticated = client.authenticate("your@email.com", "your-real-password");
        client.setTemperature(19.0f);
        assertTrue(authenticated);
    }
}