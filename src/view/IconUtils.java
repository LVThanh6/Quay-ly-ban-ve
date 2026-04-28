package view;

import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.image.BufferedImage;

public class IconUtils {

    // --- Biểu tượng cho Nút Bấm ---

    public static ImageIcon getAddIcon() {
        return createIcon(20, 20, (g) -> {
            g.setColor(new Color(40, 167, 69)); // Green
            g.fillOval(0, 0, 20, 20);
            g.setColor(Color.WHITE);
            g.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.drawLine(10, 5, 10, 15);
            g.drawLine(5, 10, 15, 10);
        });
    }

    public static ImageIcon getEditIcon() {
        return createIcon(20, 20, (g) -> {
            g.setColor(new Color(255, 193, 7)); // Yellow
            g.fillOval(0, 0, 20, 20);
            g.setColor(Color.WHITE);
            g.setStroke(new BasicStroke(2f));
            int[] xPoints = { 6, 12, 14, 8 };
            int[] yPoints = { 14, 8, 10, 16 };
            g.fillPolygon(xPoints, yPoints, 4);
            g.drawLine(6, 14, 5, 16);
            g.drawLine(5, 16, 7, 15);
        });
    }

    public static ImageIcon getDeleteIcon() {
        return createIcon(20, 20, (g) -> {
            g.setColor(new Color(220, 53, 69)); // Red
            g.fillOval(0, 0, 20, 20);
            g.setColor(Color.WHITE);
            g.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.drawLine(6, 6, 14, 14);
            g.drawLine(14, 6, 6, 14);
        });
    }

    public static ImageIcon getClearIcon() {
        return createIcon(20, 20, (g) -> {
            g.setColor(new Color(23, 162, 184)); // Cyan
            g.fillOval(0, 0, 20, 20);
            g.setColor(Color.WHITE);
            g.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.drawArc(4, 4, 12, 12, 45, 270);
            g.fillPolygon(new int[] { 13, 17, 15 }, new int[] { 5, 5, 1 }, 3);
        });
    }

    // --- Biểu tượng cho Menu ---

    public static ImageIcon getSystemIcon() {
        return createIcon(16, 16, (g) -> {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(2, 2, 12, 12);
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(4, 4, 8, 8);
        });
    }

    public static ImageIcon getFilmIcon() {
        return createIcon(16, 16, (g) -> {
            g.setColor(new Color(100, 100, 255));
            g.fillRect(2, 2, 12, 12);
            g.setColor(Color.WHITE);
            g.fillRect(3, 3, 2, 2);
            g.fillRect(3, 11, 2, 2);
            g.fillRect(11, 3, 2, 2);
            g.fillRect(11, 11, 2, 2);
        });
    }

    public static ImageIcon getCartIcon() {
        return createIcon(16, 16, (g) -> {
            g.setColor(new Color(255, 140, 0));
            g.setStroke(new BasicStroke(2f));
            g.drawLine(1, 2, 4, 2);
            g.drawLine(4, 2, 6, 10);
            g.drawLine(6, 10, 14, 10);
            g.drawLine(14, 10, 15, 4);
            g.drawLine(4, 4, 15, 4);
            g.fillOval(6, 12, 3, 3);
            g.fillOval(12, 12, 3, 3);
        });
    }

    public static ImageIcon getCategoryIcon() {
        return createIcon(16, 16, (g) -> {
            g.setColor(new Color(34, 139, 34));
            g.fillRect(2, 2, 5, 5);
            g.fillRect(9, 2, 5, 5);
            g.fillRect(2, 9, 5, 5);
            g.fillRect(9, 9, 5, 5);
        });
    }

    public static ImageIcon getSettingsIcon() {
        return createIcon(16, 16, (g) -> {
            g.setColor(Color.GRAY);
            g.fillOval(2, 2, 12, 12);
            g.setColor(Color.WHITE);
            g.fillOval(6, 6, 4, 4);
        });
    }

    // Helper tạo ảnh
    private interface IconPainter {
        void paint(Graphics2D g);
    }

    private static ImageIcon createIcon(int width, int height, IconPainter painter) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        painter.paint(g2d);
        g2d.dispose();
        return new ImageIcon(img);
    }
}
