/*
 * Copyright (c) 2015 Olivier Clermont, Jonathan Ermel, Mathieu Fortin-Boulay, Philippe Legault & Nicolas Ménard
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package rtdc.android.impl.voip;

import org.linphone.core.LinphoneChatMessage;
import rtdc.core.impl.voip.Address;
import rtdc.core.impl.voip.TextMessage;

public class AndroidTextMessage implements TextMessage{

    private LinphoneChatMessage linphoneChatMessage;

    public AndroidTextMessage(LinphoneChatMessage linphoneChatMessage){
        this.linphoneChatMessage = linphoneChatMessage;
    }

    public LinphoneChatMessage getLinphoneChatMessage(){
        return linphoneChatMessage;
    }

    @Override
    public String getText() {
        return linphoneChatMessage.getText();
    }

    @Override
    public Address getFrom() {
        return new AndroidAddress(linphoneChatMessage.getFrom());
    }

    @Override
    public Address getTo() {
        return new AndroidAddress(linphoneChatMessage.getTo());
    }
}
