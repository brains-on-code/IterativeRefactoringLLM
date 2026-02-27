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

    private final LoadAnalyticsPort analyticsPort;

    public AnalyticsService(LoadAnalyticsPort analyticsPort) {
        this.analyticsPort = analyticsPort;
    }

    @Override
    public AnalyticsResult getAnalytics(AnalyticsQuery query) {
        List<DataAnalytics> analyticsItems = analyticsPort.loadAnalytics(query);
        long totalAnalyticsCount = analyticsPort.countAnalytics(query);

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
        return analyticsPort.loadAnalyticsById(analyticsId);
    }

    @Override
    @Cacheable(value = "analytics", key = "'analytics-types'")
    public List<AnalyticsTypeInfo> getAvailableAnalyticsTypes() {
        Map<AnalyticsType, Long> analyticsCountByType = analyticsPort.loadAnalyticsCountsByType();

        return analyticsCountByType.entrySet().stream()
                .map(entry -> {
                    AnalyticsType analyticsType = entry.getKey();
                    Long analyticsCount = entry.getValue();

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
        long totalAnalyticsCount = analyticsPort.getTotalAnalyticsCount();
        long uniqueAnalyticsTypeCount = analyticsPort.getUniqueAnalyticsTypesCount();

        String latestAnalyticsTimestamp = analyticsPort.getLatestAnalyticsTimestamp().orElse("N/A");
        String oldestAnalyticsTimestamp = analyticsPort.getOldestAnalyticsTimestamp().orElse("N/A");

        Map<String, Long> analyticsCountByCategory = analyticsPort.loadAnalyticsCountsByCategory();
        List<AnalyticsSummary.CategorySummary> categorySummaries = analyticsCountByCategory.entrySet().stream()
                .map(entry -> {
                    String categoryName = entry.getKey();
                    long categoryCount = entry.getValue();

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