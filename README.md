# WAWLA
What Are We Looking At (WAWLA) is a Minecraft mod which helps provide in game documentation for vanilla and modded content. 

## Source Code
The latest source code can be found here on [GitHub](https://github.com/Darkhax-Minecraft/WAWLA) If you are using Git, you can use the following command to clone the project: `git clone git:github.com/Darkhax-Minecraft/WAWLA.git`

## Building from Source
This project can be built using the Gradle Wrapper included in the project. When the `gradlew build` command is executed, a compiled JAR will be created in `~/build/libs`. Sources and Javadocs will also be generated in the same directory. Alternatively the latest builds of WAWLA along with Sources and Javadocs can be found [here](http://maven.rubbix.net/net/darkhax/wawla/Wawla)

## How does Wawla 2 work? / What is ICSE? / What is an Info Engine?
As of Wawla 2, the mod is now using what I call an Info Engine oriented system. An Info Engine is a mod which allows information to be displayed. Some examples of Info Engines include [WAILA](http://minecraft.curseforge.com/projects/waila), [WIT](http://minecraft.curseforge.com/projects/wit-what-is-that) and [SmartCursor](http://minecraft.curseforge.com/projects/smartcursor). Previously the Wawla mod was designed to specifically run on top of WAILA, and required it to function. As of Wawla 2, the mod will run with any of the supported info engines installed. Additionally, Wawla 2 comes bundled with another mod called I Can See Everything (ICSE). This mod acts like a lightweight version of WAILA, and serves as a fallback Info Engine for when the user has no other Info Engines installed. This allows for Wawla 2 to run as a stand alone mod for those who want it. Wawla 2 is still currently in early alpha, so support for new Info Engines and new features are being added over time.
