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


<div class="table-responsive">
<table class="table table-primary table-bordered border-dark text-center">
  <thead>
    <tr>
      <th scope="col">Khóa</th>
      <th scope="col">Cập nhật lần gần nhất</th>
      <th scope="col">Chi tiết</th>
     </tr>
  </thead>
  <tbody>
    <c:forEach items="${listCosineSimilarity}" var="l" >
    <tr>
      <td scope="col">${l.key}</td>
      <td scope="col">${l.time}</td>
      <td scope="col" ><a href="<c:url value="/admin/cosinesimilarity/detail/${l.key}"/>" class="text-dark text-decoration-none">
      <i class="fa-solid fa-pen-to-square"></i> </a>
      </td>
      </tr>
   </c:forEach>
	<tr>
		<th colspan="2" scope="col">Cập nhật</th>
		<td scope="col">	<a href="<c:url value="/admin/cosinesimilarity/update"/>" class="text-dark text-decoration-none">
	      		<i class="fa-solid fa-arrows-rotate"></i> </a></td>
     
	</tr>    
   
  </tbody>
</table>
</div>