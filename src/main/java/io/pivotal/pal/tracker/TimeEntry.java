package io.pivotal.pal.tracker;

import java.time.LocalDate;
import java.util.Objects;

public class TimeEntry {
    private long timeEntryId;
    private long projectId;
    private long userId;
    private LocalDate date;
    private int hours;

    public TimeEntry(long timeEntryId, long projectId, long userId, LocalDate date, int hours) {
        this.timeEntryId = timeEntryId;
        this.projectId = projectId;
        this.userId = userId;
        this.date = date;
        this.hours = hours;
    }

    public TimeEntry(long projectId, long userId, LocalDate date, int hours) {
        this.projectId = projectId;
        this.userId = userId;
        this.date = date;
        this.hours = hours;
    }

    public TimeEntry() {
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public long getId() {
        return timeEntryId;
    }

    public void setId(long id) {
        this.timeEntryId = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeEntry timeEntry = (TimeEntry) o;
        return
                projectId == timeEntry.projectId &&
                        userId == timeEntry.userId &&
                        hours == timeEntry.hours &&
                        date.equals(timeEntry.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, userId, date, hours);
    }

    @Override
    public String toString() {
        return "TimeEntry{" +
                "timeEntryId=" + timeEntryId +
                ", projectId=" + projectId +
                ", userId=" + userId +
                ", date=" + date +
                ", hours=" + hours +
                '}';
    }
}
