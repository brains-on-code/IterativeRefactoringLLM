package org.hiast.analyticsapi.adapter.out.persistence;

import org.hiast.model.AnalyticsType;
import org.hiast.model.DataAnalytics;
import org.springframework.stereotype.Component;

/**
 * Maps between AnalyticsDocument and DataAnalytics.
 */
@Component
public class Class1 {

    /**
     * Converts an AnalyticsDocument to a DataAnalytics instance.
     */
    public DataAnalytics toDomain(AnalyticsDocument document) {
        if (document == null) {
            return null;
        }

        AnalyticsType analyticsType;
        try {
            analyticsType = AnalyticsType.valueOf(document.getType());
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Unknown analytics type: " + document.getType(), e);
        }

        return new DataAnalytics(
                document.getAnalyticsId(),
                document.getGeneratedAtAsInstant(),
                analyticsType,
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
        document.setType(dataAnalytics.getType().name());
        document.setMetrics(dataAnalytics.getMetrics());
        document.setDescription(dataAnalytics.getDescription());

        return document;
    }
}