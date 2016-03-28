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
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.Insets;

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
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

@SuppressWarnings("serial")
public class MainAppFrame extends JFrame {

    private JPanel contentPane;
    File targetFile;
    BufferedImage targetImg;
    public JPanel westPanel, eastPanel, imageInputPanel, imageOutputPanel, colorPanel;
    private static final int baseSize = 128;
    private static final String basePath
            = "C:\\Documents and Settings\\Administrator\\Desktop\\Images";

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

        JLabel lblSelectTargetPicture = new JLabel("Select target picture..");

        JButton btnDetect = new JButton("Detect");
        btnDetect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

        JButton btnAddDigit = new JButton("Add Digit");
        btnAddDigit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

        JButton buttonRecognize = new JButton("Recognize");
        buttonRecognize.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        imageOutputPanel = new JPanel();
        imageOutputPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        GroupLayout eastLayout = new GroupLayout(eastPanel);
        eastLayout.setHorizontalGroup(eastLayout.createSequentialGroup().addComponent(imageOutputPanel, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE));
        eastLayout.setVerticalGroup(eastLayout.createSequentialGroup().addComponent(imageOutputPanel, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE));
        eastPanel.setLayout(eastLayout);
        imageInputPanel = new JPanel();
        imageInputPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        colorPanel = new JPanel();
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
                                        .addComponent(btnDetect)
                                        .addGap(10)
                                        .addComponent(btnAddDigit)
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
                        .addComponent(imageInputPanel, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
                        .addComponent(colorPanel, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                        //.addGap(22)
                        .addGroup(westLayout.createParallelGroup(Alignment.BASELINE)
                                .addComponent(btnDetect)
                                .addComponent(btnAddDigit)
                                .addComponent(buttonRecognize))
                        //.addGap(18)
                        .addContainerGap())
        );

        westPanel.setLayout(westLayout);

        ColorLoader.load();
        GridBagConstraints c = new GridBagConstraints();
        int count = 0;
        for (int i = 0; i < ColorLoader.colorList.size(); i++) {
            MyColor myColor = ColorLoader.colorList.get(i);
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(20, 20));

            button.setBackground(myColor.color);
//            c.gridwidth = 150;
//            c.gridheight = 100;
            c.insets = new Insets(10, 10, 10, 10);
            c.fill = GridBagConstraints.VERTICAL;
            c.gridx = (count % 10) * c.gridwidth;
            c.gridy = (count / 10) * c.gridheight;
            colorPanel.add(button, c);
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
            Logger.getLogger(MainAppFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        imageInputPanel.setLayout(new BorderLayout(0, 0));
        imageInputPanel.add(new JLabel(new ImageIcon(targetImg)));
        imageOutputPanel.add(new JLabel(new ImageIcon(targetImg)));
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
        } catch (Exception iOException) {
        }

    }
}
