package rtdc.android.presenter.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.*;
import android.widget.*;
import rtdc.android.R;
import rtdc.android.presenter.CreateActionActivity;
import rtdc.android.presenter.MainActivity;
import rtdc.core.controller.ActionListController;
import rtdc.core.model.Action;
import rtdc.core.util.Cache;
import rtdc.core.view.ActionListView;
import java.util.*;

public class ActionPlanFragment extends AbstractFragment implements ActionListView {

    private ActionListAdapter adapter;
    private ArrayList<Action> actions = new ArrayList<Action>();
    private ActionListController controller;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_action_plan, container, false);
        AdapterView actionListView = (AdapterView) view.findViewById(R.id.ActionListView);

        adapter = new ActionListAdapter(actions);
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
    public void setActions(List<Action> actions) {
        this.actions.clear();
        this.actions.addAll(actions);
        adapter.notifyDataSetChanged();
    }

    private class ActionListAdapter extends ArrayAdapter<Action> {


        public ActionListAdapter(List<Action> actions){
            super(getActivity(), R.layout.adapter_action_plan, actions);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent){


            if(view == null)
                view = getActivity().getLayoutInflater().inflate(R.layout.adapter_action_plan, parent, false);

            final Action currentAction = getItem(position);

            final TextView status = (TextView) view.findViewById(R.id.status);

            switch (currentAction.getStatus()){
                case notStarted:
                    status.setText(R.string.action_status_not_started);
                    status.setBackgroundResource(R.drawable.rectangle_rounded_blue);
                    break;
                case inProgress:
                    status.setText(R.string.action_status_in_progress);
                    status.setBackgroundResource(R.drawable.rectangle_rounded_yellow);
                    break;
                case failed:
                    status.setText(R.string.action_status_failed);
                    status.setBackgroundResource(R.drawable.rectangle_rounded_red);
                    break;
                case completed:
                    status.setText(R.string.action_status_completed);
                    status.setBackgroundResource(R.drawable.rectangle_rounded_green);
                    break;
            }

            TextView role = (TextView) view.findViewById(R.id.role);
            role.setText(currentAction.getRoleResponsible());

            final TextView action = (TextView) view.findViewById(R.id.action);
            action.setText(currentAction.getTask());

            TextView target = (TextView) view.findViewById(R.id.target);
            target.setText(currentAction.getTarget());

            TextView deadline = (TextView) view.findViewById(R.id.deadline);
            deadline.setText(currentAction.getDeadline().toString().substring(10, 16));

            TextView description = (TextView) view.findViewById(R.id.description);
            description.setText(currentAction.getDescription());

            view.setOnClickListener(new View.OnClickListener() {

                Action.Status newStatus;

                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_action_edit);

                    Button editButton = (Button) dialog.findViewById(R.id.dialog_button_action_edit);
                    editButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            controller.editAction(currentAction);
                        }
                    });

                    Button deleteButton = (Button) dialog.findViewById(R.id.dialog_button_action_delete);
                    deleteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            new AlertDialog.Builder(getContext())
                                    .setTitle("Confirm")
                                    .setMessage("Are you sure you want to delete this action?")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            controller.deleteAction(currentAction);
                                            Toast.makeText(getContext(), "Action Deleted", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, null).show();

                        }
                    });

                    dialog.show();

                    RadioGroup statusGroup = (RadioGroup) dialog.findViewById(R.id.dialog_action_status_group);
                    statusGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                        private RadioButton previousStatus;

                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            if (previousStatus == null) {
                                previousStatus = (RadioButton) dialog.findViewById(checkedId);
                            }

                            previousStatus.setBackgroundResource(R.drawable.radio_status);
                            previousStatus.setTextColor(getContext().getResources().getColor(R.color.RTDC_black));


                            RadioButton selectedStatus = (RadioButton) dialog.findViewById(checkedId);
                            selectedStatus.setTextColor(getContext().getResources().getColor(R.color.RTDC_white));

                            switch (checkedId) {
                                case R.id.radio_action_not_started:
                                    selectedStatus.setTextColor(getContext().getResources().getColor(R.color.RTDC_dark_blue));
                                    newStatus = Action.Status.notStarted;
                                    break;
                                case R.id.radio_action_in_progress:
                                    selectedStatus.setTextColor(getContext().getResources().getColor(R.color.RTDC_yellow));
                                    newStatus = Action.Status.inProgress;
                                    break;
                                case R.id.radio_action_completed:
                                    selectedStatus.setTextColor(getContext().getResources().getColor(R.color.RTDC_green));
                                    newStatus = Action.Status.completed;
                                    break;
                                case R.id.radio_action_failed:
                                    selectedStatus.setTextColor(getContext().getResources().getColor(R.color.RTDC_red));
                                    newStatus = Action.Status.failed;
                                    break;
                                default:
                                    break;
                            }

                            previousStatus = selectedStatus;
                        }
                    });

                    Button doneButton = (Button) dialog.findViewById(R.id.dialog_button_action_done);
                    doneButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            currentAction.setStatus(newStatus);
                            controller.saveAction(currentAction);
                            dialog.dismiss();
                            adapter.notifyDataSetChanged();
                        }
                    });

                    newStatus = currentAction.getStatus();
                    switch (currentAction.getStatus()){
                        case notStarted:
                            statusGroup.check(R.id.radio_action_not_started);
                            break;
                        case inProgress:
                            statusGroup.check(R.id.radio_action_in_progress);
                            break;
                        case failed:
                            statusGroup.check(R.id.radio_action_failed);
                            break;
                        case completed:
                            statusGroup.check(R.id.radio_action_completed);
                            break;
                    }
                }
            });

            return view;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        controller.onStop();
    }
}