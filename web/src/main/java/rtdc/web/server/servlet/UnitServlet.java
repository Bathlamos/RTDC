/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Olivier Clermont, Jonathan Ermel, Mathieu Fortin-Boulay, Philippe Legault & Nicolas Ménard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package rtdc.web.server.servlet;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import rtdc.core.event.ActionCompleteEvent;
import rtdc.core.event.ErrorEvent;
import rtdc.core.event.FetchUnitsEvent;
import rtdc.core.exception.ApiException;
import rtdc.core.exception.ValidationException;
import rtdc.core.json.JSONObject;
import rtdc.core.model.Permission;
import rtdc.core.model.SimpleValidator;
import rtdc.core.model.Unit;
import rtdc.core.model.User;
import rtdc.web.server.config.PersistenceConfig;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("units")
public class UnitServlet {

    private static final Logger log = LoggerFactory.getLogger(UnitServlet.class);

    @GET
    @RolesAllowed({Permission.USER, Permission.MANAGER, Permission.ADMIN})
    public String getUnits(@Context HttpServletRequest req, @Context User user){
        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Unit> units = null;
        try{
            transaction = session.beginTransaction();
            units = (List<Unit>) session.createCriteria(Unit.class).list();
            transaction.commit();

            log.info("{}: UNIT: Getting all units for user.", user.getUsername());
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally{
            session.close();
        }
        return new FetchUnitsEvent(units).toString();
    }

    @GET
    @Path("{id}")
    @RolesAllowed({Permission.USER, Permission.MANAGER, Permission.ADMIN})
    public String getUnit(@Context HttpServletRequest req, @Context User user, @PathParam("id") int id) {
        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        Unit unit = null;
        try{
            transaction = session.beginTransaction();
            unit = (Unit) session.get(Unit.class, id);
            if(unit == null)
                throw new ApiException("Id " + id + " doesn't exist");
            transaction.commit();

            log.info("{}: UNIT: Getting unit " + id + " for user.", user.getUsername());
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally{
            session.close();
        }
        List<Unit> units = new ArrayList<>();
        units.add(unit);
        // TODO: Create a new FetchSingleUnitEvent
        return new FetchUnitsEvent(units).toString();
    }

    @PUT
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    @RolesAllowed({Permission.MANAGER, Permission.ADMIN})
    public String updateUnit(@Context HttpServletRequest req, @Context User user, @FormParam("unit" )String unitString){
        Unit unit = new Unit(new JSONObject(unitString));

        Set<ConstraintViolation<Unit>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(unit);
        if(!violations.isEmpty()) {
            log.warn("Error updating unit: " + violations.toString());
            return new ErrorEvent(violations.toString()).toString();
        }

        try {
            SimpleValidator.validateUnit(unit);
        }catch (ValidationException e){
            log.warn("Error updating unit: " + e.getMessage());
            return new ErrorEvent(e.getMessage()).toString();
        }

        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();

            // If user is a manager, it should only be able to modify its own unit
            if(user.getPermission().equals(User.Permission.MANAGER) && user.getUnit().getId() != unit.getId())
                return new ErrorEvent("Insufficient permissions: you do not have permission to modify this unit.").toString();

            session.saveOrUpdate(unit);

            transaction.commit();

            log.info("{}: UNIT: Update unit values: {}", user.getUsername(), unitString);
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return new ActionCompleteEvent(unit.getId(), "unit", "update").toString();
    }

    @DELETE
    @Path("{id}")
    @Produces("application/json")
    @RolesAllowed({Permission.ADMIN})
    public String deleteUnit(@Context HttpServletRequest req, @Context User user, @PathParam("id") int id){
        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            Unit unit = (Unit) session.load(Unit.class, id);
            session.delete(unit);
            transaction.commit();

            // TODO: Replace string with actual username
            log.warn("{}: UNIT: Unit deleted: {}", "Username", unit.getName());

        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return new ActionCompleteEvent(id, "unit", "delete").toString();
    }

}
