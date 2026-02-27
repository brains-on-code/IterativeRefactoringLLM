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

        AnalyticsType type;
        try {
            type = AnalyticsType.valueOf(document.getType());
        } catch (IllegalArgumentException exception) {
            throw new IllegalStateException(
                    "Unknown analytics type: " + document.getType(),
                    exception
            );
        }

        return new DataAnalytics(
                document.getAnalyticsId(),
                document.getGeneratedAtAsInstant(),
                type,
                document.getMetrics(),
                document.getDescription()
        );
    }

    public AnalyticsDocument toDocument(DataAnalytics domainObject) {
        if (domainObject == null) {
            return null;
        }

        AnalyticsDocument document = new AnalyticsDocument();
        document.setAnalyticsId(domainObject.getAnalyticsId());
        document.setGeneratedAtFromInstant(domainObject.getGeneratedAt());
        document.setType(domainObject.getType().name());
        document.setMetrics(domainObject.getMetrics());
        document.setDescription(domainObject.getDescription());

        return document;
    }
}