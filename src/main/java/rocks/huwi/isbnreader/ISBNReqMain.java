package rocks.huwi.isbnreader;

import java.awt.image.BufferedImage;
import javax.swing.Icon;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.net.URL;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.LayoutManager;
import net.miginfocom.swing.MigLayout;
import javax.swing.JPanel;
import java.text.DateFormat;
import java.util.GregorianCalendar;
import java.io.IOException;
import java.net.UnknownHostException;
import javax.swing.JCheckBox;
import java.awt.Label;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFrame;

// 
// Decompiled by Procyon v0.5.30
// 

public class ISBNReqMain
{
    private JFrame frmManager;
    private JTextField textField;
    private JTextField setRunNumb;
    private JLabel lblAktuellerTitel;
    private JLabel lblAutor;
    private JLabel lblTitel;
    private JLabel lblVerlag;
    private ISBNRequest ir;
    private HashMap<String, String> book;
    private HashMap<String, String> book2;
    private BookFileWriter bfw;
    private String runningNumber;
    private JTextField textField_titel;
    private JTextField textField_verlag;
    private JTextField textField_autor;
    private JLabel lblKontrolle;
    private JTextArea textPane;
    private String textAuthor;
    private String textVerlag;
    private String textTitel;
    private String Eigenerpreis;
    private String textDatei;
    private String textISBN10;
    private String textListenpreis;
    private String student;
    private ImageIcon defimage;
    private JLabel lblCover;
    private JTextField textDateiname;
    private JLabel lblIsbnnummer;
    private JTextField textField_Preis;
    private JLabel lblPreis;
    private JButton btnDatenbernehmen;
    private Label label;
    private JLabel lblDateiname;
    private JLabel lblListenpreis;
    private JTextField textField_ISBN10;
    private JTextField textField_Listenpreis;
    private JLabel lblIsbn;
    private JLabel lblCent;
    private JLabel label_2;
    private JLabel lblVerkufer;
    private JTextField textField_verkaufer;
    private JCheckBox chckbxStudent;
    private JLabel lblLaufnummereigenerPreisverkuferstudenttitelautorverlagisbnlistenpreis;
    
    public ISBNReqMain() throws UnknownHostException, IOException {
        this.ir = new ISBNRequest();
        this.bfw = new BookFileWriter();
        this.runningNumber = "0";
        this.textAuthor = "";
        this.textVerlag = "";
        this.textTitel = "";
        this.Eigenerpreis = "";
        this.textISBN10 = "";
        this.textListenpreis = "";
        this.student = "0";
        this.textDatei = "registeredBooks.csv";
        this.defimage = new ImageIcon("no_image_available.jpg", "");
        this.book = new HashMap<String, String>();
        this.book2 = new HashMap<String, String>();
        this.initialize();
        this.refresh();
    }
    
    public String getDate() {
        final GregorianCalendar now = new GregorianCalendar();
        final DateFormat df = DateFormat.getDateInstance(2);
        return df.format(now.getTime());
    }
    
