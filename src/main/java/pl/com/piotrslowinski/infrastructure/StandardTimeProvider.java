package pl.com.piotrslowinski.infrastructure;

import pl.com.piotrslowinski.model.TimeProvider;
import org.springframework.stereotype.Component;

import java.time.Clock;


@Component
public class StandardTimeProvider implements TimeProvider {

    @Override
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
