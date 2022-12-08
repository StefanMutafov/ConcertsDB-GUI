import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.LinkedList;

public class addConcertGUI extends JFrame {
        String db="jdbc:mysql://127.0.0.1:3306/concerts";
        String user="root";
        String pass="St951659";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        int new_id;
        String new_cName, new_place, new_cDate, new_price;
        LinkedList<Integer> oldId = new LinkedList();
        final int sizeX = 400;
        final int sizeY = 400;
        public addConcertGUI(String title){
                setTitle(title);
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setSize(sizeX, sizeY);
                setLocationRelativeTo(null);
                setLayout(null);
                setVisible(true);
                buildSingGui();

        }
        private void updateCombo(JComboBox j){
                try {
                        //Connecting to DB
                        con= DriverManager.getConnection(db,user,pass);
                        if(con != null){System.out.println("Connection to db for addConcerts successful!");}else{ System.out.println("Connection to db for addConcerts NOT successful!");}
                        //Getting IDs from DB
                        ps=con.prepareStatement("SELECT * FROM concerts");
                        ResultSet rs=ps.executeQuery();
                        j.removeAllItems();
                        System.out.println("Removed all items from combo");
                        while(rs.next()){
                                j.addItem(rs.getInt(1));
                                System.out.println("Aded to combo " + rs.getInt(1));
                        }
                        ps.close();
                        rs.close();
                        con.close();

                } catch (SQLException exe) {
                        throw new RuntimeException(exe);
                }

        }

