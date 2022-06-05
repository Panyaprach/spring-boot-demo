package com.example.demo.jpa.data;

import org.springframework.data.util.Streamable;

import java.util.Collections;

public interface Seek<T> extends Streamable<T> {

    static <T> Seek<T> empty() {
        return empty(Seekable.unseek());
    }

    static <T> Seek<T> empty(Seekable seekable) {
        return new SeekImpl<>(Collections.emptyList(), seekable);
    }

    Seekable getSeekable();

    int getSize();

    boolean hasContent();
}
