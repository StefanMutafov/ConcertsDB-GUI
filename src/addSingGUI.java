import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.LinkedList;


public class addSingGUI extends JFrame {
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

        JButton add = new JButton("Add");
        add.setBounds(sizeX/4, sizeY*6/8, sizeX/2, sizeY/8);
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pressed Add");

                //Database

                String db="jdbc:mysql://127.0.0.1:3306/concerts";
                String user="root";
                String pass="St951659";
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
                        ps=con.prepareStatement("INSERT INTO singers VALUES(new_id,new_fName,new_lName,new_nationality,new_style)");
                        ps.execute();
                        ps.close();
                        rs.close();
                        con.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }



                    System.out.println("Added new singer!");
                }


            }
        });


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
        repaint();
        System.out.println("Successfully build AddSingerGUI");
    }//end of build


}//class end