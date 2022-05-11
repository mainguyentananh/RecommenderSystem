<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="row">   
 <nav aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="<c:url value="/teacher/"/>">Home</a></li>
    <li class="breadcrumb-item active" aria-current="page">Cosine Similarity</li>
  </ol>
</nav>
</div>


<h3>Cosine Similarity</h3>
<div class="table-responsive">
<table class="table table-primary table-bordered border-dark text-center">
  <thead>
    <tr>
      <th scope="col">Khóa</th>
      <th scope="col">Độ dài vector</th>   
      <th scope="col">Cập nhật lần gần nhất</th>
      <th scope="col">Chi tiết</th>
     </tr>
  </thead>
  <tbody>
    <c:forEach items="${listCosineSimilarity}" var="l" >
    <tr>
      <td scope="col">${l.key}</td>
      <td scope="col">${l.countVectorName}</td>
      <td scope="col">${l.time}</td>
      <td scope="col" ><a href="<c:url value="/admin/cosinesimilarity/detail/${l.key}"/>" class="text-dark text-decoration-none">
      <i class="fa-solid fa-pen-to-square"></i> </a>
      </td>
      </tr>
   </c:forEach>
	<tr>
		<th colspan="2" scope="col">Cập nhật</th>
		<th scope="col">	<a href="<c:url value="/admin/cosinesimilarity/update"/>" class="text-dark text-decoration-none">
	      		<i class="fa-solid fa-arrows-rotate"></i> </a></th>
     	<th></th>
	</tr>    
   
  </tbody>
</table>
</div>


<h3>Bảng tính trước cosine cho hệ thống</h3>
<div class="table-responsive">
<table class="table table-primary table-bordered border-dark text-center">
  <thead>
    <tr>
      <th scope="col">Khóa</th>
      <th scope="col">Độ dài vector</th>   
      <th scope="col">Lần tính gần nhất</th>
      <th scope="col">Chi tiết</th>
     </tr>
  </thead>
  <tbody>
    <c:forEach items="${listTempCosineSimilarity}" var="l" >
    <tr>
      <td scope="col">${l.key}</td>
      <td scope="col">${l.countVectorName}</td>
      <td scope="col">${l.time}</td>
      <td scope="col" ><a href="<c:url value="/admin/tempcosinesimilarity/detail/${l.key}"/>" class="text-dark text-decoration-none">
      <i class="fa-solid fa-pen-to-square"></i> </a>
      </td>
      </tr>
   </c:forEach>
	<tr>
		<th colspan="2" scope="col">Tính lại</th>
		<th scope="col">	<a href="<c:url value="/admin/tempcosinesimilarity/create"/>" class="text-dark text-decoration-none">
	      		<i class="fa-solid fa-arrows-rotate"></i> </a></th>
     	<th></th>
	</tr>    
   
  </tbody>
</table>
</div>

<h3>Các tài liệu chưa được tính độ tương tự</h3>
<div class="table-responsive mb-3">
<table class="table table-primary table-bordered border-dark text-center">
  <thead>
    <tr>
      <th scope="col">Mã số tài liệu</th>
      <th scope="col">Tên tài liệu</th>   
      <th scope="col">Học phần</th>
      <th scope="col">Chi tiết</th>
     </tr>
  </thead>
  <tbody>
    <c:forEach items="${listDocumentInNotify}" var="l" >
    <tr>
      <td scope="col">${l.id}</td>
      <td scope="col">${l.name}</td>
      <td scope="col">${l.d_category.id} - ${l.d_category.name}</td>
    <td scope="col"><a href="<c:url value="/document/${l.id}"/>" class="text-dark text-decoration-none"><i class="fa-solid fa-pen-to-square"></i></a></td>
     </tr>
   </c:forEach>
  </tbody>
</table>
</div>
