<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	
<sec:authorize access="hasRole('ROLE_STUDENT')">
	<c:set value="student" var="url"/>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_TEACHER') or hasRole('ROLE_ADMIN')">
	<c:set value="teacher" var="url"/>
</sec:authorize>

<div class="row">   
 <nav aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="<c:url value="/${url}/"/>">Home</a></li>
    <li class="breadcrumb-item"><a href="<c:url value="/${url}/profile"/>">Profile</a></li>
    <li class="breadcrumb-item active" aria-current="page">Change Password</li>
  </ol>
</nav>
</div>


<form action="<c:url value="/${url}/profile/changepassword"/>" method="POST">
 <div class="row">
    <div class="col-lg-4 ">
      <label class="text-dark">Mật khẩu cũ</label>
      <input type="password" class="form-control" required name="password"/>
    </div>
    <div class="col-lg-4 ">
      <label class="text-dark">Mật khẩu mới</label>
      <input type="password" class="form-control" required name="npassword"/>
    </div>
    <div class="col-lg-4 ">
      <label class="text-dark">Nhập lại mật khẩu</label>
      <input type="password" class="form-control" required name="cpassword"/>
    </div>
  </div>
  <input class="btn btn-primary mt-2" type="submit" value="Xác nhận">
</form>
${notify}