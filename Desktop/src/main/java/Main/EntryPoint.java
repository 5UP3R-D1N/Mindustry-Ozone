package Main;

import Ozone.Desktop.Pre.Preload;
import Ozone.Manifest;
import arc.Events;
import arc.util.Log;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.mod.Mod;

import java.io.File;
import java.net.URL;

public class EntryPoint extends Mod {
    public static final String type = "Desktop";
    public static final String desktopAtomicURL = Manifest.jitpack + type + "/" + Manifest.atomHash + "/" + type + "-" + Manifest.atomHash + ".jar";
    //get location of this Ozone mods
    public static File ozone = new File(Ozone.class.getProtectionDomain().getCodeSource().getLocation().getFile());
    //get Mods directory from mods/Ozone.jar
    public static File parentFile = ozone.getParentFile();
    //mods/libs directory
    public static File library = new File(parentFile, Manifest.libs);
    public static final File desktopAtomic = new File(library, "Atomic-" + type + "-" + Manifest.atomHash + ".jar");
    //mods/libs/Atomic-AtomHash.jar
    public static File atom = new File(library, Manifest.atomFile);
    public static LibraryLoader libraryLoader;
    public Mod OzoneMod;

    public EntryPoint() {
        try {
            libraryLoader = new LibraryLoader(new URL[]{this.getClass().getProtectionDomain().getCodeSource().getLocation()}, this.getClass().getClassLoader());
            Preload.checkAtomic(Manifest.atomDownloadLink, atom);
            libraryLoader.addURL(atom);//Atomic Core
            libraryLoader.addURL(desktopAtomic);//Atomic Desktop Extension
            OzoneMod = (Mod) libraryLoader.loadClass("Main.Ozone").getDeclaredConstructor().newInstance();//Load main mods from LibraryLoader
        } catch (Throwable t) {
            Events.on(EventType.ClientLoadEvent.class, s -> Vars.ui.showInfoText("Failed to load ozone", "See log for more information"));
            Log.errTag("Ozone-Hook", t.toString());
        }
    }

    @Override
    public void init() {
        OzoneMod.init();
    }

    @Override
    public void loadContent() {
        OzoneMod.loadContent();
    }
}
