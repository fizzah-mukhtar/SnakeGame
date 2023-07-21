package snakegame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static java.lang.System.exit;
import static java.lang.reflect.Array.set;
import java.util.*;
import javax.swing.*;
public class SNAKE_GAME extends JFrame implements KeyListener,Runnable {
    JPanel p1,p2;
    JButton [] b = new JButton [200];
    JButton bonusfood;
    JTextArea text;
    int x=1000;
    int y=500;
    int gu=2;
    int directionx=1;
    int directiony=0;
    int speed=100;
    int difference=0;
    int oldx=0;
    int oldy=0;
    int score=0;
    int [] bx = new int [600];
    int [] by = new int [600];
    Point [] bp = new Point [600];
    Point bfp = new Point();
    Thread myt;
    boolean food = false;
    boolean runl = false;
    boolean runr = true;
    boolean runu = true;
    boolean rund = true;
    boolean bonusflag = true;
    Random r = new Random();
    JMenuBar mymenubar;
    JMenu game;
    JMenu help;
    JMenu level;
    private int exit;
    private String i0;
    public void InitializeValue()
    {
        gu = 3;
        bx[0] = 200;
        by[0] = 300;
        directionx = 10;
        directiony = 0;
        score = 0;
        food = false;
        runl = false;
        runr = true;
        runu = true;
        rund = true;
        bonusflag = true;
    }
    SNAKE_GAME()
    {
        super("SNAKE");
        setSize(1000,600);
        createbar();
        InitializeValue();
        p1 = new JPanel();
        p2 = new JPanel();
        text = new JTextArea("score:" + score);
        text.setEnabled(false);
        text.setBackground(Color.LIGHT_GRAY);
        bonusfood = new JButton();
        bonusfood.setEnabled(false);
        createFirstSnake();
        p1.setLayout(null);
        p2.setLayout(new GridLayout(0,1));
        p1.setBounds(0, 0, x, y);
        p1.setBackground(Color.BLUE);
         p2.setBounds(0, y, x, 30);
        p2.setBackground(Color.RED);
        p2.add(text);
        getContentPane().setLayout(null);
        getContentPane().add(p1);
        getContentPane().add(p2);
        show();
        setDefaultCloseOperation(exit);
         addKeyListener((KeyListener) this);
         myt = new Thread(this);
         myt.start();       
    }

    private void createFirstSnake() {
        for(int i=0;i<3;i++)
        {
       b[i] = new JButton("b" + i0);
       b[i].setEnabled(false);
       p1.add(b[i]);
       b[i].setBounds(bx[i], by[i], 10, 10);
       bx[i+1] = bx[i] - 10;
       by[i+1] = by[i];
        }
    }
    public void createbar()
            
