package com.example.demo.jpa.data;

import org.springframework.data.domain.Sort.Direction;

public interface Seekable {

    static Seekable unseek() {
        return Unseek.INSTANCE;
    }

    static Seekable ofSize(int size) {
        return SeekRequest.ofSize(size);
    }

    static Seekable of(Comparable lastSeen, int size) {
        return SeekRequest.of(lastSeen, size, Direction.ASC);
    }

    static Seekable of(Comparable lastSeen, int size, Direction sort) {
        return SeekRequest.of(lastSeen, size, sort);
    }

    default boolean asc() {
        return true;
    }

    default boolean desc() {
        return !asc();
    }

    default boolean isSeek() {
        return true;
    }

    default boolean isUnseek() {
        return !isSeek();
    }

    Direction direction();

    Comparable lastSeen();

    int getPageSize();

    Seekable first();

    Seekable next(Comparable lastSeen);

    boolean hasPrevious();
}
