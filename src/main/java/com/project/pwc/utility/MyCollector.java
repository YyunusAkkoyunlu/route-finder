package com.project.pwc.utility;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MyCollector {

    public static <T> Collector<T, ?, List<T>> reverse() {

        return Collectors.collectingAndThen(Collectors.toList(), list -> {
            Collections.reverse(list);
            return list;
        });
    }

}
