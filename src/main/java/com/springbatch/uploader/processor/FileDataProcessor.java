package com.springbatch.uploader.processor;

import com.springbatch.uploader.domain.FileData;
import com.springbatch.uploader.domain.TransformedFileData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class FileDataProcessor implements ItemProcessor<FileData, TransformedFileData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileDataProcessor.class);

    @Override
    public TransformedFileData process(FileData fileData) throws Exception {
        String brand = fileData.getBrand().toUpperCase();
        String origin = fileData.getOrigin().toUpperCase();
        String characteristics = fileData.getCharacteristics().toUpperCase();
        String index = fileData.getIndex().toUpperCase();

        TransformedFileData transformedFile = new TransformedFileData(brand, origin, characteristics,index);

        LOGGER.info("Reader input "+fileData);
        LOGGER.info("Processor output "+transformedFile);
        return transformedFile;

    }

}


