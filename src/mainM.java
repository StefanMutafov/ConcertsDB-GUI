import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class mainM extends JFrame {

    String db="jdbc:mysql://127.0.0.1:3306/concerts";
    String user="root";
    String pass="St951659";
    Connection con;

    {
        try {
            con = DriverManager.getConnection(db, user, pass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    PreparedStatement ps;
    ResultSet rs;
    final int sizeX = 400;
    final int sizeY = 700;
    public mainM(String title){
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(sizeX, sizeY);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        buildMenu();


    }

    private void buildMenu() {
        JButton addS = new JButton("Add Singer");
        JButton addC = new JButton("Add Concert");
        JButton addStoC = new JButton("Add Singer to concert");
        JButton showSforC = new JButton("Show singers for concert");
        JButton showCforS = new JButton("Show concerts for singer");
        JButton showSperfTime = new JButton("Show singer performences for time");
        JButton toFile = new JButton("Export");
        JButton importDB = new JButton("Import");

        addS.setBounds(sizeX/4, sizeY/10, sizeX/2, sizeY/16);
        addS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pressed add singer");
                new addSingGUI("Add Singer");

            }
        });

        addC.setBounds(sizeX/4, sizeY*2/10, sizeX/2, sizeY/16);
        addC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pressed add concert");
                new addConcertGUI("Add Concerts");

            }
        });

        addStoC.setBounds(sizeX/4, sizeY*3/10, sizeX/2, sizeY/16);
        addStoC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pressed add singer to concert");
                new addSingToConcertDB("Assign singer to concert");

            }
        });

        showSforC.setBounds(sizeX/4, sizeY*4/10, sizeX/2, sizeY/16);
        showSforC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pressed show singers for concerts");
                new showSingforC("Show singers for Concert");


            }
        });

        showCforS.setBounds(sizeX/4, sizeY*5/10, sizeX/2, sizeY/16);
        showCforS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pressed show concerts for singers");
                new showConcertsforS("Show concerts for singer");


            }
        });

        showSperfTime.setBounds(sizeX/4, sizeY*6/10, sizeX/2, sizeY/16);
        showSperfTime.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pressed Show Sing performances for time");
                new ShowPerfFortime("Show performaces for time");


            }
        });

        toFile.setBounds(sizeX/4, sizeY*7/10, sizeX/2, sizeY/16);
        toFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                System.out.println("Pressed Export");
                File f=new File("C:/export\\concerts.txt");
                    if(!f.exists()){
                        f.createNewFile();
                    }
                FileWriter fw=new FileWriter(f);
                ps=con.prepareStatement("Select * from  concerts");
                rs=ps.executeQuery();
                String s1="Insert into concerts values";

                while(rs.next()) {
                    if(s1.endsWith(")"))s1+=",";
                    s1+=String.format("(%d , '%s' , '%s' , '%s' , %.2f)\n", rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDate(4).toString(),rs.getDouble(5));

                }
                fw.write(s1);
                rs.close();
                ps.close();
                fw.close();
                }catch(SQLException ex) {
                    ex.printStackTrace();
                }catch(IOException ex) {
                    ex.printStackTrace();
                }


            }
        });

        importDB.setBounds(sizeX/4, sizeY*8/10, sizeX/2, sizeY/16);
        importDB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pressed Import");
                try {
                File f1=new File("C:/export\\concerts.txt");
                if(!f1.exists()){
                    f1.createNewFile();
                }
                Scanner scan=new Scanner(f1);
                int r=0;
                while(scan.hasNextLine()) {
                    String in[]=scan.nextLine().split(" ; ");
                    ps=con.prepareStatement("INSERT INTO concerts values(?,?,?,?,?)");
                    ps.setInt(1, Integer.parseInt(in[0]));
                    ps.setString(2, in[1]);
                    ps.setString(3, in[2]);
                    ps.setString(4, in[3]);
                    ps.setDouble(5, Double.parseDouble(in[4].replace(',', '.')));
                    r+=ps.executeUpdate();
                }
                System.out.println(r+" rows added to concerts.");
                }catch(SQLException ex) {
                    ex.printStackTrace();
                }catch(IOException ex) {
                    ex.printStackTrace();
                }

            }
        });





        add (addS);
        add (addC);
        add (addStoC);
        add(showSforC);
        add (showCforS);
        add(showSperfTime);
        add(toFile);
        add(importDB);
        repaint();
        System.out.println("Successfully build mainMenu");
    }//buildMenu end



    public static void main(String[] args) {
        new mainM("Main menu");

    }//main end

}//Class end