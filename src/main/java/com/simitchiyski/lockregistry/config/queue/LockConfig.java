package com.simitchiyski.lockregistry.config.queue;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.jdbc.lock.DefaultLockRepository;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;
import org.springframework.integration.jdbc.lock.LockRepository;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties({QueueProperties.class})
public class LockConfig {

    @Bean
    public DefaultLockRepository defaultLockRepository(final DataSource dataSource, final QueueProperties queueProperties) {
        final DefaultLockRepository lockRepository = new DefaultLockRepository(dataSource, clientId());

        lockRepository.setTimeToLive(timeToLive(queueProperties));
        lockRepository.setRegion(region(queueProperties));

        return lockRepository;
    }

    @Bean
    public JdbcLockRegistry jdbcLockRegistry(final LockRepository lockRepository) {
        return new JdbcLockRegistry(lockRepository);
    }

    private String region(QueueProperties queueProperties) {
        return queueProperties.getLock().getRegion();
    }

    private int timeToLive(QueueProperties queueProperties) {
        return (int) queueProperties.getLock().getTimeToLive().toMillis();
    }

    private String clientId() {
        return ClientId.clientId.get();
    }
}
