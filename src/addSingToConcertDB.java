import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.concurrent.ExecutionException;

public class addSingToConcertDB extends JFrame {
    String db="jdbc:mysql://127.0.0.1:3306/concerts";
    String user="root";
    String pass="St951659";

    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    final int sizeX = 800;
    final int sizeY = 400;

    public addSingToConcertDB(String title){
        setTitle(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(sizeX, sizeY);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        buildSingGui();
    }

    private void buildSingGui() {
        JLabel cInfo = new JLabel();
        cInfo.setOpaque(true);
        cInfo.setBounds(20, 50, 300, 300);
        cInfo.setBackground(Color.WHITE);


        JLabel sInfo = new JLabel();
        sInfo.setOpaque(true);
        sInfo.setBounds(340, 50, 300, 300);
        sInfo.setBackground(Color.WHITE);


        JComboBox choseC = new JComboBox();
        choseC.setBounds(95, 25, 150, 25 );
        try {
            con= DriverManager.getConnection(db,user,pass);
            ps = con.prepareStatement("SELECT * FROM concerts");
            rs = ps.executeQuery();
            while (rs.next()) {
                choseC.addItem(rs.getInt(1));
            }
            con.close();
            ps.close();
            rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }

        choseC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println("Recorded combo action");
                    Connection con= DriverManager.getConnection(db,user,pass);
                    PreparedStatement ps=con.prepareStatement("SELECT * FROM concerts WHERE id ="+  choseC.getSelectedItem());
                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    cInfo.setText("<html>Name: " + rs.getString(2) + "<br/>" + "Place: " + rs.getString(3) + "<br/>" + "Date: "+ rs.getString(4) + "<br/>" + "Price: "+ rs.getString(5) + "<html>");

                    ps.close();
                    rs.close();
                    con.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
        try {
            System.out.println("Recorded combo action");
            Connection con= DriverManager.getConnection(db,user,pass);
            PreparedStatement ps=con.prepareStatement("SELECT * FROM concerts WHERE id ="+  choseC.getSelectedItem());
            ResultSet rs = ps.executeQuery();
            rs.next();
            cInfo.setText("<html>Name: " + rs.getString(2) + "<br/>" + "Place: " + rs.getString(3) + "<br/>" + "Date: "+ rs.getString(4) + "<br/>" + "Price: "+ rs.getString(5) + "<html>");

            ps.close();
            rs.close();
            con.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        JComboBox choseT = new JComboBox();
        choseT.setBounds(415, 25, 150, 25 );
        choseT.addItem("warm-up");
        choseT.addItem("primary");
        choseT.addItem("guest");


        JComboBox choseS = new JComboBox();
        choseS.setBounds(415, 25, 150, 25 );
        try {
            con= DriverManager.getConnection(db,user,pass);
            ps = con.prepareStatement("SELECT * FROM singers");
            rs = ps.executeQuery();
            while (rs.next()) {
                choseS.addItem(rs.getInt(1));

            }
            ps.close();
            rs.close();
            con.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        choseS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println("Recorded combo action");
                    Connection con= DriverManager.getConnection(db,user,pass);
                    PreparedStatement ps=con.prepareStatement("SELECT * FROM singers WHERE id ="+  choseS.getSelectedItem());
                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    sInfo.setText("<html>First name: " + rs.getString(2) + "<br/>" + "Last name: " + rs.getString(3) + "<br/>" + "Nationality: "+ rs.getString(4) + "<br/>" + "Style: "+ rs.getString(5) + "<html>");

                    ps.close();
                    rs.close();
                    con.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });

        try {
            System.out.println("Recorded combo action");
            Connection con= DriverManager.getConnection(db,user,pass);
            PreparedStatement ps=con.prepareStatement("SELECT * FROM singers WHERE id ="+  choseS.getSelectedItem());
            ResultSet rs = ps.executeQuery();
            rs.next();
            sInfo.setText("<html>First name: " + rs.getString(2) + "<br/>" + "Last name: " + rs.getString(3) + "<br/>" + "Nationality: "+ rs.getString(4) + "<br/>" + "Style: "+ rs.getString(5) + "<html>");

            ps.close();
            rs.close();
            con.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }


        JButton assign =  new JButton("Assign");
        assign.setBounds(680,180,80,40);
        assign.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int sID, cID;
                try {
                    System.out.println("Recorded assign press");
                    Connection con= DriverManager.getConnection(db,user,pass);
                    PreparedStatement ps=con.prepareStatement("SELECT * FROM singers WHERE id ="+  choseS.getSelectedItem());
                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    sID = rs.getInt(1);
                    rs.close();
                    ps.close();
                    ps=con.prepareStatement("SELECT * FROM concerts WHERE id ="+  choseC.getSelectedItem());
                    rs = ps.executeQuery();
                    rs.next();
                    cID = rs.getInt(1);
                    ps=con.prepareStatement("INSERT INTO concerts_singers_relationship VALUES(?,?,?)");
                    ps.setInt(1, sID);
                    ps.setInt(2, cID);
                    ps.setString(3, (String) choseT.getSelectedItem());

                    ps.executeUpdate();
                    ps.close();
                    rs.close();
                    con.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });



        add(assign);
        add(choseC);
        add(choseS);
        add(cInfo);
        add(sInfo);
        repaint();
    }


}
