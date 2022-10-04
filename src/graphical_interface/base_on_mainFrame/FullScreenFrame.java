package graphical_interface.base_on_mainFrame;

import Method.Methods;
import graphical_interface.MainFrame;
import model.ColorModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * 全屏界面，点击拾色器按钮后，弹出此界面，截屏，全屏界面的背景图为截图，鼠标变成十字
 */
public class FullScreenFrame extends JFrame {

    //选取颜色时左上角的Label标签
    private final JLabel enlargeImgLabel;

    BufferedImage allBufferedImage = null;

    //创建方法类的对象
    private static final Methods methods = new Methods();

    //为兼容所有电脑，统一路径
    private final String path;

    //资料的文件和文件夹
    File file;
    File fileFolder;

    //创建文件夹和文件的操作
    //C盘里面的文件需要管理员权限，调用cmd太麻烦了
    //所以就在C根目录下创建，用完删除就好了
    {
        path = "C:\\colour_finder\\screenPhoto.png";
        fileFolder = new File("C:\\colour_finder");
        System.out.println("colour_finder文件夹创建状态：" + fileFolder.mkdir());
        file = new File(path);
        try {
            System.out.println("screenPhoto.png文件创建状态" + file.createNewFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public FullScreenFrame() {

        //获取屏幕的尺寸
        Toolkit tk = Toolkit.getDefaultToolkit(); // 获取缺省工具包
        //尺寸类
        Dimension di = tk.getScreenSize(); // 屏幕尺寸规格
        di.height = 1080;
        di.width = 1920;
        System.out.println("屏幕的宽为：" + di.width);
        System.out.println("屏幕的高为：" + di.height);


        this.setSize(di.width, di.height);
        this.setLocationRelativeTo(null);
        //禁止全屏显示，可选
        this.setResizable(false);
        this.setLayout(null);
        //隐藏标题栏
        this.setUndecorated(true);

        //包装的小方法，改变鼠标样式
        changeMouse();

        //截屏
        Image image = methods.screenShot(di);

        //思路二，如果成功了要考虑文件的创建和删除问题，要统一任何电脑的路径
        ImageIcon icon = new ImageIcon(image);
        JLabel fullScreenImage = new JLabel(icon);
        fullScreenImage.setSize(di.width, di.height);

        fullScreenImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //鼠标按下事件

                //获取颜色模型
                ColorModel colorModel = Methods.getColor();

                MainFrame mainFrame = new MainFrame();
                mainFrame.setFontColorText(colorModel.getHexadecimalColor());
                mainFrame.setShowColorArea(colorModel.getColor());

                //设置版本2.0的属性
                mainFrame.setRGBText(colorModel.getR() + " " + colorModel.getG() + " " + colorModel.getB());

                mainFrame.setVisible(true);

                //关闭界面
                closeFrame();

                //调用删除截屏的方法
                boolean isDelete = Methods.deleteScreenShot();
                System.out.println("流程结束后截图文件是否删除：" + isDelete);
            }


        });

        //鼠标的移动事件，实时更新左上角Label标签显示的背景图片
        fullScreenImage.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                //鼠标的拖拽事件
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                //鼠标的移动事件
                //调用放大方法（貌似是包装好的），传入一个Image，倍数，返回一个Image
                //new 一个panel，然后获取鼠标坐标，以其为中心创建panel，然后传入坐标和大小，截图，放大显示在左上角
                //获取鼠标的坐标
                Point mousePoint = MouseInfo.getPointerInfo().getLocation();
                //x轴的位置
                int X = mousePoint.x;
                //y轴的位置
                int Y = mousePoint.y;
                System.out.println("鼠标当前位置：x: " + X + "\ty: " + Y);

                //调用截图方法，传入坐标和大小截图
                Image image1 = methods.screenShot(X - 75, Y - 75, 150, 150);
                //转换
                BufferedImage bufferedImage = Methods.toBufferedImage(image1);
                //掉用放大方法    5倍
                BufferedImage bufferedImage1 = Methods.modifyImage(bufferedImage, 5);

                //能正确的写入路径证明上面的是好用的
                methods.writeImageLocal(path, bufferedImage1);

                //让label不断更新核心代码
                ImageIcon imageIcon = null;
                try {
                    imageIcon = new ImageIcon(ImageIO.read(new File(path)));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                enlargeImgLabel.setIcon(imageIcon);
            }
        });

        try {
            System.out.println("path是否为空：" + path);
            allBufferedImage = ImageIO.read(new File(path));
            System.out.println("allBufferedImage是否为空：" + allBufferedImage);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        //左上角放大的图片，先读取一遍path
        enlargeImgLabel = new JLabel(new ImageIcon(path));
        System.out.println("enlargeImgLabel是否为空：" + enlargeImgLabel);
        enlargeImgLabel.setBounds(100, 100, 150, 150);
        enlargeImgLabel.setBorder(BorderFactory.createLineBorder(Color.decode("#2255d7")));
        //enlargeImgLabel.setText("--------");
        fullScreenImage.add(enlargeImgLabel);

        this.add(fullScreenImage);

        //十字
        JLabel cross = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/crossIcon.png"))));
        cross.setBounds(0, 0, 150, 150);
        cross.setFont(new Font("微软雅黑", Font.BOLD, 22));
        cross.setForeground(Color.white);
        enlargeImgLabel.add(cross);
    }


    //包装小方法，关闭全屏界面的显示
    public void closeFrame() {
        this.dispose();
    }

    //让鼠标变成十字指针的方法
    public void changeMouse() {
        Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
        this.setCursor(cursor);
    }


}
