package com.simitchiyski.lockregistry.config.queue;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.queue")
public class QueueProperties {
    private Lock lock;
    private Execution execution;
    private ReleaseDate releaseDate;

    @Data
    public static class Lock {
        private String region;
        private Duration timeToLive;
        private Duration timeToWait;
    }

    @Data
    public static class Execution {
        private String cron;
        private Duration rate;
        private int batchSize;
        private boolean enabled;
    }

    @Data
    public static class ReleaseDate {
        private ZoneId zone;
        private LocalTime time;
        private int cutoffDateOffset;
    }
}
