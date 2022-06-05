package com.example.demo.jpa.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SeekImpl<T> implements Seek<T> {

    private final List<T> content = new ArrayList<>();
    private final Seekable seekable;

    public SeekImpl(List<T> content, Seekable seekable) {
        if (content == null) {
            throw new IllegalArgumentException("Content must not be null");
        }
        if (seekable == null) {
            throw new IllegalArgumentException("Seekable must not be null");
        }

        this.content.addAll(content);
        this.seekable = seekable;
    }

    public <U> Seek<U> map(Function<? super T, ? extends U> converter) {
        return new SeekImpl<>(getConvertedContent(converter), getSeekable());
    }

    protected <U> List<U> getConvertedContent(Function<? super T, ? extends U> converter) {
        if (converter == null) {
            throw new IllegalArgumentException("Function must not be null");
        }

        return this.stream().map(converter::apply)
                .collect(Collectors.toList());
    }

    public Iterator<T> iterator() {
        return content.iterator();
    }

    public Seekable getSeekable() {
        return seekable;
    }

    public int getSize() {
        return content.size();
    }

    public boolean hasContent() {
        return !content.isEmpty();
    }
}
