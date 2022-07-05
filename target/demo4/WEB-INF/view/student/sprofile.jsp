<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="row">   
 <nav aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="<c:url value="/student/"/>">Home</a></li>
    <li class="breadcrumb-item active" aria-current="page">Profile</li>
  </ol>
</nav>
    </div>
    
    
<a href="<c:url value="/student/profile/changepassword"/>" class="btn btn-primary mb-3">Đổi mật khẩu</a>
<a href="<c:url value="/student/profile/editphonenumber"/>" class="btn btn-primary mb-3">Chỉnh sửa số điện thoại</a>

<div class="table-responsive">
<table class="table table-primary table-bordered border-dark">
  <thead>
    <tr>
      <th scope="col">Mã số sinh viên</th>
      <th scope="col">Họ tên</th>
      <th scope="col">Mail</th>
      <th scope="col">Số điện thoại</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td scope="col">${info.id}</td>
      <td scope="col">${info.name}</td>
      <td  scope="col">${info.mail}</td>
      <td scope="col">${info.phone}</td>
    </tr>
   
  </tbody>
</table>
</div>