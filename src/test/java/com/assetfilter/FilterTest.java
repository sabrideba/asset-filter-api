package com.assetfilter;

import com.assetfilter.engine.FilterEngine;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FilterTest {

    private Map<String, String> car(String brand, String color, String year) {
        Map<String, String> map = new HashMap<>();
        map.put("brand", brand);
        map.put("color", color);
        map.put("year", year);
        return map;
    }

    @Test
    void testEqualsFilterRedCar() {
        Map<String, String> car = car("Peugeot", "red", "2020");

        Filter f = Filters.eq("color", "red");

        assertTrue(f.matches(car));
        assertEquals("property color = 'red'", f.toString());
    }

    @Test
    void testEqualsFilterFailsBlueCarFilterRedCar() {
        Map<String, String> car = car("Alfa Romeo", "blue", "2020");

        Filter f = Filters.eq("color", "red");

        assertFalse(f.matches(car));
        assertEquals("property color = 'red'", f.toString());
    }

    @Test
    void testExistsPropertyColor() {
        Map<String, String> car = car("Alfa Romeo", "red", "2020");

        Filter f = Filters.exists("color");

        assertTrue(f.matches(car));
        assertEquals("esiste la property color", f.toString());
    }

    @Test
    void testExistsFilterFails() {
        Map<String, String> car = car("Alfa Romeo", "red", "2020");

        Filter f = Filters.exists("engine");

        assertFalse(f.matches(car));
        assertEquals("esiste la property engine", f.toString());
    }

    @Test
    void testGtFilter() {
        Map<String, String> car = car("Alfa Romeo", "red", "2020");

        Filter f = Filters.gt("year", 2010);

        assertTrue(f.matches(car));
        assertEquals("property year > 2010", f.toString());
    }

    @Test
    void testGtFilterFails() {
        Map<String, String> car = car("Alfa Romeo", "red", "2009");

        Filter f = Filters.gt("year", 2010);

        assertFalse(f.matches(car));
        assertEquals("property year > 2010", f.toString());
    }

    @Test
    void testGteFilterEdge() {
        Map<String, String> car = car("Alfa Romeo", "red", "2010");

        Filter f = Filters.gte("year", 2010);

        assertTrue(f.matches(car));
        assertEquals("property year >= 2010", f.toString());
    }

    @Test
    void testLtFilter() {
        Map<String, String> car = car("Alfa Romeo", "red", "2000");

        Filter f = Filters.lt("year", 2010);

        assertTrue(f.matches(car));
        assertEquals("property year < 2010", f.toString());
    }

    @Test
    void testLteFilter() {
        Map<String, String> car = car("Alfa Romeo", "red", "2010");

        Filter f = Filters.lte("year", 2010);

        assertTrue(f.matches(car));
        assertEquals("property year <= 2010", f.toString());
    }

    @Test
    void testAndFilter() {
        Map<String, String> car = car("Alfa Romeo", "red", "2020");

        Filter f = Filters.and(
                Filters.eq("color", "red"),
                Filters.gt("year", 2010)
        );

        assertTrue(f.matches(car));
        assertEquals("(property color = 'red' AND property year > 2010)", f.toString());
    }

    @Test
    void testAndFilterFails() {
        Map<String, String> car = car("Alfa Romeo", "red", "2009");

        Filter f = Filters.and(
                Filters.eq("color", "red"),
                Filters.gt("year", 2010)
        );

        assertFalse(f.matches(car));
        assertEquals("(property color = 'red' AND property year > 2010)", f.toString());
    }

    @Test
    void testOrFilter() {
        Map<String, String> car = car("Alfa Romeo", "blue", "2020");

        Filter f = Filters.or(
                Filters.eq("color", "red"),
                Filters.gt("year", 2010)
        );

        assertTrue(f.matches(car));
        assertEquals("(property color = 'red' OR property year > 2010)", f.toString());
    }

    @Test
    void testNotFilter() {
        Map<String, String> car = car("Alfa Romeo", "red", "2020");

        Filter f = Filters.not(
                Filters.eq("color", "red")
        );

        assertFalse(f.matches(car));
        assertEquals("NOT (property color = 'red')", f.toString());
    }

    @Test
    void testMissingProperty() {
        Map<String, String> car = car("Alfa Romeo", "red", "2020");

        Filter f = Filters.eq("engine", "v6");

        assertFalse(f.matches(car));
        assertEquals("property engine = 'v6'", f.toString());
    }

    @Test
    void testInvalidNumberFormat() {
        Map<String, String> car = car("Alfa Romeo", "red", "not_a_number");

        Filter f = Filters.gt("year", 2010);

        assertFalse(f.matches(car));
        assertEquals("property year > 2010", f.toString());
    }

    @Test
    void testComplexFilter() {
        Map<String, String> car = car("Alfa Romeo", "red", "2020");

        Filter f = Filters.and(
                Filters.eq("color", "red"),
                Filters.or(
                        Filters.eq("brand", "BMW"),
                        Filters.gt("year", 2010)
                )
        );

        assertTrue(f.matches(car));
        assertEquals("(property color = 'red' AND (property brand = 'BMW' OR property year > 2010))", f.toString());
    }

    @Test
    void testBetween() {
        Map<String, String> car = car("Alfa Romeo", "red", "2015");

        Filter f = Filters.between("year", 2010, 2020);

        assertTrue(f.matches(car));
        assertEquals("(property year >= 2010 AND property year <= 2020)", f.toString());
    }

    @Test
    void testAllEquals_success() {
        Map<String, String> car = car("red", "red", "red");

        Filter f = Filters.allEquals("brand", "color", "year");

        assertTrue(f.matches(car));
        assertEquals("(brand == color AND brand == year)", f.toString());
    }

    @Test
    void testAllEquals_fail_differentValues() {
        Map<String, String> car = car("red", "blue", "red");

        Filter f = Filters.allEquals("brand", "color", "year");

        assertFalse(f.matches(car));
        assertEquals("(brand == color AND brand == year)", f.toString());
    }

    @Test
    void testAllEquals_fail_missingProperty() {
        Map<String, String> car = new HashMap<>();
        car.put("brand", "red");
        car.put("color", "red");
        // "year" mancante

        Filter f = Filters.allEquals("brand", "color", "year");

        assertFalse(f.matches(car));
        assertEquals("(brand == color AND brand == year)", f.toString());
    }

    @Test
    void testAllEquals_twoProperties_success() {
        Map<String, String> car = new HashMap<>();
        car.put("brand", "red");
        car.put("color", "red");

        Filter f = Filters.allEquals("brand", "color");

        assertTrue(f.matches(car));
        assertEquals("(brand == color)", f.toString());
    }

    @Test
    void testAllEquals_caseInsensitive() {
        Map<String, String> car = new HashMap<>();
        car.put("brand", "Red");
        car.put("color", "red");
        car.put("year", "RED");

        Filter f = Filters.allEquals("brand", "color", "year");

        assertTrue(f.matches(car));
        assertEquals("(brand == color AND brand == year)", f.toString());
    }

    @Test
    void testFalse() {
        Map<String, String> car = new HashMap<>();
        car.put("brand", "Red");
        car.put("color", "red");
        car.put("year", "RED");

        Filter f = Filters.literalFalse();

        assertFalse(f.matches(car));
        assertEquals("false", f.toString());
    }

    @Test
    void testTrue() {
        Map<String, String> car = new HashMap<>();
        car.put("brand", "Red");
        car.put("color", "red");
        car.put("year", "RED");

        Filter f = Filters.literalTrue();

        assertTrue(f.matches(car));
        assertEquals("true", f.toString());
    }

    @Test
    void testFilterList_keepOnlyRedCarsAfter2010() {
        FilterEngine engine = new FilterEngine();

        List<Map<String, String>> vehicles = List.of(
                car("Alfa Romeo", "red", "2020"),
                car("BMW", "blue", "2015"),
                car("Audi", "red", "2009"),
                car("Fiat", "red", "2018")
        );

        Filter filter = Filters.and(
                Filters.eq("color", "red"),
                Filters.gt("year", 2010)
        );

        List<Map<String, String>> result = engine.apply(vehicles, filter);

        assertEquals(2, result.size());

        assertTrue(result.stream().anyMatch(v -> v.get("brand").equals("Alfa Romeo")));
        assertTrue(result.stream().anyMatch(v -> v.get("brand").equals("Fiat")));
    }

    @Test
    void testFilterList_allFilteredOut() {

        List<Map<String, String>> vehicles = List.of(
                car("BMW", "blue", "2005"),
                car("Audi", "black", "2009")
        );

        Filter filter = Filters.and(
                Filters.eq("color", "red"),
                Filters.gt("year", 2010)
        );

        List<Map<String, String>> result = new FilterEngine().apply(vehicles, filter);

        assertTrue(result.isEmpty());
    }

    @Test
    void testFilterList_orLogic() {

        List<Map<String, String>> vehicles = List.of(
                car("BMW", "blue", "2020"),
                car("Audi", "red", "2005"),
                car("Fiat", "green", "2005")
        );

        Filter filter = Filters.or(
                Filters.eq("color", "red"),
                Filters.gt("year", 2010)
        );

        List<Map<String, String>> result = new FilterEngine().apply(vehicles, filter);

        assertEquals(2, result.size());
    }

    @Test
    void testFilterIn() {

        Map<String, String> car = car("Alfa Romeo", "blue", "2020");

        Filter filter = Filters.in("color", "red", "blue");

        List<Map<String, String>> result = new FilterEngine().apply(List.of(car), filter);

        assertEquals(1, result.size());
    }
}