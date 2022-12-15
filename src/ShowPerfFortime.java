import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.LinkedList;

public class ShowPerfFortime extends JFrame {
    String db = "jdbc:mysql://127.0.0.1:3306/concerts";
    String user = "root";
    String pass = "St951659";


    int move = 0;
    LinkedList<Integer> sIDs =new LinkedList();
    int new_id;
    String new_fName, new_lName, new_nationality, new_style;
    LinkedList<Integer> oldId = new LinkedList();
    final int sizeX = 400;
    final int sizeY = 400;

    public ShowPerfFortime(String title) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(sizeX, sizeY);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        buildshowSingpert();


    }

    private void buildshowSingpert() {
        JTextField startD = new JTextField();
        startD.setBounds(0, sizeY * 2 / 8, sizeX / 4, sizeY / 8);
       // startD.setBounds();
        JTextField endD = new JTextField();
        endD.setBounds(0, sizeY * 3 / 8, sizeX / 4, sizeY / 8);


        JComboBox idS = new JComboBox();
       idS.setBounds(0, sizeY * 1 / 8, sizeX / 4, sizeY / 8);

        JTextArea output = new JTextArea();
        output.setSize(sizeX* 2/ 4, sizeY *6/ 8);
        output.setLineWrap(true);
        output.setEditable(false);
        output.setVisible(true);

        JScrollPane scroll = new JScrollPane (output);
        scroll.setBounds(sizeX / 2-15, sizeY / 8, sizeX* 2/ 4, sizeY *6/ 8);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);




        JButton show = new JButton("Show");
        show.setBounds(0, sizeY * 4 / 8, sizeX / 4, sizeY / 8);

        try {
            Connection con = DriverManager.getConnection(db, user, pass);
            PreparedStatement ps1 = con.prepareStatement("SELECT id FROM singers");
            ResultSet rs1 = ps1.executeQuery();
            while(rs1.next()){
                idS.addItem(rs1.getInt(1));

            }
            con.close();
            ps1.close();
            rs1.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        show.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
        try {
            Connection con = DriverManager.getConnection(db, user, pass);
//            PreparedStatement ps1 = con.prepareStatement("SELECT id FROM singers");
//            ResultSet rs1 = ps1.executeQuery();
//
//            while (rs1.next()) {

                PreparedStatement ps = con.prepareStatement("select c.* from (concerts c join concerts_singers_relationship csr on c.id=csr.concert_id) join singers s on csr.singer_id=s.id where (c.c_date between ? and ?) and csr.singer_id=?");

                ps.setString(1, startD.getText());

                ps.setString(2, endD.getText());
                ps.setInt(3, (Integer) idS.getSelectedItem());
                ResultSet rs = ps.executeQuery();
                int a = 0;
                output.setText("");
                while(rs.next()) {

                    output.append(String.valueOf(a+1)+ "\n");
                    output.append("ID: " + rs.getInt(1) + "\n");
                    output.append("Name: " + rs.getString(2) + "\n");
                    output.append("Place: " + rs.getString(3) + "\n");
                    output.append("Date: " + rs.getString(4) + "\n");
                    output.append("Price: " + rs.getString(4) + "\n" +  "\n"+  "\n");
                    a++;
                }
                rs.close();
                ps.close();
//            }
//            ps1.close();
//            rs1.close();
            } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
            }
        });
        add(scroll);
            add(show);
            //add(output);
            add(endD);
            add(idS);
            add(startD);
            repaint();

    }

}
