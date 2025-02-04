package com.amazon.ata.kindlepublishingservice.converters;

import com.amazon.ata.kindlepublishingservice.dynamodb.models.PublishingStatusItem;
import com.amazon.ata.kindlepublishingservice.models.PublishingStatusRecord;

public class PublishingStatusConverter {
    public static PublishingStatusRecord toPublishingStatusRecord(PublishingStatusItem item) {
        return PublishingStatusRecord.builder()
                .withBookId(item.getBookId())
                .withStatus(item.getStatus().name())
                .withStatusMessage(item.getStatusMessage())
                .build();
    }
}
