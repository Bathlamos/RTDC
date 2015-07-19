package rtdc.android.presenter.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.*;
import android.widget.*;
import rtdc.android.R;
import rtdc.android.presenter.CreateActionActivity;
import rtdc.android.presenter.fragments.AbstractFragment;
import rtdc.core.controller.ActionListController;
import rtdc.core.model.Action;
import rtdc.core.util.Cache;
import rtdc.core.view.ActionListView;
import java.util.*;

public class ActionPlanFragment extends AbstractFragment implements ActionListView {

    private ActionListAdapter adapter;
    private ArrayList<Action> actions = new ArrayList<Action>();
    private Action actionSelected;
    private ActionListController controller;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_action_plan, container, false);
        AdapterView actionListView = (AdapterView) view.findViewById(R.id.ActionListView);

        adapter = new ActionListAdapter(getActivity(), actions);
        actionListView.setAdapter(adapter);

        if(controller == null)
            controller = new ActionListController(this);

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_action_plan, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        Intent intent;
        switch (item.getItemId()) {
            case R.id.addAction:
                intent = new Intent(getActivity(), CreateActionActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId( ) == R.id.optionsMenuBtn) {
            actionSelected = actions.get(Integer.parseInt(v.getTag().toString()));
            menu.setHeaderTitle(actionSelected.getTask() + ": " + actionSelected.getTarget());
            menu.add(0, 1, 0, "Edit");
            menu.add(0, 2, 0, "Delete");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case 1:
                Cache.getInstance().put("action", actionSelected);
                controller.editAction(actionSelected);
                break;
            case 2:
                new AlertDialog.Builder(getActivity())
                    .setTitle("Confirm")
                    .setMessage("Delete selected action?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            controller.deleteAction(actionSelected);
                            Toast.makeText(getActivity(), "Action Deleted", Toast.LENGTH_SHORT).show();
                        }})
                    .setNegativeButton(android.R.string.no, null).show();
                break;
        }
        return true;
    }

    @Override
    public void setActions(List<Action> actions) {
        this.actions.clear();
        this.actions.addAll(actions);
        adapter.notifyDataSetChanged();
    }

    private static class ActionListAdapter extends ArrayAdapter<Action> {

        private Activity activity;

        public ActionListAdapter(Activity activity, List<Action> actions){
            super(activity, R.layout.adapter_action_plan, actions);
            this.activity = activity;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent){


            if(view == null)
                view = activity.getLayoutInflater().inflate(R.layout.adapter_action_plan, parent, false);

            Action currentAction = getItem(position);

            TextView status = (TextView) view.findViewById(R.id.status);

            switch (currentAction.getStatus()){
                case notStarted:
                    status.setText(R.string.action_status_not_started);
                    status.setBackgroundResource(R.color.RTDC_dark_blue);
                    break;
                case inProgress:
                    status.setText(R.string.action_status_in_progress);
                    status.setBackgroundResource(R.color.RTDC_yellow);
                    break;
                case failed:
                    status.setText(R.string.action_status_failed);
                    status.setBackgroundResource(R.color.RTDC_red);
                    break;
                case completed:
                    status.setText(R.string.action_status_completed);
                    status.setBackgroundResource(R.color.RTDC_green);
                    break;
            }

            TextView role = (TextView) view.findViewById(R.id.role);
            role.setText(currentAction.getRoleResponsible());

            TextView action = (TextView) view.findViewById(R.id.action);
            action.setText(currentAction.getTask());

            TextView target = (TextView) view.findViewById(R.id.target);
            target.setText(currentAction.getTarget());

            TextView deadline = (TextView) view.findViewById(R.id.deadline);
            deadline.setText(currentAction.getDeadline().toString().substring(10, 16));

            TextView description = (TextView) view.findViewById(R.id.description);
            description.setText(currentAction.getDescription());

            return view;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        controller.onStop();
    }
}