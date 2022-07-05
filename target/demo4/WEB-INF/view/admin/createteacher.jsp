<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="row">   
 <nav aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="<c:url value="/teacher/"/>">Home</a></li>
    <li class="breadcrumb-item active" aria-current="page">Create Teacher</li>
  </ol>
</nav>
    </div>
<h4>Khởi tạo thông qua file excel</h4>
<span class="text-muted mb-3">Thông tin đăng nhập sẽ được xuất tại ổ D</span>
<form action="<c:url value="/admin/createteacher"/>"  method="POST" enctype="multipart/form-data">
	<div class="col-8">
	<div class="input-group">
	  <input type="file" name="filename" class="form-control" required="required">
	  <input type="submit" value="Upload" class="btn btn-primary"/>
	</div>
	</div>
</form>
${notify}

<h3 class="mt-3">Giảng viên hiện có trong hệ thống</h3>
<div class="table-responsive mt-3">
<table class="table table-primary table-bordered border-dark text-center">
  <thead>
    <tr>
      <th scope="col">Mã số giảng viên</th>
      <th scope="col">Tên giảng viên</th>
      <th scope="col">Mail</th>      
    </tr>
  </thead>
  <tbody>
    <c:forEach items="${list}" var="l" >
    <tr>
      <td scope="col">${l.id}</td>
      <td scope="col">${l.name}</td>
   	 <th scope="col">${l.mail}</th>   
    </tr>
   </c:forEach>
  </tbody>
</table>
</div>