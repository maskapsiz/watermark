package com.watermark;

import com.watermark.exception.WatermarkException;
import com.watermark.exception.WatermarkExceptionConstants;
import com.watermark.service.WatermarkService;
import com.watermark.service.WatermarkServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by inazlim on 03/04/17.
 */
public class WatermarkExceptionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testNullDocumentException() throws WatermarkException {
        WatermarkService watermarkService = WatermarkServiceImpl.getInstance();
        thrown.expect(WatermarkException.class);
        thrown.expectMessage(WatermarkExceptionConstants.DOCUMENT_NULL_EXCEPTION);
        watermarkService.process(null);
    }

    @Test
    public void testNonExistTicketException() throws Exception {
        WatermarkService watermarkService = WatermarkServiceImpl.getInstance();
        thrown.expect(WatermarkException.class);
        thrown.expectMessage(WatermarkExceptionConstants.TICKET_DOES_NOT_EXIST_EXCEPTION);
        watermarkService.retrieveDocument("");
    }

    @Test
    public void testNullTicketException() throws Exception {
        WatermarkService watermarkService = WatermarkServiceImpl.getInstance();
        thrown.expect(WatermarkException.class);
        thrown.expectMessage(WatermarkExceptionConstants.TICKET_IS_NULL_EXCEPTION);
        watermarkService.retrieveDocument(null);
    }
}
