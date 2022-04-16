<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="row">   
 <nav aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="<c:url value="/student/"/>">Home</a></li>
     <li class="breadcrumb-item"><a href="<c:url value="/student/classroom"/>">Classroom</a></li>
    <li class="breadcrumb-item active" aria-current="page">Detail</li>
  </ol>
</nav>
    </div>
    
<c:set value="${teach}" var="t"/>
<h3>Chi tiết nhóm học phần</h3>
<div class="table-responsive">
<table class="table table-primary table-bordered border-dark text-center">
  <thead>
    <tr>
      <th scope="col">Mã môn</th>
      <th scope="col">Nhóm</th>
      <th scope="col">Tên môn</th>
      <th scope="col">Học kỳ</th>
      <th scope="col">Năm học</th>
    </tr>
  </thead>
  <tbody>
   
    <tr>
   	  <td scope="col">${t.te_classroom.cr_category.id}</td>
      <td scope="col">${t.te_classroom.name}</td>
      <td scope="col">${t.te_classroom.cr_category.name}</td>
   	  <td scope="col">${t.semester}</td>
      <td scope="col">${t.year}</td>
    </tr>
  </tbody>
</table>
</div>

<h3>Giảng viên hướng dẫn</h3>

<div class="row">
	<div class="col">
			<div class="card mb-3" >
				  <div class="row">
				    <div class="col-4">
				      <img src="<c:url value="/static/img/${t.te_teacher.image}"/>" class="card-img" >
				    </div>	    
				    <div class="col-8">
				      <div class="card-body">
				
					    <p class="card-text"><strong>Họ tên:</strong> ${t.te_teacher.name}</p>
					    <p class="card-text"><strong>Mail:</strong> ${t.te_teacher.mail}</p>
					    <p class="card-text"><strong>Học vị:</strong>${t.te_teacher.degree}</p>
				        <p class="card-text"><strong>Số điện thoại:</strong> ${t.te_teacher.phone}</p>
				        
				        
				 
				      </div>
				    </div>
				  </div>
				</div>
	</div>
</div>
