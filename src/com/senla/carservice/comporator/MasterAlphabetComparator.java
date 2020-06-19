// ошибка в имени пакета
package com.senla.carservice.comporator;

import com.senla.carservice.domain.Master;

import java.util.Comparator;

// вместо компараторов можно было использовать лямбды
public class MasterAlphabetComparator implements Comparator<Master> {

    @Override
    public int compare(Master masterOne, Master masterTwo) {
        if (masterOne.getName() == null && masterTwo.getName() == null) return 0;
        if (masterOne.getName() == null) return -1;
        if (masterTwo.getName() == null) return 1;
        return masterOne.getName().compareTo(masterTwo.getName());
    }
}