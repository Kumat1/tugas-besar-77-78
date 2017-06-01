package file;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tubes extends JFrame {
    public JPanel panel, panelnew, panelawal, scroll;
    public JTextArea areanew;
    public JTextField fieldjudul, fieldtanggal, fieldkategori;
    public String Sjudul, Stanggal, Skategori, Sisi;
    public JScrollPane listItem;
    public Date tanggal;
    public JLabel list;
    public BoxLayout boxLayout;
    private JMenuBar menu;
    private JMenu fileMenu;
    private JMenuItem close, New, save, home;


    public Tubes() throws SQLException {
        super("Agenda");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        menu = new JMenuBar();
        setJMenuBar(menu);

        fileMenu = new JMenu(" File ");
        menu.add(fileMenu);

        New = new JMenuItem("New");
        fileMenu.add(New);
        save = new JMenuItem("Save");
        fileMenu.add(save);
        home = new JMenuItem("home");
        fileMenu.add(home);
        close = new JMenuItem("Close");
        fileMenu.add(close);

        CardLayout cards = new CardLayout();
        JPanel cardPanel = new JPanel();

        //panel semua
//        JPanel panel = (JPanel) getContentPane();
//        panel.setLayout(null);

        panel = new JPanel(new BorderLayout());
        panel.add(cardPanel, BorderLayout.CENTER);

        //panelscroll
        scroll = new JPanel();

        //scrollpane
//        listItem = new JScrollPane();
//        listItem.setPreferredSize(new Dimension(150,150));

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda", "root", "");
        String panggil = "SELECT * FROM isi";
        Statement sta = connection.createStatement();
        ResultSet rse = sta.executeQuery(panggil);

        while(rse.next()){
            list = new JLabel(rse.getString(1));
            list.setPreferredSize(new Dimension(100,100));
            list.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            scroll.add(list);
            scroll.add(Box.createRigidArea(new Dimension(10, 5)));
        }
        boxLayout = new BoxLayout(scroll, BoxLayout.Y_AXIS);
        scroll.setLayout(boxLayout);
        listItem = new JScrollPane(scroll);
        listItem.setPreferredSize(new Dimension(150,150));

        panel.add(listItem, BorderLayout.WEST);

        connection.close();


        //panel awal
        panelawal = new JPanel();
        panelawal.setBackground(Color.BLACK);

        //panel new
        panelnew = new JPanel(new GridBagLayout());

        fieldjudul = new JTextField(30);
        fieldtanggal = new JTextField(30);
        fieldkategori = new JTextField(30);

        areanew = new JTextArea();
        areanew.setSize(450,150);

        cardPanel.setLayout(cards);
        cards.show(cardPanel, "CardLayout");

        cardPanel.add(panelawal, "Card 1");
        cardPanel.add(areanew, "Card 2");

        getContentPane().add(panel);


        New.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GridBagConstraints c = new GridBagConstraints();
                c.weightx = 1;
                c.weighty = 1;
                c.gridwidth = 1;
                c.gridheight = 1;

                c.gridx = 0;
                c.gridy = 0;
                c.anchor = GridBagConstraints.LINE_END;
                JLabel judul = new JLabel("Judul : ");
                panelnew.add(judul,c);

                c.gridy = 1;
                JLabel kategori = new JLabel("Kategori : ");
                panelnew.add(kategori,c);

                c.gridx = 1;
                c.gridy = 0;
                c.anchor = GridBagConstraints.LINE_START;
                panelnew.add(fieldjudul,c);

                c.gridy = 1;
                panelnew.add(fieldkategori,c);

                int result = JOptionPane.showConfirmDialog(null, panelnew, "Agenda", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    Sjudul = fieldjudul.getText();
                    Skategori = fieldkategori.getText();
                }

                panelawal.setVisible(false);
                areanew.setVisible(true);
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sisi = areanew.getText();

                //tanggal save/update terakhir
                tanggal = new Date( );
                SimpleDateFormat ft = new SimpleDateFormat ("yyyy/MM/dd");

                Stanggal = ft.format(tanggal);

                try {
                    check();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setVisible(true);
                //panelnew.setVisible(true);
            }
        });

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setSize(700,500);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws SQLException {
        Tubes agenda = new Tubes();
    }

    public void check() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda", "root", "");

        String panggil = "SELECT * FROM isi";
        Statement sta = connection.createStatement();
        ResultSet rse = sta.executeQuery(panggil);

        while(rse.next()){
          if(rse.getString("title").equals(Sjudul)){
              update();
              connection.close();
                return;
          }
        }
        insert();
        connection.close();
    }

    public void update() throws SQLException {
        Connection connection = Koneksi.getConnection();

        PreparedStatement ps = connection.prepareStatement
                ("UPDATE isi SET tanggal=?, kategori=?, isi=? WHERE title=? ");

        ps.setString(1, Stanggal);
        ps.setString(2, Skategori);
        ps.setString(3, Sisi);
        ps.setString(4, Sjudul);

        ps.executeUpdate();
        connection.close();
    }

    public void insert() throws SQLException {
        Connection connection = Koneksi.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement
                ("INSERT INTO isi(title, tanggal, kategori, password, isi) VALUES(?, ?, ?, '', ?)");

        preparedStatement.setString(1, Sjudul);
        preparedStatement.setString(2, Stanggal);
        preparedStatement.setString(3, Skategori);
        preparedStatement.setString(4, Sisi);

        preparedStatement.executeUpdate();
        connection.close();
    }
}