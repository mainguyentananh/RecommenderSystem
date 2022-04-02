<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="row">   
 <nav aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="<c:url value="/teacher/"/>">Home</a></li>
    <li class="breadcrumb-item active" aria-current="page">Profile</li>
  </ol>
</nav>
    </div>
    
    
<a href="<c:url value="/teacher/profile/changepassword"/>" class="btn btn-primary mb-3">Đổi mật khẩu</a>
<a href="<c:url value="/teacher/profile/editprofile"/>" class="btn btn-primary mb-3">Chỉnh sửa thông tin</a>


<div class="row">
	<div class="col">
			<div class="card mb-3" >
				  <div class="row">
				    <div class="col-4">
				      <img src="<c:url value="/static/img/${info.image}"/>" class="card-img" >
				    </div>	    
				    <div class="col-8">
				      <div class="card-body">
				
					    <p class="card-text"><strong>Mã số giảng viên:</strong> ${info.id}</p>
					    <p class="card-text"><strong>Họ tên:</strong> ${info.name}</p>
					    <p class="card-text"><strong>Mail:</strong> ${info.mail}</p>
					    <p class="card-text"><strong>Học vị:</strong> ${info.degree}</p>
				        <p class="card-text"><strong>Ghi chú:</strong> ${info.note}</p>
				        <p class="card-text"><strong>Số điện thoại:</strong> ${info.phone}</p>
				        
				        
				 
				      </div>
				    </div>
				  </div>
				</div>
	</div>
</div>