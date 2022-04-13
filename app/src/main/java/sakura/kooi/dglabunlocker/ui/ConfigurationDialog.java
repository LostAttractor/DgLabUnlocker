package sakura.kooi.dglabunlocker.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.util.Log;
import android.util.StateSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.function.Consumer;

import sakura.kooi.dglabunlocker.utils.UiUtils;
import sakura.kooi.dglabunlocker.variables.ModuleSettings;
import sakura.kooi.dglabunlocker.variables.ResourceInject;

public class ConfigurationDialog {
    private static void createSettingSwitches(LinearLayout container) {
        createSwitch(container, "被控 | 解锁远程强度上限", "最高100完全不够用好吧",
                "unlockRemoteMaxStrength", val -> ModuleSettings.unlockRemoteMaxStrength = val);
        createSwitch(container, "被控 | 强制锁定本地强度", "暴力锁死本地强度 (不优雅实现)",
                "enforceLocalStrength", val -> ModuleSettings.enforceLocalStrength = val);
        createSwitch(container, "被控 | 屏蔽非法超高强度", "防止恶意用户烧掉你的设备",
                "deviceProtection", val -> ModuleSettings.deviceProtection = val);
        createSwitch(container, "被控 | 强制限制远程强度", "下面那个功能的防御",
                "enforceRemoteMaxStrength", val -> ModuleSettings.enforceRemoteMaxStrength = val);
        createSwitch(container, "主控 | 无视强度上限设置", "想拉多高拉多高 (坏.jpg",
                "bypassRemoteMaxStrength", val -> ModuleSettings.bypassRemoteMaxStrength = val);
    }

    @SuppressLint({"UseSwitchCompatOrMaterialCode", "SetTextI18n", "UseCompatLoadingForDrawables"})
    public static View createSettingsPanel(Context context) {
        LinearLayout container = new LinearLayout(context);
        container.setPadding(UiUtils.dpToPx(container, 16), UiUtils.dpToPx(container, 16), UiUtils.dpToPx(container, 16), UiUtils.dpToPx(container, 16));
        container.setBackground(ResourceInject.dialogSettingsBackground.getConstantState().newDrawable());
        container.setOrientation(LinearLayout.VERTICAL);
        TextView header = new TextView(context);
        header.setText("DG-Lab Unlocker 设置");
        header.setGravity(Gravity.CENTER);
        header.setPadding(0, 0, 0, UiUtils.dpToPx(container, 16));
        container.addView(header);

        createSettingSwitches(container);

        TextView space = new TextView(context);
        space.setPadding(0, UiUtils.dpToPx(space, 4), 0, 0);
        container.addView(space);

        TextView btnStatus = new TextView(context);
        btnStatus.setPadding(0, UiUtils.dpToPx(btnStatus, 3), 0, UiUtils.dpToPx(btnStatus, 3));
        btnStatus.setBackground(ResourceInject.buttonBackground.getConstantState().newDrawable());
        btnStatus.setText("模块运行状态");
        btnStatus.setTextColor(Color.BLACK);
        //btnStatus.setTextColor(0xffe99d);
        btnStatus.setGravity(Gravity.CENTER);
        btnStatus.setOnClickListener(e -> new StatusDialog(context).show());
        container.addView(btnStatus);

        return container;
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private static void createSwitch(LinearLayout container, String title, String desc, String config, Consumer<Boolean> handler) {
        LinearLayout layout = new LinearLayout(container.getContext());
        layout.setPadding(0, UiUtils.dpToPx(layout, 6), 0, 0);
        TextView textTitle = new TextView(container.getContext());
        textTitle.setText(title);
        textTitle.setTextColor(0xffffe99d);
        textTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        layout.addView(textTitle);
        Switch settingSwitch = new Switch(container.getContext());
        settingSwitch.setPadding(UiUtils.dpToPx(layout, 10), 0, 0, 0);
        StateListDrawable trackSelector = new StateListDrawable();
        trackSelector.addState(new int[]{android.R.attr.state_checked}, ResourceInject.switchOpenTrack.getConstantState().newDrawable());
        trackSelector.addState(StateSet.WILD_CARD, ResourceInject.switchCloseTrack.getConstantState().newDrawable());
        settingSwitch.setTrackDrawable(trackSelector);
        StateListDrawable thumbSelector = new StateListDrawable();
        thumbSelector.addState(new int[]{android.R.attr.state_checked}, ResourceInject.switchOpenThumb.getConstantState().newDrawable());
        thumbSelector.addState(StateSet.WILD_CARD, ResourceInject.switchCloseThumb.getConstantState().newDrawable());
        settingSwitch.setThumbDrawable(thumbSelector);
        settingSwitch.setTextOff("");
        settingSwitch.setTextOn("");
        settingSwitch.setChecked(ModuleSettings.sharedPref.getBoolean(config, false));
        settingSwitch.setOnClickListener(e -> {
            handler.accept(settingSwitch.isChecked());
            ModuleSettings.sharedPref.edit().putBoolean(config, settingSwitch.isChecked()).commit();
            Log.i("DgLabUnlocker", "Config " + config + " set to " + settingSwitch.isChecked());
        });
        layout.addView(settingSwitch);
        container.addView(layout);

        TextView textDesc = new TextView(container.getContext());
        textDesc.setText(desc);
        textDesc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
        textDesc.setPadding(0, UiUtils.dpToPx(layout, 1), 0, UiUtils.dpToPx(layout, 4));
        textDesc.setTextColor(0xffdfd2a5);
        container.addView(textDesc);
    }

}