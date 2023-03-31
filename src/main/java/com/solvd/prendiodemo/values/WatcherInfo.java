package com.solvd.prendiodemo.values;

import java.util.Objects;

public class WatcherInfo {

    private String watcherName;
    private String notifyAt;

    public WatcherInfo(String watcherName, String notifyAt) {
        this.watcherName = watcherName;
        this.notifyAt = notifyAt;
    }

    public String getWatcherName() {
        return watcherName;
    }

    public void setWatcherName(String watcherName) {
        this.watcherName = watcherName;
    }

    public String getNotifyAt() {
        return notifyAt;
    }

    public void setNotifyAt(String notifyAt) {
        this.notifyAt = notifyAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WatcherInfo that = (WatcherInfo) o;

        if (!Objects.equals(watcherName, that.watcherName)) {
            return false;
        }
        return Objects.equals(notifyAt, that.notifyAt);
    }

    @Override
    public String toString() {
        return "WatcherInfo{" +
                "watcherName='" + watcherName + '\'' +
                ", notifyAt='" + notifyAt + '\'' +
                '}';
    }
}
