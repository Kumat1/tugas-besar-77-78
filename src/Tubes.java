import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Tubes extends JFrame {
    public JPanel panel, panelnew, paneltiga;
    public JTextArea areanew;
    public JTextField fieldjudul, fieldtanggal, fieldkategori;
    public String Sjudul, Stanggal, Skategori, Sisi;
    private JMenuBar menu;
    private JMenu fileMenu;
    private JMenuItem close, New, save, home;


    public Tubes(){
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

        //panel awal
        panel = new JPanel();
        panel.setBackground(Color.GREEN);

        //panel new
        panelnew = new JPanel(new GridBagLayout());

        fieldjudul = new JTextField(30);
        fieldtanggal = new JTextField(30);
        fieldkategori = new JTextField(30);

        areanew = new JTextArea();

        //panel yang belum kepakek
        paneltiga = new JPanel();
        paneltiga.setBackground(Color.BLACK);

        cardPanel.setLayout(cards);
        cards.show(cardPanel, "CardLayout");

        cardPanel.add(panel, "Card 1");
        cardPanel.add(areanew, "Card 2");
        cardPanel.add(paneltiga, "Card 3");

        getContentPane().add(cardPanel);

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
                JLabel tanggal = new JLabel("Tanggal : ");
                panelnew.add(tanggal,c);

                c.gridy = 2;
                JLabel kategori = new JLabel("Kategori : ");
                panelnew.add(kategori,c);

                c.gridx = 1;
                c.gridy = 0;
                c.anchor = GridBagConstraints.LINE_START;
                panelnew.add(fieldjudul,c);

                c.gridy = 1;
                panelnew.add(fieldtanggal,c);

                c.gridy = 2;
                panelnew.add(fieldkategori,c);

                int result = JOptionPane.showConfirmDialog(null, panelnew, "Agenda", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    Sjudul = fieldjudul.getText();
                    Stanggal = fieldtanggal.getText();
                    Skategori = fieldkategori.getText();
                }

                panel.setVisible(false);
                areanew.setVisible(true);
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sisi = areanew.getText();
                try {
                    insert();
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
                paneltiga.setVisible(false);
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

    public static void main(String[] args){
        Tubes agenda = new Tubes();
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