    {
       mymenubar = new JMenuBar();
       game = new JMenu("GAME");
       JMenuItem newgame = new JMenuItem("new game");
       JMenuItem exit = new JMenuItem("exit");
       newgame.addActionListener(new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent e)
           {
               reset();
           }
       });
       
        exit.addActionListener(new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent e)
           {
               System.exit(0);
           }
       });
        
        game.add(newgame);
        game.addSeparator();
        game.add(exit);
        mymenubar.add(game);
        level = new JMenu("level");
        help = new JMenu("help");
        JMenuItem creator = new JMenuItem("creator");
        JMenuItem instruction = new JMenuItem("instruction");
        creator.addActionListener(new ActionListener()
        {
            @Override
             public void actionPerformed(ActionEvent e)
           {
               JOptionPane.showMessageDialog(p2, "Name" + ": Fizzah ");
           }
       });
        
         instruction.addActionListener(new ActionListener()
        {
            @Override
             public void actionPerformed(ActionEvent e)
           {
               JOptionPane.showMessageDialog(p2, "Use arrows to move the snake in different directions.\n Score is added when the snake eats a food(dot). Each dot consists of 5 points.  ");
           }
       });
        help.add(creator);
         help.add(instruction);
         mymenubar.add(help);
         setJMenuBar(mymenubar);
    }
    void reset()
    {
        InitializeValue();
        p1.removeAll();
        myt.stop();
        createFirstSnake();
        text.setText("score: " + score);
        myt = new Thread(this);
        myt.start();
    }
    
    void growup()
    {
        b[gu] = new JButton();
        b[gu].setEnabled(false);
        p1.add(b[gu]);
        int a = 10+(10*r.nextInt(48));
        int c= 10+(10*r.nextInt(32));
         bx[gu] = a;
         by[gu] = c;
         b[gu].setBounds(a,c,10,10);
         gu++;
    }
    
    void moveForward()
    {
        for (int i=0;i<gu;i++)
        {
            bp[i] = b[i].getLocation();
        }
        bx[0]+= directionx;
        by[0]+= directiony;
        b[0].setBounds(bx[0], by[0], 10, 10);
        for(int i=1;i<gu;i++)
        {
            b[i].setLocation(bp[i-1]);
        }
        if(bx[0]==x)
        {
            bx[0]=10;
        }
        else if(bx[0] == 0)
        {
            bx[0] = x-10;
        }
        else if(by[0] == y)
        {
            by[0] = 10;
        }
        else if(by[0] == 0)
        {
            by[0] = y - 10;
        }
        if(bx[0] == bx[gu-1] && by[0] == by[gu-1])
        {
            food = false;
            score+= 5;
            text.setText("score: " + score);
            if(score%50 == 0 && bonusflag == true)
            {
                p1.add(bonusfood);
                bonusfood.setBounds((10*r.nextInt(50)),(10*r.nextInt(25)),15,15);
                bfp = bonusfood.getLocation();
                bonusflag = false;
            }
        }
        if (bonusflag == false)
        {
            if(bfp.x<=bx[0] && bfp.y<=by[0] && bfp.x+10>=bx[0] && bfp.y+10>=by[0])
            {
                p1.remove(bonusfood);
                score+= 100;
                text.setText("score: " + score);
                bonusflag = true;
            }
        }
        
        if (food == false)
        {
            growup();
            food = true;
            
        }
        else
        {
            b[gu-1].setBounds(bx[gu-1], by[gu-1], 10, 10);
        }
        
        for (int i =1; i<gu; i++)
        {
            if(bp[0] == bp[i])
            {
                text.setText("Game Over " + score);
                try
                {
                    myt.join();
                }
                catch(InterruptedException ie)
                {
                    break;
                }
            }
            
            p1.repaint();
            show();
        }
    }
        public void keyPressed(KeyEvent e) {
       
            //snake moves left when player pressed left arrow
            if(runl==true&&e.getKeyCode()==37)
            {
                directionx= -10;
                directiony= 0;
                runr = false;
                runu = true;
                rund = true;
            }
            //snake moves up when player pressed up arrow
             if(runu==true&&e.getKeyCode()==38)
            {
                directionx= 0;
                directiony= -10;
                runr = true;
                runl = true;
                rund = false;
            }
             //snake moves right when player pressed right arrow
             if(runr==true&&e.getKeyCode()==39)
            {
                directionx= +10;
                directiony= 0;
                runu = true;
                rund = true;
                runl = false;
            }
             //snake moves down when player pressed down arrow
             if(rund==true&&e.getKeyCode()==40)
            {
                directiony= +10;
                directionx= 0;
                runr = true;
                runl = true;
                runu = false;
            } 
        }
public void keyReleased(KeyEvent e){   }
public void keyTyped(KeyEvent e){}
public void run()
{
    for(;;)
    {
        moveForward();
        try
        {
            Thread.sleep(speed);
        }
        catch(InterruptedException ie)
        {
            
        }
    }
}
}

package snakegame;

public class home extends javax.swing.JFrame {

    public home() {
        initComponents();
    }
    @SuppressWarnings("unchecked")

    private void PLAY_GAMEActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        SNAKE_GAME s = new SNAKE_GAME();
        s.setVisible(true);
    }                                         

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new home().setVisible(true);
            }
        });
    }

 

 

