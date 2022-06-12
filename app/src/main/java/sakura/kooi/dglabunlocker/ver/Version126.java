package sakura.kooi.dglabunlocker.ver;

import static sakura.kooi.dglabunlocker.variables.InjectPoints.*;

import java.util.Arrays;

public class Version126 extends AbstractVersionedCompatibilityProvider {
    @Override
    protected void initializeNames() {
        classGlobalVariables = "com.bjsm.dungeonlab.global.b";
        totalStrengthA = "V";
        localStrengthA = "ab";
        remoteStrengthA = "X";
        maxStrengthA = "Z";

        totalStrengthB = "W";
        localStrengthB = "ac";
        remoteStrengthB = "Y";
        maxStrengthB = "aa";

        class_BluetoothServiceDecoder = "com.bjsm.dungeonlab.service.BlueToothService$18";
        method_BluetoothServiceDecoder_decode = "a";

        class_BluetoothService = "com.bjsm.dungeonlab.service.BlueToothService";
        method_BluetoothService_updateStrength = "a";
        method_BluetoothService_updateClientSide = "b";

        class_StrengthTouchListeners = Arrays.asList(
                "com.bjsm.dungeonlab.ui.activity.HomeActivity$15",
                "com.bjsm.dungeonlab.ui.activity.HomeActivity$16",
                "com.bjsm.dungeonlab.ui.activity.HomeActivity$17",
                "com.bjsm.dungeonlab.ui.activity.HomeActivity$18"
        );

        class_RemoteSettingDialog = "com.bjsm.dungeonlab.widget.RemoteSettingDialog";
        field_RemoteSettingDialog_strengthA = "a_channel_strength_range";
        field_RemoteSettingDialog_strengthB = "b_channel_strength_range";
    }
}
