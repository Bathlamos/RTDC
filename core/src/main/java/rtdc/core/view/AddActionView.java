package rtdc.core.view;

public interface AddActionView extends View {

    String getUnitAsString();
    void setUnitAsString(String value);
    void setErrorForUnit(String error);

    String getStatusAsString();
    void setStatusAsString(String value);
    void setErrorForStatus(String error);

    String getRoleAsString();
    void setRoleAsString(String value);
    void setErrorForRole(String error);

    String getActionAsString();
    void setActionAsString(String value);
    void setErrorForAction(String error);

    String getTargetAsString();
    void setTargetAsString(String value);
    void setErrorForTarget(String error);

    String getDeadlineAsString();
    void setDeadlineAsString(String value);
    void setErrorForDeadline(String error);

    String getNotesAsString();
    void setNotesAsString(String value);
    void setErrorForNotes(String error);
}
