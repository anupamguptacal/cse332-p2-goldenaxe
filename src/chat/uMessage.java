package chat;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.function.Supplier;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import cse332.interfaces.misc.Dictionary;
import cse332.types.AlphabeticString;
import cse332.types.NGram;
import p2.clients.NGramTester;
import p2.wordsuggestor.WordSuggestor;

public class uMessage {
    private static int N = 3;
    private static String CORPUS = "eggs.txt";
    private static Supplier<Dictionary<NGram, Dictionary<AlphabeticString, Integer>>> NEW_OUTER = NGramTester
            .trieConstructor(NGram.class);
    private static Supplier<Dictionary<AlphabeticString, Integer>> NEW_INNER = NGramTester
            .trieConstructor(AlphabeticString.class);

    /*
     *
     *
     *
     *
     *
     */
    private JFrame frmUmessageLogin;
    private JTextField username;
    private JButton login;
    private Component horizontalStrut;
    private Component horizontalStrut_2;
    private Component verticalStrut;
    private JLabel errors;
    private boolean loggingIn;
    private static boolean[] loading;
    private static WordSuggestor[] markov;
    public MainWindow window;
    public UMessageServerConnection connection;

    static class MarkovLoader implements Runnable {
        private final uMessage window;
        private final int i;

        public MarkovLoader(uMessage window, int i) {
            super();
            this.window = window;
            this.i = i;
        }

        @Override
        public void run() {
            int N = uMessage.N;
            try {
                uMessage.markov[this.i] = new WordSuggestor(uMessage.CORPUS, N - this.i,
                        4, uMessage.NEW_OUTER, uMessage.NEW_INNER);
                uMessage.loading[this.i] = false;
                this.window.update();
            } catch (IOException e) {
            }
        }
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            final uMessage window = new uMessage();
            window.frmUmessageLogin.setVisible(true);
            window.errors.setText("Loading the Markov Data (n = " + uMessage.N + ")...");
            uMessage.markov = new WordSuggestor[uMessage.N];
            uMessage.loading = new boolean[uMessage.N];
            for (int i1 = 0; i1 < uMessage.N; i1++) {
                uMessage.loading[i1] = true;
            }
            for (int i2 = 0; i2 < uMessage.N; i2++) {
                new Thread(new MarkovLoader(window, i2)).start();
            }
        });
    }

    /**
     * Create the application.
     */
    public uMessage() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        this.frmUmessageLogin = new JFrame();
        this.frmUmessageLogin.setTitle("uMessage: Login");
        this.frmUmessageLogin.setBounds(100, 100, 450, 148);
        this.frmUmessageLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0, 90, 90, 90, 0, 0 };
        gridBagLayout.rowHeights = new int[] { 0, 9, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0,
                Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
        this.frmUmessageLogin.getContentPane().setLayout(gridBagLayout);

        this.verticalStrut = Box.createVerticalStrut(20);
        GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
        gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
        gbc_verticalStrut.gridx = 2;
        gbc_verticalStrut.gridy = 0;
        this.frmUmessageLogin.getContentPane().add(this.verticalStrut, gbc_verticalStrut);

        this.horizontalStrut = Box.createHorizontalStrut(20);
        GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
        gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
        gbc_horizontalStrut.gridx = 0;
        gbc_horizontalStrut.gridy = 1;
        this.frmUmessageLogin.getContentPane().add(this.horizontalStrut,
                gbc_horizontalStrut);

        JLabel usernameLabel = new JLabel("Username:");
        GridBagConstraints gbc_usernameLabel = new GridBagConstraints();
        gbc_usernameLabel.fill = GridBagConstraints.BOTH;
        gbc_usernameLabel.insets = new Insets(0, 0, 5, 5);
        gbc_usernameLabel.gridx = 1;
        gbc_usernameLabel.gridy = 1;
        this.frmUmessageLogin.getContentPane().add(usernameLabel, gbc_usernameLabel);

        this.username = new JTextField();
        this.username.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (update() && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    uMessage.this.login.setEnabled(false);
                    login();
                }
            }
        });

        GridBagConstraints gbc_username = new GridBagConstraints();
        gbc_username.gridwidth = 2;
        gbc_username.fill = GridBagConstraints.BOTH;
        gbc_username.insets = new Insets(0, 0, 5, 5);
        gbc_username.gridx = 2;
        gbc_username.gridy = 1;
        this.frmUmessageLogin.getContentPane().add(this.username, gbc_username);
        this.username.setColumns(10);

        this.horizontalStrut_2 = Box.createHorizontalStrut(20);
        GridBagConstraints gbc_horizontalStrut_2 = new GridBagConstraints();
        gbc_horizontalStrut_2.insets = new Insets(0, 0, 5, 0);
        gbc_horizontalStrut_2.gridx = 4;
        gbc_horizontalStrut_2.gridy = 1;
        this.frmUmessageLogin.getContentPane().add(this.horizontalStrut_2,
                gbc_horizontalStrut_2);

        this.login = new JButton("Login");
        this.login.setEnabled(false);
        this.login.addActionListener(e -> login());

        this.errors = new JLabel("");
        GridBagConstraints gbc_errors = new GridBagConstraints();
        gbc_errors.gridwidth = 2;
        gbc_errors.insets = new Insets(0, 0, 0, 5);
        gbc_errors.gridx = 1;
        gbc_errors.gridy = 2;
        this.frmUmessageLogin.getContentPane().add(this.errors, gbc_errors);
        GridBagConstraints gbc_login = new GridBagConstraints();
        gbc_login.insets = new Insets(0, 0, 0, 5);
        gbc_login.fill = GridBagConstraints.BOTH;
        gbc_login.gridx = 3;
        gbc_login.gridy = 2;
        this.frmUmessageLogin.getContentPane().add(this.login, gbc_login);
    }

    public boolean update() {
        boolean noneLoading = true;
        for (int i = 0; i < uMessage.loading.length; i++) {
            noneLoading &= !uMessage.loading[i];
        }
        if (noneLoading) {
            this.errors.setText("");
            this.errors.setForeground(Color.BLACK);
        }
        if (!this.loggingIn && this.username.getText().length() > 0) {
            this.login.setEnabled(true);
            this.errors.setForeground(Color.BLACK);
            return true;
        }
        else {
            this.login.setEnabled(false);
            return false;
        }
    }

    public void login() {
        this.loggingIn = true;
        update();
        try {
            this.connection = new UMessageServerConnection(this,
                    this.username.getText().replaceAll(" ", ""));
            this.connection.go();
        } catch (IOException e1) {
        }
    }

    public void badNick() {
        this.loggingIn = false;
        update();
        this.errors.setText("That username is already in use! Try another one.");
        this.errors.setForeground(Color.RED);
    }

    public void loggedIn(String username) {
        boolean noneLoading = false;
        while (!noneLoading) {
            noneLoading = true;
            for (int i = 0; i < uMessage.loading.length; i++) {
                noneLoading &= !uMessage.loading[i];
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
        }

        this.frmUmessageLogin.dispose();
        this.window = new MainWindow(username, uMessage.markov, this.connection);
        this.loggingIn = false;
        update();
    }
}
