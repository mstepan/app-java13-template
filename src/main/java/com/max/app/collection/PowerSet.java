package com.max.app.collection;

import com.max.app.MainSerialization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class PowerSet<T> extends AbstractList<List<T>> {

    private static final Logger LOG = LoggerFactory.getLogger(PowerSet.class);

    private final List<T> elements;
    private final int length;
    private final int size;

    private static final int MAX_SUPPORTED_ELEMENTS_COUNT = 30;

    public PowerSet(List<T> initialElements) {
        Objects.requireNonNull(initialElements, "null 'elements' passed");
        checkMaxPowerSetSize(initialElements);
        checkForDuplicates(initialElements);

        this.elements = new ArrayList<>(initialElements);

        if (elements.size() != initialElements.size()) {
            LOG.info("Duplicates were removed");
        }

        this.length = elements.size();
        this.size = 1 << elements.size();
    }

    private void checkForDuplicates(List<T> elements) {
        Set<T> unique = new HashSet<>(elements);
        if (unique.size() != elements.size()) {
            throw new IllegalArgumentException("Duplicates in List detected:  " + elements);
        }
    }

    private static <U> void checkMaxPowerSetSize(List<U> initialElements) {
        if (initialElements.size() > MAX_SUPPORTED_ELEMENTS_COUNT) {
            throw new IllegalArgumentException("PowerSet will be too big with value: " +
                                                       initialElements.size() + ", max supported size is: " +
                                                       MAX_SUPPORTED_ELEMENTS_COUNT);
        }
    }

    @Override
    public List<T> get(int index) {

        Objects.checkIndex(index, size);

        final List<T> res = new ArrayList<>(length);

        for (int i = 0, maskedValue = index; i < length && maskedValue != 0; ++i, maskedValue >>= 1) {
            if ((maskedValue & 1) != 0) {
                res.add(elements.get(i));
            }
        }

        return res;
    }

    @Override
    public int size() {
        return size;
    }
}
