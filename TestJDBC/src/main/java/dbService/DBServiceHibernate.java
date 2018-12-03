package dbService;

import dataSet.Address;
import dataSet.DataSet;
import dataSet.Phone;
import dataSet.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.transaction.Transactional;

public class DBServiceHibernate implements DBService {

    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "create";
    private static final String hibernate_enable_lazy_load_no_trans = "true";

    private final SessionFactory sessionFactory;

    public DBServiceHibernate(){
        Configuration configuration = getH2HibernateConfiguration();
        sessionFactory = createSessionFactory(configuration);
    }

    @Override
    public <T extends DataSet> void save(T dataset) {
        try (Session session = sessionFactory.openSession()){
            dataset.setId((Long) session.save(dataset));
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> datasetClass) {
        try (Session session = sessionFactory.openSession()){
            return  session.load(datasetClass, id);
        }
    }

    private Configuration getH2HibernateConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Phone.class);
        configuration.addAnnotatedClass(Address.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./h2db");
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
            sessionFactory.close();;
        }
    }
}
