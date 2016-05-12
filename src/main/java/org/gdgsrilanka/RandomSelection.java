package org.gdgsrilanka;

import org.gdgsrilanka.data.DataProvider;
import org.gdgsrilanka.data.JSONFileDataProvider;
import org.gdgsrilanka.models.Participant;
import org.gdgsrilanka.select.SelectionEngine;
import org.gdgsrilanka.select.ones.SelectionEngineOnesCountImpl;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tharu on 2016-05-03.
 */
public class RandomSelection {

    public RandomSelection() {


    }

    public static void main(String[] args) {

//        DataProvider provider = new FileDataProvider("resources/test-files/Data.txt");
        DataProvider provider = new JSONFileDataProvider("resources/test-files/GoogleIOResponses.json");

        List<Participant> participants = new ArrayList<Participant>();

        Participant current = null;

        /*for (int i = 0; i < 10; i++) {
            current = provider.getParticipant();
            System.out.println(current);
        }*/

        int starred = 0;
        while((current = provider.getParticipant()) != null) {
            //System.out.println(current.toString());
            if (current.getEventRating() != 0) {
                starred++;
            }
            participants.add(current);
        }

        System.out.println("Starred = " + starred);

//        SelectionEngine engine = new SelectionEnginePrimeImpl();
        SelectionEngine engine = new SelectionEngineOnesCountImpl();
        engine.processList(participants);


        while (!engine.isProcessingComplete()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("re-checking completeness");
        }


        System.out.println("selection complete. these are the lucky ones;");
        try {
            PrintWriter writer = new PrintWriter("resources/test-files/Output.txt");
            List<Participant> selected = engine.getSelectedList();
            System.out.println(selected.size() + " people selected out of "+ participants.size());
            for (Participant p : selected) {
                //System.out.println(p.toString());
                writer.println(p.getEmail());
            }
            writer.flush();
            writer.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return;

    }
}
