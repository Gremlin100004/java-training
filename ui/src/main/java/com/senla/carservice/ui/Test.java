package com.senla.carservice.ui;

import com.senla.carservice.DateUtil;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.hibernatedao.MasterDao;
import com.senla.carservice.hibernatedao.MasterDaoImpl;
import com.senla.carservice.hibernatedao.OrderDao;
import com.senla.carservice.hibernatedao.OrderDaoImpl;
import com.senla.carservice.hibernatedao.util.HibernateUtil;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        MasterDao masterDao = new MasterDaoImpl();
        OrderDao orderDao = new OrderDaoImpl();
        Date startDate = DateUtil.getDatesFromString("2020-09-19 10:00", true);
        Date endDate = DateUtil.getDatesFromString("2020-09-20 10:00", true);
        List<Master> masters = masterDao.getBusyMasters(startDate);
        masters.forEach(System.out::println);
//        Long numberMasters = orderDao.getNumberBusyPlaces(startDate, endDate, hibernateSessionFactory.getSession());
//        System.out.println(numberMasters);
//        Master master = masterDao.getMasterById((long) 1, hibernateSessionFactory.getSession());
//        System.out.println(master);
        List<Order> orders = orderDao.getCompletedOrdersSortByFilingDate(startDate, endDate);
        System.out.println(orders);
        session.close();
        HibernateUtil.getSessionFactory().close();
    }
}
