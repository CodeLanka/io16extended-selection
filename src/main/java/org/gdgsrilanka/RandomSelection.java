package org.gdgsrilanka;

import org.gdgsrilanka.data.DataProvider;
import org.gdgsrilanka.data.FileDataProvider;
import org.gdgsrilanka.models.Participant;
import org.gdgsrilanka.select.SelectionEngine;
import org.gdgsrilanka.select.ones.SelectionEngineOnesCountImpl;
import org.gdgsrilanka.select.prime.SelectionEnginePrimeImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tharu on 2016-05-03.
 */
public class RandomSelection {

    public RandomSelection() {


    }

    public static void main(String[] args) {

        DataProvider provider = new FileDataProvider("resources/test-files/Data.txt");
        List<Participant> participants = new ArrayList<Participant>();

        Participant current = null;
        while((current = provider.getParticipant()) != null) {
            participants.add(current);
        }
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
        List<Participant> selected = engine.getSelectedList();
        for (Participant p : selected) {
            System.out.println(p.toString());
        }

    }
}
