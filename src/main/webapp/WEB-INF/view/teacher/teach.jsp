<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="row">   
 <nav aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="<c:url value="/teacher/"/>">Home</a></li>
    <li class="breadcrumb-item active" aria-current="page">Teach</li>
  </ol>
</nav>
</div>

<form action="<c:url value="/teacher/teach"/>" method="post">
 <div class="row">
  
  <div class="col-lg-4">
       <label class="text-dark">Năm học</label>
      <select class="form-select" name="year">
 		<c:forEach items="${year}" var="y">
   			 <c:if test="${param.year != y.value}">
 					<option  value="${y.id}" >${y.value}</option>
 			</c:if>
 			<c:if test="${param.year == y.value }">
 				<option selected="selected" value="${y.id}" >${y.value}</option>
 			</c:if>
 		</c:forEach>
 	</select>
    </div>
  
  <div class="col-lg-4">
      <label class="text-dark">Học kỳ</label>
      <select class="form-select" name="semester">
 		<c:forEach items="${semester}" var="s">
 			
 			 <c:if test="${param.semester != s.value}">
 					<option  value="${s.id}" >${s.value}</option>
 			</c:if>
 			<c:if test="${param.semester == s.value }">
 				<option selected="selected" value="${s.id}" >${s.value}</option>
 			</c:if>
 			
 		</c:forEach>
 	</select>
    </div>
     
  </div>
<input class="btn btn-primary mt-3" type="submit" value="Search">
</form>


${notify}
<c:if test="${!empty teach}">
<div class="table-responsive mt-3">
<table class="table table-primary table-bordered border-dark text-center">
  <thead>
    <tr>
      <th scope="col">Mã môn</th>
      <th scope="col">Nhóm</th>
      <th scope="col">Tên môn</th>
      <th scope="col">Năm</th>
      <th scope="col">Học kỳ</th>
      <th scope="col">Chi tiết</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach items="${teach}" var="t" >
    <tr>
      <td scope="col">${t.te_classroom.cr_category.id}</td>
      <td scope="col">${t.te_classroom.name}</td>
      <td scope="col">${t.te_classroom.cr_category.name}</td>
	  <td scope="col">${t.year}</td>
	  <td scope="col">${t.semester}</td>
	  <td scope="col"><a href="<c:url value="/teacher/teach/classroom?classroom=${t.te_classroom.id}"/>" class="text-dark text-decoration-none">
	  	<i class="fa-solid fa-eye"></i></a>
	  </td>  
	  
    </tr>
   </c:forEach>
  </tbody>
</table>
</div>
</c:if>
