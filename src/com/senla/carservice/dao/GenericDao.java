package com.senla.carservice.dao;

import java.util.List;

public interface GenericDao<T> {

    /** Создает новую запись, соответствующую объекту object */
    public T create(T object);

    /** Возвращает объект соответствующий записи с первичным ключом key или null */
    public T getByKey(int key);

    /** Сохраняет состояние объекта group в базе данных */
    public void update(T object);

    /** Удаляет запись об объекте из базы данных */
    public void delete(T object);

    /** Возвращает список объектов соответствующих всем записям в базе данных */
    public List<T> getAll();
}