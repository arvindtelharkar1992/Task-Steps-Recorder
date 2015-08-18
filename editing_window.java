/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test_package;

import com.sun.speech.freetts.*;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.AudioPlayer;
import com.sun.speech.freetts.audio.SingleFileAudioPlayer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFileFormat.Type;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import net.java.balloontip.BalloonTip;
import net.java.balloontip.CustomBalloonTip;
import net.java.balloontip.styles.ModernBalloonStyle;
import net.java.balloontip.utils.*;
import java.io.*;

/**
 *
 * @author Arvind
 */
public class editing_window extends javax.swing.JFrame implements ActionListener,MouseListener,MouseMotionListener {

    /**
     * Creates new form editing_window
     */
public static final String Voicename="kevin16";
CustomBalloonTip[] b;
public static String speak_text;
public static int b_index;
public static boolean is_mouse_dragged;
private boolean show=false;
public static Color color_chosen=Color.GREEN;
public static String display_image_path;
public static String path_for_audio_file;
public static String original_image_folder;
public static String master_string;
BufferedImage old_image;
ImageIcon icon1,icon1_original;
Image thumb,thumb_original; 
BufferedImage original_image;
         
//Now the variables for Drawing
private int mousex                = 0;
private int mousey                = 0;

/* Previous mouse coordinates */
private int prevx                 = 0;
private int prevy                 = 0;
private boolean initialRect       = true;
private boolean initialOval       = true;
private boolean initialPen     = true;
private boolean initialLine       = true;
 
 /* Main Mouse X and Y coordiante variables */
private int  Orx                  = 0;
private int  Ory                  = 0;
private int  OrWidth              = 0;
private int  OrHeight             = 0;
private int  drawX                = 0;
private int  drawY                = 0;
public int opStatus;     
public static String[] th_temp;
public static String p;
public static JTextField t_balloon;
public static JTextField [] tf;
public static String[] ann;
      
    public editing_window() 
    {
        initComponents();
        b=new CustomBalloonTip[30]; 
        b_index=0;
        th_temp=new String[4];
        speak_text=null;
        master_string=null;
    }
    
    public void read_thumbnail_file(String path_of_thumbnail)
    {
       FileReader f_reader;
       int i;
		try {
			f_reader = new FileReader(path_of_thumbnail);
			BufferedReader b_reader= new BufferedReader(f_reader);
                        for(i=0;i<th_temp.length;i++)
                        {
		        th_temp[i]=b_reader.readLine();
                        }
	            }
                catch (Exception ex)
                {
                    System.out.println("Error in reading File!!");
                }
    }
    
    public void set_thumbnail_images()
    {
        BufferedImage bimage0, bimage1, bimage2, bimage3;
        Image thumb0,thumb1,thumb2,thumb3;
        ImageIcon icon_zero,icon_one,icon_two,icon_three;
        File file_zero,file_one,file_two,file_three;
        
     //set the image for thumbnail0
        try
       {
         file_zero=new File(th_temp[0]);
         bimage0=ImageIO.read(file_zero);
         thumb0=bimage0.getScaledInstance(thumbnail0.getWidth(),thumbnail0.getHeight(),Image.SCALE_SMOOTH);
         icon_zero=new ImageIcon(thumb0);
         thumbnail0.setIcon(icon_zero);
        }
        catch(Exception exception0)
        {
            thumbnail0.setSize(new Dimension(14,14));
            thumbnail0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proj images/squares/black_square.png"));
        }
        //set thumnnail1
        try
        {
         file_one=new File(th_temp[1]);
         bimage1=ImageIO.read(file_one);
         thumb1=bimage1.getScaledInstance(thumbnail1.getWidth(),thumbnail1.getHeight(),Image.SCALE_SMOOTH);
         icon_one=new ImageIcon(thumb1);
         thumbnail1.setIcon(icon_one);
        }
        catch(Exception exception1)
        {
            thumbnail1.setSize(new Dimension(14,14));
            thumbnail1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proj images/squares/black_square.png")));
        }
        
        //set thumbnail 2
        
         try{
         file_two=new File(th_temp[2]);
         bimage2=ImageIO.read(file_two);
         thumb2=bimage2.getScaledInstance(thumbnail2.getWidth(),thumbnail2.getHeight(),Image.SCALE_SMOOTH);
         icon_two=new ImageIcon(thumb2);
         thumbnail2.setIcon(icon_two);
        }
        catch(Exception exception2)
        {
            thumbnail2.setSize(new Dimension(14,14));
            thumbnail2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proj images/squares/black_square.png")));
        }
         
        //set thumbnail 3
          try{
         file_three=new File(th_temp[3]);
         bimage3=ImageIO.read(file_three);
         thumb3=bimage3.getScaledInstance(thumbnail3.getWidth(),thumbnail3.getHeight(),Image.SCALE_SMOOTH);
         icon_three=new ImageIcon(thumb3);
         thumbnail3.setIcon(icon_three);
        }
        catch(Exception exception3)
        {
            thumbnail3.setSize(new Dimension(14,14));
            thumbnail3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proj images/squares/black_square.png")));
        }
    }
   
