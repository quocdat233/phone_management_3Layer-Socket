package server.handler;


import server.DAO.SanPhamDAO;
import server.DAO.cauHinhDAO;
import shared.models.SanPham;
import shared.request.AddFullProductRequest;
import shared.request.DeleteProductRequest;
import shared.request.EditCauhinhRequest;
import shared.request.EditSanPhamRequest;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

public class ProductHandler {
    private static final List<ObjectOutputStream> clients = Collections.synchronizedList(new ArrayList<>());
    private static final SanPhamDAO dao = new SanPhamDAO();

    public static void registerClient(ObjectOutputStream oos) {
        synchronized (clients) {
            if (!clients.contains(oos)) {
                clients.add(oos);
            }
        }
    }


    public static void sendAllProducts(ObjectOutputStream oos) {
        try {
            oos.reset();
            oos.writeObject(dao.getAllSanPham());
            oos.flush();
        } catch (IOException e) {
            System.err.println("Error sending products to client: " + e.getMessage());
        }
    }

    public static synchronized void handleAddFullProduct(AddFullProductRequest request, ObjectOutputStream oos) {
        try {
            int masp = dao.themSanPham(request.getSanPham());
            cauHinhDAO.themCauHinh(request.getCauHinh(), masp);

            // Gửi phản hồi thành công
            oos.reset();
            oos.writeObject("success");
            oos.flush();

            new Thread(() -> {
                try {
                    Thread.sleep(100);
                    broadcastProducts();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        } catch (Exception ex) {
            try {
                oos.reset();
                oos.writeObject("fail: " + ex.getMessage());
                oos.flush();
            } catch (IOException e) {
                System.err.println("Error sending fail message: " + e.getMessage());
            }
        }}



    public static synchronized void handleDeleteProduct(DeleteProductRequest request, ObjectOutputStream oos) {
        try {
            dao.xoaSanPham(request.getProductId());

            oos.reset();
            oos.writeObject("success");
            oos.flush();

            broadcastProducts();

        } catch (Exception ex) {
            try {
                oos.reset();
                oos.writeObject("fail: " + ex.getMessage());
                oos.flush();
            } catch (IOException e) {
                System.err.println("Error sending fail message: " + e.getMessage());
            }
        }
    }
    public static synchronized void handleEditSanPham(EditSanPhamRequest request, ObjectOutputStream oos){
        try {
            dao.suaSanPham(request.getSanPham(), request.getProductID());

            oos.reset();
            oos.writeObject("success");
            oos.flush();

            new Thread(() -> {
                try {
                    Thread.sleep(100);
                    broadcastProducts();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        } catch (Exception ex) {
            try {
                oos.reset();
                oos.writeObject("fail: " + ex.getMessage());
                oos.flush();
            } catch (IOException e) {
                System.err.println("Error sending fail message: " + e.getMessage());
            }
        }}

    public static synchronized void handleEditCauHinh(EditCauhinhRequest request, ObjectOutputStream oos) {
        try {
            cauHinhDAO.suaCauHinh(request.getCauHinh(), request.getProductId());
            oos.reset();
            oos.writeObject("success");
            oos.flush();

            new Thread(() -> {
                try {
                    Thread.sleep(100);
                    broadcastProducts();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        } catch (Exception ex) {
            try {
                oos.reset();
                oos.writeObject("fail: " + ex.getMessage());
                oos.flush();
            } catch (IOException e) {
                System.err.println("Error sending fail message: " + e.getMessage());
            }}
    }




    private static synchronized void broadcastProducts() {
        List<SanPham> list;
        try {
            list = dao.getAllSanPham();
        } catch (Exception e) {
            System.err.println("Failed to get product list: " + e.getMessage());
            return;
        }

        synchronized (clients) {
            Iterator<ObjectOutputStream> iterator = clients.iterator();
            while (iterator.hasNext()) {
                ObjectOutputStream oos = iterator.next();
                try {
                    oos.writeObject(list);
                    oos.flush();
                } catch (IOException e) {
                    System.err.println("Error broadcasting to client, removing: " + e.getMessage());
                    iterator.remove();
                    try {
                        oos.close();
                    } catch (IOException ex) {
                        System.err.println("Error closing client stream: " + ex.getMessage());
                    }
                }
            }
        }
    }

}
