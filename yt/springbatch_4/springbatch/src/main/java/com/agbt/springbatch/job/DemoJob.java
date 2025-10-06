package com.agbt.springbatch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoJob {

    //factory job
    @Autowired
    private JobBuilderFactory jbf;

    //factory step
    @Autowired
    private StepBuilderFactory sbf;


    //metodo ritorna 1 istanza di Job ottenuta tramite JobBuilderFactory indicando gli step con start() ecc. e build() crea l'Obj.
    //si usa una factory x settare solo le proprieta che ci servono
    @Bean
    public Job job(){
        return jbf.get("demo job")     //nome job
                .start(firstStep())    //step da eseguire
                .build();
    }

    @Bean
    public Step firstStep(){
        return sbf.get("first step")
                .tasklet(firstTask())
                .build();
    }


    //task da eseguire
    //execute() = metodo con la logica del tasklet
    private Tasklet firstTask(){
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution,
                                        ChunkContext chunkContext) throws Exception {
                System.out.println("First Job");
                return RepeatStatus.FINISHED; //con finished finisce, con continuable si farebbero + steps
            }
        };
    }



/*

Spring Batch usa JobRepository, storage nel quali inserisce le info dello stato dei Job (autoconfigurazione da EnableBatchProcessing)



*/









}
