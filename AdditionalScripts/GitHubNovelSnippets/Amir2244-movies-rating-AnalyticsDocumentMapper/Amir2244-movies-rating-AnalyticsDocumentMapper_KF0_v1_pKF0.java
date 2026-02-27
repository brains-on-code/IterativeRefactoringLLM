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
    public DataAnalytics toDomain(AnalyticsDocument document) {
        if (document == null) {
            return null;
        }

        return new DataAnalytics(
                document.getAnalyticsId(),
                document.getGeneratedAtAsInstant(),
                mapType(document.getType()),
                document.getMetrics(),
                document.getDescription()
        );
    }

    /**
     * Converts from domain model to MongoDB document.
     */
    public AnalyticsDocument toDocument(DataAnalytics analytics) {
        if (analytics == null) {
            return null;
        }

        AnalyticsDocument document = new AnalyticsDocument();
        document.setAnalyticsId(analytics.getAnalyticsId());
        document.setGeneratedAtFromInstant(analytics.getGeneratedAt());
        document.setType(analytics.getType().name());
        document.setMetrics(analytics.getMetrics());
        document.setDescription(analytics.getDescription());

        return document;
    }

    private AnalyticsType mapType(String type) {
        try {
            return AnalyticsType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Unknown analytics type: " + type, e);
        }
    }
}