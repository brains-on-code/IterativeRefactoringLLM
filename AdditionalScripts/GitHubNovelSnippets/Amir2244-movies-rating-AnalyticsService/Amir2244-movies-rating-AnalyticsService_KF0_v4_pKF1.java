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

    private final LoadAnalyticsPort loadAnalyticsPort;

    public AnalyticsService(LoadAnalyticsPort loadAnalyticsPort) {
        this.loadAnalyticsPort = loadAnalyticsPort;
    }

    @Override
    public AnalyticsResult getAnalytics(AnalyticsQuery query) {
        List<DataAnalytics> analytics = loadAnalyticsPort.loadAnalytics(query);
        long totalAnalytics = loadAnalyticsPort.countAnalytics(query);

        int pageSize = query.getSize();
        int pageNumber = query.getPage();
        int totalPages = (int) Math.ceil((double) totalAnalytics / pageSize);

        return new AnalyticsResult(
                analytics,
                totalAnalytics,
                totalPages,
                pageNumber,
                pageSize
        );
    }

    @Override
    public Optional<DataAnalytics> getAnalyticsById(String analyticsId) {
        return loadAnalyticsPort.loadAnalyticsById(analyticsId);
    }

    @Override
    @Cacheable(value = "analytics", key = "'analytics-types'")
    public List<AnalyticsTypeInfo> getAvailableAnalyticsTypes() {
        Map<AnalyticsType, Long> analyticsCountByType = loadAnalyticsPort.loadAnalyticsCountsByType();

        return analyticsCountByType.entrySet().stream()
                .map(entry -> {
                    AnalyticsType type = entry.getKey();
                    Long count = entry.getValue();

                    return new AnalyticsTypeInfo(
                            type.name(),
                            type.getCategory(),
                            type.getDescription(),
                            count
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public AnalyticsSummary getAnalyticsSummary() {
        long totalAnalytics = loadAnalyticsPort.getTotalAnalyticsCount();
        long uniqueAnalyticsTypes = loadAnalyticsPort.getUniqueAnalyticsTypesCount();

        String latestAnalyticsTimestamp = loadAnalyticsPort.getLatestAnalyticsTimestamp().orElse("N/A");
        String oldestAnalyticsTimestamp = loadAnalyticsPort.getOldestAnalyticsTimestamp().orElse("N/A");

        Map<String, Long> analyticsCountByCategory = loadAnalyticsPort.loadAnalyticsCountsByCategory();
        List<AnalyticsSummary.CategorySummary> categorySummaries = analyticsCountByCategory.entrySet().stream()
                .map(entry -> {
                    String category = entry.getKey();
                    long count = entry.getValue();

                    double percentageOfTotal = totalAnalytics > 0
                            ? (double) count / totalAnalytics * 100
                            : 0.0;

                    return new AnalyticsSummary.CategorySummary(
                            category,
                            count,
                            percentageOfTotal
                    );
                })
                .collect(Collectors.toList());

        return new AnalyticsSummary(
                totalAnalytics,
                uniqueAnalyticsTypes,
                latestAnalyticsTimestamp,
                oldestAnalyticsTimestamp,
                categorySummaries
        );
    }
}