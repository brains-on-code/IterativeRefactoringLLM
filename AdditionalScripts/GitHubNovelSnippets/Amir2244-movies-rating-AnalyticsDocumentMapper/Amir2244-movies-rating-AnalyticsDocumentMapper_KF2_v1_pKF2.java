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

        AnalyticsType type = parseAnalyticsType(document.getType());

        return new DataAnalytics(
                document.getAnalyticsId(),
                document.getGeneratedAtAsInstant(),
                type,
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

    private AnalyticsType parseAnalyticsType(String typeValue) {
        try {
            return AnalyticsType.valueOf(typeValue);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Unknown analytics type: " + typeValue, e);
        }
    }
}