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
    public Optional<DataAnalytics> getAnalyticsById(String analyticsId) {
        return analyticsPort.loadAnalyticsById(analyticsId);
    }

    @Override
    @Cacheable(value = "analytics", key = "'analytics-types'")
    public List<AnalyticsTypeInfo> getAnalyticsTypes() {
        Map<AnalyticsType, Long> analyticsCountByType = analyticsPort.loadAnalyticsCountsByType();

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
        long totalCount = analyticsPort.getTotalAnalyticsCount();
        long uniqueTypeCount = analyticsPort.getUniqueAnalyticsTypesCount();

        String latestTimestamp = analyticsPort.getLatestAnalyticsTimestamp().orElse("N/A");
        String oldestTimestamp = analyticsPort.getOldestAnalyticsTimestamp().orElse("N/A");

        Map<String, Long> analyticsCountByCategory = analyticsPort.loadAnalyticsCountsByCategory();
        List<AnalyticsSummary.CategorySummary> categorySummaries = analyticsCountByCategory.entrySet().stream()
                .map(entry -> {
                    double percentageOfTotal = totalCount > 0
                            ? (double) entry.getValue() / totalCount * 100
                            : 0.0;
                    return new AnalyticsSummary.CategorySummary(
                            entry.getKey(),
                            entry.getValue(),
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