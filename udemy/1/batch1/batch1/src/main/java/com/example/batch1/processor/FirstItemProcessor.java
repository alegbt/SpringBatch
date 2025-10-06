package com.example.batch1.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class FirstItemProcessor implements ItemProcessor <Integer, Long>{

    @Override
    public Long process(Integer item) throws Exception {
        System.out.println("Inseide item processor");
        return (long) (item + 20);
    }

}