    private void initialize() throws UnknownHostException, IOException {
        (this.frmManager = new JFrame()).setTitle("ISBN Reader");
        this.frmManager.setResizable(true);
        final JPanel panel1 = new JPanel();
        panel1.setBounds(12, 0, 245, 618);
        this.frmManager.getContentPane().setLayout(new MigLayout("", "[86px,grow][653.00,grow][101.00,grow][][]", "[23px][][32.00][20px][14px][14px][14px,grow][14px][26.00][27.00][][][-9.00][][][108.00][-21.00]"));
        (this.textField = new JTextField()).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                try {
                    if (ISBNReqMain.this.textField.getText().length() == 10 || ISBNReqMain.this.textField.getText().length() == 13) {
                        ISBNReqMain.this.requestISBN(ISBNReqMain.this.textField.getText());
                        ISBNReqMain.this.textField.setText("");
                    }
                }
                catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        });
        final JButton btnNewButton = new JButton("Daten abfragen");
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    if (ISBNReqMain.this.textField.getText().length() == 10 || ISBNReqMain.this.textField.getText().length() == 13) {
                        ISBNReqMain.this.requestISBN(ISBNReqMain.this.textField.getText());
                        ISBNReqMain.this.textField.setText("");
                    }
                }
                catch (UnknownHostException e2) {
                    e2.printStackTrace();
                }
                catch (IOException e3) {
                    e3.printStackTrace();
                }
                ISBNReqMain.this.textField.setText("");
            }
        });
        this.frmManager.getContentPane().add(btnNewButton, "cell 2 0,growx,aligny top");
        (this.label = new Label("Optionen:")).setFont(new Font("Dialog", 1, 14));
        this.frmManager.getContentPane().add(this.label, "cell 0 1");
        this.lblDateiname = new JLabel("Dateiname:");
        this.frmManager.getContentPane().add(this.lblDateiname, "cell 0 2");
        this.textDateiname = new JTextField();
        this.frmManager.getContentPane().add(this.textDateiname, "cell 1 2,growx");
        this.textDateiname.setColumns(10);
        (this.setRunNumb = new JTextField()).setText(this.runningNumber);
        this.setRunNumb.setToolTipText("Hier laufende Nummer eingeben,  bei der begonnen werden soll.");
        this.frmManager.getContentPane().add(this.setRunNumb, "cell 1 3,alignx left,aligny top");
        this.setRunNumb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                ISBNReqMain.this.bfw.setRunningNumber(Integer.parseInt(ISBNReqMain.this.setRunNumb.getText()));
            }
        });
        this.setRunNumb.setColumns(10);
        this.lblIsbnnummer = new JLabel("ISBN-Nummer:");
        this.frmManager.getContentPane().add(this.lblIsbnnummer, "cell 0 0");
        this.textField.setToolTipText("Hier ISBN Code eingeben");
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
        this.lblPreis = new JLabel("Preis festlegen:");
        this.frmManager.getContentPane().add(this.lblPreis, "cell 0 10");
        (this.textField_Preis = new JTextField()).setHorizontalAlignment(4);
        this.frmManager.getContentPane().add(this.textField_Preis, "flowx,cell 1 10,alignx left");
        this.textField_Preis.setColumns(10);
        (this.lblAktuellerTitel = new JLabel("Aktueller Titel:")).setFont(new Font("Tahoma", 1, 14));
        this.frmManager.getContentPane().add(this.lblAktuellerTitel, "cell 0 4,growx,aligny top");
        this.lblAutor = new JLabel("Autor:");
        this.frmManager.getContentPane().add(this.lblAutor, "cell 0 5,alignx left,aligny top");
        this.textField_autor = new JTextField();
        this.frmManager.getContentPane().add(this.textField_autor, "cell 1 5,growx");
        this.textField_autor.setColumns(10);
        this.lblTitel = new JLabel("Titel:");
        this.frmManager.getContentPane().add(this.lblTitel, "cell 0 6,alignx left,aligny top");
        this.textField_titel = new JTextField();
        this.frmManager.getContentPane().add(this.textField_titel, "cell 1 6,growx");
        this.textField_titel.setColumns(10);
        this.lblVerlag = new JLabel("Verlag:");
        this.frmManager.getContentPane().add(this.lblVerlag, "cell 0 7,alignx left,aligny top");
        this.textField_verlag = new JTextField();
        this.frmManager.getContentPane().add(this.textField_verlag, "cell 1 7,growx");
        this.textField_verlag.setColumns(10);
        this.lblVerkufer = new JLabel("Verk\u00e4ufer:");
        this.frmManager.getContentPane().add(this.lblVerkufer, "cell 0 11,alignx left");
        this.textField_verkaufer = new JTextField();
        this.frmManager.getContentPane().add(this.textField_verkaufer, "flowx,cell 1 11,alignx left");
        this.textField_verkaufer.setColumns(30);
        this.lblKontrolle = new JLabel("Kontrolle:");
        this.frmManager.getContentPane().add(this.lblKontrolle, "cell 0 14");
        (this.btnDatenbernehmen = new JButton("Daten \u00fcbernehmen")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    ISBNReqMain.this.writeToFile();
                }
                catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        });
        this.frmManager.getContentPane().add(this.btnDatenbernehmen, "cell 2 11 1 4,grow");
        this.textPane = new JTextArea();
        final JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(22);
        scrollPane.getViewport().setView(this.textPane);
        this.textPane.setText("");
        this.textPane.setEditable(false);
        this.lblLaufnummereigenerPreisverkuferstudenttitelautorverlagisbnlistenpreis = new JLabel("Laufnummer | Eigener Preis | Verk\u00e4ufer | Student | Titel | Autor | Verlag | ISBN10 | Listenpreis\r\n |");
        this.frmManager.getContentPane().add(this.lblLaufnummereigenerPreisverkuferstudenttitelautorverlagisbnlistenpreis, "cell 1 14");
        this.frmManager.getContentPane().add(scrollPane, "cell 1 15,grow");
        this.lblCent = new JLabel("\u20ac                (ACHTUNG: 50 cent gehen an die Fachschaft)");
        this.frmManager.getContentPane().add(this.lblCent, "cell 1 10");
        this.label_2 = new JLabel("\u20ac");
        this.frmManager.getContentPane().add(this.label_2, "cell 1 9");
        (this.chckbxStudent = new JCheckBox("Student")).isSelected();
        this.frmManager.getContentPane().add(this.chckbxStudent, "cell 1 11");
        this.frmManager.setDefaultCloseOperation(3);
        this.frmManager.pack();
        this.frmManager.setVisible(true);
    }
    
    public void displayBookData() throws UnknownHostException, IOException {
        this.textAuthor = this.book.get("Author");
        this.textTitel = this.book.get("Title");
        this.textVerlag = this.book.get("Publisher");
        this.textISBN10 = this.book.get("ISBN10");
        this.textListenpreis = this.book.get("List Price");
        final URL url = new URL(this.book.get("Cover"));
        final BufferedImage image = ImageIO.read(url);
        this.lblCover.setIcon(new ImageIcon(image));
        this.refresh();
    }
    
    public void refresh() {
        this.textField_autor.setText(this.textAuthor);
        this.textField_verlag.setText(this.textVerlag);
        this.textField_titel.setText(this.textTitel);
        this.textField_ISBN10.setText(this.textISBN10);
        this.textField_Listenpreis.setText(this.textListenpreis);
        this.textField_Preis.setText(this.Eigenerpreis);
        this.textDateiname.setText(this.textDatei);
        this.textField_autor.repaint();
        this.textField_verlag.repaint();
        this.textField_titel.repaint();
        this.textDateiname.repaint();
        this.lblCover.repaint();
    }
    
    public void requestISBN(final String isbn) throws UnknownHostException, IOException {
        this.book = this.ir.getISBN(isbn);
        this.textDatei = String.valueOf(this.textDateiname.getText().split(".csv")[0]) + ".csv";
        this.displayBookData();
    }
    
    public void writeToFile() throws IOException {
        if (this.textField_autor.getText() != "" || this.textField_titel.getText() != "") {
            (this.book2 = new HashMap<String, String>()).put("Author", this.textField_autor.getText());
            this.book2.put("Title", this.textField_titel.getText());
            this.book2.put("Publisher", this.textField_verlag.getText());
            this.book2.put("ISBN10", this.textField_ISBN10.getText());
            this.book2.put("List Price", this.textField_Listenpreis.getText());
            this.bfw.setOwnPrice(this.textField_Preis.getText());
            this.bfw.setRunningNumber(Integer.parseInt(this.setRunNumb.getText()));
            this.bfw.setVerkaufer(this.textField_verkaufer.getText());
            final boolean selected = this.chckbxStudent.isSelected();
            if (selected) {
                this.student = "STUD";
            }
            else {
                this.student = "0";
            }
            this.bfw.setStudent(this.student);
            this.textPane.setText(String.valueOf(this.textPane.getText()) + this.bfw.getControlValues(this.book2));
            this.textDatei = String.valueOf(this.textDateiname.getText().split(".csv")[0]) + ".csv";
            this.bfw.setFilename(this.textDateiname.getText());
            this.bfw.writeBookData(this.book2);
            this.runningNumber = new StringBuilder().append(this.bfw.getRunningNumber()).toString();
            this.setRunNumb.setText(this.runningNumber);
            this.setRunNumb.repaint();
            this.clear();
        }
    }
    
    public void clear() {
        this.book.clear();
        this.book2.clear();
        this.textAuthor = "";
        this.textVerlag = "";
        this.textTitel = "";
        this.textISBN10 = "";
        this.textListenpreis = "";
        this.Eigenerpreis = "";
        this.lblCover.setIcon(this.defimage);
        this.refresh();
    }
    
    public static void main(final String[] args) throws UnknownHostException, IOException {
        final ISBNReqMain is = new ISBNReqMain();
    }
}
