

package amst;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

public class PdfGenerate {
 
    String price; 
    String description; 
    String clientInput;
    Product items;
    ObservableList list; 
    ShoppingCart ss; 
    int counter =0; 
    public final int a=0; 
    int finalPrice;
    
   PdfGenerate(){}
   
   PdfGenerate(ObservableList s,String client)
   {
       list=s;
       this.clientInput=client;
   
   }
   
 /*  PdfGenerate(String price,String description,String client){
       this.price=price; this.description=description; this.clientInput=client;
   
   } */
   
   int subTotal()
   
   {   ShoppingCart sc;
       int total=0;
       int len= list.size();
      for(int i=0;i<len;i++)
     {
       sc=(ShoppingCart) list.get(i);
       total = total+ Integer.parseInt(sc.gettotal());
     }
   return total;
   }
    public void createPdf() throws FileNotFoundException, DocumentException, BadElementException, IOException{
        
        int month=0;
        Calendar date=new GregorianCalendar();
        System.out.println(date.toString());
        month=date.get(Calendar.MONTH);
        month++;
        String current=""+date.get(Calendar.DATE)+"-"+month+"-"+date.get(Calendar.YEAR);
        Calendar cal = Calendar.getInstance();
      
         
	Document document=new Document();
        
       
        
    
        PdfWriter.getInstance(document, new FileOutputStream("Invoices\\"+current+" "+ cal.getTimeInMillis()+" "+clientInput+".pdf"));
        
       
      //PdfWriter.getInstance(document, new FileOutputStream(""+current+".pdf")) ;
        document.open();
        
        Image image=Image.getInstance("logo.jpg");
        
        image.setAlignment(Element.ALIGN_LEFT);
        image.setSpacingBefore(7);
       
       // document.add(image);
        Paragraph title=new Paragraph("Invoice",FontFactory.getFont(FontFactory.TIMES_BOLDITALIC,28, BaseColor.BLACK));
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        if(clientInput==null)
        {
            Paragraph client=new Paragraph("Client : MR.ABC  ",FontFactory.getFont(FontFactory.TIMES_BOLDITALIC,20, BaseColor.BLACK));
            document.add(client);
        }
        
        else
        {
            Paragraph client=new Paragraph("Client : "+clientInput+"",FontFactory.getFont(FontFactory.TIMES_BOLDITALIC,20, BaseColor.BLACK));
            document.add(client);
            
                }
          Paragraph today=new Paragraph("Todays Date :"+current,FontFactory.getFont(FontFactory.TIMES_BOLDITALIC,14, BaseColor.DARK_GRAY));
          today.setSpacingBefore(7);
        document.add(today);
        
        Paragraph batch=new Paragraph(" Tehreem Engineering",FontFactory.getFont(FontFactory.TIMES_BOLDITALIC,28, BaseColor.BLUE));
        batch.setAlignment(Element.ALIGN_CENTER);
         
        document.add(batch);
        document.add(new Paragraph("Batch Number"));
        document.add(new Paragraph("#"+date.get(Calendar.DATE)+"B"+date.get(Calendar.YEAR)));
      
        
        document.add(new Paragraph("------"));
        document.add(new Paragraph("------"));
      
       
     PdfPTable table=new PdfPTable(6);
     table.setSpacingBefore(2);
       
      PdfPCell cell=new PdfPCell(new Paragraph("Product"));
       
      cell.setColspan(1);
      cell.setHorizontalAlignment(23);
      cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
     
      PdfPCell cell2=new PdfPCell(new Paragraph("Quantity"));
       
      cell2.setColspan(1);
      cell2.setHorizontalAlignment(23);
      cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
      
      PdfPCell cell3=new PdfPCell(new Paragraph("Price"));
       
      cell3.setColspan(1);
      cell3.setHorizontalAlignment(23);
      cell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
      
      PdfPCell cell4=new PdfPCell(new Paragraph("GST"));
       
      cell4.setColspan(1);
      cell4.setHorizontalAlignment(23);
      cell4.setBackgroundColor(BaseColor.LIGHT_GRAY);
      
      PdfPCell cell5=new PdfPCell(new Paragraph("Cartridge"));
       
      cell5.setColspan(1);
      cell5.setHorizontalAlignment(23);
      cell5.setBackgroundColor(BaseColor.LIGHT_GRAY);
      
      PdfPCell cell6=new PdfPCell(new Paragraph("Total Price"));
       
      cell6.setColspan(1);
      cell6.setHorizontalAlignment(23);
      cell6.setBackgroundColor(BaseColor.LIGHT_GRAY);
      
      table.addCell(cell);
      table.addCell(cell2);
      table.addCell(cell3);
      table.addCell(cell4);
      table.addCell(cell5);
      table.addCell(cell6);
     
      
      // table.addCell(description);
       //table.addCell(price);
       for(int i=0;i<list.size();i++)
       {
         ss=  (ShoppingCart) list.get(i);
         table.addCell(ss.getname());
         table.addCell(ss.getquantity());
         table.addCell(ss.getprice());
         table.addCell(ss.getgst());
         table.addCell(ss.getcartridge());
         table.addCell(ss.gettotal());
         
       }
       document.add(table);
     //finalPrice = finalPrice+Integer.parseInt(ss.gettotal());
       
       Paragraph p=new Paragraph("Total Amount : "+subTotal());
       
       p.setAlignment(Element.ALIGN_RIGHT);
       document.add(p);
       
       
        document.close();
    }
}