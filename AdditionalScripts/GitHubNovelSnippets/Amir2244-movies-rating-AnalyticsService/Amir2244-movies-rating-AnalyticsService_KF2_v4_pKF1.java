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

@Service
public class AnalyticsService implements GetAnalyticsUseCase {

    private final LoadAnalyticsPort loadAnalyticsPort;

    public AnalyticsService(LoadAnalyticsPort loadAnalyticsPort) {
        this.loadAnalyticsPort = loadAnalyticsPort;
    }

    @Override
    public AnalyticsResult getAnalytics(AnalyticsQuery analyticsQuery) {
        List<DataAnalytics> analyticsResults = loadAnalyticsPort.loadAnalytics(analyticsQuery);

        long totalAnalyticsCount = loadAnalyticsPort.countAnalytics(analyticsQuery);

        int pageSize = analyticsQuery.getSize();
        int currentPage = analyticsQuery.getPage();
        int totalPages = (int) Math.ceil((double) totalAnalyticsCount / pageSize);

        return new AnalyticsResult(analyticsResults, totalAnalyticsCount, totalPages, currentPage, pageSize);
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
                .map(analyticsTypeEntry -> new AnalyticsTypeInfo(
                        analyticsTypeEntry.getKey().name(),
                        analyticsTypeEntry.getKey().getCategory(),
                        analyticsTypeEntry.getKey().getDescription(),
                        analyticsTypeEntry.getValue()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public AnalyticsSummary getAnalyticsSummary() {
        long totalAnalyticsCount = loadAnalyticsPort.getTotalAnalyticsCount();
        long uniqueAnalyticsTypesCount = loadAnalyticsPort.getUniqueAnalyticsTypesCount();

        String latestAnalyticsTimestamp = loadAnalyticsPort.getLatestAnalyticsTimestamp().orElse("N/A");
        String oldestAnalyticsTimestamp = loadAnalyticsPort.getOldestAnalyticsTimestamp().orElse("N/A");

        Map<String, Long> analyticsCountByCategory = loadAnalyticsPort.loadAnalyticsCountsByCategory();
        List<AnalyticsSummary.CategorySummary> categorySummaries = analyticsCountByCategory.entrySet().stream()
                .map(categoryEntry -> {
                    double percentageOfTotal = totalAnalyticsCount > 0
                            ? (double) categoryEntry.getValue() / totalAnalyticsCount * 100
                            : 0.0;
                    return new AnalyticsSummary.CategorySummary(
                            categoryEntry.getKey(),
                            categoryEntry.getValue(),
                            percentageOfTotal
                    );
                })
                .collect(Collectors.toList());

        return new AnalyticsSummary(
                totalAnalyticsCount,
                uniqueAnalyticsTypesCount,
                latestAnalyticsTimestamp,
                oldestAnalyticsTimestamp,
                categorySummaries
        );
    }
}