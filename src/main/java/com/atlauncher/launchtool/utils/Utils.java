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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            arguments.add(new File(System.getProperty("user.dir")).getParentFile().getParentFile().getParentFile()
                    .getAbsolutePath());
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
}
