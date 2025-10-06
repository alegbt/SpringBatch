package com.agbt.springbatch.config;

import com.agbt.springbatch.job.PlayerFieldSetMapper;
import com.agbt.springbatch.job.PlayerItemProcessor;
import com.agbt.springbatch.model.Player;
import com.agbt.springbatch.repository.PlayerRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private PlayerRepository playerRepository;


    public SpringBatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, PlayerRepository playerRepository) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.playerRepository = playerRepository;
    }


//va a leggere il file
    @Bean
    public FlatFileItemReader<Player> reader(){
        FlatFileItemReader<Player> itemReader = new FlatFileItemReader<>(); //flatfileitemreade x leggere file, indicando che tipo di obj x la struttura
        itemReader.setResource(new FileSystemResource("src/main/resources/data/players2.csv")); //filesystemresource xke sta nelle resources
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1); //skippa riga 1 (riga 1 ha intestazione)
        itemReader.setLineMapper(lineMapper()); //logica x leggere e mappare le righe
        return itemReader;
    }


    private LineMapper<Player> lineMapper(){
        DefaultLineMapper<Player> lineMapper = new DefaultLineMapper<>();

        //classe che legge e spezzetta le righe del file nei field
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(","); //delimitatore da usare x spezzare (csv usa ,)
        lineTokenizer.setStrict(false);  //con false se non trova il file non lancia exception
       // lineTokenizer.setNames("id", "firstName", "lastName", "position", "team"); //avendo messo 6 elementi in PlayerFieldSetMapper qui ne referenziamo solo 4 e darebbe errore - inoltre il mapping viene fatto con PlayerFieldSetMapper quindi non ha senso comunque


        //BeanWrapperFieldSetMapper<Player> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        //fieldSetMapper.setTargetType(Player.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(new PlayerFieldSetMapper()); //ora al posto di BeanWrapperFieldSetMapper qui usiamo la nostra classe x mappare le value all'obj
        return lineMapper;
    }


    //logica di process (scrittura usata nello step)
    @Bean
    public PlayerItemProcessor processor(){
        return new PlayerItemProcessor();
    }

    //diciamo che la parte di scrittura e' su playerRepository e facciamo save
    @Bean
    public RepositoryItemWriter<Player> writer(){
        RepositoryItemWriter<Player> writer = new RepositoryItemWriter<>();
        writer.setRepository(playerRepository);
        writer.setMethodName("save");
        return writer;
    }



    @Bean
    public Step step1(){
        return stepBuilderFactory
                .get("csv-step") //nome step
                .<Player, Player>chunk(10)                           //<I, O> chunk(int chunkSize) metodo con generics, I= input O= output chunkSize= chunk di valori elaborati da csv alla volta
                .reader(reader()) //lettura di file (scritta sopra)
                //.processor(processor())                            //disabilito logica di processor (quella della classe PlayerItemProcessor, cosi inserisce tutti i calciatori)
                .writer(writer())  //logica di writing (scritta sopra)
                //.taskExecutor(taskExecutor())
                .build();
    }


    @Bean
    public Job runJob(){
        return jobBuilderFactory
                .get("importPlayers") //nome job
                .flow(step1()) //step del job
                .end()  //termine flusso
                .build();
    }







}
