package rtdc.web.server.config;

import com.google.common.collect.Lists;
import org.fluttercode.datafactory.impl.DataFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import rtdc.core.model.*;
import rtdc.web.server.service.AsteriskRealTimeService;
import rtdc.web.server.service.AuthService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;


public class TestData implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(TestData.class.getSimpleName());

    private static final Random RANDOM = new Random();
    private static final DataFactory DF = new DataFactory();
    private static final List<String> ROLES = Lists.newArrayList("Nurse", "Unit Manager", "Administrator", "Stakeholder");
    private static final List<String> PERMISSIONS = Lists.newArrayList(Permission.ADMIN, Permission.USER);


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        List<Unit> units = generateUnits(RANDOM.nextInt(10));
        units.add(0, buildUnit("Emergency", 16, 3, 4, 2, 10, 8));
        units.add(1, buildUnit("Surgery", 40, 6, 4, 4, 13, 10));
        units.add(2, buildUnit("Medicine", 32, 10, 2, 0, 9, 5));
        logger.info("Adding " + units.size() + " units");

        List<User> root = generateRootUsers();
        logger.info("Adding " + root.size() + " root users");

        //List<User> users = generateUsers(RANDOM.nextInt(100), units);
        List<User> users = generateUsers(5, units);
        logger.info("Adding " + units.size() + " users");
        users.addAll(root);

        List<Action> actions = generateActions(RANDOM.nextInt(10), users, units);
        actions.add(0, buildAction(units.get(1), Action.Status.inProgress, "Jennifer Joyce", Action.Task.offServicingTo,
                "2 Patients", new Date(1437932727048l), "Negotiate with Internal Medicine unit for them to take 2 of our patients." +
                        "A surgery patient goes temporarily to the Medicine unit because we don't have enough beds in Surgery. However," +
                        "this is risky because Medicine might also be full."));
        actions.add(1, buildAction(units.get(0), Action.Status.inProgress, "Pam Peterson", Action.Task.pushForDischarge,
                "D301, D304, D312, D319, D322", new Date(1437932727048l), "Aggressively push for all the 5 \"Potential Discharges\" to be " +
                        "actually discharged Without pushing, we would discharge 3; with pushing, we’ll discharge 4."));
        actions.add(2, buildAction(units.get(1), Action.Status.completed, "Kim Kennedy", Action.Task.holdFor,
                "ED72", new Date(1437932727048l), "Kim will contact Pam, the Unit Manager of the Emergency Department to ask her to keep one " +
                        "patient to be sent to Surgery a bit longer (this patient has a broken arm; the pain is well under control, and " +
                        "there is no risk of infection: the patient can wait a few more hours before being sent by ED to Surgery)"));
        logger.info("Adding " + actions.size() + " actions");

        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            for(User user: users) {
                session.saveOrUpdate(user);
                session.saveOrUpdate(AuthService.generateUserCredentials(user, "password"));
            }

            for(Unit unit: units)
                session.saveOrUpdate(unit);

            for(Action action: actions)
                session.saveOrUpdate(action);

            List<Message> messages = generateMessages(users);
            for(Message message: messages)
                session.saveOrUpdate(message);

            transaction.commit();

        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        }

        try{
            transaction = session.beginTransaction();
            for(User user: users)
                AsteriskRealTimeService.addUser(user, "password");
            transaction.commit();

        } catch (RuntimeException | SQLException e) {
            if(transaction != null)
                transaction.rollback();
            System.err.println("Some users were not added to Asterisk");
        } finally {
            session.close();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //Do nothing
    }

    private static List<User> generateRootUsers(){
        List<User> users = new ArrayList<>();

        User user = new User();
        user.setUsername("Nathaniel");
        user.setEmail(DF.getEmailAddress());
        user.setFirstName("Nathaniel");
        user.setLastName("Aumonttt");
        user.setPermission(Permission.ADMIN);
        user.setPhone(DF.getNumberBetween(100000000, 999999999));
        user.setRole(DF.getItem(ROLES));
        users.add(user);

        user = new User();
        user.setUsername("Qwe");
        user.setEmail(DF.getEmailAddress());
        user.setFirstName("Jonathan");
        user.setLastName("Ermel");
        user.setPermission(Permission.ADMIN);
        user.setPhone(DF.getNumberBetween(100000000, 999999999));
        user.setRole(DF.getItem(ROLES));
        users.add(user);

        return users;
    }

    private static List<Action> generateActions(int numActions, List<User> users, List<Unit> units){
        List<Action> actions = new ArrayList<>();
        Date now = new Date();

        for(int i = 0; i < numActions - 1; i++) {
            Action action = new Action();
            action.setUnit(DF.getItem(units));
            action.setRoleResponsible(DF.getItem(ROLES));
            action.setDeadline(DF.getDateBetween(now, new Date(now.getTime() + 10000l)));
            action.setDescription(DF.getRandomText(RANDOM.nextInt(500)));
            action.setStatus(DF.getItem(Action.Status.values()));
            action.setTarget("Target " + DF.getRandomText(RANDOM.nextInt(10)));
            action.setTask(DF.getItem(Action.Task.values()));
            actions.add(action);
        }

        return actions;
    }

    private static List<User> generateUsers(int numUsers, List<Unit> units){
        List<User> users = new ArrayList<>();

        for(int i = 0; i < numUsers - 1; i++) {
            User user = new User();
            user.setFirstName(DF.getFirstName());
            user.setLastName(DF.getLastName());
            user.setUsername(user.getFirstName() + " " + i);
            user.setEmail(DF.getEmailAddress());
            user.setPermission(DF.getItem(PERMISSIONS));
            user.setPhone(DF.getNumberBetween(100000000, 999999999));
            user.setRole(DF.getItem(ROLES));
            users.add(user);
        }

        return users;
    }

    public static List<Message> generateMessages(List<User> users){
        List<Message> messages = new ArrayList<>();

        for(float i = 0; i < 90; i++){
            Message demoMessage = new Message();
            demoMessage.setSender(users.get(users.size() - 2));
            demoMessage.setReceiver(users.get(users.size() - 1));
            demoMessage.setContent("Message " + (int)(i + 1));
            demoMessage.setStatus(Message.Status.read);
            demoMessage.setTimeSent(new Date(100, 8, (int)((i/90)*10), 1, (int)((i/90)*50)));
            messages.add(demoMessage);
        }

        for(int i = 0; i < users.size() - 1; i++){
            User sender = users.get(i);
            Message demoMessage = new Message();
            demoMessage.setSender(sender);
            demoMessage.setReceiver(users.get(users.size() - 1));
            demoMessage.setContent("Hello from " + sender.getFirstName() + " " + sender.getLastName() + "!");
            demoMessage.setStatus(Message.Status.delivered);
            demoMessage.setTimeSent(new Date());
            messages.add(demoMessage);
        }

        return messages;
    }

    private static List<Unit> generateUnits(int numUnits){
        List<Unit> units = new ArrayList<>();

        for(int i = 0; i < numUnits - 1; i++) {
            Unit unit = new Unit();
            unit.setName(DF.getCity() + " Unit");
            unit.setTotalBeds(DF.getNumberBetween(10, 30));
            unit.setAvailableBeds(DF.getNumberUpTo(unit.getAvailableBeds() + 1));
            unit.setTotalAdmits(DF.getNumberBetween(1, 8));
            unit.setPotentialDc(DF.getNumberBetween(1, 8));
            unit.setDcByDeadline(DF.getNumberUpTo(unit.getPotentialDc() + 1));
            unit.setAdmitsByDeadline(DF.getNumberUpTo(unit.getTotalAdmits() + 1));
            units.add(unit);
        }

        return units;
    }

    private static Unit buildUnit(String name, int totalBeds, int availableBeds, int potentialDc, int dcByDeadline,
                           int totalAdmits, int admitsByDeadline){
        Unit unit = new Unit();
        unit.setName(name);
        unit.setTotalBeds(totalBeds);
        unit.setAvailableBeds(availableBeds);
        unit.setPotentialDc(potentialDc);
        unit.setDcByDeadline(dcByDeadline);
        unit.setTotalAdmits(totalAdmits);
        unit.setAdmitsByDeadline(admitsByDeadline);
        return unit;
    }

    private static Action buildAction(Unit unit, Action.Status status, String roleResponsible, Action.Task task, String target,
                            Date date, String description){
        Action action = new Action();
        action.setUnit(unit);
        action.setStatus(status);
        action.setRoleResponsible(roleResponsible);
        action.setTask(task);
        action.setTarget(target);
        action.setDeadline(date);
        action.setDescription(description);
        return action;
    }

}
