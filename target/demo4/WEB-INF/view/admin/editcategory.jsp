<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="row">   
 <nav aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="<c:url value="/teacher/"/>">Home</a></li>
      <li class="breadcrumb-item"><a href="<c:url value="/teacher/category"/>">Category</a></li>
    <li class="breadcrumb-item active" aria-current="page">Edit Category</li>
  </ol>
</nav>
</div>
<c:url value="/admin/category/edit" var="url"/> 
<form:form action="${url}" modelAttribute="category">
	 <div class="row">
    <div class="col-lg-6">
      <label class="text-dark">Mã học phần</label>
       <form:input path="id" class="form-control" required="true"/>
    </div>
    <div class="col-lg-6 ">
      <label class="text-dark">Tên học phần</label>
      <form:input path="name" class="form-control" required="true"/>
    </div>
  </div>

<input class="btn btn-primary mt-2" type="submit" value="Edit">
</form:form>