<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<div class="row">   
 <nav aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item active" aria-current="page">Search</li>
  </ol>
</nav>
</div>
<div class="row">
<div class="col-8">
	<strong>Kết quả tìm kiếm: ${param.search}</strong>
	<c:forEach items="${list}" var="list">
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
	</c:forEach>
</div>
<div class="col-4">
	<strong>Gợi ý</strong>
		<c:forEach items="${listRecommend}" var="lr">
		<div class="card mt-2 bg-light border-secondary">
			<div class="row">
				<div class="col-lg-10 col-md-8 ">
					<div class="card-body">

						<h5 class="card-title text-dark"><a href="<c:url value="/document/${lr.id}"/>" class="link-primary text-decoration-none">${lr.name}</a></h5>
						<p class="card-text">
							<strong>Học phần:</strong> ${lr.d_category.id} - ${lr.d_category.name}
						</p>
					</div>
				</div>			
			</div>
		</div>
	</c:forEach>
</div>
</div>