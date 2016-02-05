package rocks.huwi.isbnreader;

import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.GregorianCalendar;

public class SwingGUI {
    private JFrame frmManager;
    private JTextField textField;
    private JTextField setRunningNumber;
    private JLabel lblAktuellerTitel;
    private JLabel lblAutor;
    private JLabel lblTitel;
    private JLabel lblVerlag;
    private InformationRetriever informationRetriever;
    private Book book;
    private Book book2;
    private Persistor persistor;
    private String runningNumber;
    private JTextField textField_Titel;
    private JTextField textField_Verlag;
    private JTextField textField_Autor;
    private JLabel lblKontrolle;
    private JTextArea textPane;
    private String textAuthor;
    private String textVerlag;
    private String textTitel;
    private String textVerkaufspreis;
    private String textDatei;
    private String textISBN10;
    private String textListenpreis;
    private String textStudent;
    private ImageIcon defimage;
    private JLabel lblCover;
    private JTextField textField_Dateiname;
    private JLabel lblIsbnnummer;
    private JTextField textField_Verkaufspreis;
    private JLabel lblVerkaufspreis;
    private JButton btnSpeichern;
    private Label label;
    private JLabel lblDateiname;
    private JLabel lblListenpreis;
    private JTextField textField_ISBN10;
    private JTextField textField_Listenpreis;
    private JLabel lblIsbn;
    private JLabel lblCent;
    private JLabel label_2;
    private JLabel lblVerkäufer;
    private JTextField textField_Verkäufer;
    private JCheckBox checkBoxStudent;
    private JLabel lblLaufnummereigenerPreisverkuferstudenttitelautorverlagisbnlistenpreis;

    public SwingGUI() throws IOException {
        this.informationRetriever = new InformationRetriever();
        this.persistor = new Persistor();
        this.runningNumber = "1";
        this.textAuthor = "";
        this.textVerlag = "";
        this.textTitel = "";
        this.textVerkaufspreis = "";
        this.textISBN10 = "";
        this.textListenpreis = "";
        this.textStudent = "0";
        this.textDatei = "registeredBooks.csv";
        this.defimage = new ImageIcon("no_image_available.jpg", "");
        this.book = new Book();
        this.book2 = new Book();
        this.initialize();
        this.refresh();
    }

    public static void main(final String[] args) throws IOException {
        final SwingGUI swingGui = new SwingGUI();
    }

    public String getDate() {
        final GregorianCalendar now = new GregorianCalendar();
        final DateFormat df = DateFormat.getDateInstance(2);
        return df.format(now.getTime());
    }

