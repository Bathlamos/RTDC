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

package rtdc.android.presenter.fragments;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.*;
import android.widget.RelativeLayout;
import android.widget.TextView;
import rtdc.android.R;
import rtdc.core.Bootstrapper;
import rtdc.core.model.User;
import rtdc.core.util.Cache;
import rtdc.core.util.Pair;
import rtdc.core.util.Stringifier;

/**
 * Created by Nicolas on 2015-12-13.
 */
public class ProfileFragment extends AbstractFragment{

    private TextView fullName;
    private TextView unit;
    private TextView email;
    private TextView phone;
    private TextView role;

    private RelativeLayout profileLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        setHasOptionsMenu(true);

        profileLayout = (RelativeLayout) view.findViewById(R.id.profile_layout);

        fullName = (TextView) view.findViewById(R.id.fullNameValue);
        unit = (TextView) view.findViewById(R.id.unitValue);
        email = (TextView) view.findViewById(R.id.emailValue);
        phone = (TextView) view.findViewById(R.id.phoneValue);
        role = (TextView) view.findViewById(R.id.roleValue);

        setupProfile();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_edit_profile:
                Cache.getInstance().put("user", Cache.getInstance().get("sessionUser"));
                Bootstrapper.getFactory().newDispatcher().goToEditUser(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(Cache.getInstance().get("user") != null) {
            Pair<String, User> pair = (Pair<String, User>) Cache.getInstance().remove("user");
            User updatedUser = pair.getSecond();
            Cache.getInstance().put("sessionUser", updatedUser);
            setupProfile();
        }
    }

    private void setupProfile(){
        User user = (User) Cache.getInstance().get("sessionUser");

        fullName.setText(user.getFirstName() + " " + user.getLastName());

        if(user.getUnit() == null)
            unit.setText("-");
        else
            unit.setText(user.getUnit().getName());

        email.setText(user.getEmail());
        phone.setText(user.getPhone());

        Stringifier<User.Role> roleStringifier = User.Role.getStringifier();
        role.setText(roleStringifier.toString(user.getRole()));

        profileLayout.setBackgroundColor(Color.HSVToColor(user.getProfileColor()));
    }
}
