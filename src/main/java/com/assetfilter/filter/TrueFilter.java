package com.assetfilter.filter;

import com.assetfilter.Filter;

import java.util.Map;

public class TrueFilter implements Filter {

    @Override
    public boolean matches(Map<String, String> asset) {
        return true;
    }

    @Override
    public String toString() {
        return "true";
    }
}