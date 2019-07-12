package com.sirotenkod.test.usersmanage.utils;

import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SortUtils {
    public static Sort sortParamsToSort(String[] sortParams) {
        if (Objects.isNull(sortParams)) {
            throw new IllegalArgumentException("Params can not be empty or null");
        }

        List<Sort.Order> orders = Arrays.stream(sortParams)
                .map(SortUtils::sortParamToOrder)
                .collect(Collectors.toList());

        return Sort.by(orders);
    }

    public static Sort.Order sortParamToOrder(String param) {
        String[] props = param.split("\\.", 2);

        Sort.Direction direction;

        if (props.length > 1 && !Objects.isNull(props[1])) {
            direction = Sort.Direction.fromString(props[1]);
        } else {
            direction = null;
        }

        String property = props[0];

        return new Sort.Order(direction, property);
    }
}
