package com.watermark.service;

import com.watermark.entity.Document;
import com.watermark.exception.WatermarkException;
import com.watermark.exception.WatermarkExceptionConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WatermarkServiceImpl to generate watermark for documents
 * Thread-safe Singleton class.
 * Created by inazlim on 03/04/17.
 */
public class WatermarkServiceImpl implements WatermarkService{

    private final static Logger logger = LoggerFactory.getLogger(WatermarkServiceImpl.class);

    private static WatermarkService instance = null;

    private final Map<String, Document> documentMap = new ConcurrentHashMap<>();

    private WatermarkServiceImpl() {}

    /**
     * Lazily initialized instance
     */
    public static synchronized WatermarkService getInstance(){
        if(instance == null){
            instance = new WatermarkServiceImpl();
            logger.info("WatermarkServiceImpl initialized");
        }
        return instance;
    }

    /**
     * Creates ticket for documents
     * @param document requested document to createWatermarkTask
     * @return ticket for polling status of processing
     */
    @Override
    public String createWatermarkTask(Document document) {
        //If document is null throw exception
        if(document == null) {
            throw new WatermarkException(WatermarkExceptionConstants.DOCUMENT_NULL_EXCEPTION);
        }
        else {
            if (document.getWatermark() != null)
                throw new WatermarkException(WatermarkExceptionConstants.DOCUMENT_ALREADY_WATERMARKED);
        }

        //Generate ticket for document
        String ticket = UUID.randomUUID().toString();
        documentMap.put(ticket, document);

        //Start watermarking process in new thread
        Runnable task = () -> createWatermark(document);
        new Thread(task).start();
        return ticket;
    }

    /**
     * Retrieves document by ticket
     * @param ticket the value to retrieve document
     * @return retrieved document if ticket exists
     *          otherwise it returns null
     */
    @Override
    public Document retrieveDocument(String ticket) {
        //Check validity of ticket
        if(ticket == null) {
            throw new WatermarkException(WatermarkExceptionConstants.TICKET_IS_NULL_EXCEPTION);
        }
        else if(!documentMap.containsKey(ticket)) {
            throw new WatermarkException(WatermarkExceptionConstants.TICKET_DOES_NOT_EXIST_EXCEPTION);
        }

        //return document if watermark is available
        Document document = documentMap.get(ticket);
        if(document.getWatermark() != null) {
            logger.info("\tWatermark retrieved: " + document.getWatermark());
            documentMap.remove(document);
            return document;
        }
        //return null if watermark does not exist
        return null;
    }

    /**
     * Creates watermark for given document
     * @param document
     */
    private void createWatermark(Document document) {
        //Simulate watermarking process
        wait5Seconds();

        //Set document watermark
        String watermark = document.toString();
        document.setWatermark(watermark);
        logger.info("\tWatermark created: " + watermark);
    }

    //wait 5 Seconds for creating watermark
    private void wait5Seconds(){
        try {
            Thread.sleep(1000 * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
