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
                mapToAnalyticsType(document.getType()),
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
        document.setType(mapToTypeString(analytics.getType()));
        document.setMetrics(analytics.getMetrics());
        document.setDescription(analytics.getDescription());
        return document;
    }

    private AnalyticsType mapToAnalyticsType(String type) {
        if (type == null) {
            throw new IllegalStateException("Analytics type must not be null");
        }

        try {
            return AnalyticsType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Unknown analytics type: " + type, e);
        }
    }

    private String mapToTypeString(AnalyticsType type) {
        if (type == null) {
            throw new IllegalStateException("Analytics type must not be null");
        }

        return type.name();
    }
}