import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.LinkedList;

public class showSingforC extends JFrame {


    String db = "jdbc:mysql://127.0.0.1:3306/concerts";
    String user = "root";
    String pass = "St951659";
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    LinkedList<Integer> sIDs =new LinkedList();
    int new_id;
    String new_fName, new_lName, new_nationality, new_style;
    LinkedList<Integer> oldId = new LinkedList();
    final int sizeX = 400;
    final int sizeY = 400;

    public showSingforC(String title) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(sizeX, sizeY);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        buildshowSingforC();


    }

    private void buildshowSingforC() {
        //GUI
        //Labels
        JLabel l_id = new JLabel("ID");
        l_id.setBounds(sizeX / 4, sizeY / 8, sizeX / 4, sizeY / 8);
        l_id.setFont(new Font("Courier", Font.PLAIN, 17));
        l_id.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel l_fName = new JLabel("First Name");
        l_fName.setBounds(sizeX / 4, sizeY * 2 / 8, sizeX / 4, sizeY / 8);
        l_fName.setFont(new Font("Courier", Font.PLAIN, 17));
        l_fName.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel l_lName = new JLabel("Last Name");
        l_lName.setBounds(sizeX / 4, sizeY * 3 / 8, sizeX / 4, sizeY / 8);
        l_lName.setFont(new Font("Courier", Font.PLAIN, 17));
        l_lName.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel l_nationality = new JLabel("Nationality");
        l_nationality.setBounds(sizeX / 4, sizeY * 4 / 8, sizeX / 4, sizeY / 8);
        l_nationality.setFont(new Font("Courier", Font.PLAIN, 17));
        l_nationality.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel l_style = new JLabel("Style");
        l_style.setBounds(sizeX / 4, sizeY * 5 / 8, sizeX / 4, sizeY / 8);
        l_style.setFont(new Font("Courier", Font.PLAIN, 17));
        l_style.setHorizontalAlignment(SwingConstants.CENTER);


        //Text Areas
        JTextField id = new JTextField();
        id.setBounds(sizeX / 2, sizeY / 8, sizeX / 4, sizeY / 8);

        JTextField fName = new JTextField();
        fName.setBounds(sizeX / 2, sizeY * 2 / 8, sizeX / 4, sizeY / 8);

        JTextField lName = new JTextField();
        lName.setBounds(sizeX / 2, sizeY * 3 / 8, sizeX / 4, sizeY / 8);

        JTextField nationality = new JTextField();
        nationality.setBounds(sizeX / 2, sizeY * 4 / 8, sizeX / 4, sizeY / 8);

        JTextField style = new JTextField();
        style.setBounds(sizeX / 2, sizeY * 5 / 8, sizeX / 4, sizeY / 8);
        JComboBox ids = new JComboBox();
        ids.setBounds(0, sizeY / 8, sizeX / 4, sizeY / 8);
        try {
            con = DriverManager.getConnection(db, user, pass);
            ps = con.prepareStatement("SELECT concert_id FROM concerts_singers_relationship");
            rs = ps.executeQuery();
            while(rs.next()){
                ids.addItem(rs.getInt(1));
            }
            rs.close();
            ps.close();
            con.close();
        }catch (SQLException ex){
            ex.printStackTrace();
        }





        JButton show = new JButton("Show");
        show.setBounds(0, sizeY * 2 / 8, sizeX / 4, sizeY / 8);
        show.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println("Recorded combo action");
                    Connection con = DriverManager.getConnection(db, user, pass);
                    //TODO Тук трябва да сложа команда, да визма от релейшоншип
                    PreparedStatement ps = con.prepareStatement("SELECT singer_id FROM concerts_singers_relationship WHERE concert_id =" + ids.getSelectedItem());
                    ResultSet rs = ps.executeQuery();
                    while(rs.next()){
                        sIDs.add(rs.getInt(1));

                    }
                    ps.close();
                    rs.close();
                    ps = con.prepareStatement("SELECT * FROM singers WHERE id =" + sIDs.getFirst());
                    rs = ps.executeQuery();
                    rs.next();
                    id.setText(String.valueOf(rs.getInt(1)));
                    fName.setText(rs.getString(2));
                    lName.setText(rs.getString(3));
                    nationality.setText(rs.getString(3));
                    style.setText(rs.getString(4));


                    ps.close();
                    rs.close();
                    con.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });


        add(show);
        add(ids);
        add(l_id);
        add(l_fName);
        add(l_lName);
        add(l_nationality);
        add(l_style);
        add(id);
        add(fName);
        add(lName);
        add(nationality);
        add(style);
        repaint();
        System.out.println("Successfully build AddSingerGUI");

    }
}





