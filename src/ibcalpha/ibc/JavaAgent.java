// This file is part of IBC.
// Copyright (C) 2004 Steven M. Kearns (skearns23@yahoo.com )
// Copyright (C) 2004 - 2018 Richard L King (rlking@aultan.com)
// For conditions of distribution and use, see copyright notice in COPYING.txt

// IBC is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.

// IBC is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with IBC.  If not, see <http://www.gnu.org/licenses/>.

package ibcalpha.ibc;

import static ibcalpha.ibc.IbcTws.checkArguments;
import static ibcalpha.ibc.IbcTws.setupDefaultEnvironment;
import static ibcalpha.ibc.Utils.shellSplit;

public class JavaAgent {
    public static void premain(String agentArgs) throws Exception {
        if (Thread.getDefaultUncaughtExceptionHandler() == null) {
            Thread.setDefaultUncaughtExceptionHandler(new ibcalpha.ibc.UncaughtExceptionHandler());
        }
        String[] args = shellSplit(agentArgs == null || agentArgs.equals("") ? "NULL" : agentArgs).toArray(new String[0]);
        checkArguments(args);
        setupDefaultEnvironment(args, isGateway());
        IbcTws.load(true);
    }

    private static boolean isGateway() {
        String command = System.getProperty("sun.java.command", "").toLowerCase();
        if (command.startsWith("install4j.ibgateway") || command.contains("ibgateway.exe")) {
            return true;
        } else if (command.startsWith("install4j.jclient") || command.contains("tws.exe")) {
            return false;
        } else {
            throw new RuntimeException("Cannot determine whether TWS or Gateway: sun.java.command = " + command);
        }
    }
}
