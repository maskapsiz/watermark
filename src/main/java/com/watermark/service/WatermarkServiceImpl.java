package com.watermark.service;

import com.watermark.entity.Document;
import com.watermark.exception.WatermarkException;
import com.watermark.exception.WatermarkExceptionConstants;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WatermarkServiceImpl to generate watermark for documents
 * Created by inazlim on 03/04/17.
 */
public class WatermarkServiceImpl implements WatermarkService{

    private static WatermarkService instance = null;

    private final Map<String, Document> documentMap = new ConcurrentHashMap<>();

    private WatermarkServiceImpl() {}

    public static synchronized WatermarkService getInstance(){
        if(instance == null){
            instance = new WatermarkServiceImpl();
        }
        return instance;
    }

    /**
     * Creates ticket for documents
     * @param document requested document to process
     * @return ticket for pooling status of processing
     */
    @Override
    public String process(Document document) {
        if(document == null) throw new WatermarkException(WatermarkExceptionConstants.DOCUMENT_NULL_EXCEPTION);
        String ticket = UUID.randomUUID().toString();
        documentMap.put(ticket, document);
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
        if(ticket == null) throw new WatermarkException(WatermarkExceptionConstants.TICKET_IS_NULL_EXCEPTION);
        else if(!documentMap.containsKey(ticket)) {
            throw new WatermarkException(WatermarkExceptionConstants.TICKET_DOES_NOT_EXIST_EXCEPTION);
        }

        Document document = documentMap.get(ticket);
        if(document.getWatermark() != null) {
            return document;
        }
        return null;
    }

    /**
     * Creates watermark for given document
     * @param document
     */
    private void createWatermark(Document document) {
        wait5Seconds();
        String watermark = document.toString();
        document.setWatermark(watermark);
    }

    //wait 10 Seconds for creating watermark
    private void wait5Seconds(){
        try {
            Thread.sleep(1000 * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
