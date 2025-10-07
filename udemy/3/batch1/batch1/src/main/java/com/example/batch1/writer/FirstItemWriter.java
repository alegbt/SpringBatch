package com.example.batch1.writer;

import com.example.batch1.model.StudentCsv;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FirstItemWriter implements ItemWriter<StudentCsv> {
    @Override
    public void write(List<? extends StudentCsv> items) throws Exception {
        System.out.println("Inside item writer");
        items.forEach(System.out::println);
    }
}
