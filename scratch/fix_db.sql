USE QuanLyRapPhim;
GO

-- 1. Create missing ChiTietPhieuDat table if not exists
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ChiTietPhieuDat]') AND type in (N'U'))
BEGIN
    CREATE TABLE ChiTietPhieuDat (
        MaChiTietPhieuDat VARCHAR(20) PRIMARY KEY,
        MaPhieuDat VARCHAR(20) NOT NULL,
        MaVe VARCHAR(20) NOT NULL,
        GiaTamTinh FLOAT,
        FOREIGN KEY (MaPhieuDat) REFERENCES PhieuDatVe(MaPhieuDat),
        FOREIGN KEY (MaVe) REFERENCES Ve(MaSanPham)
    );
END
GO

-- 2. Add mock accounts to NhanVien table to avoid FK conflicts during testing
IF NOT EXISTS (SELECT 1 FROM NhanVien WHERE MaNhanVien = 'admin')
BEGIN
    INSERT INTO NhanVien (MaNhanVien, HoTen, MatKhau, SDT, LuongCoBan, VaiTro)
    VALUES ('admin', N'Quản Lý Admin', 'admin', '0123456789', 20000000, N'Quản Lý');
END

IF NOT EXISTS (SELECT 1 FROM NhanVien WHERE MaNhanVien = 'staff')
BEGIN
    INSERT INTO NhanVien (MaNhanVien, HoTen, MatKhau, SDT, LuongCoBan, VaiTro)
    VALUES ('staff', N'Nhân Viên Bán Vé', 'staff', '0987654321', 10000000, N'Nhân Viên');
END
GO
