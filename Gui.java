/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Pavan
 */
public class Gui extends JFrame {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    private JLabel HexWithSpace, HexWithOutSpace;
    private JTextField HexWSpace, HexWOSpace;
    private JButton convertB, exitB, saveB;

//Button handlers:
    private ConvertButtonHandler cbHandler;
    private ExitButtonHandler ebHandler;
    private SaveButtonHandler sbHandler;

    private String concatenate;
    private String line = null;
    public String out = "";

    public Gui() {
        HexWithSpace = new JLabel("Enter the Hex data with spaces", SwingConstants.LEFT);
        HexWithOutSpace = new JLabel("Hex data without spaces", SwingConstants.RIGHT);

        HexWSpace = new JTextField(10);
        HexWOSpace = new JTextField(10);
//SPecify handlers for each button and add (register) ActionListeners to each button.
        convertB = new JButton("Convert");
        cbHandler = new ConvertButtonHandler();
        convertB.addActionListener((ActionListener) cbHandler);
        exitB = new JButton("Exit");
        ebHandler = new ExitButtonHandler();
        exitB.addActionListener(ebHandler);
        saveB = new JButton("Save");
        sbHandler = new SaveButtonHandler();
        saveB.addActionListener(sbHandler);

        setTitle("Strip White Spaces in Hex");
        Container pane = getContentPane();
        pane.setLayout(new GridLayout(4, 4));

        pane.add(HexWSpace);
        pane.add(HexWOSpace);
        pane.add(HexWithSpace);
        pane.add(HexWithOutSpace);
        pane.add(convertB);
        pane.add(saveB);
        pane.add(exitB);
        setSize(WIDTH, HEIGHT);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public class ConvertButtonHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            out = "";
            String x = HexWSpace.getText();
            BufferedReader br = new BufferedReader(new StringReader(x));

            try {
                line = br.readLine();
            } catch (IOException ex) {
                Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (line != null) {
                concatenate = line.replaceAll("\\s+", "");
                out = out + concatenate;
                HexWOSpace.setText("" + out);
            } else {
                HexWOSpace.setText("");
            }
        }

    }

    public class ExitButtonHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    public class SaveButtonHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            try {
                String timeStamp = new SimpleDateFormat("MMddyyyy_HHmmss").format(Calendar.getInstance().getTime());
                String os = System.getProperty("os.name");
                String path = "";
                String CurrentWorkingDir = System.getProperty("user.dir");

                if (os.toLowerCase().contains("windows")) {
                    path = "C:\\" + "Hex_" + timeStamp + ".txt";
                } else if (os.toLowerCase().contains("linux")) {
                    path = CurrentWorkingDir + "/" + "Hex_" + timeStamp + ".txt";

                }

                out = HexWOSpace.getText();

                if (out.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Hex Data Without spaces is empty", "error", JOptionPane.ERROR_MESSAGE);

                } else {
                    File f = new File(path);
                    f.createNewFile();
                    FileWriter file = new FileWriter(f.getAbsoluteFile());
                    BufferedWriter bw = new BufferedWriter(file);
                    bw.write(out);
                    JOptionPane.showMessageDialog(null, "File saved: " + path);
                    bw.close();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Gui rectObj = new Gui();
    }

}
