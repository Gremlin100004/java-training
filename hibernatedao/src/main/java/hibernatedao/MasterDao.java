package hibernatedao;

import com.senla.carservice.Master;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

public interface MasterDao extends GenericDao<Master> {

    List<Master> getFreeMasters(Date date, Session session);

    List<Master> getMasterSortByAlphabet(Session session);

    List<Master> getMasterSortByBusy(Session session);

    int getNumberMasters(Session session);

    Master getMasterById(Long index, Session session);
}