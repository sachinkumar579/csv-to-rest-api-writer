package com.springbatch.uploader.batch;

import com.springbatch.uploader.domain.FileData;
import com.springbatch.uploader.domain.TransformedFileData;
import com.springbatch.uploader.listener.ApplicationListener;
import com.springbatch.uploader.processor.FileDataProcessor;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.io.IOException;


@Configuration
@EnableBatchProcessing
public class ApplicationBatch {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationBatch.class);

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ApplicationListener applicationListener;

    @Bean
    public Job importUserJob(Step step){
        return jobBuilderFactory.get("importUserJob").incrementer(new RunIdIncrementer()).listener(applicationListener).flow(step).end().build();
    }

    @Bean
    public Step step() throws IOException {
        return stepBuilderFactory.get("step").<FileData, TransformedFileData> chunk(2).reader(reader()).processor(processor()).writer(writer()).build();
    }

    public ItemReader<FileData> reader() {
        return new FlatFileItemReaderBuilder().name("reader")
                .resource(new FileSystemResource("src/main/resources/input.csv"))
                .delimited()
                .names("brand", "origin", "characteristics","index")
                .fieldSetMapper(new BeanWrapperFieldSetMapper() {{
                    setTargetType(FileData.class);
                }})
                .build();
    }

    public FileDataProcessor processor() {
        return new FileDataProcessor();
    }

    public ItemWriter<TransformedFileData> writer() throws IOException {
        String api = "https://reqres.in/api/users?page=";
        CloseableHttpClient client = HttpClients.createDefault();

        return fileData -> {
              fileData.parallelStream().forEach(file->{
                  LOGGER.info("Writer Data"+file);
                  String result = null;
                  HttpGet request = new HttpGet(api+file.getIndex());
                  CloseableHttpResponse response = null;
                  HttpEntity entity = null;

                  try {
                      response = client.execute(request);
                      entity = response.getEntity();
                      result = EntityUtils.toString(entity);
                  } catch (IOException e) {
                      LOGGER.error("Error details "+e.getMessage());
                  }

                  LOGGER.info("HTTP GET Response "+result);
              });
          };
    }
}