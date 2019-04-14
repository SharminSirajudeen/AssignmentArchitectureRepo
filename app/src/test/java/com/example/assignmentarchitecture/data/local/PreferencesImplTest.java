package com.example.assignmentarchitecture.data.local;

import android.content.SharedPreferences;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class PreferencesImplTest {
    @Mock
    SharedPreferences mPrefs;

    //Field gson of type Gson - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    @Mock
    PreferencesImpl preferencesImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testIsLanguageSelected() throws Exception {
        boolean result = preferencesImpl.isLanguageSelected();
        Assert.assertEquals(true, result);
    }

    @Test
    public void testSetLanguageSelected() throws Exception {
        preferencesImpl.setLanguageSelected(true);
    }

    @Test
    public void testClearAllPref() throws Exception {
        preferencesImpl.clearAllPref();
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme