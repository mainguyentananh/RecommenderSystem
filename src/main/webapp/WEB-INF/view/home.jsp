<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<div class="row">
	<nav aria-label="breadcrumb">
		<ol class="breadcrumb">
			<li class="breadcrumb-item active" aria-current="page">Home</li>
		</ol>
	</nav>
</div>


<c:forEach items="${listDocument}" var="document">
	<div class="col mt-3">
		<h4 class="mb-1">${document[0].d_category.name}</h4>
		<div class="list-group">
			<c:forEach items="${document}" var="d">
				  <a href="<c:url value="/document/${d.id}"/>" class="list-group-item list-group-item-action">
				  	<div class="row">
				  		<div class="col-lg-2 col-md-4 ">
				  				   <img src="<c:url value="/static/img/document.jpg"/>"
						class="card-img" style="width: 100px;height: 100px;">
				  		</div>
				  	
				  		<div class="col-lg-10 col-md-8 ">
					    <div class="d-flex w-100 justify-content-between">
					      <h5 class="mb-1">${d.name}</h5>
					     </div>
							<div class="mt-1">
							<strong>Sinh viên thực hiên:</strong> ${d.d_student.name}<br/>
							<strong>Giảng viên hướng dẫn:</strong> ${d.d_teacher.name}
						</div>
					    </div>
				    </div>
				    </a>
		 	</c:forEach>
		 	<a href="<c:url value="/document/category?id=${document[0].d_category.id}"/>" class="list-group-item list-group-item-action">
				     <small class="text-primary">Xem tất cả</small>
				  </a>
		</div>
	</div>	
</c:forEach>


