package rtdc.android.presenter.fragments;

public enum FragmentType {
    CAPACITY_OVERVIEW("Capacity Overview"),
    ACTION_PLAN("Action Plan"),
    MESSAGES("Messages"),
    MANAGE_UNITS("Manage Units"),
    MANAGE_USERS("Manage Users");

    private String title;

    private FragmentType(String title) {
        this.title = title;
    }

    public String getTitle(){ return title; }
}
