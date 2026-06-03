package com.assetfilter.filter;

import com.assetfilter.Filter;

import java.util.Map;

public class PropertyExistsFilter implements Filter {

    private final String property;

    public PropertyExistsFilter(String property) {
        this.property = property;
    }

    @Override
    public boolean matches(Map<String, String> asset) {
        return asset.containsKey(property);
    }

    @Override
    public String toString() {
        return "esiste la property " + property;
    }
}