package org.gdgsrilanka.data;

import org.gdgsrilanka.models.Participant;

import java.io.*;

/**
 * Created by Tharu on 2016-05-11.
 */
public class HybridDataTypesDataProvider implements DataProvider {

    private BufferedReader reader;

    public HybridDataTypesDataProvider(String fileName) {
        try {
            File f = new File(fileName);
            System.out.println(f.getAbsolutePath());

            reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Participant getParticipant() {
        return null;
    }

}
