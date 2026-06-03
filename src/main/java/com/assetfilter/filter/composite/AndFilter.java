package com.assetfilter.filter.composite;

import com.assetfilter.Filter;

import java.util.Arrays;
import java.util.Map;

public class AndFilter implements Filter {

    private final Filter[] filters;

    public AndFilter(Filter... filters) {
        this.filters = filters;
    }

    @Override
    public boolean matches(Map<String, String> asset) {

        return Arrays.stream(filters)
                .allMatch(f -> f.matches(asset));
    }

    @Override
    public String toString() {

        return "(" +
                Arrays.stream(filters)
                        .map(Filter::toString)
                        .reduce((a,b) -> a + " AND " + b)
                        .orElse("")
                + ")";
    }
}