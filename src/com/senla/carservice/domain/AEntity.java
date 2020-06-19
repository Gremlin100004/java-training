package com.senla.carservice.domain;

// сейчас твой класс - не абстрактный, это интерфейс
// в абстрактном классе могут быть поля - чтобы вынести общую часть из наследников
public abstract class AEntity {

    public AEntity() {
    }

    // ты уже пометил класс abstract
    public abstract Long getId();

    public abstract void setId(Long id);
}