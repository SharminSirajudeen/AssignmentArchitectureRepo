package com.example.assignmentarchitecture;

import dagger.android.AndroidInjector;
import org.junit.Assert;
import org.junit.Test;

public class AppTest {
    App app = new App();

    @Test
    public void testOnCreate() throws Exception {
        app.onCreate();
    }

    @Test
    public void testApplicationInjector() throws Exception {
        AndroidInjector<App> result = app.applicationInjector();
        Assert.assertNotNull(null, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme