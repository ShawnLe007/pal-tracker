package io.pivotal.pal.tracker;

import org.springframework.context.annotation.Bean;

import java.util.*;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    private Map<Long, TimeEntry> timeEntryTracker;
    private long idCounter = 0;

    public InMemoryTimeEntryRepository() {
        this.timeEntryTracker = new HashMap<Long, TimeEntry>();
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        Long hash = ++idCounter;
        timeEntry.setId(hash);
        timeEntryTracker.put(hash, timeEntry);
        return timeEntry;
    }

    @Override
    public TimeEntry find(long timeEntryId) {
        return timeEntryTracker.get(timeEntryId);
    }

    @Override
    public List list() {
        return new ArrayList(timeEntryTracker.values());
    }

    @Override
    public TimeEntry update(long eq, TimeEntry timeEntry) {
        if (timeEntryTracker.get(eq) != null) {
            timeEntry.setId(eq);
            timeEntryTracker.put(eq, timeEntry);
            return timeEntry;
        }

        return null;
    }

    @Override
    public void delete(long timeEntryId) {
        timeEntryTracker.remove(timeEntryId);
    }
}
