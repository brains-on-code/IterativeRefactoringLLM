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

    private static final String ANALYTICS_CACHE = "analytics";
    private static final String ANALYTICS_TYPES_CACHE_KEY = "'analytics-types'";
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
    @Cacheable(value = ANALYTICS_CACHE, key = ANALYTICS_TYPES_CACHE_KEY)
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

    private int calculateTotalPages(long totalElements, int pageSize) {
        if (pageSize <= 0) {
            return 0;
        }
        return (int) Math.ceil((double) totalElements / pageSize);
    }

    private List<AnalyticsSummary.CategorySummary> buildCategorySummaries(
            Map<String, Long> countsByCategory,
            long totalAnalytics
    ) {
        return countsByCategory.entrySet().stream()
                .map(entry -> toCategorySummary(entry.getKey(), entry.getValue(), totalAnalytics))
                .toList();
    }

    private double calculatePercentage(long count, long total) {
        if (total <= 0) {
            return 0.0;
        }
        return (double) count / total * 100;
    }

    private AnalyticsTypeInfo toAnalyticsTypeInfo(AnalyticsType type, Long count) {
        return new AnalyticsTypeInfo(
                type.name(),
                type.getCategory(),
                type.getDescription(),
                count
        );
    }

    private AnalyticsSummary.CategorySummary toCategorySummary(
            String category,
            Long count,
            long totalAnalytics
    ) {
        double percentage = calculatePercentage(count, totalAnalytics);
        return new AnalyticsSummary.CategorySummary(category, count, percentage);
    }
}