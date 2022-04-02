package demo4.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import demo4.model.document;
import demo4.model.feedback;
import demo4.service.documentService;
import demo4.service.feedbackService;

@Controller
@RequestMapping(value = "/search")
public class documentController {

	@Autowired
	private documentService documentService;
	
	@Autowired
	private feedbackService feedbackService;

	@Autowired
	private ServletContext app;
	
	@GetMapping(value="/document/{id}")
	public String getDocument(@PathVariable(value = "id") int id,Model md) {
		document document = documentService.getDocumentById(id);
		md.addAttribute("document", document);
		return "document";
	}
	
	@GetMapping(value = "/document/download/{id}")
	  public void downloadFile(HttpServletResponse response,@PathVariable(value = "id") int id) throws IOException {
		
		document document = documentService.getDocumentById(id);
//		Calendar calendar = Calendar.getInstance();
//		feedback feedback = new feedback();
//		feedback.setScore(1);
//		feedback.setTime(calendar.getTime());
//		feedback.setF_document(document);
		try {
	      File file = new File(app.getRealPath("/static/upload/file/"+document.getFile()));
	      byte[] data = FileUtils.readFileToByteArray(file);
	    
	      // Thiết lập thông tin trả về
	      response.setContentType("application/octet-stream");
	      response.setHeader("Content-disposition", "attachment; filename=" + file.getName());
	      response.setContentLength(data.length);
	      InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));
	      FileCopyUtils.copy(inputStream, response.getOutputStream());
	    } catch (Exception ex) {
	      ex.printStackTrace();
	      
	    }
	    
	  }
	
}
