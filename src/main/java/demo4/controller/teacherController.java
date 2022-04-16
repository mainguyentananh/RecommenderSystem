package demo4.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import demo4.model.account_role;
import demo4.model.category;
import demo4.model.classroom;
import demo4.model.document;
import demo4.model.documentDetail;
import demo4.model.documentDetail.pk_documentDetail;
import demo4.model.student;
import demo4.model.teach;
import demo4.model.teach.pk_teach;
import demo4.model.teacher;
import demo4.service.accountService;
import demo4.service.account_roleService;
import demo4.service.categoryService;
import demo4.service.classroomService;
import demo4.service.documentDetailService;
import demo4.service.documentService;
import demo4.service.mailService;
import demo4.service.roleService;
import demo4.service.studentService;
import demo4.service.teachService;
import demo4.service.teacherService;
import demo4.service.userPrincipal;

@Controller
@RequestMapping(value = "/teacher")
public class teacherController {

	@Autowired
	private accountService accountService;

	@Autowired
	private account_roleService account_roleService;

	@Autowired
	private roleService roleService;

	@Autowired
	private teacherService teacherService;

	@Autowired
	private categoryService categoryService;

	@Autowired
	private classroomService classroomService;

	@Autowired
	private teachService teachService;

	@Autowired
	private studentService studentService;

	@Autowired
	private documentDetailService documentDetailService;

	@Autowired
	private mailService mailService;
	
	@Autowired
	private documentService documentService;

	@Autowired
	private ServletContext app;

	@GetMapping(value = "/")
	public String home(Model md) {
	List<category> listCategory = categoryService.getAllCategory();
	List<List<document>> listDocument = new ArrayList<List<document>>();
	
	for (category category : listCategory) {
		List<document> document = documentService.getDocumentByCategoryForHome(category.getId());
		listDocument.add(document);
	}
	md.addAttribute("listDocument", listDocument);
		return "home";
	}

	/* Profile */

