package ru.otus.l041;

class GcReportItem {

    private String name;
    private long duration;

    GcReportItem(String name, long duration) {
        this.name = name;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public long getDuration() {
        return duration;
    }
}
