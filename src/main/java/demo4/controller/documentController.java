package demo4.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import demo4.model.account;
import demo4.model.document;
import demo4.model.feedback;
import demo4.service.accountService;
import demo4.service.documentService;
import demo4.service.feedbackService;

@Controller
@RequestMapping(value = "/document")
public class documentController {

	@Autowired
	private documentService documentService;

	@Autowired
	private feedbackService feedbackService;

	@Autowired
	private accountService accountService;

	@Autowired
	private ServletContext app;
	
	@GetMapping(value = "/category")
	public String getAllDocumentByCategory(@RequestParam(value = "id") String idcategory,Model md) {
		List<document> listdocument = documentService.getAllDocumentByCategory(idcategory);
		md.addAttribute("listdocument", listdocument);
		return "listdocument";
	}
	
	
	
	@GetMapping(value = "/{id}")
	public String getDocument(@PathVariable(value = "id") int id, Model md) {
		document document = documentService.getDocumentById(id);
		List<feedback> listFeedback = feedbackService.getFeedBackByIdDocument(id);
		Object result = feedbackService.avgStarDocument(id);
		if(result == null) {
			md.addAttribute("star", 0);
		}else {
			double temp = (double) result;
			long star = Math.round(temp);
			md.addAttribute("star", star);
		}
		md.addAttribute("listFeedback", listFeedback);
		md.addAttribute("document", document);	
		return "document";
	}
	
	@PostMapping(value = "/{id}")
	public String commentDocument(@PathVariable(value = "id") int id,@RequestParam(value = "comment") String comment) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		document document = documentService.getDocumentById(id);	
		Calendar calendar = Calendar.getInstance();
		feedback feedback = new feedback();
		feedback.setScore(0);
		feedback.setTime(calendar.getTime());
		feedback.setF_document(document);
		feedback.setF_account(accountService.findAccountByUserName(auth.getName()));
		feedback.setComment(comment);
		feedbackService.saveFeedback(feedback);
		return "redirect:/document/"+id;
	}
	
	@GetMapping(value = "/{id}/star")
	public String rateStarDocument(@PathVariable(value = "id") int id,@RequestParam(value = "score") int score) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		document document = documentService.getDocumentById(id);	
		Calendar calendar = Calendar.getInstance();
		feedback feedback = new feedback();
		feedback.setScore(score);
		feedback.setTime(calendar.getTime());
		feedback.setF_document(document);
		feedback.setF_account(accountService.findAccountByUserName(auth.getName()));
		feedbackService.saveFeedback(feedback);
		return "redirect:/document/"+id;
	}

	
	

	@GetMapping(value = "/download/file/{id}")
	public void downloadFile(HttpServletResponse response, @PathVariable(value = "id") int id) throws IOException {

		// get account
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		document document = documentService.getDocumentById(id);

		Calendar calendar = Calendar.getInstance();
		feedback feedback = new feedback();
		feedback.setScore(0);
		feedback.setTime(calendar.getTime());
		feedback.setF_document(document);
		feedback.setF_account(accountService.findAccountByUserName(auth.getName()));
		feedbackService.saveFeedback(feedback);

		try {
			File file = new File(app.getRealPath("/static/upload/file/" + document.getFile()));
			byte[] data = FileUtils.readFileToByteArray(file);
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment; filename=" + file.getName());
			response.setContentLength(data.length);
			InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}

	@GetMapping(value = "/download/source/{id}")
	public void downloadSource(HttpServletResponse response, @PathVariable(value = "id") int id) throws IOException {
		// get account
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		account account = accountService.findAccountByUserName(auth.getName());

		document document = documentService.getDocumentById(id);
		Calendar calendar = Calendar.getInstance();
		feedback feedback = new feedback();
		feedback.setScore(0);
		feedback.setTime(calendar.getTime());
		feedback.setF_document(document);
		feedback.setF_account(account);
		feedbackService.saveFeedback(feedback);

		try {
			File file = new File(app.getRealPath("/static/upload/source/" + document.getSource()));
			byte[] data = FileUtils.readFileToByteArray(file);
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
