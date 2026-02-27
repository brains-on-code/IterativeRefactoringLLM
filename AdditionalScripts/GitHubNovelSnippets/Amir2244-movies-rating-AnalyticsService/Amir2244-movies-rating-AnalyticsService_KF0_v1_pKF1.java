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
    public AnalyticsResult getAnalytics(AnalyticsQuery analyticsQuery) {
        List<DataAnalytics> analyticsPage = analyticsPort.loadAnalytics(analyticsQuery);

        long totalAnalyticsCount = analyticsPort.countAnalytics(analyticsQuery);

        int pageSize = analyticsQuery.getSize();
        int pageNumber = analyticsQuery.getPage();
        int totalPages = (int) Math.ceil((double) totalAnalyticsCount / pageSize);

        return new AnalyticsResult(
                analyticsPage,
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
                .map(typeCountEntry -> new AnalyticsTypeInfo(
                        typeCountEntry.getKey().name(),
                        typeCountEntry.getKey().getCategory(),
                        typeCountEntry.getKey().getDescription(),
                        typeCountEntry.getValue()
                ))
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
                .map(categoryCountEntry -> {
                    long categoryCount = categoryCountEntry.getValue();
                    double percentageOfTotal = totalAnalyticsCount > 0
                            ? (double) categoryCount / totalAnalyticsCount * 100
                            : 0.0;
                    return new AnalyticsSummary.CategorySummary(
                            categoryCountEntry.getKey(),
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