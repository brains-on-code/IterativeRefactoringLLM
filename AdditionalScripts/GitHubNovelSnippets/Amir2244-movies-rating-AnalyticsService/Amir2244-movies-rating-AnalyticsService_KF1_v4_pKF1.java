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

    private final LoadAnalyticsPort analyticsPort;

    public AnalyticsService(LoadAnalyticsPort analyticsPort) {
        this.analyticsPort = analyticsPort;
    }

    @Override
    public AnalyticsResult getAnalytics(AnalyticsQuery query) {
        List<DataAnalytics> analytics = analyticsPort.loadAnalytics(query);
        long totalCount = analyticsPort.countAnalytics(query);

        int pageSize = query.getSize();
        int pageNumber = query.getPage();
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        return new AnalyticsResult(analytics, totalCount, totalPages, pageNumber, pageSize);
    }

    @Override
    public Optional<DataAnalytics> getAnalyticsById(String id) {
        return analyticsPort.loadAnalyticsById(id);
    }

    @Override
    @Cacheable(value = "analytics", key = "'analytics-types'")
    public List<AnalyticsTypeInfo> getAnalyticsTypes() {
        Map<AnalyticsType, Long> countByType = analyticsPort.loadAnalyticsCountsByType();

        return countByType.entrySet().stream()
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
        long totalCount = analyticsPort.getTotalAnalyticsCount();
        long uniqueTypeCount = analyticsPort.getUniqueAnalyticsTypesCount();

        String latestTimestamp = analyticsPort.getLatestAnalyticsTimestamp().orElse("N/A");
        String oldestTimestamp = analyticsPort.getOldestAnalyticsTimestamp().orElse("N/A");

        Map<String, Long> countByCategory = analyticsPort.loadAnalyticsCountsByCategory();
        List<AnalyticsSummary.CategorySummary> categorySummaries = countByCategory.entrySet().stream()
                .map(entry -> {
                    String category = entry.getKey();
                    long categoryCount = entry.getValue();
                    double percentageOfTotal = totalCount > 0
                            ? (double) categoryCount / totalCount * 100
                            : 0.0;
                    return new AnalyticsSummary.CategorySummary(
                            category,
                            categoryCount,
                            percentageOfTotal
                    );
                })
                .collect(Collectors.toList());

        return new AnalyticsSummary(
                totalCount,
                uniqueTypeCount,
                latestTimestamp,
                oldestTimestamp,
                categorySummaries
        );
    }
}