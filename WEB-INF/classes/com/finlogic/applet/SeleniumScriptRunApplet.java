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
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author Ankur Mistry
 */
public class SeleniumScriptRunApplet extends Applet implements ActionListener
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
    
    private String tmp_path;
    private String script;
    private String server_path;    
    private String test_report_path;

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
                downloadButton.addActionListener(SeleniumScriptRunApplet.this);
                downloadButton.setActionCommand("download");
                runScriptButton = new JButton("Run Script", new ImageIcon("run.jpeg"));
                runScriptButton.addActionListener(SeleniumScriptRunApplet.this);
                runScriptButton.setActionCommand("run");
                runScriptButton.setEnabled(false);

                progressBar = new JProgressBar(0,100);
                progressBar.setIndeterminate(true);                
                progressBar.setVisible(false);

                seleniumPathButton = new JButton("Selenium Server Path");
                seleniumPathButton.addActionListener(SeleniumScriptRunApplet.this);
                seleniumPathButton.setActionCommand("selpath");
                jameleonPathButton = new JButton("Jameleon Path");
                jameleonPathButton.addActionListener(SeleniumScriptRunApplet.this);
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

                server_path = SeleniumScriptRunApplet.this.getCodeBase().toString().substring(0, SeleniumScriptRunApplet.this.getCodeBase().toString().indexOf('/', 7));

                setBackground(new Color(37, 152, 187, 150));
                SeleniumScriptRunApplet.this.add(seleniumPathLabel).setBounds(10, 10, 150, 30);
                SeleniumScriptRunApplet.this.add(seleniumPathButton).setBounds(150, 10, 175, 30);
                SeleniumScriptRunApplet.this.add(jameleonPathLabel).setBounds(335, 10, 150, 30);
                SeleniumScriptRunApplet.this.add(jameleonPathButton).setBounds(475, 10, 175, 30);
                SeleniumScriptRunApplet.this.add(downloadLabel).setBounds(10, 50, 100, 30);
                SeleniumScriptRunApplet.this.add(downloadButton).setBounds(150, 50, 175, 30);
                SeleniumScriptRunApplet.this.add(runScriptLabel).setBounds(335, 50, 100, 30);
                SeleniumScriptRunApplet.this.add(runScriptButton).setBounds(475, 50, 175, 30);
                SeleniumScriptRunApplet.this.add(testResultReportLabel).setBounds( 10 , 90 , 200 , 30 );
                SeleniumScriptRunApplet.this.add(progressBar).setBounds(10, 90, 640, 30);
                SeleniumScriptRunApplet.this.add(scrollPane).setBounds(10, 125, 640 , 350 );
            }
        }).start();
    }   

    public void actionPerformed(ActionEvent event)
    {
        if (event.getActionCommand().equalsIgnoreCase("download"))
        {
            try
            {
                downloadFile(scriptID);
            }
            catch (IOException exp)
            {
                JOptionPane.showMessageDialog(SeleniumScriptRunApplet.this, exp.getMessage(), "Error Downloadin File", JOptionPane.ERROR_MESSAGE);
            }
            
        }
        else if (event.getActionCommand().equalsIgnoreCase("run"))
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
        else if (event.getActionCommand().equalsIgnoreCase("selpath"))
        {
            seleniumPath = getPath();
        }
        else if (event.getActionCommand().equalsIgnoreCase("jampath"))
        {
            jameleonPath = getPath();
            test_report_path = jameleonPath + "jameleon_test_results/";
        }
    }

    private void downloadFile(final int scriptid) throws IOException
    {
        if (jameleonPath != null && seleniumPath != null)
        {

            String xmlFile = scriptid + ".xml";
            String csvFile = scriptid + ".csv";
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;

            int answer = JOptionPane.showConfirmDialog(SeleniumScriptRunApplet.this, "Start Download?");
            
            if (answer == JOptionPane.YES_OPTION)
            {              

                try
                {
                    //Get the XML File
                    URL url = new URL(server_path+"/finstudio/generated/" + xmlFile);
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();

                    bis = new BufferedInputStream(urlc.getInputStream());
                    bos = new BufferedOutputStream(new FileOutputStream(jameleonPath + "scripts/" + xmlFile));

                    int readByte;
                    while ((readByte = bis.read()) != -1)
                    {
                        bos.write(readByte);
                    }
                    bis.close();
                    bos.close();

                    //Get The CSV File
                    url = new URL(server_path+"/finstudio/generated/" + csvFile);
                    urlc = (HttpURLConnection) url.openConnection();

                    bis = new BufferedInputStream(urlc.getInputStream());
                    bos = new BufferedOutputStream(new FileOutputStream(jameleonPath + "data/" + csvFile));

                    while ((readByte = bis.read()) != -1)
                    {
                        bos.write(readByte);
                    }
                    bis.close();
                    bos.close();

                    //Get the script and store it in /tmp dir                                       
                    script = "testscript.txt";
                    tmp_path = "/tmp/";
                    
                    url = new URL(server_path+"/finstudio/generated/"+script);
                    urlc = (HttpURLConnection)url.openConnection();

                    bis = new BufferedInputStream(urlc.getInputStream());
                    bos = new BufferedOutputStream(new FileOutputStream(tmp_path+script));

                    while ((readByte = bis.read()) != -1)
                    {
                        bos.write(readByte);                        
                    }                   

                    runScriptButton.setEnabled(true);
                }
                catch (IOException exp)
                {
                    JOptionPane.showMessageDialog(SeleniumScriptRunApplet.this, exp.getMessage());
                }
                finally
                {
                    bis.close();
                    bos.close();                 
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(SeleniumScriptRunApplet.this, "Please select Jameleon and Selenium Server directory path.");
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
            int answer = fileDialog.showDialog(SeleniumScriptRunApplet.this, null);
            if (answer == JFileChooser.APPROVE_OPTION)
            {
                path = fileDialog.getSelectedFile().getAbsolutePath()+"/";
            }
        }
        catch (Exception exp)
        {
            JOptionPane.showMessageDialog(SeleniumScriptRunApplet.this, exp.getMessage());
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
            
            Process process = Runtime.getRuntime().exec("firefox file://"+test_report_path + folder + "/TestResults.html");
            process.waitFor();            
        }
        catch (Exception exp)
        {
            JOptionPane.showMessageDialog(SeleniumScriptRunApplet.this, exp.getMessage());
        }
    }


    class Task extends SwingWorker<Void, Void>
    {

        @Override        
        protected Void doInBackground() throws RuntimeException
        {
            try
            {
                if (seleniumPath != null && jameleonPath != null)
                {
                    BufferedReader readStream = null;
                    
                    try
                    {
                        progressBar.setValue(1);
                        String CMD = "/bin/sh ";                    
                    
                        Process proc = Runtime.getRuntime().exec( CMD + tmp_path + script + " " + seleniumPath + " " + jameleonPath + " " + scriptID );

                        readStream = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                        String line;

                        line = readStream.readLine();
                        while ( line != null)
                        {                            
                            logTextArea.append(line + "\n");
                            line = readStream.readLine();
                        }
                        
                        readStream = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
                        
                        line = readStream.readLine();
                        while ( line != null)
                        {                         
                            logTextArea.append(line + "\n");
                            line = readStream.readLine();
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
                    catch (RuntimeException exp)
                    {
                        readStream.close();
                        throw new Exception(exp);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(SeleniumScriptRunApplet.this, "Please select Jameleon and Selenium Server directory path.");
                }
            }
            catch (RuntimeException exp)
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
                Logger.getLogger(SeleniumScriptRunApplet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public class LinkMouseClickListener extends MouseAdapter    {

        @Override
        public void mouseClicked(MouseEvent event)
        {
            SeleniumScriptRunApplet.this.openReport();
        }
    }

}
