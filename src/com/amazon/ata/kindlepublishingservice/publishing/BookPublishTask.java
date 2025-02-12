package com.amazon.ata.kindlepublishingservice.publishing;

import com.amazon.ata.kindlepublishingservice.dao.CatalogDao;

import com.amazon.ata.kindlepublishingservice.dao.PublishingStatusDao;
import com.amazon.ata.kindlepublishingservice.enums.PublishingRecordStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class BookPublishTask implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(BookPublishTask.class);
    private final BookPublishRequestManager requestManager;
    private final CatalogDao catalogDao;
    private final PublishingStatusDao publishingStatusDao;



    public BookPublishTask(BookPublishRequestManager requestManager, CatalogDao catalogDao, PublishingStatusDao publishingStatusDao) {
        this.requestManager = requestManager;
        this.catalogDao = catalogDao;
        this.publishingStatusDao = publishingStatusDao;
    }

    @Override
    public void run() {
//        LOGGER.info("BookPublishTask started processing a request.");

        while (requestManager.getQueueSize() > 0) {
            BookPublishRequest request = requestManager.getNextRequest();
            if (request == null) {
//                LOGGER.info("No book publishing requests available. Exiting.");
                return; // Exit thread execution
            }
            try {
                // Update status to IN_PROGRESS
                publishingStatusDao.setPublishingStatus(request.getPublishingRecordId(),
                        PublishingRecordStatus.IN_PROGRESS,
                        null);
//                LOGGER.info("Updated status to IN_PROGRESS for Publishing Record ID: {}", request.getPublishingRecordId());

                // Process book publishing
                String bookId = catalogDao.createOrUpdateBook(request);

                // Update status to SUCCEEDED
                publishingStatusDao.setPublishingStatus(request.getPublishingRecordId(),
                        PublishingRecordStatus.SUCCESSFUL,
                        bookId);
//                LOGGER.info("Successfully published book with ID: {}", bookId);
            } catch (Exception e) {
//                LOGGER.error("Failed to publish book: {}", e.getMessage(), e);

                // Update status to FAILED with error message
                publishingStatusDao.setPublishingStatus(request.getPublishingRecordId(),
                        PublishingRecordStatus.FAILED,
                        null,
                        e.getMessage());
            }

        }
    }

}
