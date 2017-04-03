package com.watermark.service;

import com.watermark.entity.Document;

/**
 * Created by inazlim on 03/04/17.
 */
public interface WatermarkService {

    String process(Document document);

    Document retrieveDocument(String ticket);

}
