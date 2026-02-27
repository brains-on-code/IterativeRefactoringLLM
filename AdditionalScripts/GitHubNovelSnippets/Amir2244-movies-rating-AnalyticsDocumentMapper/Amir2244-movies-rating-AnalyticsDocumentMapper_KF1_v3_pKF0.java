package org.hiast.analyticsapi.adapter.out.persistence;

import org.hiast.model.AnalyticsType;
import org.hiast.model.DataAnalytics;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Maps between AnalyticsDocument and DataAnalytics.
 */
@Component
public class AnalyticsDocumentMapper {

    /**
     * Converts an AnalyticsDocument to a DataAnalytics instance.
     */
    public DataAnalytics toDomain(AnalyticsDocument document) {
        return Optional.ofNullable(document)
                .map(doc -> new DataAnalytics(
                        doc.getAnalyticsId(),
                        doc.getGeneratedAtAsInstant(),
                        parseAnalyticsType(doc.getType()),
                        doc.getMetrics(),
                        doc.getDescription()
                ))
                .orElse(null);
    }

    /**
     * Converts a DataAnalytics instance to an AnalyticsDocument.
     */
    public AnalyticsDocument toDocument(DataAnalytics dataAnalytics) {
        return Optional.ofNullable(dataAnalytics)
                .map(da -> {
                    AnalyticsDocument document = new AnalyticsDocument();
                    document.setAnalyticsId(da.getAnalyticsId());
                    document.setGeneratedAtFromInstant(da.getGeneratedAt());
                    document.setType(da.getType().name());
                    document.setMetrics(da.getMetrics());
                    document.setDescription(da.getDescription());
                    return document;
                })
                .orElse(null);
    }

    private AnalyticsType parseAnalyticsType(String type) {
        try {
            return AnalyticsType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Unknown analytics type: " + type, e);
        }
    }
}