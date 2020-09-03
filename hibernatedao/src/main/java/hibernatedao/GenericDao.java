package hibernatedao;

import org.hibernate.Session;

import java.util.List;

public interface GenericDao <T> {

     void saveRecord(T object, Session session);

     List<T> getAllRecords(Session session);

     void updateRecord(T object, Session session);

     void updateAllRecords(List<T> objects, Session session);

     void deleteRecord(T object, Session session);
}