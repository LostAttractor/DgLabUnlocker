package sakura.kooi.dglabunlocker.features;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicInteger;

import sakura.kooi.dglabunlocker.utils.FieldAccessor;
import sakura.kooi.dglabunlocker.variables.Accessors;

public class FeatureDoubleBugFix {
    public static final FeatureDoubleBugFix INSTANCE = new FeatureDoubleBugFix();
    private final AtomicInteger lastStrengthA = new AtomicInteger(0);
    private final AtomicInteger lastStrengthB = new AtomicInteger(0);
    private FeatureDoubleBugFix() {
    }

    public void beforeDataUpdate(Context context,
                                 int localStrengthA, int totalStrengthA, int remoteStrengthA,
                                 int localStrengthB, int totalStrengthB, int remoteStrengthB) throws ReflectiveOperationException {
        lastStrengthA.set(localStrengthA);
        lastStrengthB.set(localStrengthB);
    }

    public void afterDataUpdate(Context context,
                                int localStrengthA, int totalStrengthA, int remoteStrengthA,
                                int localStrengthB, int totalStrengthB, int remoteStrengthB) throws ReflectiveOperationException {
        boolean fixed;

        fixed = tryFixDouble(lastStrengthA, Accessors.localStrengthA, localStrengthA, remoteStrengthA);
        fixed = fixed || tryFixDouble(lastStrengthB, Accessors.localStrengthB, localStrengthB, remoteStrengthB);

        if (fixed) {
            Toast.makeText(context, "拦截了一次强度翻倍BUG", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean tryFixDouble(AtomicInteger lastStrength, FieldAccessor<Integer> localStrengthField, int localStrength, int remoteStrength) throws ReflectiveOperationException {
        if (remoteStrength == 0) {
            if (localStrength > 10 && Math.abs(localStrength - lastStrength.get()) > 1) {
                localStrengthField.set(lastStrength.get());
                Log.w("DgLabUnlocker", "Double bug blocked, set to " + lastStrength.get());
                return true;
            }
        }
        return false;
    }
}