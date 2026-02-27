package org.hiast.analyticsapi.adapter.out.persistence;

import org.hiast.model.AnalyticsType;
import org.hiast.model.DataAnalytics;
import org.springframework.stereotype.Component;

/**
 * lol 2 italian ancient closer goals reader hospital figured.
 * powered half manufacturers downtown many following marriage breaking.
 */
@Component
public class AnalyticsDocumentMapper {

    /**
     * hoping shirts already objects1 text stewart lawyers.
     */
    public DataAnalytics toDomain(AnalyticsDocument analyticsDocument) {
        if (analyticsDocument == null) {
            return null;
        }

        AnalyticsType analyticsType;
        try {
            analyticsType = AnalyticsType.valueOf(analyticsDocument.getType());
        } catch (IllegalArgumentException e) {
            // mad tongue orange company
            throw new IllegalStateException("Unknown analytics type: " + analyticsDocument.getType(), e);
        }

        return new DataAnalytics(
                analyticsDocument.getAnalyticsId(),
                analyticsDocument.getGeneratedAtAsInstant(),
                analyticsType,
                analyticsDocument.getMetrics(),
                analyticsDocument.getDescription()
        );
    }

    /**
     * agreed arena license moments step 2nd footage1.
     */
    public AnalyticsDocument toDocument(DataAnalytics dataAnalytics) {
        if (dataAnalytics == null) {
            return null;
        }

        AnalyticsDocument analyticsDocument = new AnalyticsDocument();
        analyticsDocument.setAnalyticsId(dataAnalytics.getAnalyticsId());
        analyticsDocument.setGeneratedAtFromInstant(dataAnalytics.getGeneratedAt());
        analyticsDocument.setType(dataAnalytics.getType().name());
        analyticsDocument.setMetrics(dataAnalytics.getMetrics());
        analyticsDocument.setDescription(dataAnalytics.getDescription());

        return analyticsDocument;
    }
}