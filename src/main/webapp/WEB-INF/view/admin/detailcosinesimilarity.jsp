<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="row">   
 <nav aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="<c:url value="/teacher/"/>">Home</a></li>
    <li class="breadcrumb-item"><a href="<c:url value="/admin/cosinesimilarity/"/>">Cosine Similarity</a></li>
    <li class="breadcrumb-item active" aria-current="page">${key}</li>
  </ol>
</nav>
</div>


<div class="row">
<h3>Vector Name</h3>
<div class="col">
${getVectorName}
</div>
<h3>Độ dài:</h3>
<div class="col">
${count}
</div>
<h3>Cosine Similarity</h3>
<div class="col">

${getCosinesimilarity}
</div>
</div>