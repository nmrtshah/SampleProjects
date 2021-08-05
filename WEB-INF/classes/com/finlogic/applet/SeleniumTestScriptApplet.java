package com.finlogic.applet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.applet.Applet;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author Ankur Mistry
 */
public class SeleniumTestScriptApplet extends Applet implements ActionListener
{

    private JLabel downloadLabel;
    private JLabel runScriptLabel;    
    private JLabel seleniumPathLabel;
    private JLabel jameleonPathLabel;
    private JLabel testResultReportLabel;
    private JButton downloadButton;
    private JButton runScriptButton;
    private JButton jameleonPathButton;
    private JButton seleniumPathButton;
    private JProgressBar progressBar;
    private JTextArea logTextArea;
    private JScrollPane scrollPane;
    private JFileChooser fileDialog;
    private int scriptID;
    private String seleniumPath;
    private String jameleonPath;
    private Task task;
    
    private String TMP_PATH;
    private String SCRIPT;
    private String SERVER_PATH;    
    private String TEST_REPORT_PATH;

    @Override
    public void init()
    {
        setLayout(null);

        scriptID = Integer.parseInt(getParameter("scriptID"));

        new Thread(new Runnable() {

            public void run()
            {
                //Create UI controls
                downloadLabel = new JLabel("Download Script : ");
                downloadLabel.setFont(new Font("Times New Roman", Font.BOLD, 12));
                downloadLabel.setVisible(true);
                downloadLabel.setForeground(Color.WHITE);
                runScriptLabel = new JLabel("Run Script : ");
                runScriptLabel.setFont(new Font("Times New Roman", Font.BOLD, 12));
                runScriptLabel.setVisible(true);
                runScriptLabel.setForeground(Color.WHITE);
                seleniumPathLabel = new JLabel("Selenium Directory Path : ");
                seleniumPathLabel.setFont(new Font("Times New Roman", Font.BOLD, 12));
                seleniumPathLabel.setVisible(true);
                seleniumPathLabel.setForeground(Color.WHITE);
                jameleonPathLabel = new JLabel("Jameleon Directory Path : ");
                jameleonPathLabel.setFont(new Font("Times New Roman", Font.BOLD, 12));
                jameleonPathLabel.setVisible(true);
                jameleonPathLabel.setForeground(Color.WHITE);
                testResultReportLabel = new JLabel("Click Here For Test Report");
                testResultReportLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
                testResultReportLabel.setVisible(false);
                testResultReportLabel.setForeground(Color.ORANGE);
                testResultReportLabel.addMouseListener(new LinkMouseClickListener());

                downloadButton = new JButton("Download", new ImageIcon("download.jpeg"));
                downloadButton.addActionListener(SeleniumTestScriptApplet.this);
                downloadButton.setActionCommand("download");
                runScriptButton = new JButton("Run Script", new ImageIcon("run.jpeg"));
                runScriptButton.addActionListener(SeleniumTestScriptApplet.this);
                runScriptButton.setActionCommand("run");
                runScriptButton.setEnabled(false);

                progressBar = new JProgressBar(0,100);
                progressBar.setIndeterminate(true);                
                progressBar.setVisible(false);

                seleniumPathButton = new JButton("Selenium Server Path");
                seleniumPathButton.addActionListener(SeleniumTestScriptApplet.this);
                seleniumPathButton.setActionCommand("selpath");
                jameleonPathButton = new JButton("Jameleon Path");
                jameleonPathButton.addActionListener(SeleniumTestScriptApplet.this);
                jameleonPathButton.setActionCommand("jampath");

                logTextArea = new JTextArea();
                logTextArea.setMargin(new Insets(2, 2, 2, 2));
                logTextArea.setEditable(false);
                logTextArea.setLineWrap(true);
                logTextArea.setWrapStyleWord(true);
                DefaultCaret caret = (DefaultCaret) logTextArea.getCaret();
                caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
                logTextArea.setCaret(caret);

                scrollPane = new JScrollPane(logTextArea);
                scrollPane.setViewportView(logTextArea);
                scrollPane.setAutoscrolls(true);
                scrollPane.setVisible(false);

                SERVER_PATH = SeleniumTestScriptApplet.this.getCodeBase().toString().substring(0, SeleniumTestScriptApplet.this.getCodeBase().toString().indexOf('/', 7));

                setBackground(new Color(37, 152, 187, 150));
                SeleniumTestScriptApplet.this.add(seleniumPathLabel).setBounds(10, 10, 150, 30);
                SeleniumTestScriptApplet.this.add(seleniumPathButton).setBounds(150, 10, 175, 30);
                SeleniumTestScriptApplet.this.add(jameleonPathLabel).setBounds(335, 10, 150, 30);
                SeleniumTestScriptApplet.this.add(jameleonPathButton).setBounds(475, 10, 175, 30);
                SeleniumTestScriptApplet.this.add(downloadLabel).setBounds(10, 50, 100, 30);
                SeleniumTestScriptApplet.this.add(downloadButton).setBounds(150, 50, 175, 30);
                SeleniumTestScriptApplet.this.add(runScriptLabel).setBounds(335, 50, 100, 30);
                SeleniumTestScriptApplet.this.add(runScriptButton).setBounds(475, 50, 175, 30);
                SeleniumTestScriptApplet.this.add(testResultReportLabel).setBounds( 10 , 90 , 200 , 30 );
                SeleniumTestScriptApplet.this.add(progressBar).setBounds(10, 90, 640, 30);
                SeleniumTestScriptApplet.this.add(scrollPane).setBounds(10, 125, 640 , 350 );
            }
        }).start();
    }

