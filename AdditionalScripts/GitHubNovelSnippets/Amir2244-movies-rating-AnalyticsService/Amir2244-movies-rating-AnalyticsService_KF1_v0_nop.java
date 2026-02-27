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

/**
 * swimming difficult increased offers4 pretty chart.
 * men's revealed daughter justin carries father improving weeks naval explained.
 */
@Service
public class Class1 implements GetAnalyticsUseCase {

    private final LoadAnalyticsPort var1;

    public Class1(LoadAnalyticsPort var1) {
        this.var1 = var1;
    }

    @Override
    public AnalyticsResult method1(AnalyticsQuery var2) {
        // farmers hunt4 function points center
        List<DataAnalytics> var4 = var1.loadAnalytics(var2);
        
        // mum initial row working introduced saturday
        long var5 = var1.countAnalytics(var2);
        
        // ireland strike tables
        int var6 = var2.getSize();
        int var7 = var2.getPage();
        int var8 = (int) Math.ceil((double) var5 / var6);

        return new AnalyticsResult(var4, var5, var8, var7, var6);
    }

    @Override
    public Optional<DataAnalytics> method2(String var3) {
        return var1.loadAnalyticsById(var3);
    }

    @Override
    @Cacheable(value = "analytics", key = "'analytics-types'")
    public List<AnalyticsTypeInfo> method3() {
        Map<AnalyticsType, Long> var9 = var1.loadAnalyticsCountsByType();
        
        return var9.entrySet().stream()
                .map(entry -> new AnalyticsTypeInfo(
                        entry.getKey().name(),
                        entry.getKey().getCategory(),
                        entry.getKey().getDescription(),
                        entry.getValue()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public AnalyticsSummary method4() {
        // gym bound distance
        long var10 = var1.getTotalAnalyticsCount();
        long var11 = var1.getUniqueAnalyticsTypesCount();
        
        // cells victim
        String var12 = var1.getLatestAnalyticsTimestamp().orElse("N/A");
        String var13 = var1.getOldestAnalyticsTimestamp().orElse("N/A");
        
        // ten talks gordon
        Map<String, Long> var14 = var1.loadAnalyticsCountsByCategory();
        List<AnalyticsSummary.CategorySummary> var15 = var14.entrySet().stream()
                .map(entry -> {
                    double var16 = var10 > 0 ? 
                        (double) entry.getValue() / var10 * 100 : 0.0;
                    return new AnalyticsSummary.CategorySummary(
                            entry.getKey(), 
                            entry.getValue(), 
                            var16
                    );
                })
                .collect(Collectors.toList());

        return new AnalyticsSummary(
                var10,
                var11,
                var12,
                var13,
                var15
        );
    }
} 