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
        List<DataAnalytics> analyticsItems = loadAnalyticsPort.loadAnalytics(analyticsQuery);
        long totalAnalyticsCount = loadAnalyticsPort.countAnalytics(analyticsQuery);

        int pageSize = analyticsQuery.getSize();
        int pageNumber = analyticsQuery.getPage();
        int totalPages = (int) Math.ceil((double) totalAnalyticsCount / pageSize);

        return new AnalyticsResult(analyticsItems, totalAnalyticsCount, totalPages, pageNumber, pageSize);
    }

    @Override
    public Optional<DataAnalytics> getAnalyticsById(String analyticsId) {
        return loadAnalyticsPort.loadAnalyticsById(analyticsId);
    }

    @Override
    @Cacheable(value = "analytics", key = "'analytics-types'")
    public List<AnalyticsTypeInfo> getAnalyticsTypes() {
        Map<AnalyticsType, Long> analyticsCountByType = loadAnalyticsPort.loadAnalyticsCountsByType();

        return analyticsCountByType.entrySet().stream()
                .map(typeCountEntry -> {
                    AnalyticsType analyticsType = typeCountEntry.getKey();
                    Long analyticsCount = typeCountEntry.getValue();
                    return new AnalyticsTypeInfo(
                            analyticsType.name(),
                            analyticsType.getCategory(),
                            analyticsType.getDescription(),
                            analyticsCount
                    );
                })
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
                .map(categoryCountEntry -> {
                    String categoryName = categoryCountEntry.getKey();
                    long categoryAnalyticsCount = categoryCountEntry.getValue();
                    double percentageOfTotalAnalytics = totalAnalyticsCount > 0
                            ? (double) categoryAnalyticsCount / totalAnalyticsCount * 100
                            : 0.0;
                    return new AnalyticsSummary.CategorySummary(
                            categoryName,
                            categoryAnalyticsCount,
                            percentageOfTotalAnalytics
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