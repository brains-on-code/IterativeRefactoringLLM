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

    private static final String CACHE_NAME_ANALYTICS = "analytics";
    private static final String CACHE_KEY_ANALYTICS_TYPES = "'analytics-types'";
    private static final String DEFAULT_DATE_VALUE = "N/A";

    private final LoadAnalyticsPort loadAnalyticsPort;

    public AnalyticsService(LoadAnalyticsPort loadAnalyticsPort) {
        this.loadAnalyticsPort = loadAnalyticsPort;
    }

    @Override
    public AnalyticsResult getAnalytics(AnalyticsQuery query) {
        List<DataAnalytics> analytics = loadAnalyticsPort.loadAnalytics(query);
        long totalElements = loadAnalyticsPort.countAnalytics(query);

        int pageSize = query.getSize();
        int currentPage = query.getPage();
        int totalPages = calculateTotalPages(totalElements, pageSize);

        return new AnalyticsResult(
                analytics,
                totalElements,
                totalPages,
                currentPage,
                pageSize
        );
    }

    @Override
    public Optional<DataAnalytics> getAnalyticsById(String analyticsId) {
        return loadAnalyticsPort.loadAnalyticsById(analyticsId);
    }

    @Override
    @Cacheable(value = CACHE_NAME_ANALYTICS, key = CACHE_KEY_ANALYTICS_TYPES)
    public List<AnalyticsTypeInfo> getAvailableAnalyticsTypes() {
        Map<AnalyticsType, Long> countsByType = loadAnalyticsPort.loadAnalyticsCountsByType();

        return countsByType.entrySet().stream()
                .map(entry -> toAnalyticsTypeInfo(entry.getKey(), entry.getValue()))
                .toList();
    }

    @Override
    public AnalyticsSummary getAnalyticsSummary() {
        long totalAnalytics = loadAnalyticsPort.getTotalAnalyticsCount();
        long uniqueTypes = loadAnalyticsPort.getUniqueAnalyticsTypesCount();

        String latestDate = loadAnalyticsPort.getLatestAnalyticsTimestamp()
                .orElse(DEFAULT_DATE_VALUE);
        String oldestDate = loadAnalyticsPort.getOldestAnalyticsTimestamp()
                .orElse(DEFAULT_DATE_VALUE);

        Map<String, Long> countsByCategory = loadAnalyticsPort.loadAnalyticsCountsByCategory();
        List<AnalyticsSummary.CategorySummary> categorySummaries =
                buildCategorySummaries(countsByCategory, totalAnalytics);

        return new AnalyticsSummary(
                totalAnalytics,
                uniqueTypes,
                latestDate,
                oldestDate,
                categorySummaries
        );
    }

    private static int calculateTotalPages(long totalElements, int pageSize) {
        if (pageSize <= 0 || totalElements <= 0) {
            return 0;
        }
        return (int) Math.ceil((double) totalElements / pageSize);
    }

    private static AnalyticsTypeInfo toAnalyticsTypeInfo(AnalyticsType type, Long count) {
        long safeCount = count == null ? 0L : count;
        return new AnalyticsTypeInfo(
                type.name(),
                type.getCategory(),
                type.getDescription(),
                safeCount
        );
    }

    private static List<AnalyticsSummary.CategorySummary> buildCategorySummaries(
            Map<String, Long> countsByCategory,
            long totalAnalytics
    ) {
        return countsByCategory.entrySet().stream()
                .map(entry -> toCategorySummary(entry.getKey(), entry.getValue(), totalAnalytics))
                .toList();
    }

    private static AnalyticsSummary.CategorySummary toCategorySummary(
            String category,
            long count,
            long totalAnalytics
    ) {
        double percentage = calculatePercentage(count, totalAnalytics);
        return new AnalyticsSummary.CategorySummary(
                category,
                count,
                percentage
        );
    }

    private static double calculatePercentage(long count, long totalAnalytics) {
        if (totalAnalytics <= 0) {
            return 0.0;
        }
        return (double) count * 100 / totalAnalytics;
    }
}