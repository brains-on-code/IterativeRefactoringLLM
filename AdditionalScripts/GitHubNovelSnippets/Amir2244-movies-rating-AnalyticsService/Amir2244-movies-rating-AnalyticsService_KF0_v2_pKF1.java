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
        long totalCount = loadAnalyticsPort.countAnalytics(query);

        int pageSize = query.getSize();
        int pageNumber = query.getPage();
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        return new AnalyticsResult(
                analytics,
                totalCount,
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
                .map(entry -> new AnalyticsTypeInfo(
                        entry.getKey().name(),
                        entry.getKey().getCategory(),
                        entry.getKey().getDescription(),
                        entry.getValue()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public AnalyticsSummary getAnalyticsSummary() {
        long totalAnalyticsCount = loadAnalyticsPort.getTotalAnalyticsCount();
        long uniqueAnalyticsTypeCount = loadAnalyticsPort.getUniqueAnalyticsTypesCount();

        String latestAnalyticsTimestamp = loadAnalyticsPort.getLatestAnalyticsTimestamp().orElse("N/A");
        String oldestAnalyticsTimestamp = loadAnalyticsPort.getOldestAnalyticsTimestamp().orElse("N/A");

        Map<String, Long> analyticsCountByCategory = loadAnalyticsPort.loadAnalyticsCountsByCategory();
        List<AnalyticsSummary.CategorySummary> categorySummaries = analyticsCountByCategory.entrySet().stream()
                .map(entry -> {
                    long categoryCount = entry.getValue();
                    double percentageOfTotal = totalAnalyticsCount > 0
                            ? (double) categoryCount / totalAnalyticsCount * 100
                            : 0.0;
                    return new AnalyticsSummary.CategorySummary(
                            entry.getKey(),
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