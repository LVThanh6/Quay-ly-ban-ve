USE QuanLyRapPhim;
GO

-- 1. Xóa các tài khoản cũ nếu trùng tên để cập nhật mới
DELETE FROM NhanVien WHERE MaNhanVien IN ('admin', 'nhanvien', 'staff');

-- 2. Thêm tài khoản Quản lý (Admin)
INSERT INTO NhanVien (MaNhanVien, HoTen, MatKhau, SDT, LuongCoBan, VaiTro)
VALUES ('admin', N'Quản Lý Hệ Thống', 'admin', '0911111111', 25000000, N'Quản Lý');

-- 3. Thêm tài khoản Nhân viên
INSERT INTO NhanVien (MaNhanVien, HoTen, MatKhau, SDT, LuongCoBan, VaiTro)
VALUES ('nhanvien', N'Nhân Viên Bán Vé', 'nhanvien', '0922222222', 12000000, N'Nhân Viên');

GO
