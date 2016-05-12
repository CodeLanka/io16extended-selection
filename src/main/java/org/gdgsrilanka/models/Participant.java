package org.gdgsrilanka.models;

import org.gdgsrilanka.select.SelectionWeights;

/**
 * Created by Tharu on 2016-05-03.
 */
public class Participant {

    private String name;
    private String email;

    private int ioParticipations = 0;
    private int rsvpWeight = 0;
    private int eventRating = 0;
    private boolean hasIdeamartApps = false;
    private boolean hasPlayStoreApps = false;


    public Participant(String name, String email, int ioParticipations, boolean ideamart, boolean playStore, int rsvpWeight) {
        this.name = name;
        this.email = email;

        this.ioParticipations = ioParticipations;
        this.hasIdeamartApps = ideamart;
        this.hasPlayStoreApps = playStore;

        this.rsvpWeight = rsvpWeight;
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

        int awardsAfterRSVP = totalAwardedIterations + (int) (rsvpWeight / SelectionWeights.RSVP_NORMALIZATION_WEIGHT);
        if(awardsAfterRSVP < 0) {
            totalAwardedIterations = 0;
        }

        return awardsAfterRSVP;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRsvpWeight() {
        return rsvpWeight;
    }

    public void setRsvpWeight(int rsvpWeight) {
        this.rsvpWeight = rsvpWeight;
    }

    public int getIoParticipations() {
        return ioParticipations;
    }

    public void setIoParticipations(int ioParticipations) {
        this.ioParticipations = ioParticipations;
    }

    public int getEventRating() {
        return eventRating;
    }

    public void setEventRating(int eventRating) {
        this.eventRating = eventRating;
    }

    @Override
    public String toString() {

        String info = String.format("%s [%s] {i: %d %d Star}\nIOs: %d\tIdeamart: %s\tPlaystore: %s\tRSVP=%d",
                name,
                email,
                getEligibleIterations(),
                getEventRating(),
                ioParticipations,
                (hasIdeamartApps) ? "Yes" : "No",
                (hasPlayStoreApps) ? "Yes": "No",
                rsvpWeight);

        return info;
    }
}
