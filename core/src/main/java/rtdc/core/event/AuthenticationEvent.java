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

package rtdc.core.event;

import rtdc.core.json.JSONObject;
import rtdc.core.model.ObjectProperty;
import rtdc.core.model.User;

public class AuthenticationEvent extends Event<AuthenticationEvent.Handler> {

    public static final EventType<Handler> TYPE = new EventType<Handler>("authenticationEvent");

    public enum Properties implements ObjectProperty<AuthenticationEvent>{
        user,
        authenticationToken,
        asteriskPassword
    }

    private final User user;
    private final String authenticationToken;
    private final String asteriskPassword;

    public interface Handler extends EventHandler{ void onAuthenticate(AuthenticationEvent event); }

    public AuthenticationEvent(User user, String authenticationToken, String asteriskPassword){
        this.user = user;
        this.authenticationToken = authenticationToken;
        this.asteriskPassword = asteriskPassword;
    }

    public AuthenticationEvent(JSONObject object){
        user = new User(object.getJSONObject(Properties.user.name()));
        authenticationToken = object.optString(Properties.authenticationToken.name());
        asteriskPassword = object.optString(Properties.asteriskPassword.name());
    }
    
    public User getUser(){
        return user;
    }

    public String getAuthenticationToken(){
        return authenticationToken;
    }

    public String getAsteriskPassword() { return asteriskPassword; }

    void fire() {
        for(Handler handler: getHandlers(TYPE))
            handler.onAuthenticate(this);
    }

    @Override
    public ObjectProperty[] getProperties() {
        return Properties.values();
    }

    @Override
    public String getType() {
        return TYPE.getName();
    }

    @Override
    public Object getValue(ObjectProperty property) {
        switch((Properties) property){
            case user: return user;
            case authenticationToken: return authenticationToken;
            case asteriskPassword: return asteriskPassword;
        }
        return null;
    }
}
