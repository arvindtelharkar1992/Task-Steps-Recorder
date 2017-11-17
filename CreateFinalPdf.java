/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test_package;

/**
 *
 * @author Arvind
 */
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Header;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Comparator;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Footer;

public class CreateFinalPdf {
    
    public static File[] images_for_pdf;
    public static int image_count_for_pdf;
    public void sort(File[] files) {
      Arrays.sort(files, new Comparator(){
      @Override
        public int compare(Object o1, Object o2) {
               if (((File)o1).lastModified() < ((File)o2).lastModified()) {
		return -1;
 		}
               else if (((File)o1).lastModified() > ((File)o2).lastModified()) {
		return 1;
 		}  
 	       else{
 		return 0;
 	       }
         }
        }); 
     }
    
    public void process_images_for_pdf(String task,String workspace){
        int i;
        File directory = new File(workspace+"\\"+task);
        File[] fList = directory.listFiles();
 
        for (File file : fList)
        {
            if(file.isFile() && file.getName().endsWith("jpg"))
            {
              image_count_for_pdf++;
            }
        }
        
        //now create the array for storing the image Files and insert the images
        images_for_pdf=new File[image_count_for_pdf];
        i=0;
        
         for (File file : fList)
        {
            if(file.isFile() && file.getName().endsWith("jpg"))
            {
                    images_for_pdf[i]=file;
                    i++;
            }
        }
         
        sort(images_for_pdf);    
        
    }
  
public void create_pdf(String task,String workspace) throws Exception
	{
		Document document = new Document();
		PdfWriter.getInstance(document,
                        new FileOutputStream(workspace+"\\"+task+"\\"+task+".pdf"));
		document.open();
		
        for (File file : images_for_pdf)
        {
                com.lowagie.text.Image image1 = 
				com.lowagie.text.Image.getInstance(workspace+"\\"+task+"\\"+file.getName());
                
                 image1.setCompressionLevel(0);
		      
		 if (image1.getHeight() > image1.getWidth())
		    {
		        //Maximum height is 800 pixels.
		        float percentage = 0.0f;
		        percentage = 700 / image1.getHeight();
		        image1.scalePercent(percentage * 100);
		        
		    }
		    else
		    {
		        //Maximum width is 600 pixels.
		        float percentage = 0.0f;
		        percentage = 540 / image1.getWidth();
		        image1.scalePercent(percentage * 100);
		    }
               
                  document.add(image1);
		  document.newPage();
                
	}
	
	document.close();
}
}
