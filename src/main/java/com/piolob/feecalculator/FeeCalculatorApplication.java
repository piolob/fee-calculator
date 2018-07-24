package com.piolob.feecalculator;

import com.piolob.feecalculator.service.FileWatcherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;

@SpringBootApplication
@Order(InteractiveShellApplicationRunner.PRECEDENCE - 2)
public class FeeCalculatorApplication implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(FeeCalculatorApplication.class);

    private FileWatcherService fileWatcherService;

    public FeeCalculatorApplication(FileWatcherService fileWatcherService) {
        this.fileWatcherService = fileWatcherService;
    }

    public static void main(String[] args) {
        LOG.info("FeeCalculator starts...");
        SpringApplication app = new SpringApplication(FeeCalculatorApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... args) {
        fileWatcherService.start();
    }
}
