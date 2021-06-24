package chatting.application;

// importing the necessary packages

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

import java.util.Calendar;
import java.text.SimpleDateFormat;


//I have made the Bunny as the server, start the server first
public class Server implements ActionListener {

    //The JPanel is a simplest container class.
    // It provides space in which an application can attach any other component. It inherits the JComponents class.
    JPanel p1;
    //The JTextField class is a text component that allows the editing of a single line text.
    JTextField t1;
    // A simple button with lable and actionListener
    JButton b1;
    static JPanel a1;
    //JFrame works like the main window where components like labels, buttons, textfields are added to create a GUI.
    static JFrame f1 = new JFrame();

    static Box vertical = Box.createVerticalBox();


    //Here i have made the references static cause we will be using them in the static area ie main methods
    // The brief description of the two classes ie ServerSocket and Socket is mentioned in the main method
    static ServerSocket skt;
    static Socket s;
    //Java application generally uses the data output stream to write data that can later be read by a data input stream in machine independent way.
    static DataInputStream din;
    //Java application generally uses the data output stream to write data that can later be read by a data input stream.
    static DataOutputStream dout;

    Boolean typing;

    // This is our Server class constructor here we will be instantiating the Jframe means creating the gui window to interact
    // and ultimately performing the chat activity
    Server() {
        f1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(7, 94, 84));
        // here we are setting the coordinates of the component ie Jpanel which will be used as container for the other elements like
        // textfield and button
        p1.setBounds(0, 0, 450, 70);
        f1.add(p1);

        // adding the image in the panel ie the backbutotn to stop the frame or clearing the frame
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/3.png"));
        //for scaling the image
        Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        //JLabel is used to add a label for element for unique identification
        JLabel l1 = new JLabel(i3);
        l1.setBounds(5, 17, 30, 30);
        //calling this add method will make the element that here is the icon visible
        p1.add(l1);

