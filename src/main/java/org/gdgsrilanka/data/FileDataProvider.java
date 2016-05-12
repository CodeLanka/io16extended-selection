package org.gdgsrilanka.data;

import org.gdgsrilanka.models.Participant;

import java.io.*;

/**
 * Created by Tharu on 2016-05-08.
 */
public class FileDataProvider implements DataProvider {

    BufferedReader reader;

    public FileDataProvider(String fileName) {
        try {
            File f = new File(fileName);
            System.out.println(f.getAbsolutePath());
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Participant getParticipant() {

        try {
            String entry = reader.readLine();

            if (entry != null) {
                String[] data = entry.split("\\|");
                Participant participant = new Participant();
                participant.setName(data[0]);
                participant.setEmail(data[1]);
                participant.setIoParticipations(Integer.parseInt(data[2]));
                participant.setRsvpWeight(Integer.parseInt(data[3]));
                participant.setHasIdeamartApps(data[4].matches("yes"));
                participant.setHasPlayStoreApps(data[5].matches("yes"));

                return participant;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
