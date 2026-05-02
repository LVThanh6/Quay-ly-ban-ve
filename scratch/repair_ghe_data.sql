USE QuanLyRapPhim;
GO

-- Cập nhật MaPhongChieu cho bảng Ghe dựa trên dữ liệu từ SuatChieu và TrangThaiGhe
UPDATE G
SET G.MaPhongChieu = SC.MaPhongChieu
FROM Ghe G
JOIN TrangThaiGhe TTG ON G.MaGhe = TTG.MaGhe
JOIN SuatChieu SC ON TTG.MaSuatChieu = SC.MaSuatChieu
WHERE G.MaPhongChieu IS NULL;

-- Cập nhật HangGhe và CotGhe cho bảng Ghe dựa trên tên MaGhe (ví dụ A1 -> Hang A, Cot 1)
-- Chỉ cập nhật nếu HangGhe hoặc CotGhe đang NULL
UPDATE Ghe
SET HangGhe = LEFT(MaGhe, 1),
    CotGhe = CASE 
                WHEN ISNUMERIC(SUBSTRING(MaGhe, 2, LEN(MaGhe))) = 1 
                THEN CAST(SUBSTRING(MaGhe, 2, LEN(MaGhe)) AS INT)
                ELSE NULL 
             END,
    TrangThai = ISNULL(TrangThai, N'Trống')
WHERE HangGhe IS NULL OR CotGhe IS NULL;

GO
