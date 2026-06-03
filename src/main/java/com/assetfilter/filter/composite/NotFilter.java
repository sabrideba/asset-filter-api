package com.assetfilter.filter.composite;

import com.assetfilter.Filter;

import java.util.Map;

public class NotFilter implements Filter {

    private final Filter filter;

    public NotFilter(Filter filter) {
        this.filter = filter;
    }

    @Override
    public boolean matches(Map<String, String> asset) {
        return !filter.matches(asset);
    }

    @Override
    public String toString() {
        return "NOT (" + filter + ")";
    }
}