package org.gdgsrilanka.select;

import org.gdgsrilanka.models.Participant;

import java.util.List;

/**
 * Created by Tharu on 2016-05-03.
 */
public interface SelectionEngine {

    public List<Participant> processList(List<Participant> participantList);
    public boolean isProcessingComplete();
    public List<Participant> getSelectedList();
}
