package com.pl.piotrslowinski.infrastructure;

import com.pl.piotrslowinski.model.TimeProvider;
import org.springframework.stereotype.Component;

import java.time.Clock;


@Component
public class StandardTimeProvider implements TimeProvider {

    @Override
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
