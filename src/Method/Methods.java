package Method;

import model.ColorModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Methods {


    //获取颜色的方法
    public static ColorModel getColor() {
        //创建机器人
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException awtException) {
            awtException.printStackTrace();
        }

        //获取鼠标当前位置
        Point mousePoint = MouseInfo.getPointerInfo().getLocation();
        int X = mousePoint.x;
        int Y = mousePoint.y;

        //获取颜色
        assert robot != null;
        Color color = robot.getPixelColor(X, Y);

        int R = color.getRed();
        System.out.println("R的值：" + R);
        String rString = checkColour(R);

        int G = color.getGreen();
        System.out.println("G的值：" + G);
        String gString = checkColour(G);

        int B = color.getBlue();
        System.out.println("B的值：" + B);
        String bString = checkColour(B);

        //输出判断完成后正确的颜色值
        String hexadecimalColor = "#" + rString + gString + bString;

        //构建颜色模型
        ColorModel colorModel = new ColorModel();
        colorModel.setColor(color);
        colorModel.setHexadecimalColor(hexadecimalColor);
        //版本2.0的继续构建模型，找到设置颜色的地方，加进去就行了
        colorModel.setR(R);
        colorModel.setG(G);
        colorModel.setB(B);

        System.out.println(hexadecimalColor);

        return colorModel;
    }


    //十六进制的颜色值正确性检测方法，传入判断的int，返回一个字符串直接输出就好了
    private static String checkColour(int judgeColour) {
        //最终输出的颜色值，如果小于10，也就是1位，加0返回，
        //如果大于10，调用转化为16进制的方法返回
        String outPutColour;
        //（检测出问题）2.检测前提，先转化为十六进制的，如果是一位，说明前面有个0，那就加0， ----测试成功
        String rString = Integer.toHexString(judgeColour);
        //转换成String用于判断
        if (rString.length() == 1) {
            System.out.print("检测到颜色值长度为1，已更正为：");
            outPutColour = "0" + rString;
            System.out.println(outPutColour);
        } else {
            //说明颜色大于10
            outPutColour = Integer.toHexString(judgeColour);
        }
        //返回前全部改为大写，频繁的构建String可能会有性能上的问题
        outPutColour = outPutColour.toUpperCase();
        return outPutColour;
    }


    //图片的放大方法
    public static BufferedImage modifyImage(BufferedImage mini, double scale) {
        int w = (int) Math.round(mini.getWidth() * scale);
        int h = (int) Math.round(mini.getHeight() * scale);
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2 = bi.createGraphics();
        bi = g2.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT);
        g2 = bi.createGraphics();
        //画图
        g2.drawImage(mini, 0, 0, w, h, null);
        //关闭资源
        g2.dispose();
        return bi;

    }


    //摘抄网上的转换方法，image转BufferedImage
    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        image = new ImageIcon(image).getImage();
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            int transparency = Transparency.OPAQUE;
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(
                    image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
        }

        if (bimage == null) {
            int type = BufferedImage.TYPE_INT_RGB;
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }
        Graphics g = bimage.createGraphics();

        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bimage;
    }


    //删除文件的方法
    public static boolean deleteScreenShot() {

        //软件处理文件路径
        String filePath = "C:\\colour_finder\\screenPhoto.png";
        File file = new File(filePath);
        File parentFolder = file.getParentFile();

        file.delete();
        return parentFolder.delete();
    }


    //写入图片
    public void writeImageLocal(String newPath, BufferedImage newImg) {
        if (newPath != null && newImg != null) {
            try {
                File outPutFile = new File(newPath);
                ImageIO.write(newImg, "png", outPutFile);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    //截取全屏的方法，并返回一个image
    public Image screenShot(Dimension dimension) {
        //创建机器人截取全屏
        Image image = null;
        try {
            Robot robot = new Robot();
            //Rectangle 区域类
            Rectangle rec = new Rectangle(0, 0, dimension.width, dimension.height);
            BufferedImage bi = robot.createScreenCapture(rec);
            image = bi;

        } catch (AWTException e) {
            e.printStackTrace();
        }
        return image;
    }

    public Image screenShot(int x, int y, int width, int height) {
        //创建机器人截取全屏
        Image image = null;
        try {
            Robot robot = new Robot();
            //Rectangle 区域类
            Rectangle rec = new Rectangle(x, y, width, height);
            BufferedImage bi = robot.createScreenCapture(rec);
            image = bi;

        } catch (AWTException e) {
            e.printStackTrace();
        }
        return image;
    }

}
