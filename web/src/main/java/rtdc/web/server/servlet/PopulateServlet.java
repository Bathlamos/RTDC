package rtdc.web.server.servlet;

import org.hibernate.Session;
import org.hibernate.Transaction;
import rtdc.core.model.Action;
import rtdc.core.model.Unit;
import rtdc.web.server.config.PersistenceConfig;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Date;


@Path("populate")
public class PopulateServlet {

    @GET
    public void populate(){

        Unit[] units = new Unit[3];
        buildUnit(units[0] = new Unit(), "Emergency", 16, 3, 4, 2, 10, 8);
        buildUnit(units[1] = new Unit(), "Surgery", 40, 6, 4, 4, 13, 10);
        buildUnit(units[2] = new Unit(), "Medicine", 32, 10, 2, 0, 9, 5);

        Action[] actions = new Action[3];
        buildAction(actions[0] = new Action(), units[1], Action.Status.inProgress, "Jennifer Joyce", Action.Task.offServicingTo.toString(),
                "2 Patients", new Date(1437932727048l), "Negotiate with Internal Medicine unit for them to take 2 of our patients." +
                        "A surgery patient goes temporarily to the Medicine unit because we don't have enough beds in Surgery. However," +
                        "this is risky because Medicine might also be full.");
        buildAction(actions[1] = new Action(), units[0], Action.Status.inProgress, "Pam Peterson", Action.Task.pushForDischarge.toString(),
                "D301, D304, D312, D319, D322", new Date(1437932727048l), "Aggressively push for all the 5 “Potential Discharges” to be " +
                        "actually discharged Without pushing, we would discharge 3; with pushing, we’ll discharge 4.");
        buildAction(actions[2] = new Action(), units[1], Action.Status.completed, "Kim Kennedy", Action.Task.holdFor.toString(),
                "ED72", new Date(1437932727048l), "Kim will contact Pam, the Unit Manager of the Emergency Department to ask her to keep one " +
                        "patient to be sent to Surgery a bit longer (this patient has a broken arm; the pain is well under control, and " +
                        "there is no risk of infection: the patient can wait a few more hours before being sent by ED to Surgery)");

        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            for(Unit unit: units)
                session.saveOrUpdate(unit);

            for(Action action: actions)
                session.saveOrUpdate(action);

            transaction.commit();

        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    private static void buildUnit(Unit unit, String name, int totalBeds, int availableBeds, int potentialDc, int dcByDeadline,
                           int totalAdmits, int admitsByDeadline){
        unit.setName(name);
        unit.setTotalBeds(totalBeds);
        unit.setAvailableBeds(availableBeds);
        unit.setPotentialDc(potentialDc);
        unit.setDcByDeadline(dcByDeadline);
        unit.setTotalAdmits(totalAdmits);
        unit.setAdmitsByDeadline(admitsByDeadline);
    }

    private static void buildAction(Action action, Unit unit, Action.Status status, String roleResponsible, String task, String target,
                            Date date, String description){

        action.setUnit(unit);
        action.setStatus(status);
        action.setRoleResponsible(roleResponsible);
        action.setTask(task);
        action.setDeadline(date);
        action.setDescription(description);

    }
}
