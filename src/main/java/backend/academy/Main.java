package backend.academy;

import java.util.concurrent.TimeUnit;
import lombok.experimental.UtilityClass;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@UtilityClass
public class Main {
    @SuppressWarnings("MagicNumberCheck")
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
            .include(Benchmark.class.getSimpleName())
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .mode(Mode.AverageTime)
            .timeUnit(TimeUnit.NANOSECONDS)
            .forks(1)
            .warmupForks(1)
            .warmupIterations(1)
            .warmupTime(TimeValue.seconds(30))
            .measurementIterations(1)
            .measurementTime(TimeValue.seconds(90))
            .build();

        Runner runner = new Runner(options);
        runner.run();
    }
}
