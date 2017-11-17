/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test_package;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 *
 * @author Arvind
 */

public class Task implements NativeKeyListener {

    public String[] annotation;
    public static String workspace_path;
    public static int img_index;
    public static String task_name;
    public static String o_folder_path;
    editing_window edit_w;
    public static String[] th;
    public static String thumbnail_path;
    
    task()
    {
        int i;
        workspace_path=null;
        task_name="Unnamed Task";
        annotation=new String[100] ;
        img_index=0;
        o_folder_path=null;
        thumbnail_path=null;
        th=new String[4];
        
        //Initialise all the thumbnails
        for(i=0;i<th.length;i++)
         {
             th[i]="Thumbnail";   
         }
    }
    
    public String return_workspace_name()
    {
     return workspace_path;   
    }
    
     public String return_task_name()
    {
     return task_name ;   
    }
 
    public void generate_the_pdf()
    {
        if(img_index!=0)
        {
           Create_Final_pdf p=new Create_Final_pdf();
           p.process_images_for_pdf(task_name, workspace_path);
                try 
                 {
        	  p.create_pdf(task_name,workspace_path);
                 } catch (Exception et) 
                 {
                   et.printStackTrace();
                 }
        }
    }
    
    public void generate_the_doc()
    {
        doc help_doc = new doc(task_name, workspace_path);
        try {
            help_doc.create_doc();
        } catch (Exception ex) {
            System.out.println("COuld not generate the  docx File");
        }
    }
   
    public void generate_the_info_file()
    {
     create_read_only c= new create_read_only(workspace_path,task_name);
     c.make_read_only_file();   
    }
    
    public void generate_the_thumbnail_file()
    {
        create_read_only c_th= new create_read_only(workspace_path,task_name);
        c_th.make_simple_file("Thumbnails.txt");
        c_th.write_to_file("Thumbnails.txt",th);  
        thumbnail_path=workspace_path+"\\"+task_name+"\\Thumbnails.txt";
    }
    
    public void select_workspace()
    {
         JFileChooser chooser = new JFileChooser();
         chooser.setDialogTitle("Please Select a workspace where you would like to save your Work");
         chooser.setCurrentDirectory(new java.io.File("."));
         chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
         chooser.setAcceptAllFileFilterUsed(false);

         if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
         {
            workspace_path=chooser.getSelectedFile().toString();
            JOptionPane.showMessageDialog(null,"The Workspace which you have Chosen is"+workspace_path);
          } 
         else 
         {
           JOptionPane.showMessageDialog(null,"No Directory Chosen");
           workspace_path=null;
         }
    }//end select Workspace
    
    public String get_original_image_folder()
    {    
       return o_folder_path;
    }
   
    public int check()
    {
       if(workspace_path==null)
        {
       return 0;
        }
        else
        {
        return 1;
        }
    }
    
     public void get_task_name()
    {
       task_name =JOptionPane.showInputDialog(null,"Enter Name of the task","Task Name",1);    
    }
    
    public void create_workspace()
    {
        if(task_name==null)
        {
            task_name="Unnamed Task";
        }
        new File(workspace_path+"\\"+task_name).mkdir();    
    }
    
    public void create_original_images_folder()
    {
      o_folder_path=workspace_path+"\\"+task_name+"\\O_Images";  
      new File(o_folder_path).mkdir();
    }
   
    public void initialise_the_listener()
    {
        try 
        {
                GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) 
        {
                System.err.println("There was a problem registering the native hook.");
                System.err.println(ex.getMessage());
                System.exit(1);
        }
       //Construct the example object and initialise native hook.
         GlobalScreen.getInstance().addNativeKeyListener(this);
    }//end initialise_the_listener
    
    public void remove_listener()
    {
     GlobalScreen.getInstance().removeNativeKeyListener(this);    
    }
    
    public void start_the_recording()
    {
      JOptionPane.showMessageDialog(null,"Press F7 for screen-capture");    
    }
    
    @Override
    public void nativeKeyPressed(NativeKeyEvent e) 
    {
        //Screen Capture on F7 key press
       if(e.getKeyCode()==118) //keyCode for F7 key
       {
          //Recreate the original Images folder on f7 key press
          int k; //index for thumbnail array
          Rectangle ss = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
          String s="sc";
	  String image_url;
	  String url=workspace_path+"\\"+task_name;
          String audio_file_path=workspace_path+"\\"+task_name;
            try
            {		 
               //Store the screenshot in the folder
                String  name1=s+img_index;
		BufferedImage cp = new Robot().createScreenCapture(ss);
                ImageIO.write(cp,"jpg",new File(workspace_path+"\\"+task_name+"\\"+name1+".jpg"));
                ImageIO.write(cp,"jpg",new File(o_folder_path+"\\Original_image.jpg"));
                url=workspace_path+"\\"+task_name+"\\"+name1+".jpg";
                audio_file_path=workspace_path+"\\"+task_name+"\\"+name1;                        
            }
            catch(Exception ex)
            { 
                JOptionPane.showMessageDialog(null,"Error!!..Please select a Valid Workspace");
                System.exit(1);
            }
              image_url=url;  
              k=(img_index)%4;
              th[k]=url;
              create_read_only c_temp=new create_read_only(workspace_path,task_name);
              c_temp.write_to_file("Thumbnails.txt", th);
              editing_window edit_w= new editing_window();
              edit_w.set_display_image_path(image_url);
              edit_w.set_audio_file_path(audio_file_path);
              edit_w.set_original_image_folder_path(o_folder_path);
              edit_w.create_editing_window();
              img_index++;
        }//end if
    }//end NativeKeyPressed

    @Override
    public void nativeKeyReleased(NativeKeyEvent nke) 
    {
        
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nke) 
    {
        
    }
}
