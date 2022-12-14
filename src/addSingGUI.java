import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;


public class addSingGUI extends JFrame {

    String db="jdbc:mysql://127.0.0.1:3306/concerts";
    String user="root";
    String pass="St951659";
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    int new_id;
    String new_fName, new_lName, new_nationality, new_style;
    LinkedList<Integer> oldId = new LinkedList();
    final int sizeX = 400;
    final int sizeY = 400;
    public addSingGUI(String title){
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
            if(con != null){System.out.println("Connection to db for addSinger successful!");}else{ System.out.println("Connection to db for addSinger NOT successful!");}
            //Getting IDs from DB
            ps=con.prepareStatement("SELECT * FROM singers");
            ResultSet rs=ps.executeQuery();
            j.removeAllItems();
            System.out.println("Removed all items from combo");
            while(rs.next()){
                j.addItem(rs.getInt(1));
            }
            ps.close();
            rs.close();
            con.close();

        } catch (SQLException exe) {
            throw new RuntimeException(exe);
        }

    }

    private void buildSingGui() {
        //GUI
        //Labels
        JLabel l_id = new JLabel("ID");
        l_id.setBounds(sizeX/4, sizeY/8, sizeX/4, sizeY/8);
        l_id.setFont(new Font("Courier", Font.PLAIN, 17));
        l_id.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel l_fName= new JLabel("First Name");
        l_fName.setBounds(sizeX/4, sizeY*2/8, sizeX/4, sizeY/8);
        l_fName.setFont(new Font("Courier", Font.PLAIN, 17));
        l_fName.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel l_lName= new JLabel("Last Name");
        l_lName.setBounds(sizeX/4, sizeY*3/8, sizeX/4, sizeY/8);
        l_lName.setFont(new Font("Courier", Font.PLAIN, 17));
        l_lName.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel l_nationality= new JLabel("Nationality");
        l_nationality.setBounds(sizeX/4, sizeY*4/8, sizeX/4, sizeY/8);
        l_nationality.setFont(new Font("Courier", Font.PLAIN, 17));
        l_nationality.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel l_style= new JLabel("Style");
        l_style.setBounds(sizeX/4, sizeY*5/8, sizeX/4, sizeY/8);
        l_style.setFont(new Font("Courier", Font.PLAIN, 17));
        l_style.setHorizontalAlignment(SwingConstants.CENTER);


        //Text Areas
        JTextField id = new JTextField();
        id.setBounds(sizeX/2, sizeY/8, sizeX/4, sizeY/8);

        JTextField fName = new JTextField();
        fName.setBounds(sizeX/2, sizeY*2/8, sizeX/4, sizeY/8);

        JTextField lName = new JTextField();
        lName.setBounds(sizeX/2, sizeY*3/8, sizeX/4, sizeY/8);

        JTextField nationality = new JTextField();
        nationality.setBounds(sizeX/2, sizeY*4/8, sizeX/4, sizeY/8);

        JTextField style = new JTextField();
        style.setBounds(sizeX/2, sizeY*5/8, sizeX/4, sizeY/8);
        JComboBox ids = new JComboBox();
        ids.setBounds(0, sizeY/8, sizeX/4, sizeY/8 );



        JButton show = new JButton("Show");
        show.setBounds(0, sizeY*2/8, sizeX/4, sizeY/8 );
        show.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println("Recorded combo action");
                    con= DriverManager.getConnection(db,user,pass);
                    ps=con.prepareStatement("SELECT * FROM singers WHERE id ="+  ids.getSelectedItem());
                    rs = ps.executeQuery();
                    rs.next();
                    id.setText(String.valueOf(rs.getInt(1)));
                    fName.setText(rs.getString(2));
                    lName.setText(rs.getString(3));
                    nationality.setText(rs.getString(4));
                    style.setText(rs.getString(5));

                    ps.close();
                    rs.close();
                    con.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                updateCombo(ids);

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
                    if(con != null){System.out.println("Connection to db for addSinger successful!");}else{ System.out.println("Connection to db for addSinger NOT successful!");}
                    //Getting IDs from DB
                    ps=con.prepareStatement("SELECT id FROM singers");
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
                    new_fName = fName.getText();
                    new_lName = lName.getText();
                    new_nationality = nationality.getText();
                    new_style = style.getText();

                    try {
                        //Connecting to DB
                        con= DriverManager.getConnection(db,user,pass);
                        if(con != null){System.out.println("Connection to db for addSinger successful!");}else{ System.out.println("Connection to db for addSinger NOT successful!");}
                        //New Singer Quarry
                        ps=con.prepareStatement("INSERT INTO singers VALUES(?,?,?,?,?)");
                        ps.setInt(1, new_id);
                        ps.setString(2, new_fName);
                        ps.setString(3, new_lName);
                        ps.setString(4, new_nationality);
                        ps.setString(5, new_style);
                        ps.executeUpdate();

                        ps.close();
                        rs.close();
                        con.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }



                    System.out.println("Added new singer!");
                    updateCombo(ids);
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
                        System.out.println("Connection to db for delSinger successful!");
                    } else {
                        System.out.println("Connection to db for addSinger NOT successful!");
                    }
                    if (id.getText() != null) {
                        delID = Integer.parseInt((id.getText()));
                        ps = con.prepareStatement("SELECT singer_id FROM concerts_singers_relationship WHERE singer_id = ?");
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
                                ps = con.prepareStatement("DELETE FROM concerts_singers_relationship WHERE singer_id =?");
                                ps.setInt(1, delID);
                                ps.executeUpdate();
                                System.out.println("Deleted concerts_singers_relationship with singer_id " + delID);
                                ps.close();
                            }

                            ps = con.prepareStatement("DELETE FROM SINGERS WHERE id =?");
                            ps.setInt(1, delID);
                            ps.executeUpdate();
                            System.out.println("Deleted singer with id " + delID);
                            ps.close();
                            con.close();
                            id.setText("");
                            fName.setText("");
                            lName.setText("");
                           nationality.setText("");
                            style.setText("");
                            updateCombo(ids);

                        }

                    }catch(SQLException ex){
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
        add(add);
        add(del);
        updateCombo(ids);
        repaint();
        System.out.println("Successfully build AddSingerGUI");

    }//end of build


}//class end