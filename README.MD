# State Persistence on Orientation Change
## Retain View Information on Orientation Change
### Showing two different common methods of retaining essential 'View' data.

Ever wonder why a large number of apps, including many popular ones, don't allow you to turn your device? 
Sometimes, like in the case of some games for instance, it is simply because the limited size of the device would not 
allow for all elements to fit the screen.


In most cases though, retaining information is difficult/tricky to code, computationally expensive or both. 
One of the most annoying aspects about Android development, in my limited knowledge, is the fact that every time a 
configuration change occurs (i.e. settings, fonts, orientation etc...) the device will redraw the current View, 
and reset absolutely everything within it.

Retaining this info can be painful to code; putting generated lists, variables, drawables and just about everything
else can be extremely tricky. Although the easy answer would be 
```android:configChanges="orientation|screenSize|screenLayout|keyboardHidden"``` or similar in the AndroidManifest.xml,
it's not really a solution, particularly when the application requires a different layout for ```layout-land```.

Therefore, things like LiveData, Flow, Preferences and, for larger requirements, File Storage or SQL come into play.

This app shows two common methods used to preserve View State, being LiveData and SharedPreferences. Using either method
or a combination of the two can be extremely useful and will help saving relatively small data for most applications. Compile in
IntelliJ or Gradle in terminal in order to compile.
