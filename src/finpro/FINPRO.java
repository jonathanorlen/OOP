/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finpro;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Jonathan Orlen
 */
public class FINPRO implements ActionListener{
    public static JPanel dashboardPanel;
    public static JFrame frame;
    public static JLabel panelLabel;
    
    private static JLabel titleLabel;
    private static JLabel dateLabel;
    private static JLabel timeLabel;

    private static JTextField titleField;
    private static JTextField dateField;
    private static JTextField timeField;
    
    public static JTable todoTable;
    public static DefaultTableModel model;
    private static JScrollPane scroll;
    
    public Connection conn;
    public PreparedStatement pst;
    
    private static JButton saveButton;
    private static JButton updateButton;
    private static JButton resetButton;
    private static JButton deleteButton;


    private static int id;
    /**
     * @param args the command line arguments
     */
    private static void UpdateTabel() {
        try {
            Connection getData = new koneksi().getTodos();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private static void show(){
        saveButton.setVisible(true);
        updateButton.setVisible(false);
        resetButton.setVisible(false);
        deleteButton.setVisible(false);
    }
    
    private static void hide(){
        saveButton.setVisible(false);
        updateButton.setVisible(true);
        resetButton.setVisible(true);
        deleteButton.setVisible(true);
    }
    
    private static void clear(){
        titleField.setText(null);
        dateField.setText(null);
        timeField.setText(null);
    }
    
    public static void getData(){
        try{
            
            ListSelectionModel a = todoTable.getSelectionModel();
            a.addListSelectionListener(new ListSelectionListener(){
                @Override
                public void valueChanged(ListSelectionEvent l) {
                    int getindex = a.getMinSelectionIndex();
                    
                    String title = todoTable.getValueAt(getindex, 1).toString();
                    titleField.setText(title);
                    String date = todoTable.getValueAt(getindex, 2).toString();
                    dateField.setText(date);
                    String time = todoTable.getValueAt(getindex, 3).toString();
                    timeField.setText(time);
                    id = Integer.parseInt((String) todoTable.getValueAt(getindex, 4));
                    
                    hide();

                }
            });
        }catch (Exception e){
        }
    }
    
    public static void main(String[] args) {
        
        //create koneksi class
        koneksi connect = new koneksi();
                
        // TODO code application logic here
        dashboardPanel = new JPanel();
        frame = new JFrame();
        panelLabel = new JLabel("Todo");
        panelLabel.setBounds(100,20,100,35);
        
        dashboardPanel.setLayout(null);
        dashboardPanel.add(panelLabel);
        
        frame.setSize(355,700);
        
        titleLabel = new JLabel("Title");
        titleLabel.setBounds(20,40, 50, 40);
        dashboardPanel.add(titleLabel);
        
        titleField = new JTextField(500);
        titleField.setBounds(20, 70, 300, 40);
        dashboardPanel.add(titleField);
        
        dateLabel = new JLabel("Date");
        dateLabel.setBounds(20,120, 50, 40);
        dashboardPanel.add(dateLabel);
        
        dateField = new JTextField(200);
        dateField.setBounds(20,150, 140, 40);
        dashboardPanel.add(dateField);
        
        timeLabel = new JLabel("Time");
        timeLabel.setBounds(180,120, 50, 42);
        dashboardPanel.add(timeLabel);
        
        timeField = new JTextField(200);
        timeField.setBounds(180,150, 140, 40);
        dashboardPanel.add(timeField);
        
        saveButton = new JButton("Save");
        saveButton.setBounds(20,220, 300, 40);
        saveButton.addActionListener(new FINPRO(){
            public void actionPerformed(ActionEvent e){
                String title = titleField.getText();
                String date = dateField.getText();
                String time = timeField.getText();
                
                try {
                    connect.insert(title, date, time);
                    UpdateTabel();
                } catch (SQLException ex) {
                    Logger.getLogger(FINPRO.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                clear();
            }
        });
        dashboardPanel.add(saveButton);
        
        updateButton = new JButton("Update");
        updateButton.setBounds(20,220, 100, 40);
        updateButton.addActionListener(new FINPRO(){
            public void actionPerformed(ActionEvent e){
                String title = titleField.getText();
                String date = dateField.getText();
                String time = timeField.getText();
                
                try {
                    connect.update(title, date, time,id);
                    UpdateTabel();
                } catch (SQLException ex) {
                    Logger.getLogger(FINPRO.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                clear();
                show();
            }
        });
        updateButton.setVisible(false);
        dashboardPanel.add(updateButton);
        
        
        resetButton = new JButton("Reset");
        resetButton.setBounds(120,220, 100, 40);
        resetButton.addActionListener(new FINPRO(){
            public void actionPerformed(ActionEvent e){
                clear();
                show();
            }
        });
        resetButton.setVisible(false);
        dashboardPanel.add(resetButton);
        
        deleteButton = new JButton("Delete");
        deleteButton.setBounds(220,220, 100, 40);
        deleteButton.addActionListener(new FINPRO(){
            public void actionPerformed(ActionEvent e){
                try {
                    connect.delete(id);
                    UpdateTabel();
                } catch (SQLException ex) {
                    Logger.getLogger(FINPRO.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                show();
            }
        });
        deleteButton.setVisible(false);
        dashboardPanel.add(deleteButton);
        
        String [] info = {"status","Title","Date","Time", "ID"};
        model = new DefaultTableModel(null,info){
        public Class<?> getColumnClass(int column)
        {
          switch(column)
          {
          case 0:
            return Boolean.class;
          case 1:
            return String.class;
          case 2:
            return String.class;
          case 3:
            return String.class;
          case 4:
            return String.class;

            default:
              return String.class;
          }
        }
      };
        
        todoTable = new JTable();
        todoTable.setPreferredScrollableViewportSize(new Dimension(800,50));
        todoTable.setFillsViewportHeight(true);
        scroll = new JScrollPane(todoTable);
        scroll.setBounds(20,280,300,350);
        todoTable.setModel(model);
        UpdateTabel();
        getData();
        frame.add(scroll);
        
        frame.add(dashboardPanel);
        frame.setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
