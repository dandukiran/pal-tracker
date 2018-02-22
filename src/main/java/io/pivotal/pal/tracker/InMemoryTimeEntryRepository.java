package io.pivotal.pal.tracker;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private HashMap<Long, TimeEntry> teRepo = new HashMap<>();

    public TimeEntry create(TimeEntry timeEntry) {

        timeEntry.setId(teRepo.size()+1);
        teRepo.put(timeEntry.getId(),timeEntry);

        return timeEntry;
    }

    public TimeEntry find(Long id) {

        return teRepo.get(id);
    }

    public void delete(Long id) {
        teRepo.remove(id);
    }

    public TimeEntry update(Long id, TimeEntry te) {

        teRepo.replace(id,te);
        te.setId(id);

        return te;
    }

    public List list() {
        return new ArrayList(teRepo.values());
    }
}