    @Override
    public void start()
    {
        super.start();
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equalsIgnoreCase("download"))
        {
            downloadFile(scriptID);
        }
        else if (e.getActionCommand().equalsIgnoreCase("run"))
        {
            seleniumPathButton.setEnabled(false);
            testResultReportLabel.setVisible(false);
            jameleonPathButton.setEnabled(false);
            scrollPane.setVisible(true);
            runScriptButton.setEnabled(false);
            downloadButton.setEnabled(false);
            progressBar.setVisible(true);            
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            task = new Task();
            task.execute();
        }
        else if (e.getActionCommand().equalsIgnoreCase("selpath"))
        {
            seleniumPath = getPath();
        }
        else if (e.getActionCommand().equalsIgnoreCase("jampath"))
        {
            jameleonPath = getPath();
            TEST_REPORT_PATH = jameleonPath + "jameleon_test_results/";
        }
    }

    private void downloadFile(final int id)
    {
        if (jameleonPath != null && seleniumPath != null)
        {

            String xmlFile = String.valueOf(id) + ".xml";
            String csvFile = String.valueOf(id) + ".csv";
            int answer = JOptionPane.showConfirmDialog(SeleniumTestScriptApplet.this, "Start Download?");
            if (answer == JOptionPane.YES_OPTION)
            {

                BufferedInputStream bis = null;
                BufferedOutputStream bos = null;

                try
                {
                    //Get the XML File
                    URL url = new URL(SERVER_PATH+"/finstudio/generated/" + xmlFile);
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();

                    bis = new BufferedInputStream(urlc.getInputStream());
                    bos = new BufferedOutputStream(new FileOutputStream(jameleonPath + "scripts/" + xmlFile));

                    int i;
                    while ((i = bis.read()) != -1)
                    {
                        bos.write(i);
                    }
                    bis.close();
                    bos.close();

                    //Get The CSV File
                    url = new URL(SERVER_PATH+"/finstudio/generated/" + csvFile);
                    urlc = (HttpURLConnection) url.openConnection();

                    bis = new BufferedInputStream(urlc.getInputStream());
                    bos = new BufferedOutputStream(new FileOutputStream(jameleonPath + "data/" + csvFile));

                    while ((i = bis.read()) != -1)
                    {
                        bos.write(i);
                    }
                    bis.close();
                    bos.close();

                    //Get the script and store it in /tmp dir                                       
                    SCRIPT = "testscript.txt";
                    TMP_PATH = "/tmp/";
                    
                    url = new URL(SERVER_PATH+"/finstudio/generated/"+SCRIPT);
                    urlc = (HttpURLConnection)url.openConnection();

                    bis = new BufferedInputStream(urlc.getInputStream());
                    bos = new BufferedOutputStream(new FileOutputStream(TMP_PATH+SCRIPT));

                    while ((i = bis.read()) != -1)
                    {
                        bos.write(i);                        
                    }
                    bis.close();
                    bos.close();

                    runScriptButton.setEnabled(true);
                }
                catch (Exception exp)
                {
                    JOptionPane.showMessageDialog(SeleniumTestScriptApplet.this, exp.getMessage());
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(SeleniumTestScriptApplet.this, "Please select Jameleon and Selenium Server directory path.");
        }
    }

    private String getPath()
    {
        String path = null;
        try
        {
            fileDialog = new JFileChooser();
            fileDialog.setApproveButtonText("Select");
            fileDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int answer = fileDialog.showDialog(SeleniumTestScriptApplet.this, null);
            if (answer == JFileChooser.APPROVE_OPTION)
            {
                path = fileDialog.getSelectedFile().getAbsolutePath()+"/";
            }
        }
        catch (Exception exp)
        {
            JOptionPane.showMessageDialog(SeleniumTestScriptApplet.this, exp.getMessage());
        }
        finally
        {
            return path;
        }
    }

    private void openReport()
    {
        try
        {
            int index = logTextArea.getText().lastIndexOf("Test Run:");
            String folder = logTextArea.getText(index + 10, 17);
            
            Process ps = Runtime.getRuntime().exec("firefox file://"+TEST_REPORT_PATH + folder + "/TestResults.html");
            ps.waitFor();            
        }
        catch (Exception exp)
        {
            JOptionPane.showMessageDialog(SeleniumTestScriptApplet.this, exp.getMessage());
        }
    }


    class Task extends SwingWorker<Void, Void>
    {

        @Override
        protected Void doInBackground() throws Exception
        {
            try
            {
                if (seleniumPath != null && jameleonPath != null)
                {
                    try
                    {
                        progressBar.setValue(1);
                        String CMD = "/bin/sh ";                    
                    
                        Process ps = Runtime.getRuntime().exec( CMD + TMP_PATH + SCRIPT + " " + seleniumPath + " " + jameleonPath + " " + String.valueOf(scriptID) );

                        BufferedReader is = new BufferedReader(new InputStreamReader(ps.getInputStream()));
                        String line;

                        while ((line = is.readLine()) != null)
                        {                            
                            logTextArea.append(line + "\n");
                        }
                        
                        is = new BufferedReader(new InputStreamReader(ps.getErrorStream()));
                        while ((line = is.readLine()) != null)
                        {                         
                            logTextArea.append(line + "\n");                            
                        }
                        
                        seleniumPathButton.setEnabled(true);
                        jameleonPathButton.setEnabled(true);
                        runScriptButton.setEnabled(true);
                        downloadButton.setEnabled(true);
                        progressBar.setVisible(false);
                        int index = logTextArea.getText().lastIndexOf("Test Run:");
                        String folder = logTextArea.getText(index + 10, 17);
                        if (folder.length() == 17)
                        {
                            testResultReportLabel.setVisible(true);
                        }
                        setCursor(null);
                        
                        
                    }
                    catch (Exception exp)
                    {
                        JOptionPane.showMessageDialog(SeleniumTestScriptApplet.this, exp.getMessage());
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(SeleniumTestScriptApplet.this, "Please select Jameleon and Selenium Server directory path.");
                }
            }
            catch (Exception exp)
            {
                throw new Exception(exp);
            }
            finally
            {
                return null;
            }
        }

        @Override
        public void done()
        {
            try
            {
                seleniumPathButton.setEnabled(true);
                jameleonPathButton.setEnabled(true);
                runScriptButton.setEnabled(true);
                downloadButton.setEnabled(true);
                progressBar.setVisible(false);
                int index = logTextArea.getText().lastIndexOf("Test Run:");
                String folder = logTextArea.getText(index + 10, 17);
                if ( folder.length() == 17 )
                {
                    testResultReportLabel.setVisible(true);
                }
                setCursor(null);
            }
            catch (BadLocationException ex)
            {
                Logger.getLogger(SeleniumTestScriptApplet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public class LinkMouseClickListener extends MouseAdapter    {

        @Override
        public void mouseClicked(MouseEvent e)
        {
            SeleniumTestScriptApplet.this.openReport();
        }
    }

}
