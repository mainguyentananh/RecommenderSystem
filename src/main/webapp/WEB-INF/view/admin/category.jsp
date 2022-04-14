<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="row">   
 <nav aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="<c:url value="/teacher/"/>">Home</a></li>
    <li class="breadcrumb-item active" aria-current="page">Category</li>
  </ol>
</nav>
</div>


<a href="<c:url value="/admin/category/create"/>" class="btn btn-primary mb-3">Thêm học phần</a>
<div class="table-responsive">
<table class="table table-primary table-bordered border-dark text-center">
  <thead>
    <tr>
      <th scope="col">Mã học phần</th>
      <th scope="col">Tên học phần</th>
      <th scope="col">Sửa học phần</th>
      <th scope="col">Xóa học phần</th>
      
    </tr>
  </thead>
  <tbody>
    <c:forEach items="${listCategory}" var="l" >
    <tr>
      <td scope="col">${l.id}</td>
      <td scope="col">${l.name}</td>
      <td scope="col" ><a href="<c:url value="/admin/category/edit/${l.id}"/>" class="text-dark text-decoration-none">
      <i class="fa-solid fa-pen-to-square"></i> </a>
      </td>
       <td scope="col" ><a href="<c:url value="/admin/category/delete/${l.id}"/>" class="text-dark text-decoration-none">
      <i class="fa-solid fa-trash-can"></i> </a>
      </td>
    </tr>
   </c:forEach>
  </tbody>
</table>
</div>