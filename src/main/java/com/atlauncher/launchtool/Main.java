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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    private static String launcherExecutable;
    private static String launcherLocation;

    private static File launcherExecutableFile;
    private static File launcherLocationFile;

    public static void main(String[] args) {
        Properties launcherProps = null;
        Properties inputProps = null;

        File launcherPropsFile = new File(Utils.getOSStorageDir(), "atlauncher.conf");
        try {
            launcherProps = Utils.readPropertiesFromFile(launcherPropsFile);
        } catch (IOException e) {
            System.err.println("The file " + launcherPropsFile.getAbsolutePath() + " doesn't exist! Please run " +
                    "ATLauncher at least once!");
            System.exit(1);
        }

        launcherExecutable = launcherProps.getProperty("executable", null);
        launcherLocation = launcherProps.getProperty("location", null);

        if (launcherExecutable == null || launcherLocation == null) {
            System.err.println("No launcher location or executable found, please run ATLauncher!");
            System.exit(1);
        }

        launcherExecutableFile = new File(launcherExecutable);
        launcherLocationFile = new File(launcherLocation);

        if (!launcherExecutable.contains(".exe") && !launcherExecutable.contains(".jar")) {
            System.err.println("The launcher executable location is not valid. Value given is " +
                    launcherExecutable + ", please run ATLauncher!");
            System.exit(1);
        }

        if (!launcherExecutableFile.exists() || !launcherLocationFile.isDirectory()) {
            System.err.println("The launcher location or executable is not valid. Please run ATLauncher!");
            System.exit(1);
        }

        try {
            inputProps = Utils.readPropertiesFromStream(Main.class.getResourceAsStream("/config.conf"));
        } catch (IOException e) {
            System.err.println("The properties file inside the jar is invalid! Please redownload it and try again!");
            System.exit(1);
        }

        String packCodeToInstall = inputProps.getProperty("pack_code_to_add", null);
        String packToInstall = inputProps.getProperty("pack_to_install", null);
        String packShareCodeToInstall = inputProps.getProperty("pack_share_code_to_install", null);

        if (packCodeToInstall != null) {
            launcherProps.setProperty("pack_code_to_add", packCodeToInstall);
        }

        if (packToInstall != null) {
            launcherProps.setProperty("pack_to_install", packToInstall);
        }

        if (packShareCodeToInstall != null) {
            launcherProps.setProperty("pack_share_code_to_install", packShareCodeToInstall);
        }

        try {
            launcherProps.store(new FileOutputStream(launcherPropsFile), "");
        } catch (IOException e) {
            System.err.println("Error writing to " + launcherPropsFile.getAbsolutePath());
            System.exit(1);
        }

        System.out.println("Done! Launching the launcher!");

        Utils.launch(launcherExecutableFile, launcherLocationFile);

        System.exit(0);
    }
}
