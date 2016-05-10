package org.gdgsrilanka.select.ones;

import org.gdgsrilanka.models.Participant;
import org.gdgsrilanka.select.SelectionEngine;
import org.gdgsrilanka.select.prime.Generator;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Tharu on 2016-05-10.
 */
public class SelectionEngineOnesCountImpl implements SelectionEngine {

    private ThreadPoolExecutor executor;
    private List<Participant> selectedList = new CopyOnWriteArrayList<Participant>();
    private boolean isComplete = false;

    public void processList(List<Participant> participantList) {
        executor = new ThreadPoolExecutor(100, 300, 10, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(4000));

        for (Participant p : participantList) {
            executor.execute(new ParticipantSelector(p));
        }
    }

    public boolean isProcessingComplete() {
        return !(executor.getActiveCount() > 0);
    }

    public List<Participant> getSelectedList() {
        return selectedList;
    }

    private class ParticipantSelector implements Runnable {
        private Participant participant;
        private boolean isSelected = false;

        public ParticipantSelector(Participant participant) {
            this.participant = participant;
        }

        public void run() {
            int awardedIterations = participant.getEligibleIterations();

            Generator genny = new Generator();
            BigInteger nameHash = genny.getHashIntegerValueForString(participant.getName());

            int primedIterations = 0;

            for (int i = 0; i < awardedIterations; i++) {
                BigInteger timeHash = genny.getHashIntegerValueForString(System.currentTimeMillis() + "");
                BigInteger currentValue = genny.getXOR(nameHash, timeHash);
                //now check whether the number of 1's in this hash divides the value of the hash.
                int onesCount = 0;
                for (int j = 0; j < currentValue.bitLength(); j++) {
                    if (currentValue.testBit(j)) {
                        onesCount++;
                    }
                }

                BigInteger remainder = currentValue.remainder(new BigInteger(onesCount + ""));
                if (remainder.isProbablePrime(5)) {
                    /*isSelected = true;
                    System.out.println(participant.getName() + " just got selected");
                    selectedList.add(participant);
                    break;*/
                    primedIterations++;
                }


            }
            System.out.println(participant.getName() + " primed "+ primedIterations + " from "+ awardedIterations);
            if (primedIterations > (awardedIterations / 4)) {
                System.out.println(participant.getName() + " just got selected");
                selectedList.add(participant);

            } else {
                System.out.println(String.format("%s is not selected after %d tries",
                        this.participant.getName(),
                        awardedIterations));
            }
        }
    }

}
