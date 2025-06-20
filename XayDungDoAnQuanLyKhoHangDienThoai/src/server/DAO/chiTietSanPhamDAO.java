package server.DAO;


import dataBase.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class chiTietSanPhamDAO {
    public static boolean insertImei(int maPhienBanSP, String imei, int maphieunhap) {
        String sql = "INSERT INTO ctsanpham (maimei, maphienbansp, tinhtrang, maphieunhap) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnected();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, imei);
            ps.setInt(2, maPhienBanSP);
            ps.setInt(3, 1);
            ps.setInt(4, maphieunhap);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updatePhieuXuat(String imei, int maPhieuXuat) {
        String sql = "UPDATE ctsanpham SET maphieuxuat = ?, tinhtrang = 0 WHERE maimei = ?";
        try (Connection conn = Database.getConnected();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maPhieuXuat);
            ps.setString(2, imei);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static List<String> getImeiByImportId(int maphieunhap) {
        List<String> imeiList = new ArrayList<>();
        String sql = "SELECT maimei FROM ctsanpham WHERE maphieunhap = ?";
        try (Connection conn = Database.getConnected();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maphieunhap);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                imeiList.add(rs.getString("maimei"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imeiList;
    }

    public static List<String> getImeiByExportId(int maphieuxuat) {
        List<String> imeiList = new ArrayList<>();
        String sql = "SELECT maimei FROM ctsanpham WHERE maphieuxuat = ?";
        try (Connection conn = Database.getConnected();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maphieuxuat);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                imeiList.add(rs.getString("maimei"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imeiList;
    }

    public static List<String> getImeiByMaSP(int masp, Integer tinhtrang) {
        List<String> imeiList = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
        SELECT cts.maimei
        FROM ctsanpham cts
        JOIN phienbansanpham pb ON cts.maphienbansp = pb.maphienbansp
        WHERE pb.masp = ?
    """);

        if (tinhtrang != null) {
            sql.append(" AND cts.tinhtrang = ?");
        }

        try (Connection conn = Database.getConnected();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            ps.setInt(1, masp);
            if (tinhtrang != null) {
                ps.setInt(2, tinhtrang);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                imeiList.add(rs.getString("maimei"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imeiList;
    }

    public static boolean isImeiTonTai(String imei) {
        String sql = "SELECT 1 FROM ctsanpham WHERE maimei = ?";
        try (Connection conn = Database.getConnected();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, imei);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }





}
