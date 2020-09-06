package com.senla.carservice.hibernatedao;

import com.senla.carservice.domain.Master;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

public interface MasterDao extends GenericDao<Master, Long> {

    List<Master> getBusyMasters(Date date, Session session);

    List<Master> getMasterSortByAlphabet(Session session);

    List<Master> getMasterSortByBusy(Session session);

    Long getNumberMasters(Session session);

    Master getMasterById(Long index, Session session);

    Long getNumberBusyMasters(Date executeDate, Session session);
}