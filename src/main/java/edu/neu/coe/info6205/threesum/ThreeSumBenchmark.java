package edu.neu.coe.info6205.threesum;

import edu.neu.coe.info6205.util.Benchmark_Timer;
import edu.neu.coe.info6205.util.TimeLogger;
import edu.neu.coe.info6205.util.Utilities;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class ThreeSumBenchmark {
    public ThreeSumBenchmark(int runs, int n, int m) {
        this.runs = runs;
        this.supplier = new Source(n, m).intsSupplier(10);
        this.n = n;
    }

    public void runBenchmarks() {
        System.out.println("ThreeSumBenchmark: N=" + n);
        benchmarkThreeSum("ThreeSumQuadratic", (xs) -> new ThreeSumQuadratic(xs).getTriples(), n, timeLoggersQuadratic);
        benchmarkThreeSum("ThreeSumQuadraticWithCalipers", (xs) -> new ThreeSumQuadraticWithCalipers(xs).getTriples(), n, timeLoggersQuadraticWithCalipers);
        benchmarkThreeSum("ThreeSumQuadrithmic", (xs) -> new ThreeSumQuadrithmic(xs).getTriples(), n, timeLoggersQuadrithmic);
        benchmarkThreeSum("ThreeSumCubic", (xs) -> new ThreeSumCubic(xs).getTriples(), n, timeLoggersCubic);
    }

    public static void main(String[] args) {
        new ThreeSumBenchmark(100, 250, 250).runBenchmarks();
        new ThreeSumBenchmark(50, 500, 500).runBenchmarks();
        new ThreeSumBenchmark(20, 1000, 1000).runBenchmarks();
        new ThreeSumBenchmark(10, 2000, 2000).runBenchmarks();
        new ThreeSumBenchmark(5, 4000, 4000).runBenchmarks();
        new ThreeSumBenchmark(3, 8000, 8000).runBenchmarks();
        new ThreeSumBenchmark(2, 16000, 16000).runBenchmarks();
    }

    private void benchmarkThreeSum(final String description, final Consumer<int[]> function, int n, final TimeLogger[] timeLoggers) {
        //if (description.equals("ThreeSumCubic") && n > 4000) return;

        double duration = 0;
        for(int i=0; i< runs; i++){
            long startTime = System.currentTimeMillis();
            function.accept(supplier.get());
            long endTime = System.currentTimeMillis();
            duration += (endTime - startTime);
        }
        duration = duration / runs;
//        System.out.println(description+" Total time taken "+ duration+ " for n "+n);
        for(TimeLogger timeLogger : timeLoggers){
            timeLogger.log(duration, n);
        }
    }

    private final static TimeLogger[] timeLoggersCubic = {
            new TimeLogger("(Cubic) Raw time per run (mSec): ", (time, n) -> time),
            new TimeLogger("(Cubic) Normalized time per run (n^3): ", (time, n) -> time / n / n / n * 1e6)
    };
    private final static TimeLogger[] timeLoggersQuadrithmic = {
            new TimeLogger("(Quadrithmic) Raw time per run (mSec): ", (time, n) -> time),
            new TimeLogger("(Quadrithmic) Normalized time per run (n^2 log n): ", (time, n) -> time / n / n / Utilities.lg(n) * 1e6)
    };
    private final static TimeLogger[] timeLoggersQuadratic = {
            new TimeLogger("(Quadratic) Raw time per run (mSec): ", (time, n) -> time),
            new TimeLogger("(Quadratic) Normalized time per run (n^2): ", (time, n) -> time / n / n * 1e6)
    };
    private final static TimeLogger[] timeLoggersQuadraticWithCalipers = {
            new TimeLogger("(Quadratic with Calipers) Raw time per run (mSec): ", (time, n) -> time),
            new TimeLogger("(Quadratic with Calipers) Normalized time per run (n^2): ", (time, n) -> time / n / n * 1e6)
    };

    private final int runs;
    private final Supplier<int[]> supplier;
    private final int n;
}
