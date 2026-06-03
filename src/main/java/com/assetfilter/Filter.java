package com.assetfilter;

import java.util.Map;

public interface Filter {
    boolean matches(Map<String, String> asset);
    String toString();
}