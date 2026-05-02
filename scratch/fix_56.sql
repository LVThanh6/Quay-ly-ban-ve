USE QuanLyRapPhim;
GO
UPDATE Ghe 
SET HangGhe = SUBSTRING(MaGhe, 4, 1), 
    CotGhe = CAST(SUBSTRING(MaGhe, 5, 2) AS INT) 
WHERE MaPhongChieu IN ('ROOM_05', 'ROOM_06') AND HangGhe IS NULL;
GO
