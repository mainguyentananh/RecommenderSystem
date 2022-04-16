<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="row">   
 <nav aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="<c:url value="/student/"/>">Home</a></li>
    <li class="breadcrumb-item active" aria-current="page">Classroom</li>
  </ol>
</nav>
    </div>
 
<div class="table-responsive">
<table class="table table-primary table-bordered border-dark text-center">
  <thead>
    <tr>
      <th scope="col">Nhóm</th>
      <th scope="col">Môn</th>
      <th scope="col">Chi tiết nhóm học</th>
      <th scope="col">Tài liệu</th>
      <th scope="col">Trạng thái</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach items="${documentDetail}" var="dd" >
    <tr>
      <td scope="col">${dd.dd_classroom.name}</td>
      <td scope="col">${dd.dd_classroom.cr_category.name}</td>
      <td scope="col" ><a href="<c:url value="/student/classroom/detail?classroom=${dd.dd_classroom.id}"/>" class="text-dark text-decoration-none">
      <i class="fa-solid fa-eye"></i> </a>
      </td>
      <c:if test="${dd.check == false }">
	     
	     <c:if test="${empty dd.dd_document}">
	      <td scope="col">Chưa nộp</td>
		     <td scope="col"><a href="<c:url value="/student/classroom/submitdocument?classroom=${dd.dd_classroom.id}"/>" class="text-dark text-decoration-none">
		     <i class="fa-solid fa-upload"></i> Nộp</a>
		     </td>
			</c:if>
			
			 <c:if test="${!empty dd.dd_document}">
	      <td scope="col"><a href="<c:url value="/document/${dd.dd_document.id}"/>" class="link-primary text-decoration-none" >Đã nộp</a></td>
		     <td scope="col"><a href="<c:url value="/student/classroom/editdocument?classroom=${dd.dd_classroom.id}&document=${dd.dd_document.id}"/>" class="text-dark text-decoration-none">
		    <i class="fa-solid fa-pen-to-square"></i> Chỉnh sửa</a>
		    </td>
			</c:if>
	</c:if>
	
	<c:if test="${dd.check == true }">
		<td scope="col"><a href="<c:url value="/document/${dd.dd_document.id}"/>" class="link-primary text-decoration-none" >Đã nộp</a></td>
		<td scope="col"><i class="fa-solid fa-lock"></i></td>
	</c:if>

    </tr>
   </c:forEach>
  </tbody>
</table>
</div>