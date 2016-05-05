package org.gdgsrilanka.org.gdgsrilanka.models;

import org.gdgsrilanka.select.SelectionWeights;

/**
 * Created by Tharu on 2016-05-03.
 */
public class Participant {

    private String name;
    private String nic;

    private int ioParticipations = 0;

    private boolean hasIdeamartApps = false;
    private boolean hasPlayStoreApps = false;


    public Participant(String name, String nic, int ioParticipations, boolean ideamart, boolean playStore) {
        this.name = name;
        this.nic = nic;

        this.ioParticipations = ioParticipations;
        this.hasIdeamartApps = ideamart;
        this.hasPlayStoreApps = playStore;
    }

    public Participant() {

    }

    public int getEligibleIterations() {
        int totalAwardedIterations = SelectionWeights.GENERAL_ITERATION_AWARD;

        totalAwardedIterations += ioParticipations * SelectionWeights.PER_IO_AWARD;

        if (hasIdeamartApps) {
            totalAwardedIterations += SelectionWeights.IDEAMART_AWARD;
        }

        if (hasPlayStoreApps) {
            totalAwardedIterations += SelectionWeights.PLAYSTORE_AWARD;
        }

        return totalAwardedIterations;
    }


    public boolean hasIdeamartApps() {
        return hasIdeamartApps;
    }

    public void setHasIdeamartApps(boolean hasIdeamartApps) {
        this.hasIdeamartApps = hasIdeamartApps;
    }

    public boolean hasPlayStoreApps() {
        return hasPlayStoreApps;
    }

    public void setHasPlayStoreApps(boolean hasPlayStoreApps) {
        this.hasPlayStoreApps = hasPlayStoreApps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public int getIoParticipations() {
        return ioParticipations;
    }

    public void setIoParticipations(int ioParticipations) {
        this.ioParticipations = ioParticipations;
    }
}
