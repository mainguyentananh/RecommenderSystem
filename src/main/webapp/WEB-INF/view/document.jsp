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
<div class="row">
	<div class="col">
		<form class="text-center">
			<span>Tìm kiếm tài liệu: </span> <input class="col-3" type="text"
				id="input-search" name="search" required placeholder="search">
			<input class="btn btn-sm mb-1 btn-primary" type="submit"
				value="search" id="submit">

		</form>
	</div>
</div>
<div class="row">
	<div class="col">
			<div class="card mb-3" >
				  <div class="row">
				    <div class="col-lg-2 col-md-4 col-sm-8">
				      <img src="<c:url value="/static/img/document.jpg"/>" class="card-img" >
				    </div>	    
				    <div class="col-lg-10 col-md-8 col-sm-4">
				      <div class="card-body">
				    
				        <h5 class="card-title text-dark">${document.name}</h5>
				    <p class="card-text"><strong>Sinh viên thực hiên:</strong> ${document.d_student.name}</p>
				    <p class="card-text"><strong>Giảng viên hướng dẫn:</strong> ${document.d_teacher.name}</p>
				    <p class="card-text"><strong>Học phần:</strong> ${document.d_category.id} - ${document.d_category.name}</p>
				 
				        <p class="card-text"><strong>Tóm tắt:</strong> ${document.summary}</p>
				        
				        
				     <p class="card-text"><a href="<c:url value="/search/document/download/${document.id}"/>" class="link-primary text-decoration-none">Download File Pdf</a></p>
				   <sec:authorize access="hasRole('ROLE_TEACHER') or hasRole('ROLE_ADMIN')">
				         <p class="card-text"><small class="text-muted">Download Source Code</small></p>
				     </sec:authorize>
				      </div>
				    </div>
				  </div>
				</div>
	</div>
</div>

<script type="text/javascript">
 function isEmpty(str){
	    return !str.trim().length;
	  }

	  document.getElementById("input-search").addEventListener("input", function() {
	  if( isEmpty(this.value) ) {
	   document.getElementById('submit').disabled =true;
	  } else{
	      document.getElementById('submit').disabled =false;
	  }
	});
	  </script>