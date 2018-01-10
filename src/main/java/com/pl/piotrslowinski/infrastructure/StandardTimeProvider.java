package com.pl.piotrslowinski.infrastructure;

import com.pl.piotrslowinski.model.TimeProvider;
import org.springframework.stereotype.Component;

import java.time.Clock;

/**
 * Created by user on 07.01.2018.
 */
@Component
public class StandardTimeProvider implements TimeProvider {

    @Override
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
