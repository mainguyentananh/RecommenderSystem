<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="row">   
 <nav aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="<c:url value="/student/"/>">Home</a></li>
    <li class="breadcrumb-item"><a href="<c:url value="/student/classroom"/>">Classroom</a></li>
    <li class="breadcrumb-item active" aria-current="page">Submit Document</li>
  </ol>
</nav>
    </div>
<c:set value="CT595" var="luanVan"/>
<c:url value="/student/classroom/submitdocument" var="url"/>

<form:form action="${url}" modelAttribute="document" enctype="multipart/form-data" method="POST">
	 <div class="row">
    <div class="col-lg-6">
      <label class="text-dark">Tên tài liệu</label>
      <form:input path="name" class="form-control" required="true"/>
    </div>
    
    <div class="col-lg-3">
      <label class="text-dark">Tập tin tài liệu <span class="text-danger">(pdf)</span></label>
      <input type="file" name="fdocument" class="form-control" required accept=".pdf"/>
    </div>
      
      <div class="col-lg-3">
      <label class="text-dark">Mã nguồn tài liệu <span class="text-danger">(rar)</span></label>
      <input type="file" name="srcdocument" class="form-control" required accept=".rar"/>
    </div>
    
  </div>
	
	<c:if test="${teach.te_classroom.cr_category.id == luanVan}">
	<div class="row">
    <div class="col-lg-4">
      <label class="text-dark">Thành viên hội đồng bảo vệ (1)</label>
      <form:input path="council1" class="form-control" required="true"/>
    </div>
  	  <div class="col-lg-4">
      <label class="text-dark">Thành viên hội đồng bảo vệ (2)</label>
      <form:input path="council2" class="form-control" required="true"/>
    </div>
    <div class="col-lg-4">
      <label class="text-dark">Thành viên hội đồng bảo vệ (3)</label>
      <form:input path="council3" class="form-control" required="true"/>
    </div>
  </div>
	</c:if>
	<div class="row">
	 <div class="col-lg-12">
      <label class="text-dark">Tóm tắt</label>
    <form:textarea path="summary" class="form-control" required="true" style="height: 100px;"/>
    </div>
	</div>

      <input type="hidden" readonly="readonly" name="categoryId" value="${documentDetail.dd_classroom.cr_category.id}" class="form-control"/>  	  
      <input type="hidden" readonly="readonly" name="teacherId" value="${teach.te_teacher.id}" class="form-control"/>
      <input type="hidden" readonly="readonly" name="studentId" value="${documentDetail.dd_student.id}" class="form-control"/>
       <input type="hidden" readonly="readonly" name="classroomId" value="${documentDetail.pk_documentDetail.classroom_id}" class="form-control"/>
     
	<input class="btn btn-primary mt-2" type="submit" value="Submit">
	
</form:form>