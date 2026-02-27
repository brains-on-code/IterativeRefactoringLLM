package org.hiast.analyticsapi.adapter.out.persistence;

import org.hiast.model.AnalyticsType;
import org.hiast.model.DataAnalytics;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsDocumentMapper {

    public DataAnalytics toDomain(AnalyticsDocument analyticsDocument) {
        if (analyticsDocument == null) {
            return null;
        }

        AnalyticsType analyticsType;
        try {
            analyticsType = AnalyticsType.valueOf(analyticsDocument.getType());
        } catch (IllegalArgumentException exception) {
            throw new IllegalStateException(
                    "Unknown analytics type: " + analyticsDocument.getType(),
                    exception
            );
        }

        return new DataAnalytics(
                analyticsDocument.getAnalyticsId(),
                analyticsDocument.getGeneratedAtAsInstant(),
                analyticsType,
                analyticsDocument.getMetrics(),
                analyticsDocument.getDescription()
        );
    }

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