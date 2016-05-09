package org.gdgsrilanka.test.data;

import org.gdgsrilanka.data.DataProvider;
import org.gdgsrilanka.data.FileDataProvider;
import org.gdgsrilanka.models.Participant;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Tharu on 2016-05-08.
 */
public class DataProviderTest {

    @Test
    public void testProvider() {
        DataProvider provider = new FileDataProvider("resources/test-files/Data.txt");

        int count = 0;
        Participant participant = null;
        while ((participant = provider.getParticipant()) != null) {
            count += 1;
            System.out.println(participant.toString());
        }

        Assert.assertTrue(count > 0);
    }
}
