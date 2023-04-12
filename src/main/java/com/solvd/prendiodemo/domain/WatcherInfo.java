package com.solvd.prendiodemo.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WatcherInfo {

    private String watcherName;
    private String notifyAt;
}
