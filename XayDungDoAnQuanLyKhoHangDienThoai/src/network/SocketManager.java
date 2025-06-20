package network;

import shared.models.SanPham;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.function.Consumer;

public class SocketManager {
    private static SocketManager instance;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private final String host = "localhost";
    private final int port = 8888;
    private Consumer<List<SanPham>> productListListener;
    private volatile boolean isProcessingRequest = false;

    public void setProcessingRequest(boolean processing) {
        this.isProcessingRequest = processing;
    }


    public void setProductListListener(Consumer<List<SanPham>> listener) {
        this.productListListener = listener;
    }


    private SocketManager() throws Exception {
        this.connect();
    }

    public static SocketManager getInstance() throws Exception {
        if (instance == null) {
            instance = new SocketManager();
        }

        return instance;
    }

    private void connect() throws Exception {
        socket = new Socket(host, port);
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectOutputStream getOutputStream() {
        return oos;
    }

    public ObjectInputStream getInputStream() {
        return ois;
    }

    public boolean isConnected() {
        return socket != null && !socket.isClosed();
    }

    public void close() {
        try {
            if (ois != null) {
                ois.close();
            }

            if (oos != null) {
                oos.close();
            }

            if (socket != null && !this.socket.isClosed()) {
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void send(Object request) throws IOException {
        if (oos == null) throw new IOException("Output stream is null.");
        synchronized (oos) {
            oos.reset();
            oos.writeObject(request);
            oos.flush();
        }
    }


    public Object receive() throws IOException, ClassNotFoundException {
        synchronized (ois) {
            return ois.readObject();
        }
    }

    public void startListening() {
        new Thread(() -> {
            while (socket != null && !socket.isClosed()) {
                try {
                    if (!isProcessingRequest) {
                        Object obj;
                        synchronized (ois) {
                            obj = ois.readObject();
                        }

                        if (obj instanceof List<?> list && !list.isEmpty() && list.get(0) instanceof SanPham) {
                            if (productListListener != null) {
                                SwingUtilities.invokeLater(() ->
                                        productListListener.accept((List<SanPham>) list)
                                );
                            }
                        }
                    } else {
                        Thread.sleep(100);
                    }
                } catch (SocketException | StreamCorruptedException e) {
                    System.out.println("Kết nối bị mất, cần reconnect...");
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }).start();
    }

}