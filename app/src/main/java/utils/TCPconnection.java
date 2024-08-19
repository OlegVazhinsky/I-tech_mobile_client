package utils;

import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TCPconnection {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private int port;
    private String host;
    private int timeout;

    public TCPconnection() {}

    public TCPconnection(String host, int port, int timeout) {
        this.host = host;
        this.port = port;
        this.timeout = timeout;
    }

    public void connect() throws IOException {
        socket = new Socket(host,  port);
        socket.setSoTimeout(timeout);
        //out = new PrintWriter(socket.getOutputStream(), true);
        //in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void disconnect() throws IOException {
        if (socket.isConnected()) {
            out.close();
            in.close();
            socket.close();
        }
    }

    public void writeToSocket(String message) {
        if (socket.isConnected()) {
            out.println(message);
        }
    }

    public String readFromSocket() {
        String answer = "NO DATA";
        try {
            answer = in.readLine();
        } catch (SocketTimeoutException e) {
            //"EXCEPTION - SocketTimeoutException found while trying to read from socket."
        } catch (IOException e) {
            //"EXCEPTION - IOException found while trying to read from socket."
        }
        return answer;
    }

    public boolean isConnected() {
        return socket.isConnected();
    }

}