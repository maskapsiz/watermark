package com.watermark;

import com.watermark.entity.Book;
import com.watermark.entity.Journal;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by inazlim on 03/04/17.
 */
public class DocumentToStringTest {

    @Test
    public void testBookToString() {
        Book book = new Book();
        book.setTitle("The Dark Code");
        book.setAuthor("Bruce Wayne");
        book.setTopic("Science");
        String watermark = book.toString();
        Assert.assertEquals("{content:\"book\", title:\"The Dark Code\", author:\"Bruce Wayne\", topic:\"Science\"}", watermark);

        book.setTitle("How to make money");
        book.setAuthor("Dr. Evil");
        book.setTopic("Business");
        watermark = book.toString();
        Assert.assertEquals("{content:\"book\", title:\"How to make money\", author:\"Dr. Evil\", topic:\"Business\"}", watermark);
    }

    @Test
    public void testJournalToString() throws Exception {
        Journal journal = new Journal();
        journal.setTitle("Journal of human flight routes");
        journal.setAuthor("Clark Kent");
        Assert.assertEquals("{content:\"journal\", title:\"Journal of human flight routes\", author:\"Clark Kent\"}", journal.toString());
    }
}
