import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import sweeper.Box;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

public class JavaSweeper extends JFrame {
  private Game game;

  private JPanel panel;
  private JLabel label;
  private final int COLS = 9;
  private final  int ROWS = 9;
  private final  int BOMBS = 10;
  private final int IMAGE_SIZE = 50;

  public static void main(String[] args) {
    new JavaSweeper();
  }

  private JavaSweeper(){
    game = new Game(COLS,ROWS,BOMBS);
    game.start();
    setImages();
    initLabel();
    initPanel();
    initFrame();
  }

  private void initLabel(){
    label = new JLabel("Welcome");
    add(label, BorderLayout.SOUTH);
  }

  private void initPanel(){
    panel = new JPanel(){
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Coord coord: Ranges.getAllCoords()) {
          g.drawImage((Image) game.getBox(coord).image, coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);
        }
      }
    };

    panel.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        int x = e.getX() /IMAGE_SIZE;
        int y = e.getY() / IMAGE_SIZE;
        Coord coord = new Coord(x,y);
        if(e.getButton() == MouseEvent.BUTTON1)
          game.pressLeftButton(coord);
        if(e.getButton() == MouseEvent.BUTTON3)
          game.pressRightButton(coord);
        if(e.getButton() == MouseEvent.BUTTON2)
          game.start();
        label.setText(getMessage());
        panel.repaint();
      }
    });

    panel.setPreferredSize(new Dimension(
        Ranges.getSize().x * IMAGE_SIZE,
        Ranges.getSize().y * IMAGE_SIZE));
    add(panel);
  }

  private String getMessage() {
    switch (game.getState()){
      case PLAYED: return "Think twice!";
      case BOMBED: return "YOU LOSE: BIG BA-DA-BOOM!";
      case WINNER: return "CONGRATULATIONS, PHIL CHAMPION!!!";
      default: return "Welcome!";
    }
  }

  private void initFrame(){

    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setTitle("JavaSweeper");

    setResizable(false);
    setVisible(true);
    setIconImage(getImage("icon"));
    pack();
    setLocationRelativeTo(null);
  }

  private void  setImages(){
    for (Box box: Box.values()){
      box.image = getImage(box.name().toLowerCase());
    }
  }

  private Image getImage(String name){
    String filename = "img/" + name + ".png";
    ImageIcon icon = new ImageIcon(getClass().getResource(filename));
    return icon.getImage();
  }
}
