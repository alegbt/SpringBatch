package com.example.batch1.jobs;

import com.example.batch1.model.StudentCsv;
import com.example.batch1.reader.FirstItemReader;
import com.example.batch1.writer.FirstItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.io.File;

@Configuration
public class SampleJob {


    @Autowired
    private FirstItemWriter firstItemWriter;

    @Autowired
    private FirstItemReader firstItemReader;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job chunkJob() {
        return jobBuilderFactory.get("Chunk Job")
                .incrementer(new RunIdIncrementer())
                .start(firstChunkStep())
                .build();
    }

    @Bean
    public Step firstChunkStep() {
        return stepBuilderFactory.get("First Chunk Step")
                .<StudentCsv, StudentCsv>chunk(3)
                .reader(flatFileItemReader())
                //.processor(firstItemProcessor)
                .writer(firstItemWriter)
                .build();
    }



    @Bean
    public FlatFileItemReader<StudentCsv> flatFileItemReader() {
        FlatFileItemReader<StudentCsv> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource("C:\\Users\\aless\\Desktop\\Corsi\\SpringBatch\\udemy\\3\\batch1\\batch1\\InputFiles\\students.csv"));
        reader.setLinesToSkip(1); // salta l'intestazione

        // Line mapper
        DefaultLineMapper<StudentCsv> lineMapper = new DefaultLineMapper<>();

        // Tokenizer (definisce l'ordine e i nomi delle colonne)
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(","); // separatore CSV
        tokenizer.setNames("id", "firstName", "lastName", "email"); // nomi CAMPI (non header CSV)


        // Mapper verso POJO
        BeanWrapperFieldSetMapper<StudentCsv> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(StudentCsv.class);

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        reader.setLineMapper(lineMapper);

        return reader;
    }





}
