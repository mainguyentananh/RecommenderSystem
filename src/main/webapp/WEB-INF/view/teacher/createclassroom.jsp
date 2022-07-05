<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  <%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<div class="row">   
 <nav aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="<c:url value="/teacher/"/>">Home</a></li>
    <li class="breadcrumb-item active" aria-current="page">Create Classroom</li>
  </ol>
</nav>
    </div>
<h4>Khởi tạo thông qua file excel</h4>
<sec:authorize access="hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')">
<span>File excel chứa thông tin đăng nhập vào hệ thống của sinh viên sau khi được khởi tạo: </span>
<a href="<c:url value="/teacher/download/file/excel"/>"class="link-primary text-decoration-none">Download File Excel</a>
</sec:authorize>
<form action="<c:url value="/teacher/createclassroom"/>"  method="POST" enctype="multipart/form-data">
	<div class="col-8">
	<div class="input-group">
	  <input type="file" name="filename" class="form-control" required="required">
	  <input type="submit" value="Upload" class="btn btn-primary"/>
	</div>
	</div>
</form>
${notify}