package org.hiast.analyticsapi.adapter.out.persistence;

import org.hiast.model.AnalyticsType;
import org.hiast.model.DataAnalytics;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AnalyticsDocumentMapper {

    public DataAnalytics toDomain(AnalyticsDocument document) {
        return Optional.ofNullable(document)
                .map(doc -> new DataAnalytics(
                        doc.getAnalyticsId(),
                        doc.getGeneratedAtAsInstant(),
                        mapType(doc.getType()),
                        doc.getMetrics(),
                        doc.getDescription()
                ))
                .orElse(null);
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

    private AnalyticsType mapType(String type) {
        if (type == null) {
            throw new IllegalStateException("Analytics type cannot be null");
        }

        try {
            return AnalyticsType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Unknown analytics type: " + type, e);
        }
    }
}