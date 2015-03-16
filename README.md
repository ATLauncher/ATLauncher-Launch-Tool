# ATLauncher Launch Tool

### What is it?

This is a simple Java app to do things with ATLauncher such as add pack codes, auto install packs etc.

### Links
[ATLauncher Website](http://www.atlauncher.com)

[ATLauncher Facebook](http://www.facebook.com/ATLauncher)

[ATLauncher Reddit](http://www.reddit.com/r/ATLauncher)

[ATLauncher Twitter](http://twitter.com/ATLauncher)

### Coding Standards & Styling Guidelines

Please see the STYLE.md file for coding standards and style guidelines.

### The config.conf File

In order for this to work, you need a config.conf file in the root of the jar. The content of this file should be as below.

    pack_code_to_add=TestSemiPublicPackCode
    pack_to_install=Test Pack Name
    pack_share_code_to_install=T5Mwret8

This is read in by the application as a Java properties file so make sure to have the correct escaping.

### Need Help/Have Questions?

If you have questions please don't hesitate to [contact us](http://www.atlauncher.com/contact-us/)

### License

This work is licensed under the GNU General Public License v3.0. To view a copy of this license, visit http://www.gnu.org/licenses/gpl-3.0.txt.
