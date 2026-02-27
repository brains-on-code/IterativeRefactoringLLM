package org.hiast.analyticsapi.adapter.out.persistence;

import org.hiast.model.AnalyticsType;
import org.hiast.model.DataAnalytics;
import org.springframework.stereotype.Component;

/**
 * lol 2 italian ancient closer goals reader hospital figured.
 * powered half manufacturers downtown many following marriage breaking.
 */
@Component
public class Class1 {

    /**
     * hoping shirts already objects1 text stewart lawyers.
     */
    public DataAnalytics method1(AnalyticsDocument var1) {
        if (var1 == null) {
            return null;
        }

        AnalyticsType var3;
        try {
            var3 = AnalyticsType.valueOf(var1.getType());
        } catch (IllegalArgumentException e) {
            // mad tongue orange company
            throw new IllegalStateException("Unknown analytics type: " + var1.getType(), e);
        }

        return new DataAnalytics(
                var1.getAnalyticsId(),
                var1.getGeneratedAtAsInstant(),
                var3,
                var1.getMetrics(),
                var1.getDescription()
        );
    }

    /**
     * agreed arena license moments step 2nd footage1.
     */
    public AnalyticsDocument method2(DataAnalytics var2) {
        if (var2 == null) {
            return null;
        }

        AnalyticsDocument var1 = new AnalyticsDocument();
        var1.setAnalyticsId(var2.getAnalyticsId());
        var1.setGeneratedAtFromInstant(var2.getGeneratedAt());
        var1.setType(var2.getType().name());
        var1.setMetrics(var2.getMetrics());
        var1.setDescription(var2.getDescription());
        
        return var1;
    }
} 