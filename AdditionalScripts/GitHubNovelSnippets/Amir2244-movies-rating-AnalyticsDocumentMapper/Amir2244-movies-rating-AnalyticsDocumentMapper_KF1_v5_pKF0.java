package org.hiast.analyticsapi.adapter.out.persistence;

import org.hiast.model.AnalyticsType;
import org.hiast.model.DataAnalytics;
import org.springframework.stereotype.Component;

/**
 * Maps between AnalyticsDocument and DataAnalytics.
 */
@Component
public class AnalyticsDocumentMapper {

    /**
     * Converts an AnalyticsDocument to a DataAnalytics instance.
     */
    public DataAnalytics toDomain(AnalyticsDocument document) {
        if (document == null) {
            return null;
        }

        return new DataAnalytics(
                document.getAnalyticsId(),
                document.getGeneratedAtAsInstant(),
                toAnalyticsType(document.getType()),
                document.getMetrics(),
                document.getDescription()
        );
    }

    /**
     * Converts a DataAnalytics instance to an AnalyticsDocument.
     */
    public AnalyticsDocument toDocument(DataAnalytics dataAnalytics) {
        if (dataAnalytics == null) {
            return null;
        }

        AnalyticsDocument document = new AnalyticsDocument();
        document.setAnalyticsId(dataAnalytics.getAnalyticsId());
        document.setGeneratedAtFromInstant(dataAnalytics.getGeneratedAt());
        document.setType(toTypeString(dataAnalytics.getType()));
        document.setMetrics(dataAnalytics.getMetrics());
        document.setDescription(dataAnalytics.getDescription());
        return document;
    }

    private AnalyticsType toAnalyticsType(String type) {
        if (type == null) {
            throw new IllegalStateException("Analytics type must not be null");
        }

        try {
            return AnalyticsType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Unknown analytics type: " + type, e);
        }
    }

    private String toTypeString(AnalyticsType type) {
        if (type == null) {
            throw new IllegalStateException("AnalyticsType must not be null");
        }
        return type.name();
    }
}