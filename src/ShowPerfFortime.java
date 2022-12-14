import javax.swing.*;
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
       // startD.setBounds();
        JTextField endD = new JTextField();

        JComboBox idS = new JComboBox();

        JTextArea output = new JTextArea();

        JButton show = new JButton("Show");

        try {
            Connection con = DriverManager.getConnection(db, user, pass);
            PreparedStatement ps1 = con.prepareStatement("SELECT id FROM singers");
            ResultSet rs1 = ps1.executeQuery();
            while(rs1.next()){
                idS.addItem(rs1.getInt(1));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



        try {
            Connection con = DriverManager.getConnection(db, user, pass);
            PreparedStatement ps1 = con.prepareStatement("SELECT id FROM singers");
            ResultSet rs1 = ps1.executeQuery();
            while(rs1.next()){
                idS.addItem(rs1.getInt(1));

            }

            while (rs1.next()) {

                PreparedStatement ps = con.prepareStatement("select c.* from (concerts c join concerts_singers_relationship csr on c.id=csr.concert_id) join singers s on csr.singer_id=s.id where (c.c_date between ? and ?) and csr.singer_id=?");

                ps.setString(1, startD.getText());

                ps.setString(2, endD.getText());
                ps.setInt(3, (Integer) idS.getSelectedItem());
                ResultSet rs = ps.executeQuery();
                int a = 0;
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
            }
            ps1.close();
            rs1.close();
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }


            add(startD);

    }

}
