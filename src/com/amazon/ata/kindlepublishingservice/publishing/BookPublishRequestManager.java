package com.amazon.ata.kindlepublishingservice.publishing;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ConcurrentLinkedQueue;


public class BookPublishRequestManager {
    private static final Logger LOGGER = LogManager.getLogger(BookPublishRequestManager.class);

    //private Queue<BookPublishRequest> bookRequestQueue = new LinkedList<>();
    private  ConcurrentLinkedQueue<BookPublishRequest> requestQueue = new ConcurrentLinkedQueue<>();

    public BookPublishRequestManager() {
        this.requestQueue = new ConcurrentLinkedQueue<>();
    }

    //    public BookPublishRequestManager(Queue<BookPublishRequest> bookRequestQueue) {
//        this.requestQueue = bookRequestQueue;
//    }

    public void addBookPublishRequest(BookPublishRequest bookPublishRequest) {
        requestQueue.add(bookPublishRequest);
//        LOGGER.info("DEBUG: Added book publish request: {} | Queue size: {}",
//                bookPublishRequest.getPublishingRecordId(), requestQueue.size());
    }
    public BookPublishRequest getNextRequest() {
        BookPublishRequest request = requestQueue.poll();
//        LOGGER.info("DEBUG: Fetched book publish request: {} | Remaining Queue Size: {}",
//                (request != null ? request.getPublishingRecordId() : "None"), requestQueue.size());
        return request;
    }

    //    public BookPublishRequest getBookPublishRequestToProcess() {
//        return requestQueue.poll();
//    }
    public int getQueueSize() {
        return requestQueue.size();
    }
}
