/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test_package;

/**
 *
 * @author Arvind
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class create_read_only {
	Integer i;
	String str;
	public File fread;
        String name_of_task;
        String path;
        
        create_read_only()
        {
            
        }
        
        //take workspace and taskname as input
        create_read_only(String ws,String tn)  
        {
            name_of_task=tn;
            path=ws;
        }
        
	public void make_read_only_file()
	{
		FileWriter f;
		try 
                {
			f=new FileWriter(path+"\\"+name_of_task+"\\Task.txt");
                        BufferedWriter out=new BufferedWriter(f);
                        out.write(name_of_task);
		        out.close();
                        f.close();
		        fread=new File(path+"\\"+name_of_task+"\\Task.txt");
		        fread.setWritable(false);  //make the File Read Only
		}
                catch (Exception efile) 
                {
		System.out.println("Error in writing to Task.txt");	
		}
	}
        
        public void make_simple_file(String filename)
        {
            FileWriter f_simple;
        try 
        {
	 f_simple=new FileWriter(path+"\\"+name_of_task+"\\"+filename);
         f_simple.close();
	 } 
        catch (IOException e) 
         {
			// TODO Auto-generated catch block
			e.printStackTrace();
         }
        }
        
        public void write_to_file(String filename,String[] t)
        {
            int i;
            FileWriter f_write;
            try
            {
            f_write=new FileWriter(path+"\\"+name_of_task+"\\"+filename);
            BufferedWriter out1=new BufferedWriter(f_write);
            for(i=0;i<t.length;i++)
            {
                out1.write(t[i]);
                out1.newLine();
            }
            
            out1.close();
            f_write.close();
            }
            catch(Exception e)
            {
               System.out.println("Error in File I/O");
            }
        }
        
	public String now_read_the_info_file(String file_directory_path)
	{
	       FileReader fr;
               String s=null;
		try {
			fr = new FileReader(file_directory_path+"\\Task.txt");
			BufferedReader br= new BufferedReader(fr);
		        s=br.readLine();
			br.close();
                        fr.close();
	            }
                catch (Exception ex)
                {
                    System.out.println("Error in reading File!!");
                }
                return s;
	}
}
