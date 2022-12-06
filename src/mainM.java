import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainM extends JFrame {
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


            }
        });

        addStoC.setBounds(sizeX/4, sizeY*3/10, sizeX/2, sizeY/16);
        addStoC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pressed add singer to concert");


            }
        });

        showSforC.setBounds(sizeX/4, sizeY*4/10, sizeX/2, sizeY/16);
        showSforC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pressed show singers for concerts");


            }
        });

        showCforS.setBounds(sizeX/4, sizeY*5/10, sizeX/2, sizeY/16);
        showCforS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pressed show concerts for singers");


            }
        });

        showSperfTime.setBounds(sizeX/4, sizeY*6/10, sizeX/2, sizeY/16);
        showSperfTime.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pressed Show Sing performances for time");


            }
        });

        toFile.setBounds(sizeX/4, sizeY*7/10, sizeX/2, sizeY/16);
        toFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pressed Export");


            }
        });

        importDB.setBounds(sizeX/4, sizeY*8/10, sizeX/2, sizeY/16);
        importDB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pressed Import");

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