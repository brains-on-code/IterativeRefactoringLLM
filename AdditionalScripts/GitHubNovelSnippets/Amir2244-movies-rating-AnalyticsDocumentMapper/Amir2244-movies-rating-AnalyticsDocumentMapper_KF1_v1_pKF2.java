package org.hiast.analyticsapi.adapter.out.persistence;

import org.hiast.model.AnalyticsType;
import org.hiast.model.DataAnalytics;
import org.springframework.stereotype.Component;

/**
 * Maps between {@link AnalyticsDocument} (persistence model) and
 * {@link DataAnalytics} (domain model).
 */
@Component
public class Class1 {

    /**
     * Converts an {@link AnalyticsDocument} to a {@link DataAnalytics} instance.
     *
     * @param document the persistence model to convert
     * @return the corresponding domain model, or {@code null} if {@code document} is {@code null}
     * @throws IllegalStateException if the analytics type in the document is unknown
     */
    public DataAnalytics method1(AnalyticsDocument document) {
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
     * Converts a {@link DataAnalytics} instance to an {@link AnalyticsDocument}.
     *
     * @param dataAnalytics the domain model to convert
     * @return the corresponding persistence model, or {@code null} if {@code dataAnalytics} is {@code null}
     */
    public AnalyticsDocument method2(DataAnalytics dataAnalytics) {
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