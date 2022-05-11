package demo4.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import demo4.model.account;
import demo4.model.category;
import demo4.model.document;
import demo4.model.documentDetail;
import demo4.model.student;
import demo4.model.teach;
import demo4.service.accountService;
import demo4.service.categoryService;
import demo4.service.documentDetailService;
import demo4.service.documentService;
import demo4.service.studentService;
import demo4.service.teachService;
import demo4.service.teacherService;
import demo4.service.userPrincipal;

@Controller
@RequestMapping(value = "/student")
public class studentController {

	@Autowired
	private teachService teachService;
	
	@Autowired
	private studentService studentService;
	
	@Autowired
	private accountService accountService;
	
	@Autowired
	private documentDetailService documentDetailService;
	
	@Autowired
	private documentService documentService;
	
	@Autowired
	private teacherService teacherService;
	
	@Autowired
	private categoryService categoryService;
	
	
	@Autowired
	private ServletContext app;

	
	
	@GetMapping(value = "/")
	public String home(Model md){
		List<category> listCategory = categoryService.getAllCategory();
		List<List<document>> listDocument = new ArrayList<List<document>>();	
		for (category category : listCategory) {
			List<document> document = documentService.getDocumentByCategoryForHome(category.getId());
			listDocument.add(document);
		}
		md.addAttribute("listDocument", listDocument);
		return "home";
	}
	
