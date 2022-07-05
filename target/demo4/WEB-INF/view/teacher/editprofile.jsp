<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="row">   
 <nav aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="<c:url value="/teacher/"/>">Home</a></li>
    <li class="breadcrumb-item"><a href="<c:url value="/teacher/profile"/>">Profile</a></li>
    <li class="breadcrumb-item active" aria-current="page">Edit Profile</li>
  </ol>
</nav>
    </div>


<c:url value="/teacher/profile/editprofile" var="url"/>
<form:form action="${url}" modelAttribute="teacher" enctype="multipart/form-data" method="POST">
	<form:hidden path="id"/>
	
	 <div class="row">
    <div class="col-lg-6">
      <label class="text-dark">Họ tên</label>
       <form:input path="name" class="form-control" required="true"/>
    </div>
    <div class="col-lg-6 ">
      <label class="text-dark">Mail</label>
      <form:input path="mail" class="form-control" required="true"/>
    </div>
  </div>
	 <div class="row">
    <div class="col-lg-6">
      <label class="text-dark">Học vị</label>
      <form:input path="degree" class="form-control" />
    </div>
    <div class="col-lg-6 ">
      <label class="text-dark">Ghi chú</label>
 <form:input path="note" class="form-control" />
    </div>
  </div>
	<div class="row">
    <div class="col-lg-6">
      <label class="text-dark">Số điện thoại</label>
       <form:input path="phone" class="form-control" required="true"/>
    </div>
    <div class="col-lg-6 ">
      <label class="text-dark">Ảnh</label>
   	 <form:hidden path="image"/>
      <input type="file" name="fileImage" class="form-control" />  
    </div>
  </div>

<input class="btn btn-primary mt-2" type="submit" value="Edit">
</form:form>

