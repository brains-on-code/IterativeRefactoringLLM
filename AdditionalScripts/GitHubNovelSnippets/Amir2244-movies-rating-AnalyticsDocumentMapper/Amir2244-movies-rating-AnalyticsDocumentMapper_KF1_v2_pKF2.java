package org.hiast.analyticsapi.adapter.out.persistence;

import org.hiast.model.AnalyticsType;
import org.hiast.model.DataAnalytics;
import org.springframework.stereotype.Component;

/**
 * Maps between {@link AnalyticsDocument} (persistence model) and
 * {@link DataAnalytics} (domain model).
 */
@Component
public class AnalyticsDocumentMapper {

    /**
     * Maps an {@link AnalyticsDocument} to a {@link DataAnalytics}.
     *
     * @param document the persistence model to map
     * @return the mapped domain model, or {@code null} if {@code document} is {@code null}
     * @throws IllegalStateException if the analytics type in the document is unknown
     */
    public DataAnalytics toDomain(AnalyticsDocument document) {
        if (document == null) {
            return null;
        }

        AnalyticsType analyticsType = parseAnalyticsType(document.getType());

        return new DataAnalytics(
                document.getAnalyticsId(),
                document.getGeneratedAtAsInstant(),
                analyticsType,
                document.getMetrics(),
                document.getDescription()
        );
    }

    /**
     * Maps a {@link DataAnalytics} to an {@link AnalyticsDocument}.
     *
     * @param dataAnalytics the domain model to map
     * @return the mapped persistence model, or {@code null} if {@code dataAnalytics} is {@code null}
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

    private AnalyticsType parseAnalyticsType(String type) {
        try {
            return AnalyticsType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Unknown analytics type: " + type, e);
        }
    }
}