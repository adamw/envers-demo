/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2008, Red Hat Middleware LLC or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Middleware LLC.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package pl.softwaremill.enversdemo.working;

import org.apache.log4j.BasicConfigurator;
import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.util.Arrays;

import static org.testng.Assert.*;

/**
 * @author Adam Warski (adam at warski dot org)
 */
public class WorkingDemo {
    private EntityManagerFactory emf;
    private EntityManager em;
    private Ejb3Configuration cfg;

    public void doRun() {
        em.getTransaction().begin();

        WorkingAddress a1 = new WorkingAddress("street1");
        em.persist(a1);

        WorkingAddress a2 = new WorkingAddress("street2");
        em.persist(a2);

        WorkingPerson p1 = new WorkingPerson("john", "doe", 10);
        p1.setAddress(a1);
        em.persist(p1);

        em.getTransaction().commit();

        // ---

        em.getTransaction().begin();

        p1 = em.find(WorkingPerson.class, p1.getId());
        p1.setAge(11);

        WorkingPerson p2 = new WorkingPerson("mary", "kowalski", 99);
        p2.setAddress(a1);
        em.persist(p2);

        em.getTransaction().commit();

        // --

        em.getTransaction().begin();

        p1 = em.find(WorkingPerson.class, p1.getId());
        p1.setAddress(a2);
        p1.setName("peter");

        p2 = em.find(WorkingPerson.class, p2.getId());
        p2.setAge(35);

        em.getTransaction().commit();

        // --

        AuditReader ar = AuditReaderFactory.get(em);

        WorkingPerson p1_rev1 = ar.find(WorkingPerson.class, p1.getId(), 1);
        assertEquals(p1_rev1.getAge(), 10);

        WorkingPerson p2_rev3 = ar.find(WorkingPerson.class, p2.getId(), 3);
        assertEquals(p2_rev3.getAge(), 35);

        // --

        assertEquals(ar.getRevisions(WorkingPerson.class, p2.getId()), Arrays.asList(2, 3));

        // --

        assertEquals(ar.find(WorkingAddress.class, a1.getId(), 2).getPersons().size(), 2);
    }

    public void run() throws IOException {
        setupHibernate();
        doRun();
        System.in.read();
        clearHibernate();
    }

    private void setupHibernate() {
        cfg = new Ejb3Configuration();
        cfg.configure("hibernate.working.cfg.xml");
        cfg.addAnnotatedClass(WorkingPerson.class);
        cfg.addAnnotatedClass(WorkingAddress.class);
        cfg.addAnnotatedClass(WorkingRevEntity.class);
        emf = cfg.buildEntityManagerFactory();
        em = emf.createEntityManager();
    }

    private void clearHibernate() {
        em.close();
        emf.close();
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        new WorkingDemo().run();
    }
}
