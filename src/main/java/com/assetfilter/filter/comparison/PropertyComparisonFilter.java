package com.assetfilter.filter.comparison;

import com.assetfilter.Filter;

import java.util.Map;

public class PropertyComparisonFilter implements Filter {

    private final String property;
    private final ComparisonOperator operator;
    private final double threshold;

    public PropertyComparisonFilter(
            String property,
            ComparisonOperator operator,
            double threshold) {

        this.property = property;
        this.operator = operator;
        this.threshold = threshold;
    }

    @Override
    public boolean matches(Map<String, String> asset) {

        String value = asset.get(property);

        if (value == null) {
            return false;
        }

        try {

            double actual = Double.parseDouble(value);

            return switch (operator) {
                case GT -> actual > threshold;
                case GTE -> actual >= threshold;
                case LT -> actual < threshold;
                case LTE -> actual <= threshold;
            };

        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "property " + property + " " + operator.getDesc() + " " + formatNumber(threshold);
    }

    private String formatNumber(double value) {
        if (value == (long) value) {
            return String.format("%d", (long) value);
        }
        return String.valueOf(value);
    }
}