package model;

import java.awt.*;

/**
 *
 */
public class ColorModel {

    private Color color;

    private String hexadecimalColor;

    //版本2.0更新，添加一个文本框，用来装RGB三个数值，因为发现有些PowerPoint没有十六进制
    private int R;
    private int G;
    private int B;

    public int getR() {
        return R;
    }

    public void setR(int r) {
        R = r;
    }

    public int getG() {
        return G;
    }

    public void setG(int g) {
        G = g;
    }

    public int getB() {
        return B;
    }

    public void setB(int b) {
        B = b;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getHexadecimalColor() {
        return hexadecimalColor;
    }

    public void setHexadecimalColor(String hexadecimalColor) {
        this.hexadecimalColor = hexadecimalColor;
    }
}
