package com.senla.carservice.comporator;

import com.senla.carservice.domain.Master;

import java.util.Comparator;

public class MasterBusyComparator implements Comparator<Master> {

    @Override
    public int compare(Master masterOne, Master masterTwo) {
        if (masterOne.getNumberOrder() == null && masterTwo.getNumberOrder() == null) return 0;
        if (masterOne.getNumberOrder() == null) return -1;
        if (masterTwo.getNumberOrder() == null) return 1;
        return masterOne.getNumberOrder() - masterTwo.getNumberOrder();
    }
}