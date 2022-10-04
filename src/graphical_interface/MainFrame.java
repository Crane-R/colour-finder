package graphical_interface;

import graphical_interface.base_on_mainFrame.FullScreenFrame;
import graphical_interface.base_on_mainFrame.ShowMessageFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class MainFrame extends JFrame {

    private final JTextArea showColorArea;

    private final JTextField fontColorText;

    //版本2.0的更新，新加一个文本框，加了RGB的值
    private final JTextField RGBText;

    //2.04
    public static String versionTitle = "Colour Finder v2.11";

    //get - set方法区

    public void setShowColorArea(Color color) {
        this.showColorArea.setBackground(color);
    }

    public void setFontColorText(String fontColorText) {
        this.fontColorText.setText(fontColorText);
    }

    public void setRGBText(String RGBValue) {
        this.RGBText.setText(RGBValue);
    }

    /**
     * 构造方法，主窗体初始化
     */
    public MainFrame() {

        this.setTitle(versionTitle);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 250);
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        //设置标题栏图片
        try {
            BufferedImage imageIcon = ImageIO.read(Objects.requireNonNull(this.getClass().getResource("/img/header.jpg")));
            this.setIconImage(imageIcon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //颜色预览
        JLabel showColour = new JLabel("Colour Preview");
        showColour.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 14));
        showColour.setBounds(35, 15, 160, 40);
        showColour.setForeground(Color.decode("#002FA7"));
        this.add(showColour);

        //十六进制颜色码
        JLabel hex = new JLabel("RGB(HEX)");
        hex.setFont(new Font("微软雅黑", Font.BOLD, 14));
        hex.setBounds(225, 115, 150, 30);
        hex.setForeground(Color.decode("#002FA7"));
        this.add(hex);

        //文本域
        showColorArea = new JTextArea();
        showColorArea.setBounds(30, 50, 120, 120);
        showColorArea.setEditable(false);

        //边框
        Border border = BorderFactory.createLineBorder(Color.decode("#7BB0F2"));
        showColorArea.setBorder(border);

        this.add(showColorArea);

        JButton about = new JButton("<html><span style='color:#002FA7'><b>About</b></span></html>");
        about.setBounds(330, 15, 80, 30);
        about.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        about.setFocusPainted(false);
        about.setBackground(Color.decode("#F4D201"));
        about.setBorderPainted(false);
        //边框真的丑  ---  浅色就还行
        about.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        about.addActionListener(e -> {
            //关于按钮的点击事件
            ShowMessageFrame showMessageFrame = new ShowMessageFrame();
            showMessageFrame.setVisible(true);
        });
        this.add(about);

        //按钮
        JButton getColorButton = new JButton("<html><span style='color:white'><b>Pick</b></span></html>");
        getColorButton.setBounds(230, 15, 80, 30);
        getColorButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        getColorButton.setFocusPainted(false);
        getColorButton.setBackground(Color.decode("#002FA7"));
        getColorButton.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        getColorButton.setBorderPainted(false);
        //点击按钮后弹出大窗口
        //按钮的监听事件
        getColorButton.addActionListener(e -> {

            //暂时关闭主界面的显示
            closeFrame();

            //当按钮点击后，关闭主界面打开全屏界面，但不知为什么主页面还没完全关闭就截图了
            //怀疑是关闭界面的动画导致
            //因时间紧迫，所以只好用强制线程休眠的方式来避免这个bug，经过几次测试200毫秒应该是稳定的
            try {
                Thread.sleep(200);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }

            //弹出全屏界面
            new FullScreenFrame().setVisible(true);
        });
        this.add(getColorButton);


        //文本框
        fontColorText = new JTextField();
        fontColorText.setEditable(false);
        fontColorText.setBounds(220, 140, 200, 30);
        fontColorText.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        fontColorText.setBorder(border);
        //文本框居中显示
        fontColorText.setHorizontalAlignment(JTextField.CENTER);
        this.add(fontColorText);

        //RGB
        RGBText = new JTextField();
        RGBText.setEditable(false);
        RGBText.setBounds(220, 80, 200, 30);
        RGBText.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        RGBText.setBorder(border);
        RGBText.setHorizontalAlignment(JTextField.CENTER);
        this.add(RGBText);


        JLabel RGBLabel = new JLabel("RGB");
        RGBLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
        RGBLabel.setBounds(225, 55, 150, 30);
        RGBLabel.setForeground(Color.decode("#002FA7"));
        this.add(RGBLabel);
    }

    //关闭主界面
    public void closeFrame() {
        //setVisible(false)是将当前窗体设为不可见
        //dispose应该是立即关闭窗口
        this.dispose();
    }

}
