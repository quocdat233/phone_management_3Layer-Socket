package server;
import server.DAO.SanPhamDAO;
import server.handler.ProductHandler;

import shared.models.SanPham;
import shared.request.AddFullProductRequest;
import shared.request.DeleteProductRequest;
import shared.request.EditCauhinhRequest;
import shared.request.EditSanPhamRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class Server {
    private static final List<ObjectOutputStream> clients = Collections.synchronizedList(new ArrayList());
    private static final SanPhamDAO dao = new SanPhamDAO();

    public Server() {
    }

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(8888)) {
            System.out.println("Server is running...");

            while (true) {
                try {
                    Socket socket = server.accept();
                    handleClient(socket);
                } catch (IOException e) {
                    System.err.println("Error accepting client connection: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Could not start server: " + e.getMessage());
        }
    }

    private static void handleClient(Socket socket) {
        new Thread(() -> {
            try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

                ProductHandler.registerClient(oos);
                ProductHandler.sendAllProducts(oos);

                while (true) {
                    try {
                        Object obj = ois.readObject();
                        if (obj instanceof AddFullProductRequest req) {
                            ProductHandler.handleAddFullProduct(req, oos);
                        } else if (obj instanceof DeleteProductRequest request) {
                            ProductHandler.handleDeleteProduct(request, oos);
                        }
                        else if (obj instanceof EditSanPhamRequest req) {
                            ProductHandler.handleEditSanPham(req, oos);
                        } else if (obj instanceof EditCauhinhRequest req) {
                            ProductHandler.handleEditCauHinh(req, oos);
                        }

                    } catch (ClassNotFoundException | IOException e) {
                        System.err.println("Lỗi xử lý yêu cầu từ client " + e.getMessage());
                        break;
                    }
                }
            } catch (IOException e) {
                System.err.println("Lỗi thiết lập luồng dữ liệu với client: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Lỗi đóng kết nối socket với client: " + e.getMessage());
                }
            }
        }).start();
    }}