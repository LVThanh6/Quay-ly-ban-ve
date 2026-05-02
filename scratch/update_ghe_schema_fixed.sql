USE QuanLyRapPhim;
GO

-- 1. Xóa cột nếu đã tạo sai (để tạo lại cho chuẩn)
IF EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Ghe') AND name = 'MaPhongChieu')
BEGIN
    ALTER TABLE Ghe DROP COLUMN MaPhongChieu;
END

-- 2. Thêm các cột chuẩn
ALTER TABLE Ghe ADD MaPhongChieu varchar(20);
ALTER TABLE Ghe ADD CONSTRAINT FK_Ghe_PhongChieu FOREIGN KEY (MaPhongChieu) REFERENCES PhongChieu(MaPhongChieu);

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Ghe') AND name = 'HangGhe')
BEGIN
    ALTER TABLE Ghe ADD HangGhe varchar(5);
END

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Ghe') AND name = 'CotGhe')
BEGIN
    ALTER TABLE Ghe ADD CotGhe int;
END

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Ghe') AND name = 'TrangThai')
BEGIN
    ALTER TABLE Ghe ADD TrangThai nvarchar(50) DEFAULT N'Trống';
END

GO
