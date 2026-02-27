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
        List<DataAnalytics> analyticsList = loadAnalyticsPort.loadAnalytics(analyticsQuery);
        long totalAnalyticsCount = loadAnalyticsPort.countAnalytics(analyticsQuery);

        int pageSize = analyticsQuery.getSize();
        int pageNumber = analyticsQuery.getPage();
        int totalPages = (int) Math.ceil((double) totalAnalyticsCount / pageSize);

        return new AnalyticsResult(analyticsList, totalAnalyticsCount, totalPages, pageNumber, pageSize);
    }

    @Override
    public Optional<DataAnalytics> getAnalyticsById(String analyticsId) {
        return loadAnalyticsPort.loadAnalyticsById(analyticsId);
    }

    @Override
    @Cacheable(value = "analytics", key = "'analytics-types'")
    public List<AnalyticsTypeInfo> getAnalyticsTypes() {
        Map<AnalyticsType, Long> analyticsCountsByType = loadAnalyticsPort.loadAnalyticsCountsByType();

        return analyticsCountsByType.entrySet().stream()
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
        long uniqueAnalyticsTypesCount = loadAnalyticsPort.getUniqueAnalyticsTypesCount();

        String latestAnalyticsTimestamp = loadAnalyticsPort.getLatestAnalyticsTimestamp().orElse("N/A");
        String oldestAnalyticsTimestamp = loadAnalyticsPort.getOldestAnalyticsTimestamp().orElse("N/A");

        Map<String, Long> analyticsCountsByCategory = loadAnalyticsPort.loadAnalyticsCountsByCategory();
        List<AnalyticsSummary.CategorySummary> categorySummaries = analyticsCountsByCategory.entrySet().stream()
                .map(entry -> {
                    double percentageOfTotal = totalAnalyticsCount > 0
                            ? (double) entry.getValue() / totalAnalyticsCount * 100
                            : 0.0;
                    return new AnalyticsSummary.CategorySummary(
                            entry.getKey(),
                            entry.getValue(),
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