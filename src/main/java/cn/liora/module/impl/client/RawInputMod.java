package cn.liora.module.impl.client;

import cn.liora.module.Module;
import cn.liora.module.ModuleCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MouseHelper;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.opengl.Display;

public class RawInputMod extends Module {
    private GLFWCursorPosCallback cursorCallback;

    public static int dx = 0;
    public static int dy = 0;
    private double lastX = -1;
    private double lastY = -1;

    public RawInputMod() {
        super("RawInputMod", ModuleCategory.Client);
    }

    public void start() {
        try {
            Minecraft mc = Minecraft.getMinecraft();
            long windowHandle = Display.Window.handle;

            cursorCallback = new GLFWCursorPosCallback() {
                @Override
                public void invoke(long window, double xpos, double ypos) {
                    if (lastX != -1 && lastY != -1) {
                        dx += xpos - lastX;
                        dy += ypos - lastY;
                    }
                    lastX = xpos;
                    lastY = ypos;

                    if (mc.currentScreen != null) {
                        dx = 0;
                        dy = 0;
                    }
                }
            };

            GLFW.glfwSetCursorPosCallback(windowHandle, cursorCallback);
            mc.mouseHelper = new RawMouseHelper();

        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public void stop() {
        try {
            if (cursorCallback != null) {
                cursorCallback.free();
                cursorCallback = null;
            }

            dx = dy = 0;
            lastX = lastY = -1;

            Minecraft.getMinecraft().mouseHelper = new MouseHelper(); // 还原默认
        } catch (Exception ignored) {
        }
    }

    public static class RawMouseHelper extends MouseHelper {
        @Override
        public void mouseXYChange() {
            deltaX = RawInputMod.dx;
            RawInputMod.dx = 0;
            deltaY = -RawInputMod.dy;
            RawInputMod.dy = 0;
        }
    }
}