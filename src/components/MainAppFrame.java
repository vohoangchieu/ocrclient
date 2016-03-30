/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

/**
 *
 * @author chieuvh
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import org.apache.log4j.Logger;

@SuppressWarnings("serial")
public class MainAppFrame extends JFrame {

    private static final Logger logger = Logger.getLogger(MainAppFrame.class);
    private JPanel contentPane = null;
    private File targetFile = null;
    private BufferedImage targetImg = null;
    private Image outputImg = null;
    private Color color1 = null, color2 = null;
    public JPanel westPanel = null, eastPanel = null, imageInputPanel = null, imageOutputPanel = null, colorPanel = null;
    private final String basePath = "I:\\";
    private List<JButton> colorButtonList = new ArrayList();
    private final JButton buttonRecognize = new JButton("Recognize");
    private final JButton btnReSelectColor = new JButton("Re Select Color");
    private JLabel imageOutputLabel = new JLabel();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainAppFrame frame = new MainAppFrame();
                    frame.setVisible(true);
                    frame.setResizable(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MainAppFrame() throws Exception {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1200, 720);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        westPanel = new JPanel();
        westPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        eastPanel = new JPanel();
        eastPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        contentPane.add(westPanel, BorderLayout.WEST);
        contentPane.add(eastPanel, BorderLayout.EAST);

        JButton btnBrowse = new JButton("Browse");
        btnBrowse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                browseButtonActionPerformed(e);
            }
        });

        JLabel lblSelectTargetPicture = new JLabel("Select picture..");

        btnReSelectColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.info("btnReSelectColor.actionPerformed");
                color1 = null;
                color2 = null;
                for (JButton jButton : colorButtonList) {
                    jButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
                }

            }
        });

        JButton btnRemoveColorInImage = new JButton("Remove Color In Image");
        btnRemoveColorInImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.info("btnRemoveColorInImage.actionPerformed");
                if (targetImg == null) {
                    JOptionPane.showMessageDialog(null,
                            "You must select one image to be the reference.", "Aborting...",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (color1 == null || color2 == null) {
                    JOptionPane.showMessageDialog(null,
                            "You must select 2 color to be remove in image", "Aborting...",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                outputImg = transformColorToTransparency(targetImg, color1, color2);
                BufferedImage resultImage2 = imageToBufferedImage(outputImg, targetImg.getWidth(), targetImg.getHeight());
                File outFile2 = new File(basePath, "map_with_transparency2.png");
                try {
                    ImageIO.write(resultImage2, "PNG", outFile2);
                } catch (IOException ex) {
                    logger.error(ex.getMessage(), ex);
                }
//                imageOutputPanel.removeAll();
//                imageOutputPanel.add(new JLabel(new ImageIcon(resultImage2)));
                imageOutputLabel.setIcon(new ImageIcon(resultImage2));
                imageOutputPanel.updateUI();
            }
        });

        buttonRecognize.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        imageOutputPanel = new JPanel();
        imageOutputPanel.add(imageOutputLabel);
        imageOutputPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        GroupLayout eastLayout = new GroupLayout(eastPanel);
        eastLayout.setHorizontalGroup(eastLayout.createSequentialGroup().addComponent(imageOutputPanel, GroupLayout.PREFERRED_SIZE, 600, GroupLayout.PREFERRED_SIZE));
        eastLayout.setVerticalGroup(eastLayout.createSequentialGroup().addComponent(imageOutputPanel, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE));
        eastPanel.setLayout(eastLayout);
        imageInputPanel = new JPanel();
        imageInputPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        colorPanel = new JPanel(new GridBagLayout());
        GroupLayout westLayout = new GroupLayout(westPanel);
        westLayout.setHorizontalGroup(westLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(westLayout.createSequentialGroup()
                        //.addGap(6)
                        .addGroup(westLayout.createParallelGroup(Alignment.LEADING)
                                .addGroup(westLayout.createSequentialGroup()
                                        .addComponent(lblSelectTargetPicture)
                                        //.addGap(6)
                                        .addComponent(btnBrowse))
                                .addGroup(westLayout.createSequentialGroup()
                                        .addGap(10)
                                        .addComponent(btnReSelectColor)
                                        .addGap(10)
                                        .addComponent(btnRemoveColorInImage)
                                        .addGap(10)
                                        .addComponent(buttonRecognize))
                        //                                .addGroup(groupLayout.createSequentialGroup()
                        //                                        .addComponent(colorPanel))
                        )
                )
                //                                .addGroup(groupLayout.createSequentialGroup()
                //                                        .addComponent(colorPanel)

                .addGroup(westLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(imageInputPanel, GroupLayout.PREFERRED_SIZE, 600, GroupLayout.PREFERRED_SIZE))
                .addGroup(westLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(colorPanel, GroupLayout.PREFERRED_SIZE, 600, GroupLayout.PREFERRED_SIZE))
        );
        westLayout.setVerticalGroup(westLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(westLayout.createSequentialGroup()
                        .addGroup(westLayout.createParallelGroup(Alignment.LEADING)
                                .addGroup(westLayout.createSequentialGroup()
                                        //.addGap(7)
                                        .addComponent(lblSelectTargetPicture))
                                .addGroup(westLayout.createSequentialGroup()
                                        //.addGap(3)
                                        .addComponent(btnBrowse)))
                        //.addGap(18)
                        .addComponent(imageInputPanel, GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE)
                        .addComponent(colorPanel, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                        .addGap(20)
                        .addGroup(westLayout.createParallelGroup(Alignment.BASELINE)
                                .addComponent(btnReSelectColor)
                                .addComponent(btnRemoveColorInImage)
                                .addComponent(buttonRecognize))
                        //                        .addGap(20)
                        .addContainerGap())
        );

        westPanel.setLayout(westLayout);

        ColorLoader.load();

        for (int i = 0; i < ColorLoader.colorList.size(); i++) {
            MyColor myColor = ColorLoader.colorList.get(i);
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(20, 20));
            button.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
            button.setBackground(myColor.color);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    logger.info("buttonColor.actionPerformed");
                    if (color1 != null && color2 != null) {
                        btnReSelectColor.doClick();
                    }
                    JButton button = (JButton) e.getSource();
                    Color color = button.getBackground();
                    button.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));

                    if (color1 == null) {
                        color1 = color;
                        color1 = new Color(51, 0, 0);
                    } else if (color2 == null) {
                        color2 = color;
                        color2 = new Color(255, 153, 204);
                    }
                }
            });
            colorButtonList.add(button);

        }

        GridBagConstraints c = new GridBagConstraints();
//        c.insets = new Insets(2, 2, 2, 2);
        c.weightx = 20;
        c.weighty = 20;
        int count = 0;
        for (JButton jButton : colorButtonList) {

            c.fill = GridBagConstraints.VERTICAL;
            c.gridx = (count % 13) * c.gridwidth;
            c.gridy = (count / 13) * c.gridheight;
            colorPanel.add(jButton, c);
            count++;
        }
    }

    public BufferedImage rescale(BufferedImage originalImage) {
//        BufferedImage resizedImage = new BufferedImage(baseSize, baseSize, BufferedImage.TYPE_INT_RGB);
//        Graphics2D g = resizedImage.createGraphics();
//        g.drawImage(originalImage, 0, 0, baseSize, baseSize, null);
//        g.dispose();
//        return resizedImage;
        return originalImage;
    }

    public void setTarget(File reference) {
        try {
            targetFile = reference;
            targetImg = rescale(ImageIO.read(reference));
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }

        imageInputPanel.setLayout(new BorderLayout(0, 0));
        imageInputPanel.add(new JLabel(new ImageIcon(targetImg)));
//        imageOutputPanel.add(new JLabel(new ImageIcon(targetImg)));
        setVisible(true);
    }

    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fc = new JFileChooser(basePath);
        fc.setFileFilter(new JPEGImageFileFilter());
        int res = fc.showOpenDialog(null);
        // We have an image!
        try {
            if (res == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                setTarget(file);
            } // Oops!
            else {
                JOptionPane.showMessageDialog(null,
                        "You must select one image to be the reference.", "Aborting...",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

    }

    private Image transformColorToTransparency(BufferedImage image, Color c1, Color c2) {
        // Primitive test, just an example
        final int r1 = c1.getRed();
        final int g1 = c1.getGreen();
        final int b1 = c1.getBlue();
        final int r2 = c2.getRed();
        final int g2 = c2.getGreen();
        final int b2 = c2.getBlue();
        logger.info(String.format("r1=%d,r2=%d", r1, r2));
        logger.info(String.format("g1=%d,g1=%d", g1, g2));
        logger.info(String.format("b1=%d,b2=%d", b1, b2));
        ImageFilter filter = new RGBImageFilter() {
            @Override
            public final int filterRGB(int x, int y, int rgb) {
                int r = (rgb & 0xFF0000) >> 16;
                int g = (rgb & 0xFF00) >> 8;
                int b = rgb & 0xFF;
                if (r >= r1 && r <= r2
                        && g >= g1 && g <= g2
                        && b >= b1 && b <= b2) {
                    // Set fully transparent but keep color

                    return rgb & 0xFFFFFF;
                }
                return rgb;
            }
        };

        ImageProducer ip = new FilteredImageSource(image.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }

    private BufferedImage imageToBufferedImage(Image image, int width, int height) {
        BufferedImage dest = new BufferedImage(
                width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = dest.createGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return dest;
    }

}