	/*profile*/
	@GetMapping(value = "/profile")
	public String profile(Model md) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		student s = studentService.getStudentById(auth.getName());
		md.addAttribute("info", s);
		return "sprofile";
	}
	
	@GetMapping(value = "/profile/changepassword")
	public String changePassword() {
		return "changepassword";
	}
	
	@PostMapping(value = "/profile/changepassword")
	public String confirmChangePassword(@RequestParam(name = "password") String password,
			@RequestParam(name = "npassword") String npassword,
			@RequestParam(name = "cpassword") String cpassword,
			Model md
			) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		userPrincipal user = (userPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(encoder.matches(password, user.getPassword())) {
			if(npassword.equalsIgnoreCase(cpassword)) {
				account ac = accountService.getAccountById(user.getId());
				ac.setPassword(encoder.encode(npassword));
				accountService.updateAccount(ac);
				md.addAttribute("notify", "<div class=\"alert alert-success text-center col-4 mt-2\" role=\"alert\">Thay đổi mật khẩu thành công</div>");
				return "changepassword";
			}
		}
		
		md.addAttribute("notify", "<div class=\"alert alert-danger text-center col-4 mt-2\" role=\"alert\">Thay đổi mật khẩu không thành công</div>");
		return "changepassword";
	}
	
	@GetMapping(value = "/profile/editphonenumber")
	public String editPhoneNumber() {
		return "editphonenumber";
	}
	
	@PostMapping(value = "/profile/editphonenumber")
	public String confirmEditPhoneNumber(Model md,
			@RequestParam(value = "password") String password,
			@RequestParam(value= "nphone") String nphone
			) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		userPrincipal user = (userPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(encoder.matches(password, user.getPassword())) {
			student stu = studentService.getStudentById(user.getUsername());
			stu.setPhone(nphone);
			studentService.updateStudent(stu);
			md.addAttribute("notify", "<div class=\"alert alert-success text-center col-4 mt-2\" role=\"alert\">Cập nhật số điện thoại thành công</div>");
			return "editphonenumber";
		}
		
		md.addAttribute("notify", "<div class=\"alert alert-danger text-center col-4 mt-2\" role=\"alert\">Cập nhật số điện thoại không thành công</div>");
		return "editphonenumber";
	}
	
	/*classroom*/
	@GetMapping(value = "/classroom")
	public String classroom(Model md) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<documentDetail> dd = documentDetailService.getDocumentDetailByStudentId(auth.getName());
		md.addAttribute("documentDetail", dd);
		return "classroom";
	}
	
	
	@GetMapping(value = "/classroom/detail")
	public String classroomDetail(@RequestParam(value = "classroom") String classroomId,Model md) {
		teach teach = teachService.findTeachByClassroomId(classroomId);
		md.addAttribute("teach", teach);
		return "classroomdetail";
	}
	
	@GetMapping(value = "/classroom/submitdocument")
	public String submitDocument(@RequestParam(value = "classroom") String classroomId,Model md) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		documentDetail documentDetail = documentDetailService.getDocumentDetailByPrimaryKey(classroomId, auth.getName());
		if(documentDetail != null && documentDetail.isCheck() == false) {
			teach teach = teachService.findTeachByClassroomId(classroomId);
			document document = new document();
			md.addAttribute("teach", teach);
			md.addAttribute("documentDetail", documentDetail);
			md.addAttribute("document", document);
			
			return "submitdocument";
		}
		
		return "classroom";
	}
	
	
	@PostMapping(value = "/classroom/submitdocument",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public String confirmSubmitDocument(@RequestParam(value = "fdocument") MultipartFile fdocument,
			@RequestParam(value = "srcdocument") MultipartFile srcdocument,
			@RequestParam(value = "studentId") String studentId,
			@RequestParam(value = "categoryId") String categoryId,
			@RequestParam(value = "teacherId") String teacherId,
			@RequestParam(value = "classroomId") String classroomId,
			@ModelAttribute(value = "document") document document,
			Model md) throws IllegalStateException, IOException, Exception {
		

		String pathFile = app.getRealPath("/static/upload/file");
		String pathSource = app.getRealPath("/static/upload/source");
		
		File folderFile = new File(pathFile);
		File folderSource = new File(pathSource);
	
		if (!folderFile.exists()) {
			folderFile.mkdirs();
		}
		
		if (!folderSource.exists()) {
			folderSource.mkdirs();
		}
		
		if (!fdocument.isEmpty()) {
			// upload file to server
			String fileName = fdocument.getOriginalFilename();
			File uploadFile = new File(pathFile, fileName);
			fdocument.transferTo(uploadFile);
			document.setFile(fileName);
		}
		
		if (!srcdocument.isEmpty()) {
			// upload source to server
			String sourceName = srcdocument.getOriginalFilename();
			File uploadSource = new File(pathSource, sourceName);
			srcdocument.transferTo(uploadSource);
			document.setSource(sourceName);
		}
		
		document.setD_category(categoryService.getCategoryById(categoryId));
		document.setD_student(studentService.getStudentById(studentId));
		document.setD_teacher(teacherService.getTeacherById(teacherId));
		
		try {
		documentService.saveDocument(document);
		}catch (Exception e) {
			try {
				documentService.saveDocument(document);
			} catch (Exception e2) {
				documentService.saveDocument(document);
			}
		}
		
		documentDetail documentDetail = documentDetailService.getDocumentDetailByPrimaryKey(classroomId, studentId);
		documentDetail.setDd_document(document);
		documentDetailService.updateDocumentDetail(documentDetail);
		return "redirect:/student/classroom";
	}
	
	
	@GetMapping(value = "/classroom/editdocument")
	public String editDocument(@RequestParam(value = "classroom") String classroomId,
			@RequestParam(value = "document") String documentId,
			Model md) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		documentDetail documentDetail = documentDetailService.getDocumentDetailByPrimaryKey(classroomId, auth.getName());
		if(documentDetail != null && documentDetail.isCheck() == false) {
			document document = documentService.getDocumentById(documentDetail.getDd_document().getId());
			md.addAttribute("document", document);			
			return "editdocument";
		}
		
		return "classroom";
	}
	
	
	@PostMapping(value = "/classroom/editdocument",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public String confirmEditDocument(@RequestParam(value = "fdocument") MultipartFile fdocument,
			@RequestParam(value = "srcdocument") MultipartFile srcdocument,
			@ModelAttribute(value = "document") document newDocument,
			Model md) throws IllegalStateException, IOException {
		

		document updateDocument = documentService.getDocumentById(newDocument.getId());
		
		String pathFile = app.getRealPath("/static/upload/file");
		String pathSource = app.getRealPath("/static/upload/source");
		
		File folderFile = new File(pathFile);
		File folderSource = new File(pathSource);
	
		if (!folderFile.exists()) {
			folderFile.mkdirs();
		}
		
		if (!folderSource.exists()) {
			folderSource.mkdirs();
		}
		
		//Delete old file 
		String oldFileName = updateDocument.getFile();
		File deleteOldFile = new File(pathFile,oldFileName);
		deleteOldFile.delete();
	
		
		//Delete old source
		String oldSourceName = updateDocument.getSource();
		File deleteOldSource = new File(pathSource,oldSourceName);
		deleteOldSource.delete();
			
			
		// upload file to server 
		String fileName = fdocument.getOriginalFilename();
		File uploadFile = new File(pathFile, fileName);
		fdocument.transferTo(uploadFile);
		
		
		// upload source to server
		String sourceName = srcdocument.getOriginalFilename();
		File uploadSource = new File(pathSource, sourceName);
		srcdocument.transferTo(uploadSource);
	
		
		updateDocument.setFile(fileName);	
		updateDocument.setSource(sourceName);
		updateDocument.setName(newDocument.getName());
		updateDocument.setSummary(newDocument.getSummary());
		
		documentService.updateDocument(updateDocument);
		
		return "redirect:/student/classroom";
	}
	
	
	
}
