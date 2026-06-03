# Asset Filter API

A composable and extensible Java API for filtering assets represented as:

```java
Map<String, String> filters;
```
- Property names are case-sensitive  
- Property values are compared case-insensitively  
- Numeric comparisons are supported via parsing to double  

---

## Core Concept

All filters implement:

```java
public interface Filter {
    boolean matches(Map<String, String> asset);
}
```