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

<hr/>
<h3>Giảng viên hướng dẫn</h3>
<div class="table-responsive">
<table class="table table-primary table-bordered border-dark text-center">
  <thead>
    <tr>
      <th scope="col">Họ tên</th>
      <th scope="col">Mail</th>
      <th scope="col">Học vị</th>
      <th scope="col">Số điện thoại</th>
      <th scope="col">Ảnh</th>
    </tr>
  </thead>
  <tbody>
   
    <tr>
   	  <td scope="col">${t.te_teacher.name}</td>
      <td scope="col">${t.te_teacher.mail}</td>
      <td scope="col">${t.te_teacher.degree}</td>
   	  <td scope="col">${t.te_teacher.phone}</td>
      <td scope="col"><img style="width: 150px; height: 150px;" src="<c:url value="/static/img/${t.te_teacher.image}"/>"/></td>
    </tr>
  </tbody>
</table>
</div>

