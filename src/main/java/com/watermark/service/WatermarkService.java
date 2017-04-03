package com.watermark.service;

import com.watermark.entity.Document;
import com.watermark.exception.WatermarkException;
import com.watermark.exception.WatermarkExceptionConstants;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by inazlim on 03/04/17.
 */
public interface WatermarkService {

    String process(Document document);

    Document retrieveDocument(String ticket);

}
