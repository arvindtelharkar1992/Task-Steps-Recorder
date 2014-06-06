/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test_package;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author Arvind
 */
public class create_zip_folder {
    
    public String path_of_zipfile;
    
    public String name_of_zipfolder;
    
    create_zip_folder(String s1,String s2)
    {
        path_of_zipfile=s1;
        name_of_zipfolder=s2;
    }
    
     public void create_zip_file() throws IOException
    {
        FileOutputStream fout = null;
        ZipOutputStream zout;
        try {
            //creating the byte array
            byte []b;
            b=new byte[1024];
            
           
            
            //zip File test.zip
            File f=new File(path_of_zipfile);
            String zipFile=path_of_zipfile+"\\"+name_of_zipfolder;
            fout = new FileOutputStream(zipFile);
            
            zout=new ZipOutputStream(new BufferedOutputStream(fout));
            
            File []s=f.listFiles();
            
            //Iterating over the Files in the Folder
            for(int i=0;i<s.length;i++)
            {
                 
                if(s[i].getName().endsWith("jpg")||s[i].getName().endsWith("pdf") || s[i].getName().endsWith("wav") || s[i].getName().endsWith("txt") )
            {
               
                //System.out.println(s[i].getName());
                
                b=new byte[(int)s[i].length()];
                FileInputStream fin=new FileInputStream(s[i]);
                zout.putNextEntry(new ZipEntry(s[i].getName()));
                int length;
                while((length=fin.read(b))>0)
                {
                    zout.write(b,0,length);
                }
                zout.closeEntry();
                fin.close();
            }
            }//end for
            
            zout.close();
            
        }
        catch(Exception e) 
        {    
            System.out.println("could not create Zip File");
        }
        
        
    }//end create_zip_file
    
    
    
    
}
