package dbService;

import accountService.account.UserAccount;
import app.DBService;
import cache.CacheEngine;
import cache.MyElement;
import dataSet.Address;
import dataSet.DataSet;
import dataSet.Phone;
import dataSet.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;

public class DBServiceHibernate implements DBService {

    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "create";
    private static final String hibernate_enable_lazy_load_no_trans = "true";

    private final SessionFactory sessionFactory;
    private final CacheEngine cacheEngine;

    public DBServiceHibernate(CacheEngine cacheEngine){
        Configuration configuration = getH2HibernateConfiguration();
        sessionFactory = createSessionFactory(configuration);
        this.cacheEngine = cacheEngine;
    }

    @Override
    public <T extends DataSet> void save(T dataSet) {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(dataSet);
            transaction.commit();
            cacheEngine.put(new MyElement(dataSet.getId(), dataSet));
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> dataSetClass) {
        if (cacheEngine.get(id) == null){
            try (Session session = sessionFactory.openSession()) {
                cacheEngine.put(new MyElement(id, session.get(dataSetClass, id)));
            }
        }
        return (T) cacheEngine.get(id).getValue();
    }

    @Override
    public UserAccount getUserByLogin(String login) {
        try (Session session = sessionFactory.openSession()){
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<UserAccount> criteria = criteriaBuilder.createQuery(UserAccount.class);
            Root<UserAccount> from = criteria.from(UserAccount.class);
            criteria.where(criteriaBuilder.equal(from.get("login"),login));
            Query<UserAccount> query = session.createQuery(criteria);
            UserAccount result = query.uniqueResult();
            if (result != null) {
                if (cacheEngine.get(result.getId()) == null) {
                    cacheEngine.put(new MyElement(result.getId(), result));
                }
            }else {
                return null;
            }
            return  (UserAccount) cacheEngine.get(result.getId()).getValue();
        }
    }

    @Override
    public Collection<UserAccount> getUserList() {
        try (Session session = sessionFactory.openSession()){
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<UserAccount> criteria = criteriaBuilder.createQuery(UserAccount.class);
            Root<UserAccount> from = criteria.from(UserAccount.class);
            criteria.orderBy(criteriaBuilder.asc(from.get("id")));
            Query<UserAccount> query = session.createQuery(criteria);
            return query.getResultList();
        }
    }


    private Configuration getH2HibernateConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Phone.class);
        configuration.addAnnotatedClass(Address.class);
        configuration.addAnnotatedClass(UserAccount.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:~/test");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "");

        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", hibernate_enable_lazy_load_no_trans);
        return configuration;
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public void disconnect() {
        if (!sessionFactory.isClosed()){
            sessionFactory.close();
        }
        cacheEngine.dispose();
    }
}
