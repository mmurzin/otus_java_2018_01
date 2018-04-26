package ru.otus.l0101;

import org.hibernate.Session;
import ru.otus.l0101.models.UserDataSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserDataSetDAO {
    private final Session session;

    public UserDataSetDAO(Session session) {
        this.session = session;
    }

    public void save(UserDataSet dataSet) {
        session.save(dataSet);
    }

    public UserDataSet read(long id) {
        return session.load(UserDataSet.class, id);
    }

    public List<UserDataSet> readAll() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteriaQuery =
                builder.createQuery(UserDataSet.class);
        criteriaQuery.from(UserDataSet.class);
        return session.createQuery(criteriaQuery).list();
    }
}
