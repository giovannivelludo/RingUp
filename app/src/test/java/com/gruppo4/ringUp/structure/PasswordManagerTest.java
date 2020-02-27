package com.gruppo4.ringUp.structure;

import android.content.Context;

import org.easymock.EasyMock;
import org.easymock.Mock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import it.lucacrema.preferences.PreferencesManager;

import static com.gruppo4.ringUp.structure.PasswordManager.PREFERENCES_PASSWORD_KEY;
import static org.easymock.EasyMock.expect;

/**
 * @author Alberto Ursino
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(PreferencesManager.class)
public class PasswordManagerTest {

    @Mock
    private Context mockedContext;

    private static final String PASSWORD = "ciao";
    private static final String SHOULD_NOT = "It should not have thrown an exception";


    @Before
    public void init() {
        mockedContext = EasyMock.mock(Context.class);
        PowerMock.mockStatic(PreferencesManager.class);
    }

    @Test
    public void setPassword() {
        //TODO: mock getSalt() and make it return a predetermined value, then check if setString
        // is passed the hash of (password + salt)
        expect(PreferencesManager.setString(mockedContext, PREFERENCES_PASSWORD_KEY, PASSWORD)).andReturn(true);
        PowerMock.replay(PreferencesManager.class);
        try {
            PasswordManager.setPassword(mockedContext, PASSWORD);
        } catch (Exception e) {
            Assert.fail(SHOULD_NOT);
        }
        PowerMock.verify(PreferencesManager.class);
    }

    @Test
    public void isPassSaved() {
        expect(PreferencesManager.getString(mockedContext, PREFERENCES_PASSWORD_KEY)).andReturn(PASSWORD);
        PowerMock.replay(PreferencesManager.class);
        Assert.assertTrue(PasswordManager.isPassSaved(mockedContext));
        PowerMock.verify(PreferencesManager.class);
    }

}