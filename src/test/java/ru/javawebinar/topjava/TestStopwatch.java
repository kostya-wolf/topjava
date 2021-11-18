package ru.javawebinar.topjava;

import org.junit.AssumptionViolatedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TestStopwatch extends Stopwatch {
    private static Logger logger = LoggerFactory.getLogger(TestStopwatch.class);

    @Override
    protected void succeeded(long nanos, Description description) {
        logger.info("Тест {} выполнен успешно за {}", description.getMethodName(), getMilliseconds(nanos));
    }

    @Override
    protected void failed(long nanos, Throwable e, Description description) {
        logger.warn("Тест {} выполнен с ошибкой за {}", description.getMethodName(), getMilliseconds(nanos));
    }

    @Override
    protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
        logger.info("Тест {} пропущен за {}", description.getMethodName(), getMilliseconds(nanos));
    }

    @Override
    protected void finished(long nanos, Description description) {
        //logger.info("Тест {} окончен. Выполнение заняло {} наносекунд.", description.getMethodName(), nanos);
    }

    private String getMilliseconds(long nanos) {
        return TimeUnit.MILLISECONDS.convert(nanos, TimeUnit.NANOSECONDS) + " мс.";
    }
}
