package com.amazon.ata.kindlepublishingservice.activity;

import com.amazon.ata.kindlepublishingservice.converters.PublishingStatusConverter;
import com.amazon.ata.kindlepublishingservice.dao.PublishingStatusDao;
import com.amazon.ata.kindlepublishingservice.dynamodb.models.PublishingStatusItem;
import com.amazon.ata.kindlepublishingservice.exceptions.PublishingStatusNotFoundException;
import com.amazon.ata.kindlepublishingservice.models.PublishingStatusRecord;
import com.amazon.ata.kindlepublishingservice.models.requests.GetPublishingStatusRequest;
import com.amazon.ata.kindlepublishingservice.models.response.GetPublishingStatusResponse;
import com.amazonaws.services.lambda.runtime.Context;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class GetPublishingStatusActivity {
    private PublishingStatusDao publishingStatusDao;
    @Inject
    public GetPublishingStatusActivity(PublishingStatusDao publishingStatusDao) {
        this.publishingStatusDao = publishingStatusDao;
    }

    public GetPublishingStatusResponse execute(GetPublishingStatusRequest request) {
        //extract id from request
        String publishingStatusId = request.getPublishingRecordId();
        //Query the database using the Dao
        List<PublishingStatusItem> statusItems = publishingStatusDao.getPublishingStatuses(publishingStatusId);

        if (statusItems.isEmpty()) {
            throw new PublishingStatusNotFoundException(
                    "No publishing status found with the provided ID: " + publishingStatusId);
        }

        List<PublishingStatusRecord> statusRecords = statusItems.stream()
                .map(PublishingStatusConverter::toPublishingStatusRecord)
                .collect(Collectors.toList());

        // Return response
        return GetPublishingStatusResponse.builder()
                .withPublishingStatusHistory(statusRecords)
                .build();


    }
}
