package vttp.proj2.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;

@SpringBootApplication
@EnableScheduling
public class BackendApplication implements CommandLineRunner{

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job importCoursesJob; // Il nome del Bean Job dalla BatchConfig

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // La vecchia chiamata è stata sostituita dalla logica Batch
        // udemySvc.updateUdemyCoursesOnMongo();

        // Esecuzione del Job Batch all'avvio
        System.out.println("Avvio Job di Importazione Catalogo Corsi (Spring Batch)...");

        jobLauncher.run(importCoursesJob, new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis()).toJobParameters());

        System.out.println("Richiesta di esecuzione Job Batch inviata.");
    }
}