package pl.softwaremill.enversdemo.working;

import org.hibernate.envers.RevisionListener;

/**
 * @author Adam Warski (adam at warski dot org)
 */
public class WorkingRevListener implements RevisionListener {
    private static int i = 0;

    public void newRevision(Object revisionEntity) {
        ((WorkingRevEntity) revisionEntity).setUsername("user" + i++);
    }
}
