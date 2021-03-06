## Version History

### BETA 008.1

_01.08.2010_

*   BUG FIXES
    *   Fixed bug on startup with the Simple Interface
    *   Fixed bugs with the Color Switcher
    *   Released a separate version for Mac OS X Tiger 10.4

* * *

### BETA 008

_01.07.2010_

*   FEATURES
    *   SVG Export & Switching support added using the Batik Library – Big thanks to Steren!
    *   Support for scaling image size when exporting as a JPEG or PNG file
    *   Flip canvas, horizontally or vertically – Thanks to Count_Zero!
    *   Smooth module added to smooth up sharp edges and hard angles
    *   Ribbon Shapes module added, based on code by James Alliban & Eric Natzke – Thanks Guys!
    *   Limit module added, to limit the number of shapes on the canvas – Thanks to hellocatfood!
    *   Splatter Shapes module added, based on code by Stamen Design – Thanks!

*   CHANGES
    *   Transparency of the background color can now be set
    *   Persian Localisation added – Thanks to Omid Saadat!
    *   Shapes Module: Added option to render just the outline of the shape until the pen is lifted
    *   Alchemy now remembers the Export file directory between sessions

*   BUG FIXES
    *   Fixed inconsistant coloring bug with Mirror & Color Switcher
    *   Color Palette now pops up in the middle of the screen (not outside it)
    *   Updated JPen tablet library, with support for Java 1.6 on mac and other improvements
    *   Fixed bug causing fullscreen transparent mode to draw the background with some affect modules
    *   Fixed Color Switcher bug where bg/fg colors were switching
    *   Line Width UI element now updates correctly when using shortcuts

* * *

### ALPHA 007.2

_06.05.2009_

*   BUG FIXES
    *   Gradient Module Colour fixes
    *   Fixed bug where PDF files containing transparency had their colours converted to CMYK and back again, resulting in incorrect colours

* * *

### ALPHA 007.1

_27.04.2009_

*   CHANGES
    *   JPen Tablet library updated – now with support for 64bit Windows machines

*   BUG FIXES
    *   Corrupt PDF files when using the Gradient module fixed
    *   Plugin Comparator error handling added to fix errors on launch
    *   Mirror Vista UI fixes

* * *

### ALPHA 007

_09/04/2009_

*   FEATURES
    *   Pressure Shapes module for drawing shapes using pressure from a tablet
    *   Gradient module for adding colour gradients to shapes
    *   Load a background image from the view menu to draw on top of

*   CHANGES
    *   Colour button has been streamlined and combined with the background/foreground button
    *   Line Weight spinner has been redesigned
    *   Sliders and number spinners can now be controlled with the mouse scroll wheel
    *   The max/min values of sliders and number spinners can be set by CTRL/COMMAND clicking them and inputing the values in the popup
    *   Warning added when using File > New with shapes on the canvas
    *   Localisations added for Simplified Chinese/Traditional Chinese/Spanish/Dutch/French/German

    *   Pullshapes Size slider added to better control shape size
    *   Displace module now behaves better with spine-based (variable line width) shapes
    *   Trace Shapes images are now scaled correctly and local images can also be loaded

*   BUG FIXES
    *   Fixed several issues caused by custom cursors stopping Alchemy from launching
    *   Fixed a bug causing the tool bar to disappear completely when being reattached
    *   Colour selector will now appear in the correct window on dual monitor systems

* * *

### ALPHA 006

_11.12.2008_

*   FEATURES
    *   Jpeg export functionality added
    *   Session file names can now be set in the options/preferences
    *   Transparent Fullscreen mode added
    *   Colour eye dropper added
    *   Pull Shapes module added

*   CHANGES
    *   Colour Switcher module ‘Constant’ button added, it can now change colours constantly when the pen is dragged
    *   Options/Preferences interface redesigned
    *   Fullscreen mode now hides the mac menubar
    *   Changing the canvas size during a session now changes the canvas size of the session PDF
    *   JPen library added (but not yet used), to allow pen pressure/tilt access
    *   Camera colour module has been removed because of numerous bugs, it may be readded if we find a good java video library
    *   Colour Picker popup and secondary window redesigned and standard across all platforms now
    *   Shapes folder added to store PDF shapes used by PullShapes
    *   iText PDF library update

*   BUG FIXES
    *   Mirror module transparency bug fix
    *   Switch menus opening properly in the specified application, not the default application
    *   PDF colour accuracy fix, PDF colour format set to DEVICERGB
    *   Shortcuts interface bug fixed
    *   Language bundle loading fix
    *   Dialogs added for when the modules folder is missing

* * *

### ALPHA 005

_26.06.2008_

*   FEATURES
    *   Draw over/under existing shapes mode
    *   Colour Switcher Module
    *   Simple interface mode introduced, aimed at kidz!

*   CHANGES
    *   Preferences/Options window added to change inteface mode
    *   Modules can now be turned on and off in the preferences/options window
    *   A default list of modules can be created in modules/modules.txt

*   BUG FIXES
    *   Session timer now saves correctly only when the canvas has changed
    *   Minor interface fixes (especially for Linux)

* * *

### ALPHA 004

_03.05.2008_

*   FEATURES
    *   Load PDF session
    *   Camera Colour affect module
    *   Detach Shapes create module
    *   Scrawl Shapes create module

*   CHANGES
    *   Module selection shortcuts added
    *   Sub Toolbar slider changed to a custom UI element
    *   Alchemy now requires Java Version 5+
    *   Export transparent PNG files

*   BUG FIXES
    *   Mirror module fixed to work with Camera Colour module
    *   Warning dialog added when overwriting files
    *   Japanese localisation fixes

* * *

### ALPHA 003.1

_26.03.2008_

*   Features
    *   Hide cursor function
*   Changes
    *   Keyboard shortcuts (fullscreen, recording) fixed
    *   Displace module now working with straight shapes
    *   Record indicator is no longer showing off screen on windows

* * *

### ALPHA 003

_24.03.2008_

*   Features
    *   Colour added – new colour picker in the tool bar
    *   Foreground / Background button in the tool bar lets you draw with the background colour
    *   Keyboard Shortcuts can now be assigned by the user
    *   Displace affect module added
*   Changes
    *   Performance has been greatly improved when drawing with many shapes on the canvas
    *   Line smoothing can be turned on and off in the settings menu
    *   Help system has been changed to the FLOSS manual Alchemy.pdf file in the Alchemy folder
    *   Export menu can now create a PNG file as well as a PDF
*   Bug Fixes
    *   Mirror module bug when using median shapes fixed
    *   Warning dialog when closing Alchemy now functions properly
    *   Trace Shapes now shows a warning dialog when a network connection fails

* * *

### ALPHA 002

_02.03.2008_

*   Features
    *   The toolbar is now detachable into a seperate palette window
    *   Trace Shapes / Speed Shapes / X Shapes modules added
    *   Copy function to copy the canvas to the clipboard as a bitmap
    *   Alchemy Help added
    *   Japanese Localisation
    *   Canvas smoothing option added
    *   Background colour is now changeable
*   Changes
    *   PDF saving functionality improved. PDF files are now viewable throughout
    *   Interface improvements, icon changes
    *   Random module now has a distortion slider
    *   Type Shapes module can now create shapes using the mouse and keyboard
    *   More robust microphone detection and more accurate sound levels
*   Bug Fixes
    *   Toolbar hiding functionality improved
    *   Toolbar repainting when drawing ‘blind’ fixed
    *   Shortcut key mappings fixed
    *   Fullscreen mode on a mac is now stable

* * *

### ALPHA 001

_30.01.2008_

Initial Release
