package pl.softwaremill.enversdemo.demo;

import org.apache.log4j.BasicConfigurator;
import org.hibernate.ejb.Ejb3Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;

/**
 * @author Adam Warski (adam at warski dot org)
 */
public class Demo {
    private EntityManagerFactory emf;
    private EntityManager em;
    private Ejb3Configuration cfg;

    public void doRun() {
        em.getTransaction().begin();

        Person p1 = new Person("john", "doe", 10);
        em.persist(p1);

        em.getTransaction().commit();

        // ---

        em.getTransaction().begin();

        p1 = em.find(Person.class, p1.getId());
        p1.setAge(11);

        Person p2 = new Person("mary", "kowalski", 99);
        em.persist(p2);

        em.getTransaction().commit();

        // --

        em.getTransaction().begin();

        p1 = em.find(Person.class, p1.getId());
        p1.setName("peter");

        p2 = em.find(Person.class, p2.getId());
        p2.setAge(35);

        em.getTransaction().commit();
    }

    public void run() throws IOException {
        setupHibernate();
        doRun();
        System.in.read();
        clearHibernate();
    }

    private void setupHibernate() {
        cfg = new Ejb3Configuration();
        cfg.configure("hibernate.demo.cfg.xml");
        cfg.addAnnotatedClass(Person.class);
        emf = cfg.buildEntityManagerFactory();
        em = emf.createEntityManager();
    }

    private void clearHibernate() {
        em.close();
        emf.close();
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        new Demo().run();
    }
}
