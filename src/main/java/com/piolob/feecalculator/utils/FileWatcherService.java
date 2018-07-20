package com.piolob.feecalculator.utils;

import com.piolob.feecalculator.configuration.DataFeeder;
import com.piolob.feecalculator.configuration.FeeCalculatorConfig;
import com.piolob.feecalculator.configuration.GlobalProperties;
import com.piolob.feecalculator.exception.FeeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.nio.file.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class FileWatcherService extends Thread {
    private static final Logger LOG = LoggerFactory.getLogger(FileWatcherService.class);
    private AtomicBoolean stop = new AtomicBoolean(false);

    private GlobalProperties globalProperties;
    private DataFeeder dataFeeder;
    private CacheManager cacheManager;

    public FileWatcherService(GlobalProperties globalProperties, DataFeeder dataFeeder, CacheManager cacheManager) {
        this.globalProperties = globalProperties;
        this.dataFeeder = dataFeeder;
        this.cacheManager = cacheManager;
    }

    private void processOnChange(Path filename) throws FeeException {
        if (filename.toString().equals(globalProperties.getFeesDiscountsFileName().getName())) {
            dataFeeder.updateFeeDiscounts();
        } else if (filename.toString().equals(globalProperties.getCustomerFeesFileName().getName())) {
            dataFeeder.updateCustomerFees();
        }
        clearCache();
    }

    private void clearCache() {
        cacheManager.getCache(FeeCalculatorConfig.CALCULATED_FEES).clear();
        cacheManager.getCache(FeeCalculatorConfig.PROCESSED_CUSTOMER).clear();
    }


    @Override
    public void run() {
        LOG.info("fileWatcherService is running...");
        try (WatchService watcher = FileSystems.getDefault().newWatchService()) {
            Path path = globalProperties.getInputDirectoryName().toPath();
            path.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.OVERFLOW);
            while (!isStopped()) {
                WatchKey key;
                try {
                    key = watcher.poll(25, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    return;
                }
                if (key == null) {
                    Thread.yield();
                    continue;
                }

                Thread.sleep(50);

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    @SuppressWarnings("unchecked")
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path filename = ev.context();

                    if (kind == StandardWatchEventKinds.OVERFLOW) {
                        Thread.yield();
                        continue;
                    } else if (kind == java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY) {
                        processOnChange(filename);
                    }

                    boolean valid = key.reset();
                    if (!valid) {
                        break;
                    }
                }
                Thread.yield();
            }
        } catch (Throwable e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public boolean isStopped() {
        return stop.get();
    }

    public void stopThread() {
        stop.set(true);
    }
}
