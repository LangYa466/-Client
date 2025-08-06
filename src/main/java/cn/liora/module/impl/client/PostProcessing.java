package cn.liora.module.impl.client;

import cn.liora.Client;
import cn.liora.event.impl.ShaderEvent;
import cn.liora.module.Module;
import cn.liora.module.ModuleCategory;
import cn.liora.util.render.RenderUtil;
import cn.liora.util.render.blur.KawaseBloom;
import cn.liora.util.render.blur.KawaseBlur;
import cn.liora.value.impl.BoolValue;
import cn.liora.value.impl.NumberValue;
import net.minecraft.client.shader.Framebuffer;

/**
 * @author ChengFeng
 * @since 2024/8/3
 **/
public class PostProcessing extends Module {
    public PostProcessing() {
        super("PostProcessing", ModuleCategory.Client);
        locked = true;
    }

    private static Framebuffer stencilFramebuffer = new Framebuffer(1, 1, false);

    public static final BoolValue blur = new BoolValue("Blur", false);
    public static final NumberValue blurIterations = new NumberValue("BlurIterations", 3f,  5f, 1f, 1f);
    public static final NumberValue blurOffset = new NumberValue("BlurOffset", 3f,  5f, 1f, 1f);
    public static final BoolValue bloom = new BoolValue("Bloom", false);
    public static final NumberValue bloomIterations = new NumberValue("BloomIterations", 3f,  5f, 1f, 1f);
    public static final NumberValue bloomOffset = new NumberValue("BloomOffset", 3f,  5f, 1f, 1f);

    /**
     * 仅用于Widget在GuiInGame绘制模糊效果
     */
    public static void drawBlur() {
        if (blur.getValue()) {
            stencilFramebuffer = RenderUtil.createFrameBuffer(stencilFramebuffer);
            stencilFramebuffer.framebufferClear();
            stencilFramebuffer.bindFramebuffer(false);
            Client.instance.eventBus.post(new ShaderEvent(false));
            stencilFramebuffer.unbindFramebuffer();
            KawaseBlur.renderBlur(stencilFramebuffer.framebufferTexture, blurIterations.getValue().intValue(), blurOffset.getValue().intValue());
        }

        if (bloom.getValue()) {
            stencilFramebuffer = RenderUtil.createFrameBuffer(stencilFramebuffer);
            stencilFramebuffer.framebufferClear();
            stencilFramebuffer.bindFramebuffer(false);
            Client.instance.eventBus.post(new ShaderEvent(true));
            stencilFramebuffer.unbindFramebuffer();
            KawaseBloom.renderBlur(stencilFramebuffer.framebufferTexture, bloomIterations.getValue().intValue(), bloomOffset.getValue().intValue());
        }
    }
}
