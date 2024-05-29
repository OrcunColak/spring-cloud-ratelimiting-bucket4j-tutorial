import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// See https://glaforge.medium.com/client-side-consumption-of-a-rate-limited-api-in-java-9fbf08673791
@Slf4j
class BucketTest {

    @Test
    void testBucket() {
        var args = List.of("a", "b", "c", "d", "e");

        // 1 tokens per second.
        var bucket = Bucket.builder()
                .addLimit(Bandwidth.simple(1, Duration.ofSeconds(1)))
                .build();

        for (String arg : args) {
            bucket.asBlocking().consumeUninterruptibly(1);
            log.info(arg);
        }
    }

    @Test
    void testBucket2() {
        var args = List.of("a", "b", "c", "d", "e");

        // 1 tokens per second.
        var bucket = Bucket.builder()
                .addLimit(Bandwidth.simple(1, Duration.ofSeconds(1)))
                .build();

        try (ExecutorService executor = Executors.newFixedThreadPool(4)) {
            for (String arg : args) {
                bucket.asBlocking().consumeUninterruptibly(1);
                executor.submit(() -> log.info(arg));
            }
        }
    }
}
