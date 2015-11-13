package com.bnavarro.pick5football;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.RoboLayoutInflater;

import static org.junit.Assert.assertEquals;

/**
 * Created by navman on 11/12/2015.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml")
public class MatchDataManagementServiceTest {

    MainActivity mainActivity;

    public void testPopulateMatchMap (){
        mainActivity =  Robolectric.setupActivity(MainActivity.class);
        MatchDataManagementService.populateMatchMap("Week 1", mainActivity, false);

    }
}
