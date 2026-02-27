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
    public AnalyticsResult method1(AnalyticsQuery query) {
        List<DataAnalytics> analytics = loadAnalyticsPort.loadAnalytics(query);
        long totalCount = loadAnalyticsPort.countAnalytics(query);

        int pageSize = query.getSize();
        int pageNumber = query.getPage();
        int totalPages = calculateTotalPages(totalCount, pageSize);

        return new AnalyticsResult(
                analytics,
                totalCount,
                totalPages,
                pageNumber,
                pageSize
        );
    }

    @Override
    public Optional<DataAnalytics> method2(String id) {
        return loadAnalyticsPort.loadAnalyticsById(id);
    }

    @Override
    @Cacheable(value = "analytics", key = ANALYTICS_TYPES_CACHE_KEY)
    public List<AnalyticsTypeInfo> method3() {
        Map<AnalyticsType, Long> analyticsCountsByType = loadAnalyticsPort.loadAnalyticsCountsByType();

        return analyticsCountsByType.entrySet().stream()
                .map(this::toAnalyticsTypeInfo)
                .toList();
    }

    @Override
    public AnalyticsSummary method4() {
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
                .map(entry -> toCategorySummary(entry, totalAnalyticsCount))
                .toList();
    }

    private double calculatePercentage(long count, long total) {
        if (total <= 0) {
            return 0.0;
        }
        return (double) count * 100 / total;
    }

    private AnalyticsTypeInfo toAnalyticsTypeInfo(Map.Entry<AnalyticsType, Long> entry) {
        AnalyticsType type = entry.getKey();
        Long count = entry.getValue();

        return new AnalyticsTypeInfo(
                type.name(),
                type.getCategory(),
                type.getDescription(),
                count
        );
    }

    private AnalyticsSummary.CategorySummary toCategorySummary(
            Map.Entry<String, Long> entry,
            long totalAnalyticsCount
    ) {
        String category = entry.getKey();
        Long count = entry.getValue();
        double percentage = calculatePercentage(count, totalAnalyticsCount);

        return new AnalyticsSummary.CategorySummary(
                category,
                count,
                percentage
        );
    }
}