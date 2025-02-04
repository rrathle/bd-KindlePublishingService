package com.amazon.ata.kindlepublishingservice.publishing;

import dagger.Provides;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.Queue;

public class BookPublishRequestManager {

    private Queue<BookPublishRequest> bookRequestQueue = new LinkedList<>();

    public BookPublishRequestManager(Queue<BookPublishRequest> bookRequestQueue) {
        this.bookRequestQueue = bookRequestQueue;
    }

    public void addBookPublishRequest(BookPublishRequest bookPublishRequest) {
        bookRequestQueue.add(bookPublishRequest);
    }
    public BookPublishRequest getBookPublishRequestToProcess() {
        return bookRequestQueue.poll();
    }
}
