package tech.bananaz.spring.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventSearchCriteria {

    private String key;
    private Object value;
    private Operation operation;

}
