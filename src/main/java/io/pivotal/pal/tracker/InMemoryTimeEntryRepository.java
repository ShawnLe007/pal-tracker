package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    private Map<Long, TimeEntry> timeEntryTracker = new ConcurrentHashMap<>();
    private AtomicLong idCounter = new AtomicLong(0);

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        Long hash = idCounter.incrementAndGet();
        timeEntry.setId(hash);
        timeEntryTracker.put(hash, timeEntry);
        return timeEntry;
    }

    @Override
    public TimeEntry find(long timeEntryId) {
        return timeEntryTracker.get(timeEntryId);
    }

    @Override
    public List<TimeEntry> list() {
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
