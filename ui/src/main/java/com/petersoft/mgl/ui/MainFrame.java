package com.petersoft.mgl.ui;

import com.petersoft.mgl.connection.DBConnector;
import com.petersoft.mgl.dto.JavitasDTO;
import com.petersoft.mgl.dto.LepcsoDTO;
import com.petersoft.mgl.filehandler.ExcelFileHandler;
import com.petersoft.mgl.model.*;
import com.petersoft.mgl.service.JavitasService;
import com.petersoft.mgl.service.KarbantartasService;
import com.petersoft.mgl.service.LepcsoService;
import com.petersoft.mgl.serviceimpl.JavitasServiceImpl;
import com.petersoft.mgl.serviceimpl.KarbantartasServiceImpl;
import com.petersoft.mgl.serviceimpl.LepcsoServiceImpl;
import com.petersoft.mgl.utility.NumberConstants;
import com.petersoft.mgl.utility.StringConstants;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MainFrame extends JFrame {
    private JPanel framePanel;
    private JButton lepcsoListButton;
    private JButton erVedButton;
    private JButton hibaButton;
    private JButton javitasButton;
    private JButton lepcsoInputButton;
    private JButton karbantartasMenuButton, leltarButton, dolgozoButton;
    private JMenuItem exitMenuItem;
    private JMenuItem loadKarbantartasMenuItem;
    private JMenuItem adatBazisItem;
    private JButton reportItem;
    private LepcsoListPanel lepcsoListPanel;
    private HibaInputPanel hibaInputPanel;
    private JavitasInputPanel javitasInputPanel;
    private ErVedInputPanel erVedInputPanel;
    private LepcsoDetailPanel lepcsoDetailPanel;
    private KarbanTartasInputPanel karbanTartasInputPanel;
    private LeltarPanel leltarPanel;
    private JButton exitMenuButton;
    private LepcsoService lepcsoService = new LepcsoServiceImpl();
    private JelentesPanel jelentesPanel;
    private NapiDolgozoPanel napiDolgozoPanel;


    public MainFrame() {
        super(StringConstants.APP_NAME);
        setLayout(new BorderLayout());
        constructAppWindow();
        setJMenuBar(createFrameMenu());
        DBConnector.open();
        initPanels();
        showFramePanel();
        setButtonListeners();
        setMenuItemListeners();

    }

    private void showSearchBarDialog() throws Exception {
        framePanel.setVisible(false);
        lepcsoListPanel.setVisible(false);
     //   hibaInputPanel.setVisible(false);
        erVedInputPanel.setVisible(false);
        javitasInputPanel.setVisible(false);
        karbanTartasInputPanel.setVisible(false);
        if (lepcsoDetailPanel != null) {
            lepcsoDetailPanel.setVisible(false);
        }
        jelentesPanel.setVisible(false);
        leltarPanel.setVisible(false);
        napiDolgozoPanel.setVisible(false);
        new SearchBarDialog(this);
    }

    public void showKarbanTartasPanel() throws Throwable {
        framePanel.setVisible(false);
        this.karbanTartasInputPanel = new KarbanTartasInputPanel(this);
        add(this.karbanTartasInputPanel);
        karbanTartasInputPanel.setVisible(true);
    }

    public void showKarbanTartasPanel(Lepcso lepcso) throws Throwable {
        framePanel.setVisible(false);
        this.karbanTartasInputPanel = new KarbanTartasInputPanel(this, lepcso);
        add(this.karbanTartasInputPanel);
        karbanTartasInputPanel.setVisible(true);
    }

    private void initPanels() {
        try {
            this.karbanTartasInputPanel = new KarbanTartasInputPanel(this);
            this.lepcsoListPanel = new LepcsoListPanel(this);
            this.erVedInputPanel = new ErVedInputPanel(this);
            this.hibaInputPanel = new HibaInputPanel(this);
            this.javitasInputPanel = new JavitasInputPanel(this);
            this.jelentesPanel = new JelentesPanel(this);
            this.leltarPanel = new LeltarPanel(this);
            this.napiDolgozoPanel = new NapiDolgozoPanel(this);
        } catch (Throwable e) {
            JOptionPane.showMessageDialog(null, e.getStackTrace());
        }
    }


    public void showLepcsoListPanel() throws Throwable {

            framePanel.setVisible(false);
            this.lepcsoListPanel = new LepcsoListPanel(this);
            add(this.lepcsoListPanel);
            refreshTable();
            lepcsoListPanel.setVisible(true);
        }



        public void showLepcsoDetailPanel (Lepcso lepcso) throws Exception {
            framePanel.setVisible(false);
            this.lepcsoDetailPanel = new LepcsoDetailPanel(this, lepcso);
            this.add(lepcsoDetailPanel);
            lepcsoDetailPanel.setVisible(true);
        }

        public void showErVedInputPanel () throws Throwable {
            framePanel.setVisible(false);
            this.erVedInputPanel = new ErVedInputPanel(this);
            add(erVedInputPanel);
            erVedInputPanel.setVisible(true);
        }

        public void showErVedInputPanel (Lepcso lepcso) throws Throwable {
            framePanel.setVisible(false);
            this.erVedInputPanel = new ErVedInputPanel(this, lepcso);
            add(erVedInputPanel);
            erVedInputPanel.setVisible(true);
        }

        public void showHibaInputPanel (Lepcso lepcso) throws Throwable {
//            framePanel.setVisible(false);
//            this.hibaInputPanel = new HibaInputPanel(this, lepcso);
//            add(hibaInputPanel);
//            hibaInputPanel.setVisible(true);
        }

        public void showHibaInputPanel () throws Throwable {
//            framePanel.setVisible(false);
//            this.hibaInputPanel = new HibaInputPanel(this);
//            add(hibaInputPanel);
//            hibaInputPanel.setVisible(true);
        }

        public void showHibaInputPanel (Lepcso lepcso, Hiba hiba) throws Throwable {
//            framePanel.setVisible(false);
//            this.hibaInputPanel = new HibaInputPanel(this, lepcso, hiba);
//            add(hibaInputPanel);
//            hibaInputPanel.setVisible(true);
        }

        public void showJavitasInputPanel () throws Throwable {
            framePanel.setVisible(false);
            this.javitasInputPanel = new JavitasInputPanel(this);
            add(javitasInputPanel);
            javitasInputPanel.setVisible(true);

        }

        public void showJavitasInputPanel (Lepcso lepcso) throws Throwable {
            framePanel.setVisible(false);
            this.javitasInputPanel = new JavitasInputPanel(this, lepcso);
            add(javitasInputPanel);
            javitasInputPanel.setVisible(true);
        }

//        public void showJavitasInputPanel (Lepcso lepcso, Hiba hiba) throws Throwable {
//            framePanel.setVisible(false);
//         //   this.javitasInputPanel = new JavitasInputPanel(this, lepcso, hiba);
//            add(javitasInputPanel);
//            javitasInputPanel.setVisible(true);
//
//        }

        public void showJavitasInputPanel (Lepcso lepcso, Javitas javitas) throws Throwable {
            framePanel.setVisible(false);
            this.javitasInputPanel = new JavitasInputPanel(this, lepcso, javitas);
            add(javitasInputPanel);
            javitasInputPanel.setVisible(true);

        }

        public void showJelentesPanel () {
            framePanel.setVisible(false);
            this.jelentesPanel = new JelentesPanel(this);
            add(jelentesPanel);
            jelentesPanel.setVisible(true);
        }

        public void showDolgozoPanel () {
            framePanel.setVisible(false);
            this.napiDolgozoPanel = new NapiDolgozoPanel(this);
            add(napiDolgozoPanel);
            napiDolgozoPanel.setVisible(true);
        }


        public void showFramePanel () {
            framePanel = new JPanel();
            framePanel.setLayout(new GridLayout(0, 1));
            lepcsoListButton = new JButton("Lépcsőkar alapadatok");
            erVedButton = new JButton("Érintésvédelmi jegyzőkönyv rögzítése");
            hibaButton = new JButton("Hiba rögzítése");
            javitasButton = new JButton("Javítás rögzítése");
            exitMenuButton = new JButton("Kilépés");
            lepcsoInputButton = new JButton("Lepcso Adatok Felvitele");
            karbantartasMenuButton = new JButton("Karbantartás");
            leltarButton = new JButton("Alkatrész kereső");
            dolgozoButton = new JButton("Dolgozó Napi Beosztás");
            framePanel.add(lepcsoListButton);
            //  framePanel.add(hibaButton);
            framePanel.add(javitasButton);
            framePanel.add(erVedButton);
            framePanel.add(karbantartasMenuButton);
            framePanel.add(new JSeparator(SwingConstants.HORIZONTAL));
            framePanel.add(lepcsoInputButton);
            framePanel.add(dolgozoButton);
            framePanel.add(reportItem);
            framePanel.add(leltarButton);
            framePanel.add(exitMenuButton);
            framePanel.setVisible(true);
            add(framePanel, BorderLayout.CENTER);
            createBorder();
            setButtonListeners();
        }

        public void refreshTable () throws Throwable {
            List<Lepcso> lepcsoList = lepcsoService.getAllLepcso();
            this.lepcsoListPanel.setTableModel(lepcsoList);
            this.lepcsoListPanel.updateTable();
        }

        private void setMenuItemListeners () {
            exitMenuItem.addActionListener(e -> {
                int answer = JOptionPane.showConfirmDialog(null,
                        "Valóban ki akarsz lépni?",
                        "Kilépés", JOptionPane.OK_CANCEL_OPTION);
                if (answer == JOptionPane.OK_OPTION) {
                    DBConnector.shutdown();
                    System.exit(0);
                }
            });

            loadKarbantartasMenuItem.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel file", "xlsx");
                fileChooser.setFileFilter(filter);
                int returnVal = fileChooser.showOpenDialog(this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    JOptionPane.showMessageDialog(null, "A beolvasás eltarthat néhány másodpercig." +
                            "\nVárj türelemmel!");
                    try {
                        Map<Integer, Set<String>> resultMap;
                        ExcelFileHandler.LoadListener listener = new ExcelFileHandler.LoadListener() {
                            @Override
                            public void setProgress(int percent) {
                                //progressBar.setProgress(percent);
                                //System.out.println(percent);
                            }
                        };
                        resultMap = ExcelFileHandler.importDataFromKarbantartasExcel(file.getAbsolutePath(), listener);
                        addKarbantartasFromExcelToDataBase(resultMap);

                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null,
                                "Hiba IO", "Olvasási Hiba",
                                JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,
                                ex.getStackTrace(), "Exception Hiba",
                                JOptionPane.ERROR_MESSAGE);
                    } catch (Throwable ex) {
                        JOptionPane.showMessageDialog(null,
                                ex.getStackTrace(), "Általános Hiba",
                                JOptionPane.ERROR_MESSAGE);
                    }

                }

            });

            reportItem.addActionListener(e -> {
                showJelentesPanel();

            });

            adatBazisItem.addActionListener(e -> {
                        String dbName = "mgl2020";
                        String dbUser = "mgluser";
                        String dbPass = "MGL2020User";
                        String executeCmd = "";
                        executeCmd = "mysqldump -u "+dbUser+" -p"+dbPass+" "+dbName+" -r MGLBackup.sql";

                        Process runtimeProcess = null;
                        try {
                            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        int processComplete = 0;
                        try {
                            processComplete = runtimeProcess.waitFor();
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                        if(processComplete == 0){

                System.out.println("Backup taken successfully");

            } else {

                System.out.println("Could not take mysql backup");

            }
                    }
            );
        }

        private void addKarbantartasFromExcelToDataBase (Map <Integer, Set<String>> resultMap) throws Throwable {
            LepcsoService lepcsoService = new LepcsoServiceImpl();
            KarbantartasService karbantartasService = new KarbantartasServiceImpl();
            Map<Integer, Lepcso> lepcsoMapDB = lepcsoService.getAllLepcso().stream()
                    .collect(Collectors.toMap(Lepcso::getLepcsoSzama, lepcso -> lepcso));
            Map<String, KarbantartasTipus> karbTipusMapDB = karbantartasService.getKarbantartasTipusList().stream()
                    .collect(Collectors.toMap(KarbantartasTipus::getKarbTipusKod, karbTipus -> karbTipus));

            Set<Integer> excelLepcsoSzamSet = resultMap.keySet();

            int addedLepcsoNo = 0;
            for (Integer lepcsoExcel : excelLepcsoSzamSet) {
                if (lepcsoMapDB.containsKey(lepcsoExcel)) {
                    Lepcso l = lepcsoMapDB.get(lepcsoExcel);
                    if (l.getStatus().getStatusId() < 3) {
                        for (String excelKarbTipus : resultMap.get(lepcsoExcel)) {
                            if (karbTipusMapDB.containsKey(excelKarbTipus)) {
                                KarbantartasTipus kTipus = karbTipusMapDB.get(excelKarbTipus);
                                Karbantartas k = new Karbantartas();
                                k.setLepcso(l);
                                k.setKarbantartasTipus(kTipus);
                                karbantartasService.addKarbantartas(k);
                            }
                        }
                        addedLepcsoNo++;
                    }
                }
            }
            JOptionPane.showMessageDialog(null,
                    "" + addedLepcsoNo + "darab lépcső karbantartásai hozzáadva.");

        }

        private void setButtonListeners () {
            lepcsoListButton.addActionListener(e -> {
                framePanel.setVisible(false);
                try {
                    showLepcsoListPanel();
                } catch (Throwable exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                }
            });

            erVedButton.addActionListener(e -> {
                framePanel.setVisible(false);
                try {
                    showErVedInputPanel();
                } catch (Throwable exception) {
                    exception.printStackTrace();
                }
                erVedInputPanel.setVisible(true);
            });

//        hibaButton.addActionListener(e -> {
//            framePanel.setVisible(false);
//            try {
//                showHibaInputPanel();
//            } catch (Throwable exception) {
//                exception.printStackTrace();
//            }
//            hibaInputPanel.setVisible(true);
//        });

            javitasButton.addActionListener(e -> {
                framePanel.setVisible(false);
                try {
                    showJavitasInputPanel();
                } catch (Throwable exception) {
                    exception.printStackTrace();
                }
                javitasInputPanel.setVisible(true);
            });

            exitMenuButton.addActionListener(e -> {
                int answer = JOptionPane.showConfirmDialog(null,
                        "Valóban ki akarsz lépni?",
                        "Kilépés", JOptionPane.OK_CANCEL_OPTION);
                if (answer == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            });

            lepcsoInputButton.addActionListener(e -> {
                framePanel.setVisible(false);
                try {
                    showLepcsoInputPanel();
                } catch (Throwable exception) {
                    exception.printStackTrace();
                }
            });

            karbantartasMenuButton.addActionListener(e -> {
                framePanel.setVisible(false);
                try {
                    showKarbanTartasPanel();
                } catch (Throwable exception) {
                    JOptionPane.showMessageDialog(null, exception.getStackTrace());
                }
            });

            leltarButton.addActionListener(e -> {
                framePanel.setVisible(false);
                try {
                    showLeltarPanel();
                } catch (Throwable throwable) {
                    JOptionPane.showMessageDialog(null, throwable.getStackTrace());
                }
            });

            dolgozoButton.addActionListener(e -> {
                framePanel.setVisible(false);
                showDolgozoPanel();
            });
        }

        private void showLeltarPanel () {
            framePanel.setVisible(false);
            LeltarPanel leltarPanel = new LeltarPanel(this);
            add(leltarPanel);
            leltarPanel.setVisible(true);
        }

        private void showLepcsoInputPanel () throws Throwable {
            framePanel.setVisible(false);
            LepcsoInputPanel lepcsoInputPanel = new LepcsoInputPanel(this);
            add(lepcsoInputPanel);
            lepcsoInputPanel.setVisible(true);
        }


        private void createBorder () {
            Border outerBorder = BorderFactory.createEmptyBorder(30, 100, 30, 100);
            Border innerBorder = BorderFactory.createEtchedBorder();
            framePanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
        }

        private void constructAppWindow () {
            setSize(NumberConstants.MAINFRAME_WIDTH, NumberConstants.MAINFRAME_HEIGHT);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setVisible(true);

        }

        private JMenuBar createFrameMenu () {
            JMenuBar menuBar = new JMenuBar();
            menuBar.setBackground(new Color(149, 153, 153));
            menuBar.setPreferredSize(new Dimension(300, 40));
            JMenu fileMenu = new JMenu(StringConstants.FILE_MENU_TITLE);
            exitMenuItem = new JMenuItem(StringConstants.EXIT_MENU_TITLE);
            adatBazisItem = new JMenuItem(StringConstants.SAVE_DATABASE_MENU_ITEM);
            reportItem = new JButton(StringConstants.REPORT_MENU_ITEM);
            loadKarbantartasMenuItem = new JMenuItem(StringConstants.LOAD_KARBANTARTAS_FROM_EXCEL);
            fileMenu.add(adatBazisItem);
            //  fileMenu.add(reportItem);
            fileMenu.add(loadKarbantartasMenuItem);
            fileMenu.add(exitMenuItem);
            JMenuItem searchMenu = new JMenuItem(new AbstractAction(StringConstants.SEARCH_MENU_ITEM) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        showSearchBarDialog();
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null, exception.getStackTrace());
                    }
                }
            });

            JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
            menuBar.add(fileMenu);
            menuBar.add(separator);
            menuBar.add(searchMenu);

            return menuBar;
        }


    }

