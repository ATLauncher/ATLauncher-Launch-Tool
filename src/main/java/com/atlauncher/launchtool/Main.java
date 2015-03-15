/*
 * ATLauncher Launch Tool - https://github.com/ATLauncher/ATLauncher-Launch-Tool
 * Copyright (C) 2015 ATLauncher
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.atlauncher.launchtool;

import com.atlauncher.launchtool.utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    private static String launcherExecutable;
    private static String launcherLocation;

    private static File launcherExecutableFile;
    private static File launcherLocationFile;

    public static void main(String[] args) {
        try {
            if (!Utils.getOSStorageDir().exists()) {
                Utils.getOSStorageDir().mkdirs();
            }

            File f = new File(Utils.getOSStorageDir(), "atlauncher.conf");

            if (!f.exists()) {
                System.err.println("The file " + f.getAbsolutePath() + " doesn't exist! Please run ATLauncher at " +
                        "least once!");
                System.exit(1);
            }

            Properties props = new Properties();
            props.load(new FileInputStream(f));

            launcherExecutable = props.getProperty("executable", null);
            launcherLocation = props.getProperty("location", null);

            if (launcherExecutable == null || launcherLocation == null) {
                System.err.println("No launcher location or executable found in " + f.getAbsolutePath() + ", please " +
                        "run ATLauncher!");
                System.exit(1);
            }

            launcherExecutableFile = new File(launcherExecutable);
            launcherLocationFile = new File(launcherLocation);

            if (!launcherExecutable.contains(".exe") && !launcherExecutable.contains(".jar")) {
                System.err.println("The launcher executable location is not valid. Value given is " +
                        launcherExecutable + " Please run ATLauncher!");
                System.exit(1);
            }

            if (!launcherExecutableFile.exists() || !launcherLocationFile.isDirectory()) {
                System.err.println("The launcher location or executable is not valid. Please run ATLauncher!");
                System.exit(1);
            }

            props.store(new FileOutputStream(f), "");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done! Launching the launcher!");

        Utils.launch(launcherExecutable);

        System.exit(0);
    }
}
