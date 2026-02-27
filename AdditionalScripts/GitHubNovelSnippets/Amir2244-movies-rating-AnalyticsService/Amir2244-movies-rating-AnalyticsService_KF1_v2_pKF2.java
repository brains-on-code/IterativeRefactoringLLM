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

@Service
public class AnalyticsService implements GetAnalyticsUseCase {

    private static final String ANALYTICS_TYPES_CACHE_KEY = "'analytics-types'";
    private static final String DEFAULT_TIMESTAMP = "N/A";

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
        int totalPages = calculateTotalPages(totalCount, pageSize);

        return new AnalyticsResult(analytics, totalCount, totalPages, pageNumber, pageSize);
    }

    @Override
    public Optional<DataAnalytics> getAnalyticsById(String id) {
        return loadAnalyticsPort.loadAnalyticsById(id);
    }

    @Override
    @Cacheable(value = "analytics", key = ANALYTICS_TYPES_CACHE_KEY)
    public List<AnalyticsTypeInfo> getAnalyticsTypes() {
        Map<AnalyticsType, Long> analyticsCountsByType = loadAnalyticsPort.loadAnalyticsCountsByType();

        return analyticsCountsByType.entrySet().stream()
                .map(entry -> new AnalyticsTypeInfo(
                        entry.getKey().name(),
                        entry.getKey().getCategory(),
                        entry.getKey().getDescription(),
                        entry.getValue()
                ))
                .toList();
    }

    @Override
    public AnalyticsSummary getAnalyticsSummary() {
        long totalAnalyticsCount = loadAnalyticsPort.getTotalAnalyticsCount();
        long uniqueAnalyticsTypesCount = loadAnalyticsPort.getUniqueAnalyticsTypesCount();

        String latestTimestamp = loadAnalyticsPort.getLatestAnalyticsTimestamp()
                .orElse(DEFAULT_TIMESTAMP);
        String oldestTimestamp = loadAnalyticsPort.getOldestAnalyticsTimestamp()
                .orElse(DEFAULT_TIMESTAMP);

        List<AnalyticsSummary.CategorySummary> categorySummaries =
                buildCategorySummaries(totalAnalyticsCount);

        return new AnalyticsSummary(
                totalAnalyticsCount,
                uniqueAnalyticsTypesCount,
                latestTimestamp,
                oldestTimestamp,
                categorySummaries
        );
    }

    private int calculateTotalPages(long totalCount, int pageSize) {
        if (pageSize <= 0) {
            return 0;
        }
        return (int) Math.ceil((double) totalCount / pageSize);
    }

    private List<AnalyticsSummary.CategorySummary> buildCategorySummaries(long totalAnalyticsCount) {
        Map<String, Long> analyticsCountsByCategory =
                loadAnalyticsPort.loadAnalyticsCountsByCategory();

        return analyticsCountsByCategory.entrySet().stream()
                .map(entry -> new AnalyticsSummary.CategorySummary(
                        entry.getKey(),
                        entry.getValue(),
                        calculatePercentage(entry.getValue(), totalAnalyticsCount)
                ))
                .toList();
    }

    private double calculatePercentage(long count, long total) {
        if (total <= 0) {
            return 0.0;
        }
        return (double) count / total * 100;
    }
}