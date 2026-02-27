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
    public AnalyticsResult getAnalytics(AnalyticsQuery query) {
        List<DataAnalytics> analytics = loadAnalyticsPort.loadAnalytics(query);
        long totalElements = loadAnalyticsPort.countAnalytics(query);

        int pageSize = query.getSize();
        int currentPage = query.getPage();
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        return new AnalyticsResult(analytics, totalElements, totalPages, currentPage, pageSize);
    }

    @Override
    public Optional<DataAnalytics> getAnalyticsById(String analyticsId) {
        return loadAnalyticsPort.loadAnalyticsById(analyticsId);
    }

    @Override
    @Cacheable(value = "analytics", key = "'analytics-types'")
    public List<AnalyticsTypeInfo> getAvailableAnalyticsTypes() {
        Map<AnalyticsType, Long> countsByType = loadAnalyticsPort.loadAnalyticsCountsByType();

        return countsByType.entrySet().stream()
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
        long totalAnalytics = loadAnalyticsPort.getTotalAnalyticsCount();
        long uniqueTypes = loadAnalyticsPort.getUniqueAnalyticsTypesCount();

        String latestDate = loadAnalyticsPort.getLatestAnalyticsTimestamp().orElse("N/A");
        String oldestDate = loadAnalyticsPort.getOldestAnalyticsTimestamp().orElse("N/A");

        Map<String, Long> countsByCategory = loadAnalyticsPort.loadAnalyticsCountsByCategory();
        List<AnalyticsSummary.CategorySummary> categorySummaries = countsByCategory.entrySet().stream()
                .map(entry -> {
                    double percentage = totalAnalytics > 0
                            ? (double) entry.getValue() / totalAnalytics * 100
                            : 0.0;
                    return new AnalyticsSummary.CategorySummary(
                            entry.getKey(),
                            entry.getValue(),
                            percentage
                    );
                })
                .collect(Collectors.toList());

        return new AnalyticsSummary(
                totalAnalytics,
                uniqueTypes,
                latestDate,
                oldestDate,
                categorySummaries
        );
    }
}