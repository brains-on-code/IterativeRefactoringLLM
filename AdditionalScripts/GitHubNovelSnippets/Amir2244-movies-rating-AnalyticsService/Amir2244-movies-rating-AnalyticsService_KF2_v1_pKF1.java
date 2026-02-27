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

    private final LoadAnalyticsPort analyticsPersistencePort;

    public AnalyticsService(LoadAnalyticsPort analyticsPersistencePort) {
        this.analyticsPersistencePort = analyticsPersistencePort;
    }

    @Override
    public AnalyticsResult getAnalytics(AnalyticsQuery query) {
        List<DataAnalytics> analyticsItems = analyticsPersistencePort.loadAnalytics(query);

        long totalAnalyticsCount = analyticsPersistencePort.countAnalytics(query);

        int pageSize = query.getSize();
        int currentPage = query.getPage();
        int totalPages = (int) Math.ceil((double) totalAnalyticsCount / pageSize);

        return new AnalyticsResult(analyticsItems, totalAnalyticsCount, totalPages, currentPage, pageSize);
    }

    @Override
    public Optional<DataAnalytics> getAnalyticsById(String analyticsId) {
        return analyticsPersistencePort.loadAnalyticsById(analyticsId);
    }

    @Override
    @Cacheable(value = "analytics", key = "'analytics-types'")
    public List<AnalyticsTypeInfo> getAvailableAnalyticsTypes() {
        Map<AnalyticsType, Long> analyticsCountByType = analyticsPersistencePort.loadAnalyticsCountsByType();

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
        long totalAnalyticsCount = analyticsPersistencePort.getTotalAnalyticsCount();
        long uniqueAnalyticsTypesCount = analyticsPersistencePort.getUniqueAnalyticsTypesCount();

        String latestAnalyticsTimestamp = analyticsPersistencePort.getLatestAnalyticsTimestamp().orElse("N/A");
        String oldestAnalyticsTimestamp = analyticsPersistencePort.getOldestAnalyticsTimestamp().orElse("N/A");

        Map<String, Long> analyticsCountByCategory = analyticsPersistencePort.loadAnalyticsCountsByCategory();
        List<AnalyticsSummary.CategorySummary> categorySummaries = analyticsCountByCategory.entrySet().stream()
                .map(categoryCountEntry -> {
                    double percentageOfTotal = totalAnalyticsCount > 0
                            ? (double) categoryCountEntry.getValue() / totalAnalyticsCount * 100
                            : 0.0;
                    return new AnalyticsSummary.CategorySummary(
                            categoryCountEntry.getKey(),
                            categoryCountEntry.getValue(),
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