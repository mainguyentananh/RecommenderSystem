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
    <li class="breadcrumb-item active" aria-current="page">${listdocument[0].d_category.id}</li>
  </ol>
</nav>
    </div>

<h3>${listdocument[0].d_category.name}</h3>
<div class="row mb-3">
	<c:forEach items="${listdocument}" var="list">
		<div class="col-6">
		<div class="card mt-2">
			<div class="row">
				<div class="col-lg-2 col-md-4 mt-3">
					<img src="<c:url value="/static/img/document.jpg"/>"
						class="card-img" >
				</div>
				<div class="col-lg-10 col-md-8 ">
					<div class="card-body">

						<h5 class="card-title text-dark"><a href="<c:url value="/document/${list.id}"/>" class="link-primary text-decoration-none">${list.name}</a></h5>
						<p class="card-text">
							<strong>Sinh viên thực hiên:</strong> ${list.d_student.name}
						</p>
						<p class="card-text">
							<strong>Giảng viên hướng dẫn:</strong> ${list.d_teacher.name}
						</p>
						<p class="card-text">
							<strong>Học phần:</strong> ${list.d_category.id} - ${list.d_category.name}
						</p>
					</div>
				</div>			
			</div>
		</div>
		</div>
		</c:forEach>
	
</div>
