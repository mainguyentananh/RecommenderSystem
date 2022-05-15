<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="row">   
 <nav aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="<c:url value="/teacher/"/>">Home</a></li>
    <li class="breadcrumb-item active" aria-current="page">Evaluating System</li>
  </ol>
</nav>
</div>
<h3 class="text-center">Đánh giá hệ thống</h3>
<div class="row">
<div class="col">
	<strong>Số lượng người dùng trong hệ thống: </strong>${countAccountInSystem}<br/>
	<strong>Số lượng người dùng trong hệ thống đã tương tác với tài liệu: </strong>${countAccountFeedback}<br/>
	<strong>Chỉ số với người dùng: </strong>${countEvaluationForAccount}<br/>
	<strong>Chỉ số với sản phẩm: </strong>${countEvaluationForItem}<br/>
	<strong>Đánh giá hệ thống với chỉ số với người dùng: </strong>${evaluationAccount}%<br/>
	<strong>Đánh giá hệ thống với chỉ số với sản phẩm: </strong>${evaluationItem}%<br/>
</div>
</div>
<div class="row mt-3">
	<div class="col">
	<h5>Logs</h5>
	<c:forEach items="${logs}" var="l">
		<div>${l}</div>
	</c:forEach>
	</div>
</div>