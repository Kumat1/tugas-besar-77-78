package file;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tubes extends JFrame {
    public JPanel panel, panelnew, panelawal, scroll, panellock, panelload;
    public JTextArea areanew;
    public JTextField fieldjudul, fieldtanggal, fieldkategori, fieldlock;
    public String Sjudul, Stanggal, Skategori, Spassword, Sisi, Sjenis;
    public JScrollPane listItem;
    public Date tanggal;
    public JLabel list, depan;
    public BoxLayout boxLayout;
    public ImageIcon logo, icon, locker, iconlock;
    public Image imagelogo, imagelock;
    public JComboBox CBkategori, CBjenis;
    public drawpad paintPad, openPad;
    private JMenuBar menu;
    private JMenu fileMenu, Option, Changekat;
    private JMenuItem close, New, save, home, lock, unlock, delete, blue, white, red, green, yellow, clearp, saveImage, openImage;


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
        Option = new JMenu(" Option ");
        menu.add(Option);
        Option.setVisible(false);
        New = new JMenuItem("New");
        fileMenu.add(New);
        openImage = new JMenuItem("Open Image");
        fileMenu.add(openImage);
        home = new JMenuItem("home");
        fileMenu.add(home);
        close = new JMenuItem("Close");
        fileMenu.add(close);
        save = new JMenuItem("Save");
        Option.add(save);
        lock = new JMenuItem("Lock");
        Option.add(lock);
        unlock = new JMenuItem("Unlock");
        delete = new JMenuItem("Delete");
        clearp = new JMenuItem("Clear");
        saveImage = new JMenuItem("Save Image");

        Option.add(delete);
        Option.add(clearp);
        Option.add(saveImage);
        Changekat = new JMenu("Change kategori");
        Option.add(Changekat);
        blue = new JMenuItem("Blue");
        red = new JMenuItem("Red");
        white = new JMenuItem("White");
        yellow = new JMenuItem("Yellow");
        green = new JMenuItem("Green");
        Changekat.add(blue);
        Changekat.add(red);
        Changekat.add(white);
        Changekat.add(yellow);
        Changekat.add(green);

        CardLayout cards = new CardLayout();
        JPanel cardPanel = new JPanel();

        panel = new JPanel(new BorderLayout());
        panel.add(cardPanel, BorderLayout.CENTER);

        listjudul();

        logo = new ImageIcon("logo.png");
        imagelogo= logo.getImage().getScaledInstance(250,250,Image.SCALE_SMOOTH);
        icon = new ImageIcon(imagelogo);

        depan = new JLabel(icon);

        panelawal = new JPanel(new BorderLayout());
        panelawal.setBackground(Color.gray);
        panelawal.add(depan, BorderLayout.CENTER);

        panelnew = new JPanel(new GridBagLayout());
        panellock = new JPanel(new GridBagLayout());
        panelload = new JPanel(new GridBagLayout());

        fieldjudul = new JTextField(30);
        fieldtanggal = new JTextField(30);
        fieldkategori = new JTextField(30);

        fieldlock = new JTextField(30);
        String kategori[] = {"Blue","Red","Yellow","Green","White"};
        CBkategori = new JComboBox(kategori);

        String jenis[] = {"Text","Draw"};
        CBjenis = new JComboBox(jenis);

        areanew = new JTextArea();
        areanew.setLineWrap(true);
        areanew.setSize(350,150);

        cardPanel.setLayout(cards);
        cards.show(cardPanel, "CardLayout");

        paintPad = new drawpad();
        openPad = new drawpad();

        cardPanel.add(panelawal, "Card 1");
        cardPanel.add(areanew, "Card 2");
        cardPanel.add(paintPad, "Card 3");
        cardPanel.add(openPad, "Card 4");

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

                c.gridy = 2;
                JLabel jenis = new JLabel("Jenis : ");
                panelnew.add(jenis,c);

                c.gridx = 1;
                c.gridy = 0;
                c.anchor = GridBagConstraints.LINE_START;
                fieldjudul.setText("");
                panelnew.add(fieldjudul,c);

                c.gridy = 1;
                panelnew.add(CBkategori,c);

                c.gridy = 2;
                panelnew.add(CBjenis,c);

                int result = JOptionPane.showConfirmDialog(null, panelnew, "Agenda", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    Sjudul = fieldjudul.getText();
                    Skategori = (String)CBkategori.getSelectedItem();
                    Sjenis = (String)CBjenis.getSelectedItem();

                    panelawal.setVisible(false);

                    if(Sjenis.equals("Draw"))
                    {
                        paintPad.setVisible(true);
                        Option.setVisible(true);
                        save.setVisible(false);
                    }
                    else{
                        areanew.setText("");
                        areanew.setVisible(true);
                        Option.setVisible(true);
                        saveImage.setVisible(false);
                    }
                }
            }
        });

        openImage.addActionListener(new ActionListener() {
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
                panelload.add(judul,c);

                c.gridx = 1;
                c.gridy = 0;
                c.anchor = GridBagConstraints.LINE_START;
                fieldjudul.setText("");
                panelload.add(fieldjudul,c);

                int result = JOptionPane.showConfirmDialog(null, panelload, "Load Image", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    Sjudul = fieldjudul.getText();

                    panelawal.setVisible(false);
                    areanew.setVisible(false);

                    try {
                        load();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    openPad.setVisible(true);

                    Option.setVisible(true);
                    save.setVisible(false);
                }
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

                panel.remove(listItem);
                panel.revalidate();
                panel.repaint();

                try {
                    listjudul();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelawal.setVisible(true);
                areanew.setVisible(false);
                Option.setVisible(false);
            }
        });

        lock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GridBagConstraints l = new GridBagConstraints();
                l.weightx = 1;
                l.weighty = 1;
                l.gridwidth = 1;
                l.gridheight = 1;

                l.gridx = 0;
                l.gridy = 0;
                l.anchor = GridBagConstraints.LINE_END;
                JLabel password = new JLabel("password: ");
                panellock.add(password,l);

                l.gridx = 1;
                l.gridy = 0;
                l.anchor = GridBagConstraints.LINE_START;
                panellock.add(fieldlock,l);

                int hasil = JOptionPane.showConfirmDialog(null, panellock, "lock", JOptionPane.OK_CANCEL_OPTION);

                if (hasil == JOptionPane.OK_OPTION) {
                    Spassword = fieldlock.getText();

                    try {
                        pass();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                    panel.remove(listItem);
                    panel.revalidate();
                    panel.repaint();

                    try {
                        listjudul();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        unlock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Spassword = "";
                try {
                    pass();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                panel.remove(listItem);
                panel.revalidate();
                panel.repaint();

                try {
                    listjudul();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    delete();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                panel.remove(listItem);
                panel.revalidate();
                panel.repaint();

                try {
                    listjudul();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        blue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Changekat("Blue");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        red.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Changekat("Red");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        white.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Changekat("White");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        yellow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Changekat("Yellow");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        green.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Changekat("Green");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        saveImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage drawing = paintPad.getBufferedImage();

                File outputFile = new File(Sjudul + ".png");
                try {
                    ImageIO.write(drawing, "png", outputFile);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        clearp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintPad.clearp();
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

    public void load() throws IOException {
        openPad.image = ImageIO.read(new File(Sjudul + ".png"));
        repaint();
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

    public void open(String judul) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda", "root", "");
        String panggil = "SELECT * FROM isi where title = ?";
        PreparedStatement sta = connection.prepareStatement(panggil);
        sta.setString(1, judul);
        ResultSet rse = sta.executeQuery();

        while(rse.next())
        {
            Sjudul = rse.getString(1);
            Stanggal = rse.getString(2);
            Skategori = rse.getString(3);
            Spassword = rse.getString(4);
            Sisi = rse.getString(5);
        }

        if(Spassword!=""){
            GridBagConstraints a = new GridBagConstraints();
            a.weightx = 1;
            a.weighty = 1;
            a.gridwidth = 1;
            a.gridheight = 1;

            a.gridx = 0;
            a.gridy = 0;
            a.anchor = GridBagConstraints.LINE_END;
            JLabel apassword = new JLabel("password: ");
            panellock.add(apassword,a);

            a.gridx = 1;
            a.gridy = 0;
            a.anchor = GridBagConstraints.LINE_START;
            fieldlock.setText("");
            panellock.add(fieldlock,a);

            int hasil = JOptionPane.showConfirmDialog(null, panellock, "unlock password", JOptionPane.OK_CANCEL_OPTION);

            if (hasil == JOptionPane.OK_OPTION) {
                if (Spassword.equals(fieldlock.getText())) {
                    panelawal.setVisible(false);
                    areanew.setText(Sisi);
                    areanew.setVisible(true);

                    Option.add(unlock);
                    Option.setVisible(true);

                    setTitle(Sjudul);
                }
                else{
                    JOptionPane.showMessageDialog(Tubes.this,"Password Salah");

                    Thread T = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Thread.sleep(1000);
                            }
                            catch(InterruptedException e){
                                e.printStackTrace();
                            }
                            try {
                                open(judul);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    T.start();
                }
            }
        }
        else{
            panelawal.setVisible(false);
            areanew.setText(Sisi);
            areanew.setVisible(true);
            Option.setVisible(true);

            setTitle(Sjudul);
        }
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

    public void pass() throws SQLException {
        Connection connection = Koneksi.getConnection();

        PreparedStatement ps = connection.prepareStatement
                ("UPDATE isi SET tanggal=?, password=? WHERE title=? ");

        ps.setString(1, Stanggal);
        ps.setString(2, Spassword);
        ps.setString(3, Sjudul);
        ps.executeUpdate();
        connection.close();
    }

    public void delete() throws SQLException {
        Connection connection = Koneksi.getConnection();

        PreparedStatement ps = connection.prepareStatement
                ("DELETE FROM isi WHERE title=?");

        ps.setString(1, Sjudul);
        ps.executeUpdate();
        connection.close();
    }

    public void Changekat(String warna) throws SQLException {
        Connection connection = Koneksi.getConnection();

        Skategori = warna;
        PreparedStatement ps = connection.prepareStatement
                ("UPDATE isi SET kategori=? WHERE title=? ");

        ps.setString(1, Skategori);
        ps.setString(2, Sjudul);
        ps.executeUpdate();
        connection.close();

        panel.remove(listItem);
        panel.revalidate();
        panel.repaint();

        try {
            listjudul();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void listjudul() throws SQLException {
        scroll = new JPanel();

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda", "root", "");
        String panggil = "SELECT * FROM isi";
        Statement sta = connection.createStatement();
        ResultSet rse = sta.executeQuery(panggil);

        locker = new ImageIcon("gembok.png");
        imagelock = locker.getImage().getScaledInstance(10,10,Image.SCALE_SMOOTH);
        iconlock = new ImageIcon(imagelock);

        while(rse.next()){
            String judul = rse.getString(1);
            if(rse.getString(4)!=""){
                list = new JLabel(judul);
                list.setHorizontalAlignment(SwingConstants.LEFT);
                list.setVerticalAlignment(SwingConstants.CENTER);
                list.setIcon(iconlock);
                list.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            }
            else {
                list = new JLabel(judul);
            }

            if(rse.getString(3).equals("Blue")){
                list.setBackground(Color.BLUE);
            }
            else if(rse.getString(3).equals("Red")){
                list.setBackground(Color.red);
            }
            else if(rse.getString(3).equals("Yellow")){
                list.setBackground(Color.YELLOW);
            }
            else if(rse.getString(3).equals("Green")){
                list.setBackground(Color.GREEN);
            }
            else if(rse.getString(3).equals("White")){
                list.setBackground(Color.WHITE);
            }

            list.setOpaque(true);
            list.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            list.setMaximumSize(new Dimension(Integer.MAX_VALUE, list.getMinimumSize().height + 5));

            list.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        open(judul);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });

            scroll.add(list);
            scroll.add(Box.createRigidArea(new Dimension(1, 5)));
        }
        boxLayout = new BoxLayout(scroll, BoxLayout.Y_AXIS);
        scroll.setLayout(boxLayout);
        listItem = new JScrollPane(scroll);
        listItem.setPreferredSize(new Dimension(150,150));

        panel.add(listItem, BorderLayout.WEST);
        connection.close();
    }
}