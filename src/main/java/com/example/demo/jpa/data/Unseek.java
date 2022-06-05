package com.example.demo.jpa.data;

import org.springframework.data.domain.Sort.Direction;

enum Unseek implements Seekable {

    INSTANCE;

    public boolean isSeek() {
        return false;
    }

    public Direction direction() {
        throw new UnsupportedOperationException();
    }

    public Comparable lastSeen() {
        return null;
    }

    public int getPageSize() {
        throw new UnsupportedOperationException();
    }

    public Seekable first() {
        return this;
    }

    public Seekable next(Comparable lastSeen) {
        return this;
    }

    public boolean hasPrevious() {
        return false;
    }
}
