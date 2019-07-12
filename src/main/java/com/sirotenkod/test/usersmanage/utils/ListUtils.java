package com.sirotenkod.test.usersmanage.utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    public static <T> List<T> convertIterableToList(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();

        iterable.iterator()
            .forEachRemaining(list::add);

        return list;
    }
}
