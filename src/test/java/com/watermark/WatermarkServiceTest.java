package com.watermark;

import com.watermark.entity.Book;
import com.watermark.entity.Journal;
import com.watermark.service.WatermarkService;
import com.watermark.service.WatermarkServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by inazlim on 03/04/17.
 */
public class WatermarkServiceTest {

    private WatermarkService watermarkService;
    private Book book1;
    private Book book2;
    private String book1Watermark;
    private String book2Watermark;
    private Journal journal1;
    private String journal1Watermark;

    @Before
    public void setUp() throws Exception {
        watermarkService = WatermarkServiceImpl.getInstance();

        book1 = new Book();
        book1.setTitle("The Dark Code");
        book1.setAuthor("Bruce Wayne");
        book1.setTopic("Science");
        book1Watermark = "{content:\"book\", title:\"The Dark Code\", author:\"Bruce Wayne\", topic:\"Science\"}";

        book2 = new Book();
        book2.setTitle("How to make money");
        book2.setAuthor("Dr. Evil");
        book2.setTopic("Business");
        book2Watermark = "{content:\"book\", title:\"How to make money\", author:\"Dr. Evil\", topic:\"Business\"}";

        journal1 = new Journal();
        journal1.setTitle("Journal of human flight routes");
        journal1.setAuthor("Clark Kent");
        journal1Watermark = "{content:\"journal\", title:\"Journal of human flight routes\", author:\"Clark Kent\"}";
    }

    @Test
    public void testSingleWatermark() throws Exception {
        //Book1
        String ticket = watermarkService.createWatermarkTask(book1);
        Book bookWithWatermark;
        while(true){
            bookWithWatermark = (Book) watermarkService.retrieveDocument(ticket);
            if(bookWithWatermark != null) break;
        }
        Assert.assertEquals(book1Watermark, bookWithWatermark.getWatermark());

        //Book2
        ticket = watermarkService.createWatermarkTask(book2);
        while(true){
            bookWithWatermark = (Book) watermarkService.retrieveDocument(ticket);
            if(bookWithWatermark != null) break;
        }
        Assert.assertEquals(book2Watermark, bookWithWatermark.getWatermark());

        //Journal
        ticket = watermarkService.createWatermarkTask(journal1);
        Journal journalWithWatermark;
        while (true) {
            journalWithWatermark = (Journal) watermarkService.retrieveDocument(ticket);
            if(journalWithWatermark != null) break;
        }
        Assert.assertEquals(journal1Watermark, journalWithWatermark.getWatermark());
    }
}
