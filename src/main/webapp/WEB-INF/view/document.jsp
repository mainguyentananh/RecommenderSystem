<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<div class="row">
	<nav aria-label="breadcrumb">
		<ol class="breadcrumb">
			<li class="breadcrumb-item active" aria-current="page">Document</li>
		</ol>
	</nav>
</div>

<div class="row mb-3">
	<div class="col-lg-6 col-md-12 col-sm-12">
		<div class="card mb-1">
			<div class="row">
				<div class="col-lg-2 col-md-4 col-sm-8">
					<img src="<c:url value="/static/img/document.jpg"/>"
						class="card-img mt-3">
						<!-- Star -->
						<p class="card-text text-center">
							<c:forEach begin="1" end="5" step="1" var="i">		
									<c:if test="${i <= star}">
									<a href="<c:url value="/document/${document.id}/star?score=${i}"/>"><span class="fa fa-star checked"></span></a>
									</c:if>
									<c:if test="${i > star}">
										<a href="<c:url value="/document/${document.id}/star?score=${i}"/>"><span class="fa fa-star text-dark"></span></a>
									</c:if>
							</c:forEach>
					
							<a
								href="<c:url value="/document/download/file/${document.id}"/>"
								class="link-primary text-decoration-none">Download File Pdf</a>
							<sec:authorize
								access="hasRole('ROLE_TEACHER') or hasRole('ROLE_ADMIN')">
				        <a
									href="<c:url value="/document/download/source/${document.id}"/>"
									class="link-primary text-decoration-none">Download Source
									Code</a>
							</sec:authorize>
						</p>
				</div>
				<div class="col-lg-10 col-md-8 col-sm-4">
					<div class="card-body">

						<h5 class="card-title text-dark">${document.name}</h5>
						<strong>Sinh viên thực hiên:</strong> ${document.d_student.name}
						<br/>
						<strong>Giảng viên hướng dẫn:</strong> ${document.d_teacher.name}
						<br/>
						<strong>Học phần:</strong> ${document.d_category.id}-${document.d_category.name}
						<br/>
						<strong>Tóm tắt:</strong>
						<p class="card-text listComment">
							 ${document.summary}
						</p>
						
					
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="col-lg-6 col-md-12 col-sm-12">
	<strong>Các bình luận về tài liệu</strong>
	<c:if test="${!empty listFeedback}">
	<div class="listComment">
		<c:forEach items="${listFeedback}" var="l">
		<c:if test="${!empty l.comment}">
				<strong>${l.f_account.username}:</strong> ${l.comment}
		<br />
	</c:if>
		</c:forEach>
	</div>
	</c:if>

<form action="<c:url value="/document/${document.id}"/>"
	method="POST" class="mt-3">
	<div class="form-floating">
		<textarea class="form-control" name="comment"
			placeholder="Leave a comment here" id="floatingTextarea2"
			style="height: 100px" required="required"></textarea>
		<label for="floatingTextarea2">Comment</label>
	</div>
	<input class="btn btn-primary mt-2" type="submit" value="Comment">
</form>

	</div>
	
	
</div>
<strong >Gợi ý</strong>
<div class="row mb-3">
	<c:forEach items="${listRecommendDocument}" var="list">
				<div class="col-lg-2 col-md-4 col-sm-6">
						<div class="card bg-light border-secondary mb-3">
						<div class="card-body">
						<h5 class=" text-dark"><a href="<c:url value="/document/${list.id}"/>" class="link-primary text-decoration-none">${list.name}</a></h5>
							<strong>SVTH:</strong> ${list.d_student.name}<br/>
							<strong>GVHD:</strong> ${list.d_teacher.name}<br/>
							<strong>HP:</strong> ${list.d_category.id} - ${list.d_category.name}
						</div>
						</div>
					</div>
		</c:forEach>
	</div>




