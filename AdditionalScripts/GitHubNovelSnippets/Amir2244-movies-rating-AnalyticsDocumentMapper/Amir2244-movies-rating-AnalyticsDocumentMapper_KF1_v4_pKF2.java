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
     * Maps a persistence {@link AnalyticsDocument} to a domain {@link DataAnalytics}.
     *
     * @param document persistence model; may be {@code null}
     * @return mapped domain model, or {@code null} if {@code document} is {@code null}
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
     * Maps a domain {@link DataAnalytics} to a persistence {@link AnalyticsDocument}.
     *
     * @param dataAnalytics domain model; may be {@code null}
     * @return mapped persistence model, or {@code null} if {@code dataAnalytics} is {@code null}
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

    /**
     * Converts a type name to {@link AnalyticsType}.
     *
     * @param type non-null type name
     * @return corresponding {@link AnalyticsType}
     * @throws IllegalStateException if {@code type} does not match any {@link AnalyticsType}
     */
    private AnalyticsType toAnalyticsType(String type) {
        try {
            return AnalyticsType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Unknown analytics type: " + type, e);
        }
    }
}