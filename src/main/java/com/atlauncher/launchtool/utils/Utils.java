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
package com.atlauncher.launchtool.utils;

import javax.swing.JOptionPane;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Utils {
    public static File getOSStorageDir() {
        switch (OperatingSystem.getOS()) {
            case WINDOWS:
                return new File(System.getenv("APPDATA"), "/.atlauncher");
            case OSX:
                return new File(System.getProperty("user.home"), "/Library/Application Support/.atlauncher");
            default:
                return new File(System.getProperty("user.home"), "/.atlauncher");
        }
    }

    public static boolean isWindows() {
        return OperatingSystem.getOS() == OperatingSystem.WINDOWS;
    }

    public static boolean isMac() {
        return OperatingSystem.getOS() == OperatingSystem.OSX;
    }

    public static boolean isLinux() {
        return OperatingSystem.getOS() == OperatingSystem.LINUX;
    }


    public static void launch(File launcherExecutablePath, File launcherPath) {
        List<String> arguments = new ArrayList<String>();

        if (Utils.isMac() && new File(new File(System.getProperty("user.dir")).getParentFile().getParentFile(),
                "MacOS").exists()) {
            arguments.add("open");
            arguments.add("-n");
            arguments.add(launcherExecutablePath.getParentFile().getParentFile().getParentFile().getAbsolutePath());
        } else {
            String path = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
            if (Utils.isWindows()) {
                path += "w";
            }
            arguments.add(path);
            arguments.add("-jar");
            arguments.add(launcherExecutablePath.getAbsolutePath());
        }

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.directory(launcherPath);
        processBuilder.command(arguments);

        try {
            processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties readPropertiesFromFile(File file) throws IOException {
        Properties props = new Properties();
        props.load(new FileInputStream(file));
        return props;
    }

    public static Properties readPropertiesFromStream(InputStream stream) throws IOException {
        if (stream == null) {
            throw new IOException("The stream provided is incorrect while trying to read in properties!");
        }

        Properties props = new Properties();
        props.load(stream);
        return props;
    }

    public static void showExitPopup(String message) {
        System.out.println(message);

        String[] options = {"Close", "Download Launcher"};

        int ret = JOptionPane.showOptionDialog(null, "<html><p align=\"center\">There was an error running this " +
                        "application!<br/><br/>If you haven't already please download and run the launcher at least " +
                        "once.<br/><br/>If you have already done that you can try running ATLauncher once before " +
                        "doing this again.</p></html>", "Error", JOptionPane.DEFAULT_OPTION,
                JOptionPane.ERROR_MESSAGE, null, options, options[0]);

        if (ret == 1) {
            Utils.openBrowser("http://www.atlauncher.com/download");
        }

        System.exit(1);
    }

    private static void openBrowser(String URL) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(URL));
            } catch (Exception e) {
            }
        }
    }
}
