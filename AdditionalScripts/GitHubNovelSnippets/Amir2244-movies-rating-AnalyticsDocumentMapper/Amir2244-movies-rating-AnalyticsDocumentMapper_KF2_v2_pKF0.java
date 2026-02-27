package org.hiast.analyticsapi.adapter.out.persistence;

import java.util.Optional;
import org.hiast.model.AnalyticsType;
import org.hiast.model.DataAnalytics;
import org.springframework.stereotype.Component;

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
        try {
            return AnalyticsType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Unknown analytics type: " + type, e);
        }
    }
}