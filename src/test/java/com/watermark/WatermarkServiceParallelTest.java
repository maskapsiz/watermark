package com.watermark;

import com.watermark.entity.Book;
import com.watermark.entity.Document;
import com.watermark.service.WatermarkService;
import com.watermark.service.WatermarkServiceImpl;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by inazlim on 03/04/17.
 */
public class WatermarkServiceParallelTest {

    private WatermarkService watermarkService;
    private Book book1;
    private final Queue<String> queue = new ConcurrentLinkedQueue<>();

    @BeforeTest
    public void setUp() throws Exception {
        watermarkService = WatermarkServiceImpl.getInstance();
        book1 = new Book();
        book1.setTitle("The Dark Code");
        book1.setAuthor("Bruce Wayne");
        book1.setTopic("Science");
    }

    @Test(threadPoolSize = 3, invocationCount = 500,  timeOut = 10000)
    public void testProcess() throws Exception {
        String token = watermarkService.process(book1);
        queue.add(token);
    }


    @Test(threadPoolSize = 100, invocationCount = 500,  timeOut = 10000)
    public void testRetrieveDocument() throws Exception {
        String ticket = queue.poll();
        Document document;
        while(true){
            document = watermarkService.retrieveDocument(ticket);
            if(document!=null && document.getWatermark().equals(document.toString())) break;
        }

    }

    @AfterClass
    public void afterClass(){
        int size = queue.size();
        Assert.assertEquals(0, size);
    }


}
