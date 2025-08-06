package cn.liora.ui.clickgui2;

import cn.liora.util.render.RenderUtil;

public abstract class Movable {
    public float x, y, width, height;
    public int mouseX, mouseY;
    public boolean hovering;

    public void update(float x, float y, float width, float height, int mouseX, int mouseY) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.hovering = RenderUtil.hovering(mouseX, mouseY, x, y, width, height);
    }

    public abstract void render();

    public abstract void mouseClicked(int mouseX, int mouseY, int mouseButton);
}