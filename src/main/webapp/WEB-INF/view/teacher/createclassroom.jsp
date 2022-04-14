<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="row">   
 <nav aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="<c:url value="/teacher/"/>">Home</a></li>
    <li class="breadcrumb-item active" aria-current="page">Create Classroom</li>
  </ol>
</nav>
    </div>
<h4>Khởi tạo thông qua file excel</h4>
<form action="<c:url value="/teacher/createclassroom"/>"  method="POST" enctype="multipart/form-data">
	<div class="col-8">
	<div class="input-group">
	  <input type="file" name="filename" class="form-control" required="required">
	  <input type="submit" value="Upload" class="btn btn-primary"/>
	</div>
	</div>
</form>
${notify}