package rtdc.android.presenter.fragments;

import rtdc.core.i18n.ResBundle;

public enum FragmentType {
    CAPACITY_OVERVIEW(ResBundle.get().capacityOverviewTitle()),
    ACTION_PLAN(ResBundle.get().actionPlanTitle()),
    MESSAGES(ResBundle.get().messagesTitle()),
    MANAGE_UNITS(ResBundle.get().manageUnitsTitle()),
    MANAGE_USERS(ResBundle.get().manageUsersTitle()),
    PROFILE(ResBundle.get().profileTitle());

    private String title;

    FragmentType(String title) {
        this.title = title;
    }

    public String getTitle(){ return title; }
}
