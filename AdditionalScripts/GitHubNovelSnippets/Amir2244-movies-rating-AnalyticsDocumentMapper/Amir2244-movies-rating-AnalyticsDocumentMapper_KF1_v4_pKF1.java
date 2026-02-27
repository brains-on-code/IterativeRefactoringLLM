package org.hiast.analyticsapi.adapter.out.persistence;

import org.hiast.model.AnalyticsType;
import org.hiast.model.DataAnalytics;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsDocumentMapper {

    public DataAnalytics toDomain(AnalyticsDocument document) {
        if (document == null) {
            return null;
        }

        AnalyticsType analyticsType;
        try {
            analyticsType = AnalyticsType.valueOf(document.getType());
        } catch (IllegalArgumentException exception) {
            throw new IllegalStateException("Unknown analytics type: " + document.getType(), exception);
        }

        return new DataAnalytics(
                document.getAnalyticsId(),
                document.getGeneratedAtAsInstant(),
                analyticsType,
                document.getMetrics(),
                document.getDescription()
        );
    }

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
}