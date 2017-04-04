package com.watermark;

import com.watermark.entity.Book;
import com.watermark.entity.Document;
import com.watermark.service.WatermarkService;
import com.watermark.service.WatermarkServiceImpl;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by inazlim on 03/04/17.
 */
public class WatermarkServiceParallelTest {

    private WatermarkService watermarkService;
    private final Queue<String> queue = new ConcurrentLinkedQueue<>();

    @BeforeTest
    public void setUp() throws Exception {
        watermarkService = WatermarkServiceImpl.getInstance();
    }

    @DataProvider(name = "testBooks")
    public Object[][] testBooks() {
        return new Object[][] {
                { "Book1 Topic", "Book1 Title", "Book1 Author" },
                { "Book2 Topic", "Book2 Title", "Book2 Author" },
                { "Book3 Topic", "Book3 Title", "Book3 Author" },
                { "Book4 Topic", "Book4 Title", "Book4 Author" },
                { "Book5 Topic", "Book5 Title", "Book5 Author" },
        };
    }


    @Test(dataProvider = "testBooks", threadPoolSize = 5, invocationCount = 100,  timeOut = 10000)
    public void testCreateWatermarkTask(String topic, String title, String author) throws Exception {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setTopic(topic);

        String token = watermarkService.createWatermarkTask(book);
        queue.add(token);
    }


    @Test(threadPoolSize = 100, invocationCount = 500,  timeOut = 10000)
    public void testRetrieveDocument() throws Exception {
        String ticket = queue.poll();

        //poll service until watermark exists
        while(true){
            Document document = watermarkService.retrieveDocument(ticket);
            //if document watermark exists break
            if(document!=null && document.getWatermark().equals(document.toString())) {
                break;
            }
        }

    }

    @AfterClass
    public void afterClass(){
        int size = queue.size();
        Assert.assertEquals(size, 0);
    }


}
