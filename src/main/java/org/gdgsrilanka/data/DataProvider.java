package org.gdgsrilanka.data;

import org.gdgsrilanka.models.Participant;

/**
 * Created by Tharu on 2016-05-08.
 */
public interface DataProvider {

    /**
     * Gets a {@link Participant} every time this function is called.
     * @return Participant if any more are available, null if not
     */
    public Participant getParticipant();
}
