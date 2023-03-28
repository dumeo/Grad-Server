package com.grad;

import org.springframework.data.relational.core.sql.In;

import java.util.List;

public class ClassB {
    private List<Integer> listB;

    public ClassB(List<Integer> listB) {
        this.listB = listB;
    }

    public List<Integer> getListB() {
        return listB;
    }
}
