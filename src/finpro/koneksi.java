/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finpro;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
/**
 *
 * @author Jonathan Orlen
 */
public class koneksi {
    
    private static Connection mysqlconfig;
    
    public static Statement stm;
    
    public static void main(String args[]){
        try {
            mysqlconfig = DriverManager.getConnection("jdbc:mysql://localhost/aoop","root","");
            stm = mysqlconfig.createStatement();
            System.out.println("koneksi berhasil;");
        } catch (Exception e) {
            System.err.println("koneksi gagal" +e.getMessage());
        }
    } 
    
    public static Connection getTodos()throws SQLException{
            
        try {
        //remove old row
            int jumlah = FINPRO.todoTable.getRowCount();
            if(jumlah >= 1){
                FINPRO.model.getDataVector().removeAllElements();
            }
            mysqlconfig = DriverManager.getConnection("jdbc:mysql://localhost/aoop","root","");
            ResultSet rs = mysqlconfig.createStatement().executeQuery("SELECT * FROM todo ORDER BY date,time ASC"); 
            
            while(rs.next()){
                boolean status = rs.getString(2).equals("0")? false: true;
                Object[] data = {status, rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(1)};
                FINPRO.model.addRow(data);
            }
        } catch (Exception e) {
            System.err.println("koneksi gagal "+e.getMessage()); 
        }
        return mysqlconfig;
    }  
    
    public static Connection insert(String title, String date, String time)throws SQLException{
                try {
                    String sql = "INSERT INTO todo (title,date,time,status)"
                            + "VALUES (?,?,?,?)";
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/aoop","root","");
                    PreparedStatement prepared = connection.prepareStatement(sql);
                    prepared.setString(1, title);
                    prepared.setString(2, date);
                    prepared.setString(3, time);
                    prepared.setString(4, "0");
                    prepared.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Insert Berhasil");
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
                
        return null;
    } 
    
    public static Connection update(String title, String date, String time, int id)throws SQLException{
                try {
                    String sql = "UPDATE todo SET title =?, date=?, time=?  WHERE id = ?;";
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/aoop","root","");
                    PreparedStatement prepared = connection.prepareStatement(sql);
                    prepared.setString(1, title);
                    prepared.setString(2, date);
                    prepared.setString(3, time);
                    prepared.setString(4, String.valueOf(id));
                    prepared.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Update Berhasil");
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
                
        return null;
    } 
    
    public static Connection delete(int id)throws SQLException{
                try {
                    String sql = "DELETE FROM todo WHERE id=?;";
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/aoop","root","");
                    PreparedStatement prepared = connection.prepareStatement(sql);
                    prepared.setString(1, String.valueOf(id));
                    prepared.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Delete Berhasil");
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
                
        return null;
    } 
    
}
