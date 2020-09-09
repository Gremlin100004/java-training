package com.senla.carservice.ui;

import com.senla.carservice.domain.Master;
import com.senla.carservice.hibernatedao.MasterDao;
import com.senla.carservice.hibernatedao.MasterDaoImpl;
import com.senla.carservice.hibernatedao.OrderDao;
import com.senla.carservice.hibernatedao.OrderDaoImpl;
import com.senla.carservice.util.DateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        MasterDao masterDao = new MasterDaoImpl();
        OrderDao orderDao = new OrderDaoImpl();
        Session session = masterDao.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Date startDate = DateUtil.getDatesFromString("2020-09-15 10:00", true);
//        Date endDate = DateUtil.getDatesFromString("2020-09-20 10:00", true);
        List<Master> masters = masterDao.getFreeMasters(startDate);
        masters.forEach(System.out::println);
//        Order order = orderDao.getLastOrder();
//        System.out.println(order);
        transaction.rollback();
    }
}