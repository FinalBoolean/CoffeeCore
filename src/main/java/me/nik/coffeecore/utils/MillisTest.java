package me.nik.coffeecore.utils;

public class MillisTest {

    private final long start;

    public MillisTest() {
        this.start = System.currentTimeMillis();
    }

    public long getMillis() {
        return System.currentTimeMillis() - this.start;
    }
}