package Ozone;

import Atom.Random;
import Ozone.Commands.PlayerInterface;
import Ozone.Patch.DesktopInput;
import Ozone.Patch.SettingsDialog;
import Ozone.Patch.Translation;
import Ozone.UI.OzoneMenu;
import arc.Core;
import arc.scene.ui.Dialog;
import arc.struct.ObjectMap;
import arc.util.Log;
import arc.util.Time;
import mindustry.Vars;

public class Main {

    public static void init() {
        Log.infoTag("Ozone", "Hail o7");
        loadSettings();
        patch();
        initUI();
        PlayerInterface.init();
    }


    public static void loadContent() {

    }


    private static void loadSettings() {
        Manifest.colorPatch = Core.settings.getBool("ozone.colorPatch", Manifest.colorPatch);
        Manifest.antiSpam = Core.settings.getBool("ozone.antiSpam", Manifest.antiSpam);
    }

    private static void patch() {
        try {
            Log.infoTag("Ozone", "Patching");
            Translation.patch();
            Time.mark();
            ObjectMap<String, String> modified = Core.bundle.getProperties();
            for (ObjectMap.Entry<String, String> s : Interface.bundle) {
                modified.put(s.key, s.value);
            }
            if (Manifest.colorPatch)
                for (String s : Core.bundle.getKeys()) {
                    modified.put(s, getRandomHexColor() + modified.get(s));
                }
            Core.bundle.setProperties(modified);
            Log.infoTag("Ozone", "Patching translation done: " + Time.elapsed());
            Vars.control.input = new DesktopInput();
            Vars.ui.settings = new SettingsDialog();

            Log.infoTag("Ozone", "Patching Complete");
        } catch (Throwable t) {
            Log.infoTag("Ozone", "Patch failed");
            Log.err(t);
        }
    }

    public static void initUI() {
        Manifest.menu = new OzoneMenu("Ozone Menu", Core.scene.getStyle(Dialog.DialogStyle.class));

    }

    public static String getRandomHexColor() {
        return "[" + Random.getRandomHexColor() + "]";
    }

}
