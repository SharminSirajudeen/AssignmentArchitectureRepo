package com.example.assignmentarchitecture.presentation.activities.splashscreen;

import android.support.v4.app.SupportActivity;
import android.support.v4.util.SimpleArrayMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SplashScreenTest {
    @Mock
    SimpleArrayMap<Class<? extends SupportActivity.ExtraData>, SupportActivity.ExtraData> mExtraDataMap;

    @InjectMocks
    SplashScreen splashScreen;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testOnWindowFocusChanged() throws Exception {
        splashScreen.onWindowFocusChanged(true);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme