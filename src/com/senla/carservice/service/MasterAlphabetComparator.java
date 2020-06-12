package com.senla.carservice.service;

import com.senla.carservice.domain.Master;

import java.util.Comparator;

// компараторы лучше помещать в отдельный пакет - это все-таки не сервис, а то, что использует сервис
public class MasterAlphabetComparator implements Comparator<Master> {

    @Override
    public int compare(Master masterOne, Master masterTwo) {
        // фигурные скобки для IF-ELSE в джава принято ставить ВСЕГДА!!! даже если в теле одна строка
        if (masterOne.getName() == null && masterTwo.getName() == null) return 0;
        if (masterOne.getName() == null) return -1;
        if (masterTwo.getName() == null) return 1;
        return masterOne.getName().compareTo(masterTwo.getName());
    }
}