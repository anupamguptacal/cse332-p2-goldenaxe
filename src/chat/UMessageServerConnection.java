package chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class UMessageServerConnection extends Thread {
    private final static String SERVER = "umessage.countablethoughts.com";
    private final static String SERVER_PASSWORD = "jTeaT.xdcLJG4Kk>>H2TNZ4eeb}Da7";
    public final static String CHAT_CHANNEL = "#main";

    private final String username;

    private final BufferedWriter out;
    private final BufferedReader in;

    private final uMessage main;
    private final Socket socket;

    public UMessageServerConnection(uMessage main, String username)
            throws UnknownHostException, IOException {
        this.username = username.toLowerCase();
        this.main = main;

        // Connect directly to the IRC server.
        this.socket = new Socket(UMessageServerConnection.SERVER, 9001);
        this.out = new BufferedWriter(
                new OutputStreamWriter(this.socket.getOutputStream()));
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

    public void go() throws IOException {
        // Log on to the server.
        write("PASS", UMessageServerConnection.SERVER_PASSWORD);
        write("NICK", this.username);
        write("USER", this.username, "-", "-", "-");

        // Read lines from the server until it tells us we have connected.
        String line = null;
        while ((line = this.in.readLine()) != null) {
            int code = Integer.parseInt(line.split(" ")[1]);
            switch (code) {
            case IRCCodes.RplMyInfo:
                this.main.loggedIn(this.username);
                write("JOIN", UMessageServerConnection.CHAT_CHANNEL);
                start();
                return;
            case IRCCodes.ErrNickNameInUse:
                this.main.badNick();
                this.socket.close();
                return;
            }
        }
    }

    public String getAccountName() {
        return this.username;
    }

    public void send(String cmd, String text) {
        try {
            cmd = cmd.trim();
            switch (cmd) {
            case "MAIN":
                m_channel(UMessageServerConnection.CHAT_CHANNEL, text);
                break;
            default:
                String[] parts = text.split(" ", 2);
                cmd = parts[0].trim();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write(String... args) throws IOException {
        StringBuilder str = new StringBuilder();

        int i = 0;
        while (i < args.length - 1) {
            str.append(args[i] + " ");
            i++;
        }
        if (args.length > 0) {
            str.append(args[i]);
        }
        this.out.write(str.toString());
        this.out.write("\r\n");
        this.out.flush();
    }

    public void m_channel(String channel, String msg) throws IOException {
        write("PRIVMSG", channel, ":" + msg);
    }

    @Override
    public void run() {
        try {
            String line = null;
            // Keep reading lines from the server.
            while ((line = this.in.readLine()) != null) {
                if (line.startsWith("PING")) {
                    write("PONG", line.substring(5));
                }
                else if (line.startsWith(":umessage")) {
                    int code = Integer.parseInt(line.split(" ")[1]);
                    switch (code) {
                    // List of users...
                    case IRCCodes.RplNamReply:
                        String[] names = line.split(":")[2].split(" ");
                        this.main.window.addUsers(names);
                        break;
                    }
                }
                else {
                    String cmd = line.split(" ")[1];
                    switch (cmd) {
                    case "PART":
                        this.main.window.removeUser(line.split(":")[1].split("!")[0]);
                        break;
                    case "JOIN":
                        this.main.window.addUser(line.split(":")[1].split("!")[0]);
                        break;
                    case "PRIVMSG":
                        if (!line.split(" ")[2].equals(this.username)) {
                            return;
                        }
                        String[] lineParts = line.split(":");
                        this.main.window.gotMessage(lineParts[1].split("!")[0],
                                lineParts[2]);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
