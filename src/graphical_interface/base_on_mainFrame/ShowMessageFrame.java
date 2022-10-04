package graphical_interface.base_on_mainFrame;

import graphical_interface.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ShowMessageFrame extends JFrame {

    public ShowMessageFrame() {

        this.setTitle(MainFrame.versionTitle);
        this.setSize(475, 232);
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        try {
            BufferedImage imageIcon = ImageIO.read(Objects.requireNonNull(this.getClass().getResource("/img/header.jpg")));
            this.setIconImage(imageIcon);
        } catch (IOException e) {
            e.printStackTrace();
        }


        JLabel img = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/blueWarning.png"))));
        img.setBounds(30, 45, 60, 60);
        this.add(img);

        JLabel one = new JLabel("为解决卓越项目的取色问题而设计此软件。");
        one.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        one.setBounds(115, 20, 300, 30);
        this.add(one);

        JLabel two = new JLabel("作者：周星学");
        two.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        two.setBounds(115, 45, 300, 30);
        this.add(two);

        JLabel three = new JLabel("发布日期：2021年11月9日");
        three.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        three.setBounds(115, 70, 300, 30);
        this.add(three);

        //2.0最近更新日期
        JLabel updateTime = new JLabel("<html><span style='color:#002FA7'>最近更新日期：2022年04月13日</span></html>");
        updateTime.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        updateTime.setBounds(115, 95, 300, 30);
        this.add(updateTime);

        JButton ensure = new JButton("<html><span style='color:#002FA7'><b>Sure</b></span></html>");
        ensure.setBounds(300, 130, 100, 30);
        ensure.setFocusPainted(false);
        ensure.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        ensure.setBackground(Color.decode("#F4D201"));
        ensure.setBorderPainted(false);
        ensure.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        ensure.addActionListener(e -> closeFrame());
        this.add(ensure);
    }

    public void closeFrame() {
        //setVisible(false)是将当前窗体设为不可见
        //dispose应该是立即关闭窗口
        this.dispose();
    }
}
