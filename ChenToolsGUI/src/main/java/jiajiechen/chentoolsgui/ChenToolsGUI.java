package jiajiechen.chentoolsgui;

import jiajiechen.chentools.ChenTools;
import jiajiechen.chentools.ChenTools.InputOutput;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
@SuppressWarnings("serial")
public class ChenToolsGUI extends javax.swing.JFrame implements InputOutput {
    private JTextArea console;
    private JButton enterbutton;
    private JTextField entertext;
    private JComboBox<String> select;

    /**
     * Auto-generated main method to display this JFrame
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ChenToolsGUI inst = new ChenToolsGUI();
                inst.setLocationRelativeTo(null);
                inst.setVisible(true);
            }
        });
    }

    public ChenToolsGUI() {
        super();
        initGUI();
    }

    boolean syncLock;
    public static ChenToolsGUI inst;

    private void initGUI() {
        try {
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            getContentPane().setLayout(null);
            this.setPreferredSize(new java.awt.Dimension(512, 288));
            this.setTitle("ChenTools v" + ChenTools.version);
            {
                entertext = new JTextField();
                getContentPane().add(entertext);
                entertext.setBounds(0, 24, 400, 24);
                entertext.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent evt) {
                        entertextKeyTyped(evt);
                    }
                });
            }
            {
                enterbutton = new JButton();
                getContentPane().add(enterbutton);
                enterbutton.setText("\u8f93\u5165");
                enterbutton.setBounds(400, 24, 96, 24);
                enterbutton.setEnabled(false);
                enterbutton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        enterbuttonActionPerformed(evt);
                    }
                });
            }
            {
                console = new JTextArea();
                getContentPane().add(console);
                console.setBounds(0, 47, 496, 203);
                console.setEditable(false);
            }
            {
                ComboBoxModel<String> selectModel = new DefaultComboBoxModel<String>(
                        ChenTools.m);
                select = new JComboBox<String>();
                getContentPane().add(select);
                select.setModel(selectModel);
                select.setBounds(0, -1, 496, 24);
                select.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        selectActionPerformed(evt);
                    }
                });
            }
            pack();
            this.setSize(512, 288);
        } catch (Exception e) {
            // add your error handling code here
            e.printStackTrace();
        }
        ChenTools.io = this;
        inst = this;
        syncLock = true;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String version = ChenTools
                        .httpDownload("https://github.com/wiadufachen/ChenTools/blob/master/LATEST?raw=true");
                if (version.equals("")) {
                    JOptionPane.showConfirmDialog(ChenToolsGUI.this, "无法检测更新！",
                            "错误", JOptionPane.OK_OPTION,
                            JOptionPane.WARNING_MESSAGE);
                }
                if (version.compareTo(ChenTools.version) > 0) {
                    JLabel label = new JLabel();
                    Font font = label.getFont();

                    // create some css from the label's font
                    StringBuffer style = new StringBuffer("font-family:"
                            + font.getFamily() + ";");
                    style.append("font-weight:"
                            + (font.isBold() ? "bold" : "normal") + ";");
                    style.append("font-size:" + font.getSize() + "pt;");

                    // html content
                    JEditorPane ep = new JEditorPane(
                            "text/html",
                            "<html><body style=\"" + style
                                    + "\">" //
                                    + "检测到新版本！<a href=\"https://github.com/wiadufachen/ChenTools/blob/master/ChenToolsGUI.jar?raw=true\">下载地址</a>" //
                                    + "</body></html>");

                    // handle link events
                    ep.addHyperlinkListener(new HyperlinkListener() {
                        @Override
                        public void hyperlinkUpdate(HyperlinkEvent e) {
                            if (e.getEventType().equals(
                                    HyperlinkEvent.EventType.ACTIVATED))
                                try {
                                    Desktop.getDesktop()
                                            .browse(new java.net.URI(
                                                    "https://github.com/wiadufachen/ChenTools/blob/master/ChenTools.jar?raw=true"));
                                } catch (IOException e1) {
                                } catch (URISyntaxException e1) {
                                }
                        }
                    });
                    ep.setEditable(false);
                    ep.setBackground(label.getBackground());

                    // show
                    JOptionPane.showMessageDialog(null, ep);

                }
            }
        });
        thread.start();
        enterbutton.setEnabled(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    public void wantNumber() {
    }

    public void wantText() {
    }

    public static int currentSelect;
    public static ChenTools t = new ChenTools();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            ChenTools.work(currentSelect);
        }
    };

    public void writeToConsole(String str) {
        console.append(str);
    }

    public synchronized String getInput() throws InterruptedException {
        String ret = "";
        boolean flag = false;
        while (!flag) {
            flag = true;
            enterbutton.setEnabled(true);
            entertext.requestFocusInWindow();
            while (syncLock == true) {
                ChenToolsGUI.inst.wait();
            }
            syncLock = true;
            ret = entertext.getText().toString();
            entertext.setText("");
        }
        return ret;
    }

    public void inputError() {
        JOptionPane.showConfirmDialog(this, "输入错误！", "错误",
                JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE);
    }

    private synchronized void enterbuttonActionPerformed(ActionEvent evt) {
        try {
            syncLock = false;
            ChenToolsGUI.this.notify();
            enterbutton.setEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Thread thread = null;

    private void selectActionPerformed(ActionEvent evt) {
        if (evt.getActionCommand().equals("comboBoxChanged")) {
            currentSelect = select.getSelectedIndex();
            console.setText("");
            if (thread != null) {
                thread.interrupt();
            }
            thread = new Thread(runnable);
            thread.start();
        }
    }

    private void entertextKeyTyped(KeyEvent evt) {
        if (evt.getKeyChar() == '\n') {
            enterbutton.doClick();
        }
    }
}