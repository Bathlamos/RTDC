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

package rtdc.core.model;

import rtdc.core.exception.ValidationException;
import rtdc.core.i18n.ResBundle;
import rtdc.core.json.JSONObject;
import rtdc.core.util.Stringifier;

import java.util.Date;

public class Action extends RootObject{

    public enum Properties implements ObjectProperty<Action> {
        id,
        unit,
        status,
        personResponsible,
        roleResponsible,
        task,
        target,
        deadline,
        description,
        lastUpdate;
    }

    public enum Status {
        notStarted,
        inProgress,
        completed,
        failed;

        public static Stringifier<Status> getStringifier(){
            return new Stringifier<Status>() {
                @Override
                public String toString(Action.Status status) {
                    switch(status){
                        case completed: return ResBundle.get().completed();
                        case failed: return ResBundle.get().failed();
                        case inProgress: return ResBundle.get().inProgress();
                        case notStarted: return ResBundle.get().notStarted();
                        default: return status.name();
                    }
                }
            };
        }
    }

    public enum Task {
        pushForDischarge,
        offServicingTo,
        holdFor;

        public static Stringifier<Task> getStringifier(){
            return new Stringifier<Task>() {
                @Override
                public String toString(Task task) {
                    switch(task){
                        case holdFor: return ResBundle.get().hold();
                        case offServicingTo: return ResBundle.get().offServicing();
                        case pushForDischarge: return ResBundle.get().pushForDischarge();
                        default: return task.name();
                    }
                }
            };
        }
    }

    private int id;
    private Unit unit;
    private Status status; // Can never be null
    private User personResponsible;
    private String roleResponsible;
    private Task task; // This is the title
    private String target;
    private Date deadline;
    private String description;
    private Date lastUpdate;

    public Action(){}

    public Action(JSONObject object) {
        setId(object.optInt(Properties.id.name()));
        setUnit(new Unit(object.getJSONObject(Properties.unit.name())));

        status = Status.valueOf(object.optString(Properties.status.name()));

        if(object.has(Properties.personResponsible.name()))
            setPersonResponsible(new User(object.getJSONObject(Properties.personResponsible.name())));

        setRoleResponsible(object.optString(Properties.roleResponsible.name()));

        task = Task.valueOf(object.optString(Properties.task.name()));

        if(object.has(Properties.deadline.name()))
            setDeadline(new Date(object.getLong(Properties.deadline.name())));
        setTarget(object.optString(Properties.target.name()));
        setDescription(object.optString(Properties.description.name()));

        setLastUpdate(new Date(object.getLong(Properties.lastUpdate.name())));
    }

    @Override
    public ObjectProperty[] getProperties() {
        return Properties.values();
    }

    @Override
    public String getType() {
        return "action";
    }

    @Override
    public Object getValue(ObjectProperty property) {
        switch((Properties) property){
            case id: return getId();
            case unit: return getUnit();
            case status: return getStatus();
            case personResponsible: return getPersonResponsible();
            case roleResponsible: return getRoleResponsible();
            case task: return getTask();
            case target: return getTarget();
            case deadline: return getDeadline();
            case description: return getDescription();
            case lastUpdate: return getLastUpdate();
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getPersonResponsible() {
        return personResponsible;
    }

    public void setPersonResponsible(User personResponsible) {
        this.personResponsible = personResponsible;
    }

    public String getRoleResponsible() {
        return roleResponsible;
    }

    public void setRoleResponsible(String roleResponsible) {
        this.roleResponsible = roleResponsible;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLastUpdate(){ return lastUpdate; }

    public void setLastUpdate(Date lastUpdate){ this.lastUpdate = lastUpdate; }
}
