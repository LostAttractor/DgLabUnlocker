package sakura.kooi.dglabunlocker.variables;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;

import sakura.kooi.dglabunlocker.features.AbstractFeature;

public class ModuleSettings {
    public static SharedPreferences sharedPref;

    public static void loadConfiguration(Context context) {
        sharedPref = context.getSharedPreferences("dglabunlocker", Context.MODE_PRIVATE);

        for(AbstractFeature feature : HookRegistry.featureInstances.values()) {
            feature.setEnabled(sharedPref.getBoolean(feature.getConfigurationKey(), false));
        }
    }

    public static void showSettingsDialog(Context context) throws ReflectiveOperationException {
        try {
            ((Dialog) Accessors.constructorBugDialog.invoke(context)).show();
        } catch (Throwable throwable) {
            throw new ReflectiveOperationException(throwable);
        }
    }
}
