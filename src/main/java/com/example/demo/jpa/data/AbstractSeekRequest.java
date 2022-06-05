package com.example.demo.jpa.data;

import java.io.Serializable;

public abstract class AbstractSeekRequest implements Seekable, Serializable {
    private static final long serialVersionUID = 1L;
    private final Comparable lastSeen;
    private final int size;

    public AbstractSeekRequest(Comparable lastSeen, int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Page size must not be less than one!");
        }

        this.lastSeen = lastSeen;
        this.size = size;
    }

    public int getPageSize() {
        return size;
    }

    public Comparable lastSeen() {
        return lastSeen;
    }

    public boolean hasPrevious() {
        return lastSeen != null;
    }

    public abstract Seekable next(Comparable lastSeen);

    public abstract Seekable first();
}
