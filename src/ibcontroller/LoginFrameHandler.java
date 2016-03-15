// This file is part of the "IBController".
// Copyright (C) 2004 Steven M. Kearns (skearns23@yahoo.com )
// Copyright (C) 2004 - 2011 Richard L King (rlking@aultan.com)
// For conditions of distribution and use, see copyright notice in COPYING.txt

// IBController is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.

// IBController is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with IBController.  If not, see <http://www.gnu.org/licenses/>.

package ibcontroller;

import java.awt.Window;
import java.awt.event.WindowEvent;
import javax.swing.JComboBox;
import javax.swing.JFrame;

final class LoginFrameHandler extends AbstractLoginHandler {

    @Override
    public boolean recogniseWindow(Window window) {
        if (! (window instanceof JFrame)) return false;

        // we check for the presence of the Login button because 
        // TWS displays a different (information-only) dialog, also 
        // entitled Login, when it's trying to reconnect
        return ((Utils.titleEquals(window, "New Login") ||
                Utils.titleEquals(window, "Login")) &&
                Utils.findButton(window, "Login") != null);
    }

    @Override
    protected final boolean initialise(final Window window, int eventID) throws IBControllerException {
        setTradingModeCombo(window);

        final String STORE_SETTINGS_ON_SERVER_CHECKBOX = "Use/store settings on server";
        if (! Utils.setCheckBoxSelected(
                window,
                STORE_SETTINGS_ON_SERVER_CHECKBOX,
                Settings.getBoolean("StoreSettingsOnServer", false))) throw new IBControllerException(STORE_SETTINGS_ON_SERVER_CHECKBOX);

        return true;
    }
    
    @Override
    protected final boolean preLogin(final Window window, int eventID) throws IBControllerException {
        if (LoginManager.getIBAPIUserName().length() == 0) {
            setMissingCredential(window, 0);
        } else if (LoginManager.getIBAPIPassword().length() == 0) {
            setMissingCredential(window, 1);
        } else {
            return true;
        }
        return false;
    }
    
    @Override
    protected final boolean setFields(Window window, int eventID) throws IBControllerException {
        setCredential(window, "IBAPI user name", 0, LoginManager.getIBAPIUserName());
        setCredential(window, "IBAPI password", 1, LoginManager.getIBAPIPassword());
        return true;
    }
    
}

