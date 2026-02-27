package org.hiast.analyticsapi.application.service;

import org.hiast.analyticsapi.application.port.in.GetAnalyticsUseCase;
import org.hiast.analyticsapi.application.port.out.LoadAnalyticsPort;
import org.hiast.analyticsapi.domain.model.AnalyticsQuery;
import org.hiast.model.AnalyticsType;
import org.hiast.model.DataAnalytics;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Application service implementing analytics use cases.
 * This service orchestrates business logic and coordinates with output ports.
 */
@Service
public class AnalyticsService implements GetAnalyticsUseCase {

    private final LoadAnalyticsPort analyticsLoader;

    public AnalyticsService(LoadAnalyticsPort analyticsLoader) {
        this.analyticsLoader = analyticsLoader;
    }

    @Override
    public AnalyticsResult getAnalytics(AnalyticsQuery query) {
        List<DataAnalytics> analyticsItems = analyticsLoader.loadAnalytics(query);
        long totalAnalyticsCount = analyticsLoader.countAnalytics(query);

        int pageSize = query.getSize();
        int pageNumber = query.getPage();
        int totalPages = (int) Math.ceil((double) totalAnalyticsCount / pageSize);

        return new AnalyticsResult(
                analyticsItems,
                totalAnalyticsCount,
                totalPages,
                pageNumber,
                pageSize
        );
    }

    @Override
    public Optional<DataAnalytics> getAnalyticsById(String analyticsId) {
        return analyticsLoader.loadAnalyticsById(analyticsId);
    }

    @Override
    @Cacheable(value = "analytics", key = "'analytics-types'")
    public List<AnalyticsTypeInfo> getAvailableAnalyticsTypes() {
        Map<AnalyticsType, Long> analyticsCountByType = analyticsLoader.loadAnalyticsCountsByType();

        return analyticsCountByType.entrySet().stream()
                .map(typeCountEntry -> {
                    AnalyticsType analyticsType = typeCountEntry.getKey();
                    Long analyticsCount = typeCountEntry.getValue();

                    return new AnalyticsTypeInfo(
                            analyticsType.name(),
                            analyticsType.getCategory(),
                            analyticsType.getDescription(),
                            analyticsCount
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public AnalyticsSummary getAnalyticsSummary() {
        long totalAnalyticsCount = analyticsLoader.getTotalAnalyticsCount();
        long uniqueAnalyticsTypeCount = analyticsLoader.getUniqueAnalyticsTypesCount();

        String latestAnalyticsTimestamp = analyticsLoader.getLatestAnalyticsTimestamp().orElse("N/A");
        String oldestAnalyticsTimestamp = analyticsLoader.getOldestAnalyticsTimestamp().orElse("N/A");

        Map<String, Long> analyticsCountByCategory = analyticsLoader.loadAnalyticsCountsByCategory();
        List<AnalyticsSummary.CategorySummary> categorySummaries = analyticsCountByCategory.entrySet().stream()
                .map(categoryCountEntry -> {
                    String categoryName = categoryCountEntry.getKey();
                    long categoryCount = categoryCountEntry.getValue();

                    double percentageOfTotal = totalAnalyticsCount > 0
                            ? (double) categoryCount / totalAnalyticsCount * 100
                            : 0.0;

                    return new AnalyticsSummary.CategorySummary(
                            categoryName,
                            categoryCount,
                            percentageOfTotal
                    );
                })
                .collect(Collectors.toList());

        return new AnalyticsSummary(
                totalAnalyticsCount,
                uniqueAnalyticsTypeCount,
                latestAnalyticsTimestamp,
                oldestAnalyticsTimestamp,
                categorySummaries
        );
    }
}