	@GetMapping(value = "/profile")
	public String profile(Model md) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		teacher teacher = teacherService.getTeacherById(auth.getName());
		md.addAttribute("info", teacher);
		return "tprofile";
	}

	@GetMapping(value = "/profile/changepassword")
	public String changePassword() {
		return "changepassword";
	}

	@PostMapping(value = "/profile/changepassword")
	public String confirmChangePassword(@RequestParam(name = "password") String password,
			@RequestParam(name = "npassword") String npassword, @RequestParam(name = "cpassword") String cpassword,
			Model md) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		userPrincipal user = (userPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (encoder.matches(password, user.getPassword())) {
			if (npassword.equalsIgnoreCase(cpassword)) {
				account ac = accountService.getAccountById(user.getId());
				ac.setPassword(encoder.encode(npassword));
				accountService.updateAccount(ac);
				md.addAttribute("notify",
						"<div class=\"alert alert-success text-center col-4 mt-2\" role=\"alert\">Thay đổi mật khẩu thành công</div>");
				return "changepassword";
			}
		}

		md.addAttribute("notify",
				"<div class=\"alert alert-danger text-center col-4 mt-2\" role=\"alert\">Thay đổi mật khẩu không thành công</div>");
		return "changepassword";
	}

	@GetMapping(value = "/profile/editprofile")
	public String editProfile(Model md) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		teacher teacher = teacherService.getTeacherById(auth.getName());
		md.addAttribute("teacher", teacher);
		return "editprofile";
	}

	@PostMapping(value = "/profile/editprofile", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public String confirmEditprofile(@ModelAttribute(value = "teacher") teacher teacher,
			@RequestParam(value = "fileImage") MultipartFile file, Model md) throws IllegalStateException, IOException {
		
		
		String path = app.getRealPath("/static/img");
		
		//check, create folder
		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		
		if(!file.isEmpty()) {
			
			//delete oldFile
			File oldImage = new File(path,teacher.getImage());
			oldImage.delete(); 
			
			String fileName = file.getOriginalFilename();
			File uploadFile = new File(path, fileName);
			file.transferTo(uploadFile);
			teacher.setImage(fileName);
			
		}
		
		teacherService.updateTeacher(teacher);
		
		return "redirect:/teacher/profile";
	}

	/* Create Class Room */
	@GetMapping(value = "/createclassroom")
	public String createClassRoom() {
		return "createclassroom";
	}

	@PostMapping(value = "/createclassroom", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public String confirmCreateClassRoom(@RequestParam(value = "filename") MultipartFile file, Model md)
			throws IllegalStateException, IOException, InvalidFormatException, MessagingException {
		String path = app.getRealPath("/static/upload/excel");
		File folder = new File(path);

		if (!folder.exists()) {
			folder.mkdirs();
		}
		if (!file.isEmpty()) {
			// upload file to server
			String fileName = file.getOriginalFilename();
			File uploadFile = new File(path, fileName);
			file.transferTo(uploadFile);

			// read file from server
			String pathFile = path + "/" + fileName;
			File readFile = new File(pathFile);
			try {
				readExcel(readFile);

			} catch (Exception e) {
				md.addAttribute("notify",
						"<div class=\"alert alert-warning text-center col-4 mt-2\" role=\"alert\">Định dạng file không đúng</div>");
				return "createclassroom";
			}

			md.addAttribute("notify",
					"<div class=\"alert alert-success text-center col-4 mt-2\" role=\"alert\">Khởi tạo thành công</div>");
			return "createclassroom";
		}
		return "createclassroom";
	}

	public static String generateRandomPassword(int len) {
		final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		return RandomStringUtils.random(len, chars);
	}

	// handle 6 table
	private void readExcel(File readFile) throws InvalidFormatException, IOException, MessagingException {
		Workbook workbook = new XSSFWorkbook(readFile);
		Sheet datatypeSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = datatypeSheet.iterator();
		String readTeacherId = null;
		String readCategoryId = null;
		String readClassroomName = null;
		String readYear = null;
		String readSemester = null;
		String classroomId = null;
		String readStudentId, mail, phoneNumber, fullName;
		String[] splitName;
		String edu = "@student.ctu.edu.vn";
		while (iterator.hasNext()) {

			classroom classroom = new classroom();
			teach teach = new teach();
			documentDetail documentDetail = new documentDetail();
			Row row = iterator.next();

			// Get data according to row
			if (row.getRowNum() == 2) {
				readTeacherId = row.getCell(1).toString().replaceAll(".0$", "");
			}
			if (row.getRowNum() == 3) {
				readCategoryId = row.getCell(1).toString();
				readClassroomName = row.getCell(3).toString().replaceAll(".0$", "");
			}
			if (row.getRowNum() == 4) {
				readYear = row.getCell(1).toString();
				readSemester = row.getCell(3).toString().replaceAll(".0$", "");
				classroomId = readTeacherId + readCategoryId + readClassroomName + readYear.replaceAll("-", "")
						+ readSemester;

				// handle table classroom
				classroom.setId(classroomId);
				classroom.setName(readCategoryId + " nhóm " + readClassroomName);
				classroom.setCr_category(categoryService.getCategoryById(readCategoryId));

				// check table classroom
				if (!classroomService.checkClassroomById(classroomId)) {
					classroomService.saveClassroom(classroom);
				}

				// handle table teach
				pk_teach pk_teach = new pk_teach();
				pk_teach.setTeacher_id(readTeacherId);
				pk_teach.setClassroom_id(classroomId);
				teach.setPk_teach(pk_teach);
				teach.setSemester(readSemester);
				teach.setYear(readYear);
				teach.setTe_teacher(teacherService.getTeacherById(readTeacherId));
				teach.setTe_classroom(classroomService.getClassroomById(classroomId));

				// check table teach
				if (!teachService.checkTeachClassRoom(readTeacherId, classroomId)) {
					teachService.saveTeach(teach);
				}

			}

			if (row.getRowNum() > 6) {
				readStudentId = row.getCell(1).toString();
				// check and create student,account
				if (!studentService.checkStudent(readStudentId)) {
					fullName = row.getCell(2).toString().trim();
					phoneNumber = row.getCell(4).toString();
					splitName = fullName.split(" ");
					mail = splitName[splitName.length - 1] + readStudentId + edu;

					// handle table student
					student student = new student();
					student.setId(readStudentId);
					student.setPhone(phoneNumber);
					student.setName(fullName);
					student.setMail(mail);
					studentService.saveStudent(student);

					// handle table account
					BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
					String passwordAccount = generateRandomPassword(8);
					account account = new account();
					account.setUsername(readStudentId);
					account.setPassword(encoder.encode(passwordAccount));

					try {
						accountService.saveAccount(account);
					} catch (Exception e) {
						accountService.saveAccount(account);
					}

					// handle table account_role
					account_role account_role = new account_role();
					account_role.setAccount(accountService.findAccountByUserName(readStudentId));
					account_role.setRole(roleService.findRoleByRoleName("ROLE_STUDENT"));
					account_roleService.saveAccount_Role(account_role);

					// send account for student by mail
					String subject = "Computer Science - Can Tho University";
					String content = "Dear " + readStudentId + ". Login information to the system with username: "
							+ readStudentId + "and password: " + passwordAccount;
					 mailService.sendEmail("mainguyentananh@gmail.com", mail, subject, content);

				}

				// handle table documentdetail
				pk_documentDetail pk_documentDetail = new pk_documentDetail();
				pk_documentDetail.setClassroom_id(classroomId);
				pk_documentDetail.setStudent_id(readStudentId);
				documentDetail.setPk_documentDetail(pk_documentDetail);
				documentDetail.setDd_classroom(classroomService.getClassroomById(classroomId));
				documentDetail.setDd_student(studentService.getStudentById(readStudentId));
				if(!documentDetailService.checkDocumentDetailByPrimaryKey(classroomId, readStudentId)) {
					documentDetailService.saveDocumentDetail(documentDetail);
				}
			}

		}
		workbook.close();
	}

	/* Teach */
	@GetMapping(value = "/teach")
	public String teach() {
		return "teach";
	}

	@PostMapping(value = "/teach")
	public String findTeach(@RequestParam(value = "year") String year,
			@RequestParam(value = "semester") String semester, Model md) {
		int afterYear = Integer.parseInt(year) - 1;
		String keyYear = String.valueOf(afterYear) + "-" + year;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<teach> teach = teachService.getTeachByYearAndSemester(auth.getName(), keyYear, semester);
		if (!teach.isEmpty()) {
			md.addAttribute("teach", teach);
		} else {
			md.addAttribute("notify",
					"<div class=\"alert alert-warning text-center col-4 mt-2\" role=\"alert\">Không có nhóm do giảng viên hướng dẫn</div>");
		}

		return "teach";
	}

	@GetMapping(value = "/teach/classroom")
	public String teachClassroom(@RequestParam(value = "classroom") String classroomId, Model md) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// check teacher have teach classroom
		if (teachService.checkTeachClassRoom(auth.getName(), classroomId)) {
			List<documentDetail> documentDetail = documentDetailService.getDocumentDetailByClassroomId(classroomId);
			md.addAttribute("documentDetail", documentDetail);
			return "teachclassroom";
		}
		md.addAttribute("notify",
				"<div class=\"alert alert-warning text-center col-4 mt-2\" role=\"alert\">Không có nhóm do giảng viên hướng dẫn</div>");
		return "teach";
	}

	@GetMapping(value = "/teach/classroom/lock")
	public String lockStudentClassroom(@RequestParam(value = "classroom") String classroomId,
			@RequestParam(value = "student") String studentId, Model md) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// check teacher have teach classroom
		if (teachService.checkTeachClassRoom(auth.getName(), classroomId)) {
			documentDetail documentDetail = documentDetailService.getDocumentDetailByPrimaryKey(classroomId, studentId);
			documentDetail.setCheck(true);
			documentDetailService.updateDocumentDetail(documentDetail);
			return "redirect:/teacher/teach/classroom?classroom=" + classroomId;
		}
		md.addAttribute("notify",
				"<div class=\"alert alert-warning text-center col-4 mt-2\" role=\"alert\">Không có nhóm do giảng viên hướng dẫn</div>");
		return "teach";
	}

	@GetMapping(value = "/teach/classroom/unlock")
	public String unlockStudentClassroom(@RequestParam(value = "classroom") String classroomId,
			@RequestParam(value = "student") String studentId, Model md) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// check teacher have teach classroom
		if (teachService.checkTeachClassRoom(auth.getName(), classroomId)) {
			documentDetail documentDetail = documentDetailService.getDocumentDetailByPrimaryKey(classroomId, studentId);
			documentDetail.setCheck(false);
			documentDetailService.updateDocumentDetail(documentDetail);
			return "redirect:/teacher/teach/classroom?classroom=" + classroomId;
		}
		md.addAttribute("notify",
				"<div class=\"alert alert-warning text-center col-4 mt-2\" role=\"alert\">Không có nhóm do giảng viên hướng dẫn</div>");
		return "teach";
	}

	@GetMapping(value = "/teach/classroom/lockall")
	public String lockAllStudentClassroom(@RequestParam(value = "classroom") String classroomId, Model md) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// check teacher have teach classroom
		if (teachService.checkTeachClassRoom(auth.getName(), classroomId)) {
			List<documentDetail> listDocumentDetail = documentDetailService.getDocumentDetailByClassroomId(classroomId);
			for (documentDetail documentDetail : listDocumentDetail) {
				documentDetail.setCheck(true);
				documentDetailService.updateDocumentDetail(documentDetail);
			}

			return "redirect:/teacher/teach/classroom?classroom=" + classroomId;
		}
		md.addAttribute("notify",
				"<div class=\"alert alert-warning text-center col-4 mt-2\" role=\"alert\">Không có nhóm do giảng viên hướng dẫn</div>");
		return "teach";
	}

	@GetMapping(value = "/teach/classroom/unlockall")
	public String unlockAllStudentClassroom(@RequestParam(value = "classroom") String classroomId, Model md) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// check teacher have teach classroom
		if (teachService.checkTeachClassRoom(auth.getName(), classroomId)) {
			List<documentDetail> listDocumentDetail = documentDetailService.getDocumentDetailByClassroomId(classroomId);
			for (documentDetail documentDetail : listDocumentDetail) {
				documentDetail.setCheck(false);
				documentDetailService.updateDocumentDetail(documentDetail);
			}

			return "redirect:/teacher/teach/classroom?classroom=" + classroomId;
		}
		md.addAttribute("notify",
				"<div class=\"alert alert-warning text-center col-4 mt-2\" role=\"alert\">Không có nhóm do giảng viên hướng dẫn</div>");
		return "teach";
	}
}
