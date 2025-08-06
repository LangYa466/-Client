package cn.liora.module.impl.render;

import cn.liora.event.api.EventTarget;
import cn.liora.event.impl.TickEvent;
import cn.liora.module.Module;
import cn.liora.module.ModuleCategory;
import cn.liora.module.impl.render.motionBlur.MotionBlurResourceManager;
import cn.liora.value.impl.NumberValue;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Field;
import java.util.Map;

public class MotionBlur extends Module {
    public static final NumberValue amount = new NumberValue("Multiplier", 2, 10, 1,1);

    float lastValue = 0F;
    private Map<String, MotionBlurResourceManager> domainResourceManagers;

    public MotionBlur() {
        super("MotionBlur", ModuleCategory.Render);
    }

    @Override
    public void onDisable() {
        mc.entityRenderer.stopUseShader();
    }

    @Override
    public void onEnable() {
        if(this.domainResourceManagers == null) {
            try {
                Field[] fields = SimpleReloadableResourceManager.class.getDeclaredFields();
                for (Field field : fields) {
                    if (field.getType() == Map.class) {
                        field.setAccessible(true);
                        this.domainResourceManagers = (Map<String, MotionBlurResourceManager>) field.get(mc.getResourceManager());
                        break;
                    }
                }
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }

        if(!this.domainResourceManagers.containsKey("motionblur")) {
            this.domainResourceManagers.put("motionblur", new MotionBlurResourceManager());
        }

        this.lastValue = amount.getValue().floatValue();
        applyShader();
    }

    public boolean isFastRenderEnabled() {
        try {
            Field fastRender = GameSettings.class.getDeclaredField("ofFastRender");
            return fastRender.getBoolean(mc.gameSettings);
        } catch (Exception exception) {
            return false;
        }
    }

    public void applyShader() {
        mc.entityRenderer.loadShader(new ResourceLocation("motionblur", "motionblur"));
    }

    @EventTarget
    public void onTick(TickEvent event) {
        if((!mc.entityRenderer.isShaderActive() || this.lastValue != amount.getValue()) && mc.theWorld != null && !isFastRenderEnabled()) {
            this.lastValue = amount.getValue().floatValue();
            applyShader();
        }
        if(this.domainResourceManagers == null) {
            try {
                Field[] fields = SimpleReloadableResourceManager.class.getDeclaredFields();
                for (Field field : fields) {
                    if (field.getType() == Map.class) {
                        field.setAccessible(true);
                        this.domainResourceManagers = (Map<String, MotionBlurResourceManager>) field.get(mc.getResourceManager());
                        break;
                    }
                }
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
        if(!this.domainResourceManagers.containsKey("motionblur")) {
            this.domainResourceManagers.put("motionblur", new MotionBlurResourceManager());
        }
    }
}