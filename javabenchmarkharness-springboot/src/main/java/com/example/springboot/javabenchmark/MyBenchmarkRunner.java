package com.example.springboot.javabenchmark;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1)
@Warmup(iterations = 1, timeUnit = TimeUnit.MILLISECONDS, time = 5000)
@Measurement(iterations = 2, timeUnit = TimeUnit.MILLISECONDS, time = 5000)
public class MyBenchmarkRunner {
	
    @Benchmark
    public void test() {
        // todo
    }

    // [1] pom configuration for springbool application, reference: https://github.com/spring-projects/spring-boot/issues/384
    
    // [2] run java main from any class using plugin, reference: https://www.baeldung.com/maven-java-main-method
    // mvn exec:java -Dexec.mainClass="com.example.springboot.javabenchmark.MyBenchmarkRunner"
    
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                 .include(MyBenchmarkRunner.class.getSimpleName())
                 .threads(4)
                 .forks(1)
                 .build();

        new Runner(opt).run();
    }
}