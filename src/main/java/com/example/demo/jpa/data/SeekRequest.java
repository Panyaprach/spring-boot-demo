package com.example.demo.jpa.data;

import org.springframework.data.domain.Sort.Direction;

public class SeekRequest extends AbstractSeekRequest {
    private final Direction direction;

    protected SeekRequest(Comparable lastSeen, int size, Direction direction) {
        super(lastSeen, size);

        this.direction = direction;
    }

    public static SeekRequest of(Comparable lastSeen, int size) {
        return of(lastSeen, size, Direction.ASC);
    }

    public static SeekRequest of(Comparable lastSeen, int size, Direction sort) {
        return new SeekRequest(lastSeen, size, sort);
    }

    public static SeekRequest ofSize(int size) {
        return of(null, size);
    }

    public Direction direction() {
        return direction;
    }

    public boolean asc() {
        return direction.isAscending();
    }

    public Seekable next(Comparable lastSeen) {
        return new SeekRequest(lastSeen, getPageSize(), direction);
    }

    public Seekable first() {
        return new SeekRequest(null, getPageSize(), direction);
    }
}