        private void buildSingGui() {
                //Labels
                JLabel l_id = new JLabel("ID");
                l_id.setBounds(sizeX/4, sizeY/8, sizeX/4, sizeY/8);
                l_id.setFont(new Font("Courier", Font.PLAIN, 17));
                l_id.setHorizontalAlignment(SwingConstants.CENTER);
                JLabel l_cName= new JLabel("Concert Name");
                l_cName.setBounds(sizeX/4, sizeY*2/8, sizeX/4, sizeY/8);
                l_cName.setFont(new Font("Courier", Font.PLAIN, 15));
                l_cName.setHorizontalAlignment(SwingConstants.CENTER);
                JLabel l_place= new JLabel("Place");
                l_place.setBounds(sizeX/4, sizeY*3/8, sizeX/4, sizeY/8);
               l_place.setFont(new Font("Courier", Font.PLAIN, 17));
                l_place.setHorizontalAlignment(SwingConstants.CENTER);
                JLabel l_cDate= new JLabel("Date");
                l_cDate.setBounds(sizeX/4, sizeY*4/8, sizeX/4, sizeY/8);
                l_cDate.setFont(new Font("Courier", Font.PLAIN, 17));
                l_cDate.setHorizontalAlignment(SwingConstants.CENTER);
                JLabel l_price= new JLabel("Price");
                l_price.setBounds(sizeX/4, sizeY*5/8, sizeX/4, sizeY/8);
                l_price.setFont(new Font("Courier", Font.PLAIN, 17));
                l_price.setHorizontalAlignment(SwingConstants.CENTER);


                //Text Areas
                JTextField id = new JTextField();
                id.setBounds(sizeX/2, sizeY/8, sizeX/4, sizeY/8);

                JTextField cName = new JTextField();
                cName.setBounds(sizeX/2, sizeY*2/8, sizeX/4, sizeY/8);

                JTextField place = new JTextField();
                place.setBounds(sizeX/2, sizeY*3/8, sizeX/4, sizeY/8);

                JTextField cDate = new JTextField();
                cDate.setBounds(sizeX/2, sizeY*4/8, sizeX/4, sizeY/8);

                JTextField price = new JTextField();
                price.setBounds(sizeX/2, sizeY*5/8, sizeX/4, sizeY/8);
                JComboBox idc = new JComboBox();
                idc.setBounds(0, sizeY/8, sizeX/4, sizeY/8 );

                JButton show = new JButton("Show");
                show.setBounds(0, sizeY*2/8, sizeX/4, sizeY/8 );
                show.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                try {
                                        System.out.println("Recorded combo action");
                                        con= DriverManager.getConnection(db,user,pass);
                                        ps=con.prepareStatement("SELECT * FROM concerts WHERE id ="+  idc.getSelectedItem());
                                        rs = ps.executeQuery();
                                        rs.next();
                                        id.setText(String.valueOf(rs.getInt(1)));
                                        cName.setText(rs.getString(2));
                                        place.setText(rs.getString(3));
                                        cDate.setText(rs.getString(4));
                                        price.setText(rs.getString(5));

                                        ps.close();
                                        rs.close();
                                        con.close();
                                } catch (SQLException ex) {
                                        throw new RuntimeException(ex);
                                }
                                updateCombo(idc);

                        }
                });

                JButton add = new JButton("Add");
                add.setBounds(sizeX/2, sizeY*6/8, sizeX/4, sizeY/8);
                add.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                System.out.println("Pressed Add");

                                //Database
                                try {
                                        //Connecting to DB
                                        con= DriverManager.getConnection(db,user,pass);
                                        if(con != null){System.out.println("Connection to db for addConcert successful!");}else{ System.out.println("Connection to db for addConcert NOT successful!");}
                                        //Getting IDs from DB
                                        ps=con.prepareStatement("SELECT id FROM concerts");
                                        rs=ps.executeQuery();
                                        while(rs.next()){
                                                oldId.add(rs.getInt(1));
                                        }
                                        ps.close();
                                        rs.close();
                                        con.close();

                                } catch (SQLException exe) {
                                        throw new RuntimeException(exe);
                                }

                                boolean check = true;
                                //Getting singer info
                                new_id = Integer.parseInt((id.getText()));
                                for(int i =0; i<oldId.size();i++){
                                        if(oldId.get(i) == new_id){
                                                id.setText("");
                                                JOptionPane.showMessageDialog(null, "The ID you entered, already exists!");
                                                check = false;
                                                break;
                                        }
                                }
                                if(check) {
                                        new_cName = cName.getText();
                                        new_place = place.getText();
                                        new_cDate = cDate.getText();
                                        new_price = price.getText();

                                        try {
                                                //Connecting to DB
                                                con= DriverManager.getConnection(db,user,pass);
                                                if(con != null){System.out.println("Connection to db for addConcerts successful!");}else{ System.out.println("Connection to db for addConcerts NOT successful!");}
                                                //New Singer Quarry
                                                ps=con.prepareStatement("INSERT INTO concerts VALUES(?,?,?,?,?)");
                                                ps.setInt(1, new_id);
                                                ps.setString(2, new_cName);
                                                ps.setString(3, new_place);
                                                ps.setString(4, new_cDate);
                                                ps.setString(5, new_price);
                                                ps.executeUpdate();

                                                ps.close();
                                                rs.close();
                                                con.close();
                                        } catch (SQLException ex) {
                                                throw new RuntimeException(ex);
                                        }



                                        System.out.println("Added new concert!");
                                        updateCombo(idc);
                                }


                        }
                });


                JButton del = new JButton("Delete");
                del.setBounds(sizeX/4, sizeY*6/8, sizeX/4, sizeY/8);
                del.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                System.out.println("Pressed Del");
                                int delID = 0;
                                boolean check = true;
                                boolean check1 = false;
                                //Database


                                try {
                                        //Connecting to DB
                                        con = DriverManager.getConnection(db, user, pass);
                                        if (con != null) {
                                                System.out.println("Connection to db for delConcert successful!");
                                        } else {
                                                System.out.println("Connection to db for delConcert NOT successful!");
                                        }
                                        if (id.getText() != null) {
                                                delID = Integer.parseInt((id.getText()));
                                                ps = con.prepareStatement("SELECT concert_id FROM concerts_singers_relationship WHERE concert_id = ?");
                                                ps.setInt(1, delID);
                                                rs = ps.executeQuery();
                                                if (rs.next()) {
                                                        check1 = true;
                                                        int result = JOptionPane.showConfirmDialog(null, "Deleting this record will result in a record with the same ID from another table to be deleted.Do you wish to continue?", "Confirm delete",
                                                                JOptionPane.YES_NO_OPTION,
                                                                JOptionPane.QUESTION_MESSAGE);

                                                        if (result == JOptionPane.NO_OPTION) {
                                                                check = false;
                                                        }
                                                }
                                        }
                                        rs.close();
                                        ps.close();
                                        if (check) {
                                                if (check1) {
                                                        ps = con.prepareStatement("DELETE FROM concerts_singers_relationship WHERE concert_id =?");
                                                        ps.setInt(1, delID);
                                                        ps.executeUpdate();
                                                        System.out.println("Deleted concerts_singers_relationship with singer_id " + delID);
                                                        ps.close();
                                                }

                                                ps = con.prepareStatement("DELETE FROM concerts WHERE id =?");
                                                ps.setInt(1, delID);
                                                ps.executeUpdate();
                                                System.out.println("Deleted concert with id " + delID);
                                                ps.close();
                                                con.close();
                                                id.setText("");
                                                cName.setText("");
                                                place.setText("");
                                                cDate.setText("");
                                                price.setText("");
                                                updateCombo(idc);

                                        }

                                }catch(SQLException ex){
                                        throw new RuntimeException(ex);
                                }


                        }


                });


                add(del);
                add(add);
                add(show);
                add(l_cName);
                add(l_cDate);
                add(l_place);
                add(l_price);
                add(l_id);
                add(cName);
                add(cDate);
                add(place);
                add(price);
                add(id);
                add(idc);
                updateCombo(idc);
                repaint();

        }


}
