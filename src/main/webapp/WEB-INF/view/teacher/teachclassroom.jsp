<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="row">   
 <nav aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="<c:url value="/teacher/"/>">Home</a></li>
     <li class="breadcrumb-item"><a href="<c:url value="/teacher/teach"/>">Teach</a></li>
    <li class="breadcrumb-item active" aria-current="page">Classroom</li>
  </ol>
</nav>
</div>

<div class="table-responsive mt-3">
<table class="table table-primary table-bordered border-dark text-center">
  <thead>
    <tr>
      <th scope="col">Mã số</th>
      <th scope="col">Họ tên</th>
      <th scope="col">Mail</th>
      <th scope="col">Số điện thoại</th>
      <th scope="col">Tài liệu</th>
      <th scope="col">Trạng thái</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach items="${documentDetail}" var="dd" >
    <c:set value="${dd.dd_classroom.id}" var="classroomId"/>
    <tr>
      <td scope="col">${dd.dd_student.id}</td>
      <td scope="col">${dd.dd_student.name}</td>
      <td scope="col">${dd.dd_student.mail}</td>
	  <td scope="col">${dd.dd_student.phone}</td>
	  
	  <c:if test="${empty dd.dd_document}">
	  	<td scope="col">Chưa nộp</td>
	  </c:if>
	  <c:if test="${!empty dd.dd_document}">
	  	<td scope="col">
	  	<a href="<c:url value="/document/${dd.dd_document.id}"/>" class="link-primary text-decoration-none">Đã nộp</a>
	  	</td>
	  </c:if>
	  <c:if test="${dd.check == true}">
	  <td scope="col"><a href="<c:url value="/teacher/teach/classroom/unlock?classroom=${dd.dd_classroom.id}&student=${dd.dd_student.id}"/>" class="text-dark text-decoration-none">
	 	 <i class="fa-solid fa-lock"></i></a>
	  </td>
	  </c:if>
	  
	  <c:if test="${dd.check == false}">
	  <td scope="col"><a href="<c:url value="/teacher/teach/classroom/lock?classroom=${dd.dd_classroom.id}&student=${dd.dd_student.id}"/>" class="text-dark text-decoration-none">
	 	 <i class="fa-solid fa-lock-open"></i></a>
	  </td>
	  </c:if>
	
    </tr>
   </c:forEach>
  </tbody>
   <tfoot>
   <tr>
   <td colspan="3" ></td>
  	<th scope="col">Tất cả</th>
  	<td scope="col"><a href="<c:url value="/teacher/teach/classroom/lockall?classroom=${classroomId}"/>" class="text-dark text-decoration-none">
	 	 <i class="fa-solid fa-lock"></i></a></td>
  	<td scope="col"><a href="<c:url value="/teacher/teach/classroom/unlockall?classroom=${classroomId}"/>" class="text-dark text-decoration-none">
	 	 <i class="fa-solid fa-lock-open"></i></a></td>
  </tr>
  </tfoot>  
</table>
</div>
