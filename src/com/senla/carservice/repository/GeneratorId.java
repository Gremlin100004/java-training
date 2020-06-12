package com.senla.carservice.repository;

// для нейминга в случае двух существительных используй английский порядок слов - обратный (IdGenerator)
// этот класс - не репозиторий и не должен лежать в этом пакете, это утилитный класс, ему не нужен ни интерфейс
// статического метода достаточно
// кроме того, создай новый объект генератора - и айдишки пойдут с нуля
public class GeneratorId implements IGeneratorId {
    private Long id;

    public GeneratorId() {
        this.id = 0L;
    }

    @Override
    public Long getId() {
        return this.id++;
    }
}