package com.assetfilter;

import com.assetfilter.filter.*;
import com.assetfilter.filter.comparison.ComparisonOperator;
import com.assetfilter.filter.comparison.PropertyComparisonFilter;
import com.assetfilter.filter.composite.AndFilter;
import com.assetfilter.filter.composite.NotFilter;
import com.assetfilter.filter.composite.OrFilter;

import java.util.Arrays;

public final class Filters {

    private Filters() {
    }

    public static Filter exists(String property) {
        return new PropertyExistsFilter(property);
    }

    public static Filter eq(String property, String value) {
        return new PropertyEqualsFilter(property, value);
    }

    public static Filter gt(String property, double value) {
        return new PropertyComparisonFilter(
                property,
                ComparisonOperator.GT,
                value
        );
    }

    public static Filter gte(String property, double value) {
        return new PropertyComparisonFilter(
                property,
                ComparisonOperator.GTE,
                value
        );
    }

    public static Filter lt(String property, double value) {
        return new PropertyComparisonFilter(
                property,
                ComparisonOperator.LT,
                value
        );
    }

    public static Filter lte(String property, double value) {
        return new PropertyComparisonFilter(
                property,
                ComparisonOperator.LTE,
                value
        );
    }

    public static Filter and(Filter... filters) {
        return new AndFilter(filters);
    }

    public static Filter or(Filter... filters) {
        return new OrFilter(filters);
    }

    public static Filter not(Filter filter) {
        return new NotFilter(filter);
    }

    public static Filter literalTrue() {
        return new TrueFilter();
    }

    public static Filter literalFalse() {
        return new FalseFilter();
    }

    public static Filter between(String property, double min, double max) {
        return and(
                gte(property, min),
                lte(property, max)
        );
    }

    public static Filter in(String property, String... values) {
        return or(
                Arrays.stream(values)
                        .map(v -> new PropertyEqualsFilter(property, v))
                        .toArray(Filter[]::new)
        );
    }

    public static Filter allEquals(String... properties) {
        if (properties == null || properties.length < 2) {
            throw new IllegalArgumentException("At least 2 properties required");
        }

        return and(
                Arrays.stream(properties, 1, properties.length)
                        .map(p -> new PropertyEqualsPropertyFilter(properties[0], p))
                        .toArray(Filter[]::new)
        );
    }
}