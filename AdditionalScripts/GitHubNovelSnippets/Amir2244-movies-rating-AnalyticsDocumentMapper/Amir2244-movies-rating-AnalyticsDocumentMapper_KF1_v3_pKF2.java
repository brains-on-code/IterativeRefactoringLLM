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
     * Converts a persistence {@link AnalyticsDocument} to a domain {@link DataAnalytics}.
     *
     * @param document the persistence model; may be {@code null}
     * @return the corresponding domain model, or {@code null} if {@code document} is {@code null}
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
     * Converts a domain {@link DataAnalytics} to a persistence {@link AnalyticsDocument}.
     *
     * @param dataAnalytics the domain model; may be {@code null}
     * @return the corresponding persistence model, or {@code null} if {@code dataAnalytics} is {@code null}
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
     * Parses the given type string into an {@link AnalyticsType}.
     *
     * @param type the type name; must not be {@code null}
     * @return the corresponding {@link AnalyticsType}
     * @throws IllegalStateException if the given type does not match any {@link AnalyticsType}
     */
    private AnalyticsType parseAnalyticsType(String type) {
        try {
            return AnalyticsType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Unknown analytics type: " + type, e);
        }
    }
}