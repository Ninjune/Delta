# Setup Developer Environment
### IntelliJ Idea
To get started, clone this repository.

This project uses [DevAuth](https://github.com/DJtheRedstoner/DevAuth), so you can log in using your real
minecraft account. To configure, run the program once by adding `"-Ddevauth.enabled=true"`
to the launch arguments, found by editing the run configuration drop down at top of IntelliJ.
Then follow the instructions provided at the DevAuth GitHub.

To run the mod you will need two JDKs, one Java 17 jdk and one Java 1.8 jdk. You can download those
from [here](https://adoptium.net/temurin/releases) (or use your own downloads).

When you import your project into IntelliJ, you need to set the gradle jvm to the Java 17 JDK in the gradle tab, and the
Project SDK to the Java 1.8 JDK. Then click on the sync button in IntelliJ, and it should create a run task
called `Minecraft Client`. If it doesn't then try relaunching your IntelliJ. **Warning for Mac users**: You might have to remove the `-XStartOnFirstThread` vm argument from your run configuration. In the future, that should be handled by the plugin, but for now you'll probably have to do that manually.

To export, run the `gradle build` task. Use the
file `build/libs/<modid>-<version>.jar`. Ignore the jars in the `build/badjars` folder. Those are intermediary jars that
are used by the build system but *do not work* in a normal forge installation.