    private void initialize() throws IOException {
        (this.frmManager = new JFrame()).setTitle("ISBN Reader 2.0");
        this.frmManager.setResizable(true);
        final JPanel panel1 = new JPanel();
        panel1.setBounds(12, 0, 245, 618);
        this.frmManager.getContentPane().setLayout(new MigLayout("", "[86px,grow][653.00,grow][101.00,grow][][]", "[23px][][32.00][20px][14px][14px][14px,grow][14px][26.00][27.00][][][-9.00][][][108.00][-21.00]"));
        (this.textField = new JTextField()).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                try {
                    if (SwingGUI.this.textField.getText().length() == 10 || SwingGUI.this.textField.getText().length() == 13) {
                        SwingGUI.this.requestISBN(SwingGUI.this.textField.getText());
                        SwingGUI.this.textField.setText("");
                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        });
        final JButton btnNewButton = new JButton("Daten abfragen");
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    if (SwingGUI.this.textField.getText().length() == 10 || SwingGUI.this.textField.getText().length() == 13) {
                        SwingGUI.this.requestISBN(SwingGUI.this.textField.getText());
                        SwingGUI.this.textField.setText("");
                    }
                } catch (UnknownHostException e2) {
                    e2.printStackTrace();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                SwingGUI.this.textField.setText("");
            }
        });
        this.frmManager.getContentPane().add(btnNewButton, "cell 2 0,growx,aligny top");
        (this.label = new Label("Optionen:")).setFont(new Font("Dialog", 1, 14));
        this.frmManager.getContentPane().add(this.label, "cell 0 1");
        this.lblDateiname = new JLabel("Dateiname:");
        this.frmManager.getContentPane().add(this.lblDateiname, "cell 0 2");
        this.textField_Dateiname = new JTextField();
        this.frmManager.getContentPane().add(this.textField_Dateiname, "cell 1 2,growx");
        this.textField_Dateiname.setColumns(10);
        (this.setRunningNumber = new JTextField()).setText(this.runningNumber);
        this.setRunningNumber.setToolTipText("Hier laufende Nummer eingeben, bei der begonnen werden soll.");
        this.frmManager.getContentPane().add(this.setRunningNumber, "cell 1 3,alignx left,aligny top");
        this.setRunningNumber.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                //rocks.huwi.isbnreader.SwingGUI.this.persistor.setRunningNumber(Integer.parseInt(rocks.huwi.isbnreader.SwingGUI.this.setRunningNumber.getText()));
            }
        });
        this.setRunningNumber.setColumns(10);
        this.lblIsbnnummer = new JLabel("ISBN-Nummer:");
        this.frmManager.getContentPane().add(this.lblIsbnnummer, "cell 0 0");
        this.textField.setToolTipText("Hier ISBN-Nummer eingeben");
        this.frmManager.getContentPane().add(this.textField, "cell 1 0,growx,aligny center");
        this.textField.setColumns(10);
        final JLabel lblLaufendeNummer = new JLabel("Laufende Nummer:");
        this.frmManager.getContentPane().add(lblLaufendeNummer, "cell 0 3,growx,aligny center");
        this.lblCover = new JLabel("");
        this.frmManager.getContentPane().add(this.lblCover, "cell 2 5 1 5,alignx center,aligny center");
        this.lblIsbn = new JLabel("ISBN-10:");
        this.frmManager.getContentPane().add(this.lblIsbn, "cell 0 8");
        this.textField_ISBN10 = new JTextField();
        this.frmManager.getContentPane().add(this.textField_ISBN10, "cell 1 8,growx");
        this.textField_ISBN10.setColumns(10);
        this.lblListenpreis = new JLabel("Listenpreis:");
        this.frmManager.getContentPane().add(this.lblListenpreis, "cell 0 9");
        (this.textField_Listenpreis = new JTextField()).setHorizontalAlignment(4);
        this.textField_Listenpreis.setText("");
        this.frmManager.getContentPane().add(this.textField_Listenpreis, "flowx,cell 1 9,alignx left");
        this.textField_Listenpreis.setColumns(10);
        this.lblVerkaufspreis = new JLabel("Verkaufspreis:");
        this.frmManager.getContentPane().add(this.lblVerkaufspreis, "cell 0 10");
        (this.textField_Verkaufspreis = new JTextField()).setHorizontalAlignment(4);
        this.frmManager.getContentPane().add(this.textField_Verkaufspreis, "flowx,cell 1 10,alignx left");
        this.textField_Verkaufspreis.setColumns(10);
        (this.lblAktuellerTitel = new JLabel("Aktueller Titel:")).setFont(new Font("Tahoma", 1, 14));
        this.frmManager.getContentPane().add(this.lblAktuellerTitel, "cell 0 4,growx,aligny top");
        this.lblAutor = new JLabel("Autor:");
        this.frmManager.getContentPane().add(this.lblAutor, "cell 0 5,alignx left,aligny top");
        this.textField_Autor = new JTextField();
        this.frmManager.getContentPane().add(this.textField_Autor, "cell 1 5,growx");
        this.textField_Autor.setColumns(10);
        this.lblTitel = new JLabel("Titel:");
        this.frmManager.getContentPane().add(this.lblTitel, "cell 0 6,alignx left,aligny top");
        this.textField_Titel = new JTextField();
        this.frmManager.getContentPane().add(this.textField_Titel, "cell 1 6,growx");
        this.textField_Titel.setColumns(10);
        this.lblVerlag = new JLabel("Verlag:");
        this.frmManager.getContentPane().add(this.lblVerlag, "cell 0 7,alignx left,aligny top");
        this.textField_Verlag = new JTextField();
        this.frmManager.getContentPane().add(this.textField_Verlag, "cell 1 7,growx");
        this.textField_Verlag.setColumns(10);
        this.lblVerkäufer = new JLabel("Verkäufer*in:");
        this.frmManager.getContentPane().add(this.lblVerkäufer, "cell 0 11,alignx left");
        this.textField_Verkäufer = new JTextField();
        this.frmManager.getContentPane().add(this.textField_Verkäufer, "flowx,cell 1 11,alignx left");
        this.textField_Verkäufer.setColumns(30);
        this.lblKontrolle = new JLabel("Kontrolle:");
        this.frmManager.getContentPane().add(this.lblKontrolle, "cell 0 14");
        (this.btnSpeichern = new JButton("Buch abspeichern")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    SwingGUI.this.writeToFile();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        });
        this.frmManager.getContentPane().add(this.btnSpeichern, "cell 2 11 1 4,grow");
        this.textPane = new JTextArea();
        final JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(22);
        scrollPane.getViewport().setView(this.textPane);
        this.textPane.setText("");
        this.textPane.setEditable(false);
        this.lblLaufnummereigenerPreisverkuferstudenttitelautorverlagisbnlistenpreis = new JLabel("Laufnummer | Eigener Preis | Verkäufer | Student | Titel | Autor | Verlag | ISBN10 | ISBN13 | Listenpreis\r\n |");
        this.frmManager.getContentPane().add(this.lblLaufnummereigenerPreisverkuferstudenttitelautorverlagisbnlistenpreis, "cell 1 14");
        this.frmManager.getContentPane().add(scrollPane, "cell 1 15,grow");
        this.lblCent = new JLabel("\u20ac                (Obacht! 100 Cent gehen an die Fachschaft)");
        this.frmManager.getContentPane().add(this.lblCent, "cell 1 10");
        this.label_2 = new JLabel("\u20ac");
        this.frmManager.getContentPane().add(this.label_2, "cell 1 9");
        (this.checkBoxStudent = new JCheckBox("ist Student")).isSelected();
        this.frmManager.getContentPane().add(this.checkBoxStudent, "cell 1 11");
        this.frmManager.setDefaultCloseOperation(3);
        this.frmManager.pack();
        this.frmManager.setVisible(true);
    }

    public void displayBookData() throws IOException {
        this.textAuthor = this.book.getAuthor();
        this.textTitel = this.book.getTitle();
        this.textVerlag = this.book.getPublisher();
        this.textISBN10 = this.book.getIsbn10();
        this.textListenpreis = this.book.getListPrice();
        final URL url = new URL(this.book.getCoverURL());
        final BufferedImage image = ImageIO.read(url);
        this.lblCover.setIcon(new ImageIcon(image));
        this.refresh();
    }

    public void refresh() {
        this.textField_Autor.setText(this.textAuthor);
        this.textField_Verlag.setText(this.textVerlag);
        this.textField_Titel.setText(this.textTitel);
        this.textField_ISBN10.setText(this.textISBN10);
        this.textField_Listenpreis.setText(this.textListenpreis);
        this.textField_Verkaufspreis.setText(this.textVerkaufspreis);
        this.textField_Dateiname.setText(this.textDatei);
        this.textField_Autor.repaint();
        this.textField_Verlag.repaint();
        this.textField_Titel.repaint();
        this.textField_Dateiname.repaint();
        this.lblCover.repaint();
    }

    public void requestISBN(final String isbn) throws IOException {
        this.book = this.informationRetriever.retrieveBook(isbn);
        this.textDatei = String.valueOf(this.textField_Dateiname.getText().split(".csv")[0]) + ".csv";
        this.displayBookData();
    }

    public void writeToFile() throws IOException {
        if (this.textField_Autor.getText() != "" || this.textField_Titel.getText() != "") {
            this.book2 = new Book();
            this.book2.setAuthor(this.textField_Autor.getText());
            this.book2.setTitle(this.textField_Titel.getText());
            this.book2.setPublisher(this.textField_Verlag.getText());
            this.book2.setIsbn10(this.textField_ISBN10.getText());
            this.book2.setListPrice(this.textField_Listenpreis.getText());
            this.book2.setSellingPrice(this.textField_Verkaufspreis.getText());
            this.book2.setRunningNumber(Integer.parseInt(this.setRunningNumber.getText()));
            this.book2.setSeller(this.textField_Verkäufer.getText());
            final boolean isStudentSelected = this.checkBoxStudent.isSelected();
            /*if (selected) {
                this.textStudent = "STUD";
            } else {
                this.textStudent = "0";
            }*/
            this.book2.setStudent(isStudentSelected);
            this.textPane.setText(String.valueOf(this.textPane.getText()) + this.persistor.convertToCSV(this.book2));
            this.textDatei = String.valueOf(this.textField_Dateiname.getText().split(".csv")[0]) + ".csv";
            //this.persistor.setFilename(this.textField_Dateiname.getText());
            this.persistor.writeCSV(this.book2, this.textField_Dateiname.getText());
            this.runningNumber = String.valueOf(book2.getRunningNumber() + 1);
            this.setRunningNumber.setText(this.runningNumber);
            this.setRunningNumber.repaint();
            this.clear();
        }
    }

    public void clear() {
        //this.book.clear();
        //this.book2.clear();
        this.textAuthor = "";
        this.textVerlag = "";
        this.textTitel = "";
        this.textISBN10 = "";
        this.textListenpreis = "";
        this.textVerkaufspreis = "";
        this.lblCover.setIcon(this.defimage);
        this.refresh();
    }
}