        //here we have set the mouse action listener to stop the frame using System.exit
        l1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });

        // adding another icon as the user dp
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/1.png"));
        Image i5 = i4.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel l2 = new JLabel(i6);
        l2.setBounds(40, 5, 60, 60);
        p1.add(l2);

        //adding the video icon as on whatsapp
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel l5 = new JLabel(i9);
        l5.setBounds(290, 20, 30, 30);
        p1.add(l5);

        // adding the phone icon as on whatsapp
        ImageIcon i11 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/phone.png"));
        Image i12 = i11.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i13 = new ImageIcon(i12);
        JLabel l6 = new JLabel(i13);
        l6.setBounds(350, 20, 35, 30);
        p1.add(l6);

        // adding the extra menu button
        ImageIcon i14 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/3icon.png"));
        Image i15 = i14.getImage().getScaledInstance(13, 25, Image.SCALE_DEFAULT);
        ImageIcon i16 = new ImageIcon(i15);
        JLabel l7 = new JLabel(i16);
        l7.setBounds(410, 20, 13, 25);
        p1.add(l7);

        // Here we are using the Jlable and calling its constructor to set the title as on TextView
        JLabel l3 = new JLabel("Bunny");
        l3.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        l3.setForeground(Color.WHITE);
        l3.setBounds(110, 15, 100, 18);
        p1.add(l3);

        // This is the another textfield to display the current status of the user ie is he reading or typing at the current instance
        JLabel l4 = new JLabel("Active Now");
        l4.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
        l4.setForeground(Color.WHITE);
        l4.setBounds(110, 35, 100, 20);
        p1.add(l4);


        // Timer from javax.swing helps in performing the certain task at certain interval here we are using it for displaying the user status
        Timer t = new Timer(1, new ActionListener() {
            // we defined the action to be performed in this method
            public void actionPerformed(ActionEvent ae) {
                if (!typing) {
                    l4.setText("Active Now");
                }
            }
        });
        // sets the timer's initial delay
        t.setInitialDelay(2000);


        a1 = new JPanel();
        a1.setBounds(5, 75, 440, 570);
        a1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f1.add(a1);


        t1 = new JTextField();
        t1.setBounds(5, 655, 310, 40);
        t1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f1.add(t1);

        t1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                l4.setText("typing...");

                t.stop();

                typing = true;
            }

            public void keyReleased(KeyEvent ke) {
                typing = false;

                if (!t.isRunning()) {
                    t.start();
                }
            }
        });

        // working on send button
        b1 = new JButton("Send");
        b1.setBounds(320, 655, 123, 40);
        b1.setBackground(new Color(7, 94, 84));
        b1.setForeground(Color.WHITE);
        b1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        //added the actionListener
        b1.addActionListener(this);
        f1.add(b1);

        // the actual windows area to display the chat messages
        f1.getContentPane().setBackground(Color.WHITE);
        f1.setLayout(null);
        f1.setSize(450, 700);
        f1.setLocation(400, 200);
        f1.setUndecorated(true);
        f1.setVisible(true);

    }

    // The defining the action performed upon clicking on the send button
    public void actionPerformed(ActionEvent ae) {
        //performing the exception handling
        try {

            // extracting the text been typed in the textfiend into a string object
            String out = t1.getText();

            JPanel p2 = formatLabel(out);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            a1.add(vertical, BorderLayout.PAGE_START);

            //a1.add(p2);
            //here we are finally displaying the text on the panel or chat area
            dout.writeUTF(out);
            t1.setText("");
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    //aligning the messages as for sender to the right of the screen or windows and wrapping the text to new line if it is long
    // also highlighting the text while displaying the text on the panel
    public static JPanel formatLabel(String out) {
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));

        JLabel l1 = new JLabel("<html><p style = \"width : 150px\">" + out + "</p></html>");
        l1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        l1.setBackground(new Color(37, 211, 102));
        l1.setOpaque(true);
        l1.setBorder(new EmptyBorder(15, 15, 15, 50));

        // We are using the Calendar class from the java.util package to display the date and time when the messages was sent and received
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel l2 = new JLabel();
        l2.setText(sdf.format(cal.getTime()));

        p3.add(l1);
        p3.add(l2);
        return p3;
    }

    // everyone's favorite main method
    public static void main(String[] args) {

        //Here we are adding the socket prgramming
        // Socket and ServerSocket classes are used for connection-oriented socket programming and DatagramSocket and
        // DatagramPacket classes are used for connection-less socket programming.
        //The client in socket programming must know two information:
        //IP Address of Server, and
        //Port number.
        // We have already declared the instance references gloabally in the current class

// Socket Class  : A socket is simply an endpoint for communications between the machines. The Socket class can be used to create a socket.
//        Method	                                        Description
//        1) public InputStream getInputStream()	  returns the InputStream attached with this socket.
//        2) public OutputStream getOutputStream()	  returns the OutputStream attached with this socket.
//        3) public synchronized void close()	      closes this socket

//        ServerSocket class : The ServerSocket class can be used to create a server socket.
//                             This object is used to establish communication with the clients.

//        Important methods
//        Method                            	Description
//        1) public Socket accept()          	returns the socket and establish a connection between server and client.
//        2) public synchronized void close()	closes the server socket.

        new Server().f1.setVisible(true);

        String msginput = "";
        try {
            skt = new ServerSocket(6001);

            //here the loop part is necessary for the maintaining the connection between server and client and make effective message communication
            while (true) {
                s = skt.accept();
                din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());

                while (true) {
                    msginput = din.readUTF();

                    //we called the formatLabel method for properly allinging the text in chat area
                    JPanel p2 = formatLabel(msginput);

                    JPanel left = new JPanel(new BorderLayout());
                    left.add(p2, BorderLayout.LINE_START);
                    vertical.add(left);
                    f1.validate();
                }
            }
        } catch (Exception e) {
        }
        //end of main method
    }

}
