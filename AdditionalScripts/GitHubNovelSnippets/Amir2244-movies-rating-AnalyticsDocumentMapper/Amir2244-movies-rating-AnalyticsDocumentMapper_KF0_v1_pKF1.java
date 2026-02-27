package org.hiast.analyticsapi.adapter.out.persistence;

import org.hiast.model.AnalyticsType;
import org.hiast.model.DataAnalytics;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between domain models and MongoDB documents.
 * Handles the transformation between the hexagonal architecture layers.
 */
@Component
public class AnalyticsDocumentMapper {

    /**
     * Converts from MongoDB document to domain model.
     */
    public DataAnalytics toDomain(AnalyticsDocument analyticsDocument) {
        if (analyticsDocument == null) {
            return null;
        }

        AnalyticsType analyticsType;
        try {
            analyticsType = AnalyticsType.valueOf(analyticsDocument.getType());
        } catch (IllegalArgumentException exception) {
            throw new IllegalStateException(
                    "Unknown analytics type: " + analyticsDocument.getType(),
                    exception
            );
        }

        return new DataAnalytics(
                analyticsDocument.getAnalyticsId(),
                analyticsDocument.getGeneratedAtAsInstant(),
                analyticsType,
                analyticsDocument.getMetrics(),
                analyticsDocument.getDescription()
        );
    }

    /**
     * Converts from domain model to MongoDB document.
     */
    public AnalyticsDocument toDocument(DataAnalytics dataAnalytics) {
        if (dataAnalytics == null) {
            return null;
        }

        AnalyticsDocument analyticsDocument = new AnalyticsDocument();
        analyticsDocument.setAnalyticsId(dataAnalytics.getAnalyticsId());
        analyticsDocument.setGeneratedAtFromInstant(dataAnalytics.getGeneratedAt());
        analyticsDocument.setType(dataAnalytics.getType().name());
        analyticsDocument.setMetrics(dataAnalytics.getMetrics());
        analyticsDocument.setDescription(dataAnalytics.getDescription());

        return analyticsDocument;
    }
}