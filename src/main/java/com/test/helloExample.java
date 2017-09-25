package com.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicLong;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


@RestController
//@RequestMapping("/abc")
public class helloExample {
	private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
    //http://localhost:9090/myMavenTomcat/rest/hello
	@RequestMapping("/hello")
	public ModelAndView helloWorld() {
 
		String message = "<br><div style='text-align:center;'>"
				+ "<h3>********** Hello World, Spring MVC Tutorial</h3>This message is coming from CrunchifyHelloWorld.java **********</div><br><br>";
		return new ModelAndView("hello", "message", message);
	}
	
	/**
	 * This method is to explain how @RequestParam works and picks parameter from URI
	 * @param String name
	 * @return Greeting DTO
	 */
	//http://localhost:9090/myMavenTomcat/rest/greeting?name=Girish
	@GetMapping(value ="/greeting", produces = {MediaType.APPLICATION_JSON_VALUE} )
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
	
	/**
	 * This method is to explain how @RequestBody works with POST method and processes the JSON object that has been passed as input.
	 * @param JSON => {"id": 10,"content": "android"}
	 * I am running this web service using Postman. Set Headers as key="Content-Type" and"value":"application/json" and body will have raw JSON mentioned above
	 * @return Greeting DTO
	 */
	//http://localhost:9090/myMavenTomcat/rest/greetID
	@PostMapping(value ="/greetID", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE} )
    public Greeting greetID(@RequestBody Greeting aGreeting) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, aGreeting.getContent()));
    }
	
	/**
	 * This method is to explain how @PathVariable & @RequestParam works with POST method and picks values from URI.
	 * @param int greetID and String name
	 * I am running this web service using Postman and since it is POST method need to set Headers as key="Content-Type" and"value":"application/json"
	 * @return Greeting DTO
	 * @PathVariable is to obtain some placeholder from the URI
	 * @RequestParam is to obtain an parameter 
	 */
	//http://localhost:9090/myMavenTomcat/rest/formGreet/10?name=girish
	//http://localhost:9090/myMavenTomcat/rest/formGreet/10 (as name is not required)
	@PostMapping(value ="/formGreet/{greetID}", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE} )
	public Greeting formGreeting(@PathVariable("greetID") int id,
	   		@RequestParam(value = "name", required = false) String name) {
	        return new Greeting(id,String.format(template, name));
	}
	
		//http://localhost:9090/myMavenTomcat/rest/formGreet/formGreet
		@PostMapping(value ="/formGreet/upload", produces = {MediaType.TEXT_PLAIN_VALUE}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}  )
		public String uploadGreeting(@FormDataParam("file") InputStream fileInputStream,
                @FormDataParam("file") FormDataContentDisposition fileMetaData) {
			OutputStream outpuStream = null;
	        String fileName = fileMetaData.getFileName();
	        System.out.println("File Name: " + fileMetaData.getFileName());
	        String filePath = "C://CodeKul//MyPractice//Eclipse_Oxygen//uploaded" + fileName;
	         
	        try {
	            int read = 0;
	            byte[] bytes = new byte[1024];
	            outpuStream = new FileOutputStream(new File(filePath));
	            while ((read = fileInputStream.read(bytes)) != -1) {
	                outpuStream.write(bytes, 0, read);
	            }
	            outpuStream.flush();
	            outpuStream.close();
	        } catch(IOException iox){
	            iox.printStackTrace();
	        } finally {
	            if(outpuStream != null){
	                try{outpuStream.close();} catch(Exception ex){}
	            }
	        }
			return "Sucess";
		}
		
		//http://localhost:9090/myMavenTomcat/rest/formGreet/formGreet
				@GetMapping(value ="/formGreet/pdf/{fileName}")
				public String iTextPDFFile(@PathVariable("fileName") String fileName) {
					
					Document document = new Document();
			        String filePath = "C://CodeKul//MyPractice//Eclipse_Oxygen//uploaded" + fileName;
			         
			        try {
			            PdfWriter.getInstance(document, new FileOutputStream(new File(filePath)));
			            document.open();

			            Paragraph p = new Paragraph();
			            p.add("This is my paragraph 1");
			            p.setAlignment(Element.ALIGN_CENTER);

			            document.add(p);

			            Paragraph p2 = new Paragraph();
			            p2.add("This is my paragraph 2"); //no alignment

			            document.add(p2);

			            Font f = new Font();
			            f.setStyle(Font.BOLD);
			            f.setSize(8);

			            document.add(new Paragraph("This is my paragraph 3", f));

			            //close
			            document.close();

			            System.out.println("Done");

			        } catch (FileNotFoundException e) {
			            e.printStackTrace();
			        } catch (DocumentException e) {
			            e.printStackTrace();
			        } catch (IOException e) {
			            e.printStackTrace();
			        }
					return "Success";
				}
}