    public void clearPanel(){
    Graphics g = jp.getGraphics();
    g.setColor(jp.getBackground());
    g.fillRect(0,0,jp.getBounds().width,jp.getBounds().height);
    g.dispose();
  }

    public void draw_balloon_on_MouseHover(String s,JComponent J){
        
        ModernBalloonStyle modern_style =new ModernBalloonStyle(10,10,Color.RED,Color.YELLOW,Color.GREEN);
                   modern_style.setCornerStyles(true,true,true,true);
                           
        JLabel msg =new JLabel(s);
        final BalloonTip b_tip_rect = new BalloonTip(
					J,
					msg,
					modern_style,
					BalloonTip.Orientation.LEFT_BELOW,
					BalloonTip.AttachLocation.SOUTHWEST,
					10,10,
					true
				);
        b_tip_rect.setCloseButton(null,true);
        b_tip_rect.setOpacity(0.9f);
        TimingUtils.showTimedBalloon(b_tip_rect,1000);
    }
    
    public String return_master_string_for_screenshot()
    {
        return master_string;
    }
    
    public void compose_master_string()
    {
     int i;
     master_string=null;
     
     if(b_index==0) //No annotation..all balloons are empty
       {
         master_string=null;
       }
             
     else if(b_index==1) //Just one annotation String...only 1 balloon
     {
        master_string=ann[0];
     }
             
     else  //more than one Balloon
     {
     
      for(i=0;i<b_index;i++)
        {
           
            if(i==0)
            {
                master_string=ann[i];
            }
            
            else if(i==b_index-1)
            {
                master_string=master_string + ann[i];
            }
            
            else
            {
                master_string=master_string + "," + ann[i] + ",";           
            }
            
            
        }//end for
      
     }//end else
     System.out.println(master_string);
    }
    public void convert_text_to_speech()
    {
        int i;
        Voice voice;
        AudioPlayer audioPlayer = null;
        
        VoiceManager vm=VoiceManager.getInstance();
        
        voice=vm.getVoice(Voicename);
        
        voice.allocate();
        
        audioPlayer = new SingleFileAudioPlayer(path_for_audio_file,Type.WAVE); 
        voice.setAudioPlayer(audioPlayer);
         if(b_index>0) //if at all annotation is present then do the voice conversion
       {
         //Initialise the arrays for textboxes and Strings 
        tf=new JTextField[b_index]; 
        ann=new String[b_index];
   
        //get the annotations from the ballooned textboxes in a String array
        for(i=0;i<b_index;i++)
        {
            tf[i]=(JTextField) b[i].getContents();
            ann[i]=tf[i].getText();
        }
       
        for(i=0;i<b_index;i++)
        {
            try
        {
            voice.speak(ann[i]);
        }
        catch(Exception es)
        {
            System.out.println("Cannot speak!!");
        }
        }
        
        voice.deallocate();
        audioPlayer.close();
       }//end if
         
    else
        {
         try
        {
            voice.speak("No Annotation Provided");
        }
        catch(Exception es)
        {
            System.out.println("Cannot speak!!");
        }
            voice.deallocate();
            audioPlayer.close();  
        }//end else
    }
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jp = new javax.swing.JPanel();
        display_label = new javax.swing.JLabel();
        rect_draw = new javax.swing.JButton();
        Save = new javax.swing.JButton();
        circle_draw = new javax.swing.JButton();
        pen = new javax.swing.JButton();
        restore = new javax.swing.JButton();
        cyan = new javax.swing.JButton();
        green = new javax.swing.JButton();
        yellow = new javax.swing.JButton();
        blue = new javax.swing.JButton();
        magenta = new javax.swing.JButton();
        orange = new javax.swing.JButton();
        grey = new javax.swing.JButton();
        black = new javax.swing.JButton();
        red = new javax.swing.JButton();
        white = new javax.swing.JButton();
        th3 = new javax.swing.JLabel();
        thumbnail0 = new javax.swing.JLabel();
        thumbnail1 = new javax.swing.JLabel();
        thumbnail2 = new javax.swing.JLabel();
        thumbnail3 = new javax.swing.JLabel();
        line = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit the Screenshot");
        setResizable(false);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                remove_file_in_O_Images_folder(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                show_image(evt);
                show_the_thumbnail_images(evt);
            }
        });

        display_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                mouse_released_onLabel(evt);
            }
        });
        display_label.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                mouse_dragged_onLabel(evt);
            }
        });

        javax.swing.GroupLayout jpLayout = new javax.swing.GroupLayout(jp);
        jp.setLayout(jpLayout);
        jpLayout.setHorizontalGroup(
            jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(display_label, javax.swing.GroupLayout.DEFAULT_SIZE, 709, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpLayout.setVerticalGroup(
            jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(display_label, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
                .addContainerGap())
        );

        rect_draw.setIcon(new javax.swing.ImageIcon(getClass().getResource("/final_images/rect grey 50 50.png"))); // NOI18N
        rect_draw.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                rect_draw_mouse_entered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                rect_draw_mouseExited(evt);
            }
        });
        rect_draw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rect_drawActionPerformed(evt);
            }
        });

        Save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/final_images/save 50 50.png"))); // NOI18N
        Save.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                save_mouse_entered(evt);
            }
        });
        Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveActionPerformed(evt);
            }
        });

        circle_draw.setBackground(new java.awt.Color(255, 0, 0));
        circle_draw.setIcon(new javax.swing.ImageIcon(getClass().getResource("/final_images/1111.png"))); // NOI18N
        circle_draw.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                circle_draw_mouseEntered(evt);
            }
        });
        circle_draw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                circle_drawActionPerformed(evt);
            }
        });

        pen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/final_images/freeehand 50 50.png"))); // NOI18N
        pen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pen_mouse_Entered(evt);
            }
        });
        pen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                penActionPerformed(evt);
            }
        });

        restore.setIcon(new javax.swing.ImageIcon(getClass().getResource("/final_images/reset 50 50.png"))); // NOI18N
        restore.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                restore_mouse_entered(evt);
            }
        });
        restore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restoreActionPerformed(evt);
            }
        });

        cyan.setBackground(new java.awt.Color(0, 255, 255));
        cyan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proj images/squares/cyan_square.gif"))); // NOI18N
        cyan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cyan.setFocusable(false);
        cyan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cyan.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cyan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cyanActionPerformed(evt);
            }
        });

        green.setBackground(new java.awt.Color(102, 204, 0));
        green.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proj images/squares/green_square.jpg"))); // NOI18N
        green.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        green.setFocusable(false);
        green.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        green.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        green.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                greenActionPerformed(evt);
            }
        });

        yellow.setBackground(new java.awt.Color(255, 255, 0));
        yellow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proj images/squares/yellow_square.jpg"))); // NOI18N
        yellow.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        yellow.setFocusable(false);
        yellow.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        yellow.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        yellow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yellowActionPerformed(evt);
            }
        });

        blue.setBackground(new java.awt.Color(0, 0, 204));
        blue.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proj images/squares/blue_square.jpg"))); // NOI18N
        blue.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        blue.setFocusable(false);
        blue.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        blue.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        blue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blueActionPerformed(evt);
            }
        });

        magenta.setBackground(new java.awt.Color(255, 0, 255));
        magenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proj images/squares/magenra_square.gif"))); // NOI18N
        magenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        magenta.setFocusable(false);
        magenta.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        magenta.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        magenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                magentaActionPerformed(evt);
            }
        });

        orange.setBackground(new java.awt.Color(255, 102, 0));
        orange.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proj images/squares/orange_square.gif"))); // NOI18N
        orange.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        orange.setFocusable(false);
        orange.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        orange.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        orange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orangeActionPerformed(evt);
            }
        });

        grey.setBackground(new java.awt.Color(102, 102, 102));
        grey.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proj images/squares/gray_square.jpg"))); // NOI18N
        grey.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        grey.setFocusable(false);
        grey.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        grey.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        grey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                greyActionPerformed(evt);
            }
        });

        black.setBackground(new java.awt.Color(0, 0, 0));
        black.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proj images/squares/black_square.png"))); // NOI18N
        black.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        black.setFocusable(false);
        black.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        black.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        black.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blackActionPerformed(evt);
            }
        });

        red.setBackground(new java.awt.Color(255, 0, 0));
        red.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proj images/squares/red_square.gif"))); // NOI18N
        red.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        red.setFocusable(false);
        red.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        red.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        red.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redActionPerformed(evt);
            }
        });

        white.setBackground(new java.awt.Color(255, 255, 255));
        white.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proj images/squares/white.png"))); // NOI18N
        white.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        white.setFocusable(false);
        white.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        white.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        white.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                whiteActionPerformed(evt);
            }
        });

        line.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proj images/line.png"))); // NOI18N
        line.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                line_mouse_entered(evt);
            }
        });
        line.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lineActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(blue, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(yellow, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(magenta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(grey, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(orange, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(black, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(white, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cyan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(red, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(green, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 37, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(thumbnail0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(thumbnail1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(thumbnail2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(thumbnail3, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(th3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rect_draw, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(85, 85, 85)
                        .addComponent(circle_draw, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(line, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(91, 91, 91)
                        .addComponent(pen, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(86, 86, 86)
                        .addComponent(Save, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(90, 90, 90)
                        .addComponent(restore, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {black, blue, cyan, green, grey, magenta, orange, red, white, yellow});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(orange, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(yellow, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(magenta, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(grey, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(blue, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(red, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cyan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(black, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(white, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(green, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(restore, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(pen, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(rect_draw, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(circle_draw, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(line, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(Save, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(thumbnail0, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(thumbnail1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(th3, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(thumbnail2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                        .addComponent(thumbnail3, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(121, 121, 121))))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {black, blue, cyan, green, grey, magenta, orange, red, white, yellow});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void show_image(java.awt.event.WindowEvent evt) {
          File file=new File(display_image_path);       
        try {
            old_image=ImageIO.read(file);
        } catch (IOException ex) {
            Logger.getLogger(editing_window.class.getName()).log(Level.SEVERE, null, ex);
        }
          thumb = old_image.getScaledInstance(display_label.getWidth(),display_label.getHeight(), Image.SCALE_SMOOTH);
          icon1=new ImageIcon(thumb);    
          display_label.setIcon(icon1); 
    }

    private void rect_drawActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rect_drawActionPerformed
        opStatus=1;  //to indicate that rectangle button has been pressed
    }//GEN-LAST:event_rect_drawActionPerformed

    private void mouse_released_onLabel(java.awt.event.MouseEvent evt) {
         Rectangle r_balloon =new Rectangle(evt.getX(),evt.getY(),2,2);
       
         switch(opStatus)
        {
            case 1: releasedRect();
                    if(is_mouse_dragged==true)
                    draw_balloon_tip(r_balloon);
                    break;
                
            case 2: releasedOval();
                    if(is_mouse_dragged==true)
                    draw_balloon_tip(r_balloon);
                    break;
            
            case 3: releasedpen();
                    break;
                
            case 4: releasedLine();
                    break;
        }
    }
    
    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {
         Rectangle r=new Rectangle(jp.getX(),jp.getY(),jp.getWidth(),jp.getHeight()+10);
         try
         {
          BufferedImage bi = ScreenImage.createImage(r);
          ScreenImage.writeImage(bi, "Screen-Image.jpg");
          ImageIO.write(bi, "jpg",new File(display_image_path) );
         }
         catch(Exception exp)
         { 
             JOptionPane.showMessageDialog(null,"Error in Saving!!");
         }
        
         //Now handle the annotation Strings and their voice conversion
         convert_text_to_speech();
         compose_master_string();
    }

    private void rect_draw_mouse_entered(java.awt.event.MouseEvent evt) {
        draw_balloon_on_MouseHover("Draw Rectangle",rect_draw);
    }

    private void circle_drawActionPerformed(java.awt.event.ActionEvent evt) {
        opStatus=2;  //to indicate that Circle button has been pressed
    }

    private void penActionPerformed(java.awt.event.ActionEvent evt) {/
        opStatus=3; //to indicate pen has been Selected
    }

    private void restoreActionPerformed(java.awt.event.ActionEvent evt) {
        int i;
        for(i=0;i<b_index;i++)
        {
         b[i].closeBalloon();
        }
       
        File file=new File(original_image_folder+"\\Original_image.jpg");       
        try {
            old_image=ImageIO.read(file);
        } catch (IOException ex) 
        {
            Logger.getLogger(editing_window.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        thumb = old_image.getScaledInstance(display_label.getWidth(),display_label.getHeight(), Image.SCALE_SMOOTH);
        icon1=new ImageIcon(thumb);
        display_label.setIcon(icon1);
        b_index=0;
    }
    private void circle_draw_mouseEntered(java.awt.event.MouseEvent evt) {
        draw_balloon_on_MouseHover("Draw Circle",circle_draw);
    }

    private void pen_mouse_Entered(java.awt.event.MouseEvent evt) {
        draw_balloon_on_MouseHover("Use Pen",pen);
    }

    private void save_mouse_entered(java.awt.event.MouseEvent evt) {
        draw_balloon_on_MouseHover("Save image",Save);
    }

    private void restore_mouse_entered(java.awt.event.MouseEvent evt) {
        draw_balloon_on_MouseHover("Restore Original Screenshot",restore);
    }

    private void cyanActionPerformed(java.awt.event.ActionEvent evt) {
        color_chosen=Color.CYAN;
    }

    private void whiteActionPerformed(java.awt.event.ActionEvent evt) {
        color_chosen=Color.WHITE;
    }

    private void yellowActionPerformed(java.awt.event.ActionEvent evt) {
        color_chosen=Color.YELLOW;
    }

    private void blueActionPerformed(java.awt.event.ActionEvent evt) {
        color_chosen=Color.BLUE;
    }

    private void magentaActionPerformed(java.awt.event.ActionEvent evt) {
        color_chosen=Color.MAGENTA;
    }

    private void orangeActionPerformed(java.awt.event.ActionEvent evt) {
        color_chosen=Color.ORANGE;
    }

    private void greyActionPerformed(java.awt.event.ActionEvent evt) {
        color_chosen=Color.GRAY;
    }

    private void blackActionPerformed(java.awt.event.ActionEvent evt) {
        color_chosen=Color.BLACK;
    }

    private void redActionPerformed(java.awt.event.ActionEvent evt) {
        color_chosen=Color.RED;
    }

    private void greenActionPerformed(java.awt.event.ActionEvent evt) {
        color_chosen=Color.GREEN;
    }

    private void remove_file_in_O_Images_folder(java.awt.event.WindowEvent evt) {
        File file_temp=new File(original_image_folder+"\\Original_image.jpg");
        boolean del;
        del=file_temp.delete();
        
        //Save the current Image
        Rectangle r=new Rectangle(jp.getX(),jp.getY(),jp.getWidth(),jp.getHeight()+10);
          
         try
         {
          BufferedImage bi = ScreenImage.createImage(r);
          ScreenImage.writeImage(bi, "Screen-Image.jpg");
          ImageIO.write(bi, "jpg",new File(display_image_path) );
         }
         catch(Exception exp)
         { 
             JOptionPane.showMessageDialog(null,"Error in Saving!!");
         }
         
        convert_text_to_speech();
        
        //Compose The Final Master String for the Screenshot
        compose_master_string();
    }

    private void mouse_dragged_onLabel(java.awt.event.MouseEvent evt) {
        is_mouse_dragged=true;
        
        switch(opStatus)
        {
            case 1: rectoperation(evt); //Draw Rectangle
                    break;
                
            case 2: ovalOperation(evt); //Draw Oval    
                    break;
            
            case 3: penOperation(evt);   
                    break;  
            
            case 4: lineOperation(evt);    
                    break;
        }
    }

    private void show_the_thumbnail_images(java.awt.event.WindowEvent evt) {
         FileReader f_reader;
         int i;
		try {
			f_reader = new FileReader(task.thumbnail_path);
			BufferedReader b_reader= new BufferedReader(f_reader);
                        
                        for(i=0;i<th_temp.length;i++)
                        {
		        th_temp[i]=b_reader.readLine();
                        }
	                b_reader.close();
                        f_reader.close();
	            }
                catch (Exception ex)
                {
                    System.out.println("Error in reading File!!");
                }
                set_thumbnail_images();
    }

    private void lineActionPerformed(java.awt.event.ActionEvent evt) {
        opStatus=4;
    }

    private void line_mouse_entered(java.awt.event.MouseEvent evt) {
        draw_balloon_on_MouseHover("Draw line",line);
    }

    /**
     * @param args the command line arguments
     */
    public void set_display_image_path(String s) {
       display_image_path=s;
    }
    
    public void set_audio_file_path(String s)
    {
        path_for_audio_file=s;
    }
    
    public void set_original_image_folder_path(String s)
    {
     original_image_folder=s;   
    }
    
    public void draw_balloon_tip(Rectangle x)
    {
        final JTextField t1=new JTextField(20);
        ModernBalloonStyle m =new ModernBalloonStyle(5,5,Color.GREEN,Color.GREEN,Color.RED);
                   m.setCornerStyles(true,true,true,true);
                 
        b[b_index] = new CustomBalloonTip(
					display_label,
					t1,
                                        x,
					m,
					BalloonTip.Orientation.LEFT_BELOW,
					BalloonTip.AttachLocation.SOUTHWEST,
					10,10,
					true
				);
         
        b[b_index].setCloseButton(null,true);
        b[b_index].setOpacity(0.9f);
        b[b_index].setVisible(true);
        b_index++;
    }
    
    public void create_editing_window()
    {
      /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new editing_window().setVisible(true);
            }
        });   
      
    }
    
   public void lineOperation(MouseEvent e)
 {
     Color c_line;
     c_line=color_chosen;
     Graphics g  = display_label.getGraphics();
     g.setColor(c_line);

    /*
      In initial state setup default values
      for mouse coordinates
    */
    if (initialLine)
    {
       setGraphicalDefaults(e);
       g.setXORMode(c_line);
       g.drawLine(Orx,Ory,mousex,mousey);
       initialLine=false;
    }

    /*
      Make sure that the mouse has actually
      moved from its previous position.
    */
    if (mouseHasMoved(e))
    {
       /*
         Delete previous line shadow
         by xor-ing the graphical object
       */
       g.setXORMode(c_line);
       g.drawLine(Orx,Ory,mousex,mousey);

       /* Update new mouse coordinates */
       mousex = e.getX();
       mousey = e.getY();

       /* Draw line shadow */
       g.drawLine(Orx,Ory,mousex,mousey);
    }
 }

    public void rectoperation(MouseEvent e)
    {
        Color c_rect;
        c_rect=color_chosen;
        
        Graphics g=display_label.getGraphics();
        g.setColor(c_rect);
        
         if (initialRect){
        setGraphicalDefaults(e);
        initialRect = false;
       }

        if (mouseHasMoved(e)){
       /*
         Delete previous rectangle shadow
         by xor-ing the graphical object
       */
       g.setXORMode(display_label.getBackground());
       g.drawRect(drawX,drawY,OrWidth,OrHeight);

       /* Update new mouse coordinates */
       mousex = e.getX();
       mousey = e.getY();

       /* Check new mouse coordinates for negative errors */
       setActualBoundry();

       /* Draw rectangle shadow */
       g.drawRect(drawX,drawY,OrWidth,OrHeight);

    }    
        
    }//end rectoperation
    
    public void ovalOperation(MouseEvent e){
     Color c_oval;
     c_oval=color_chosen;
     
     Graphics g  = display_label.getGraphics();
     g.setColor(c_oval);

    /*
      In initial state setup default values
      for mouse coordinates
    */
    if (initialOval)
    {
       setGraphicalDefaults(e);
       initialOval=false;
    }

    /*
      Make sure that the mouse has actually
      moved from its previous position.
    */
    if (mouseHasMoved(e))
    {
       /*
         Delete previous oval shadow
         by xor-ing the graphical object
       */
       g.setXORMode(display_label.getBackground());
       g.drawOval(drawX,drawY,OrWidth,OrHeight);

       /* Update new mouse coordinates */
       mousex = e.getX();
       mousey = e.getY();

       /* Check new mouse coordinates for negative errors */
       setActualBoundry();

       /* Draw oval shadow */
       g.drawOval(drawX,drawY,OrWidth,OrHeight);
    }
 }//end ovalOperation
    
    
    public void penOperation(MouseEvent e){
     Color c_pen;
     c_pen=color_chosen;
    
     Graphics g  = display_label.getGraphics();
     g.setColor(c_pen);

    /*
      In initial state setup default values
      for mouse coordinates
    */
    if (initialPen)
    {
       setGraphicalDefaults(e);
       initialPen = false;
       g.drawLine(prevx,prevy,mousex,mousey);
    }

    /*
      Make sure that the mouse has actually
      moved from its previous position.
    */
    if (mouseHasMoved(e))
    {
       /*
          set mouse coordinates to
          current mouse position
       */
       mousex = e.getX();
       mousey = e.getY();

       /*
          draw a line from the previous mouse coordinates
          to the current mouse coordinates
       */
       g.drawLine(prevx,prevy,mousex,mousey);

       /*
          set the current mouse coordinates to
          previous mouse coordinates for next time
       */
       prevx = mousex;
       prevy = mousey;
    }
 }//end penoperation

    public void setGraphicalDefaults(MouseEvent e){
    mousex   = e.getX();
    mousey   = e.getY();
    prevx    = e.getX();
    prevy    = e.getY();
    Orx      = e.getX();
    Ory      = e.getY();
    drawX    = e.getX();
    drawY    = e.getY();
    OrWidth  = 0;
    OrHeight = 0;
 }//end setGrpahicsDefaultss
    
    public boolean mouseHasMoved(MouseEvent e){
    return (mousex != e.getX() || mousey != e.getY());
 }//end mouseHasMoved
    
     public void setActualBoundry(){
       /*
         If the any of the current mouse coordinates
         are smaller than the origin coordinates, meaning
         if drag occured in a negative manner, where either
         the x-shift occured from right and/or y-shift occured
         from bottom to top.
       */
       if (mousex < Orx || mousey < Ory)
       {
          /*
            if the current mouse x coordinate is smaller
            than the origin x coordinate,
            equate the drawX to be the difference between
            the current width and the origin x coordinate.
          */
          if (mousex < Orx)
          {
             OrWidth = Orx - mousex;
             drawX   = Orx - OrWidth;
          }
          else
          {
             drawX    = Orx;
             OrWidth  = mousex - Orx;

          }
          /*
            if the current mouse y coordinate is smaller
            than the origin y coordinate,
            equate the drawY to be the difference between
            the current height and the origin y coordinate.
          */
          if (mousey < Ory)
          {
             OrHeight = Ory - mousey;
             drawY    = Ory - OrHeight;
          }
          else
          {
             drawY    = Ory;
             OrHeight = mousey - Ory;
          }
       }
       /*
         Else if drag was done in a positive manner meaning
         x-shift occured from left to right and or y-shift occured
         from top to bottom
       */
       else
       {
          drawX    = Orx;
          drawY    = Ory;
          OrWidth  = mousex - Orx;
          OrHeight = mousey - Ory;
       }
 }
    public void releasedRect(){
    initialRect = true;
    Graphics g  = display_label.getGraphics();
    g.setColor(color_chosen);
    g.drawRect(drawX,drawY,OrWidth,OrHeight);
 }
    
     public void releasedOval(){
       initialOval = true;
       Graphics g  = display_label.getGraphics();
       g.setColor(color_chosen);
       g.drawOval(drawX,drawY,OrWidth,OrHeight);
 }
     
      public void releasedpen(){
       initialPen = true;
 }

    public void releasedLine(){
    if ((Math.abs(Orx - mousex) + Math.abs(Ory - mousey)) != 0){
       initialLine = true;
       Graphics g  = display_label.getGraphics();
       g.setColor(color_chosen);
       g.drawLine(Orx,Ory,mousex,mousey);
    }
 } 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Save;
    private javax.swing.JButton black;
    private javax.swing.JButton blue;
    private javax.swing.JButton circle_draw;
    private javax.swing.JButton cyan;
    private javax.swing.JLabel display_label;
    private javax.swing.JButton green;
    private javax.swing.JButton grey;
    private javax.swing.JPanel jp;
    private javax.swing.JButton line;
    private javax.swing.JButton magenta;
    private javax.swing.JButton orange;
    private javax.swing.JButton pen;
    private javax.swing.JButton rect_draw;
    private javax.swing.JButton red;
    private javax.swing.JButton restore;
    private javax.swing.JLabel th3;
    private javax.swing.JLabel thumbnail0;
    private javax.swing.JLabel thumbnail1;
    private javax.swing.JLabel thumbnail2;
    private javax.swing.JLabel thumbnail3;
    private javax.swing.JButton white;
    private javax.swing.JButton yellow;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
       
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
       
    }
}
