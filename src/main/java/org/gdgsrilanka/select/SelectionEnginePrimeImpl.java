package org.gdgsrilanka.select;

import org.gdgsrilanka.RandomSelection;
import org.gdgsrilanka.org.gdgsrilanka.models.Participant;
import org.gdgsrilanka.select.prime.Generator;
import org.gdgsrilanka.select.prime.PrimeCheck;
import sun.security.jgss.wrapper.GSSNameElement;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Tharu on 2016-05-03.
 */
public class SelectionEnginePrimeImpl implements SelectionEngine {

    private List<Participant> participants;
    private List<Participant> selectedList;
    private boolean processingDone = false;

    public SelectionEnginePrimeImpl(List<Participant> participants) {
        this.participants = participants;
        this.selectedList = new ArrayList<Participant>();  //FIXME I am not thread safe
    }

    public List<Participant> processList(List<Participant> participantList) {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 300, 10, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(4000));
        for (Participant participant : participantList) {
            ParticipantSelector selector = new ParticipantSelector(participant);
            executor.execute(selector);
        }
        return null;
    }

    public boolean isProcessingComplete() {
        return false;
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

            for(int i=0;i < awardedIterations;i++) {
                BigInteger timeHash = genny.getHashIntegerValueForString(System.currentTimeMillis() + "");
                BigInteger currentValue = genny.getXOR(nameHash, timeHash);
                if(genny.isPrime(currentValue)) {
                    selectParticipant();
                    isSelected = true;
                    break;
                }
            }

            if (!isSelected) {
                System.out.println(String.format("%s is not selected after %d tries",
                        this.participant.getName(),
                        awardedIterations));
            }

        }

        private void selectParticipant() {
            selectedList.add(this.participant);
        }

    }
}

