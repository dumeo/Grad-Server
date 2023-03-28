package com.grad;
import org.springframework.data.relational.core.sql.In;

import java.util.List;
public class ClassA {
    private  List<Integer> listA;

    public ClassA(List<Integer> listA) {
        this.listA = listA;
    }

    public List<Integer> getListA() {
        return listA;
    }
}
