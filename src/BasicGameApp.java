import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.ArrayList;

public class BasicGameApp implements Runnable, MouseListener, MouseMotionListener, MouseWheelListener {

    final int WIDTH = 1000;
    final int HEIGHT = 700;

    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;
    public BufferStrategy bufferStrategy;

    public Image background;

    // Zoom + Pan
    double scale = 1.0;
    double offsetX = 0;
    double offsetY = 0;
    Point lastMouse;

    // Map data
    ArrayList<MapArea> areas = new ArrayList<>();
    String currentInfo = "Click a building to see info";

    public static void main(String[] args) {
        BasicGameApp app = new BasicGameApp();
        new Thread(app).start();
    }

    public BasicGameApp() {
        setUpGraphics();

        background = Toolkit.getDefaultToolkit().getImage("campus_map.png");

        // Define clickable buildings (adjust to your map!)
        areas.add(new MapArea(100, 150, 120, 100,
                "Science Building\nDept: Science\nTeachers: Smith, Lee\nClasses: Bio, Chem"));

        areas.add(new MapArea(300, 200, 150, 120,
                "Math Building\nDept: Math\nTeacher: Johnson\nClasses: Algebra, Calc"));

        areas.add(new MapArea(600, 300, 140, 110,
                "English Building\nDept: English\nTeacher: Davis\nClasses: Lit, Writing"));

        areas.add(new MapArea(450, 100, 130, 90,
                "Main Office\nAdministration"));
    }

    public void run() {
        while (true) {
            render();
            pause(20);
        }
    }

    public void pause(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {}
    }

    private void setUpGraphics() {
        frame = new JFrame("Campus Map");

        panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.setLayout(null);

        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
        canvas.addMouseWheelListener(this);

        panel.add(canvas);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
    }

    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();

        g.clearRect(0, 0, WIDTH, HEIGHT);

        // Apply zoom + pan
        g.translate(offsetX, offsetY);
        g.scale(scale, scale);

        // Draw map
        g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);

        // Draw clickable zones (DEBUG - remove later if you want)
        g.setColor(Color.RED);
        for (MapArea area : areas) {
            g.drawRect(area.rect.x, area.rect.y, area.rect.width, area.rect.height);
        }

        // Reset transform for UI
        g.setTransform(new AffineTransform());

        // Info panel
        g.setColor(Color.BLACK);
        g.fillRect(10, 1300, 980, 130);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 25));
        drawMultilineString(g, currentInfo, 20, 1320);

        g.dispose();
        bufferStrategy.show();
    }

    public void drawMultilineString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n")) {
            g.drawString(line, x, y);
            y += 20;
        }
    }

    // =========================
    // MOUSE CONTROLS
    // =========================

    @Override
    public void mousePressed(MouseEvent e) {
        lastMouse = e.getPoint();

        // Convert click to map coordinates
        int mapX = (int)((e.getX() - offsetX) / scale);
        int mapY = (int)((e.getY() - offsetY) / scale);

        for (MapArea area : areas) {
            if (area.rect.contains(mapX, mapY)) {
                currentInfo = area.info;
                return;
            }
        }

        currentInfo = "No building selected";
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int dx = e.getX() - lastMouse.x;
        int dy = e.getY() - lastMouse.y;

        offsetX += dx;
        offsetY += dy;

        lastMouse = e.getPoint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        double zoomFactor = 1.1;

        if (e.getWheelRotation() < 0) {
            scale *= zoomFactor; // zoom in
        } else {
            scale /= zoomFactor; // zoom out
        }

        // Optional: clamp zoom
        scale = Math.max(0.5, Math.min(scale, 3.0));
    }

    // Unused mouse methods
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}

    // =========================
    // MAP AREA CLASS
    // =========================
    class MapArea {
        Rectangle rect;
        String info;

        public MapArea(int x, int y, int w, int h, String info) {
            rect = new Rectangle(x, y, w, h);
            this.info = info;
        }
    }
}