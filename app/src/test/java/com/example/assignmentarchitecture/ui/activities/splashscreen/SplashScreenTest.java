package com.example.assignmentarchitecture.ui.activities.splashscreen;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.ViewModelStore;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v4.app.FragmentController;
import android.support.v4.app.SupportActivity;
import android.support.v4.util.SimpleArrayMap;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.app.AppCompatDelegate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

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