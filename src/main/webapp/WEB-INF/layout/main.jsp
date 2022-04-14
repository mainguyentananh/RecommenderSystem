<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Home</title>
<!-- Material Icons -->
<link
	href="https://fonts.googleapis.com/icon?family=Material+Icons+Round"
	rel="stylesheet">
<link rel="icon" href="<c:url value="/static/img/logo-ctu.png"/>" />
<!-- CSS Files -->
<link rel="stylesheet" type="text/css"
	href="<c:url value="/static/css/main.css?version=51"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/static/ft/css/all.min.css"/>">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">

</head>
<body class="g-sidenav-show  bg-gray-200">
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	
<sec:authorize access="hasRole('ROLE_STUDENT')">
	<c:set value="student" var="url"/>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_TEACHER') or hasRole('ROLE_ADMIN')">
	<c:set value="teacher" var="url"/>
</sec:authorize>



	<div
		class="sidenav navbar navbar-vertical navbar-expand-xs fixed-start mt-3 ms-3 bg-dark rounded-3"
		id="sidenav-main">
		<div class="sidenav-header text-center">
			<i class=" cursor-pointer text-white" aria-hidden="true"
				id="iconSidenav"></i> <a class="navbar-brand m-0">
				<div class="text-white">
					<img src="<c:url value="/static/img/favicon.ico"/>"
						class="icon-cit"> <span class="ms-1 text-white">Computer
						Science</span>
				</div>

			</a>
		</div>
		
		<div class="collapse navbar-collapse " id="sidenav-collapse-main">
			<ul class="navbar-nav">
				
				
				<!-- Dash board main -->	
				<li class="nav-item"><a
					class="nav-link text-white active bg-primary"
					href="<c:url value="/${url}/"/>">
						<div class="text-white">
							<i class="fa-solid fa-house"></i>
						</div> <span class="nav-link-text ms-1">Home</span>

				</a></li>
				<li class="nav-item"><a class="nav-link text-white " href="<c:url value="/${url}/profile"/>">
						<div class="text-white">
							<i class="fa-solid fa-address-card"></i>
						</div> <span class="nav-link-text ms-1">Profile</span>
				</a></li>
				
				<!-- Dash board Student -->	
				<sec:authorize access="hasRole('ROLE_STUDENT')">
					<li class="nav-item"><a class="nav-link text-white " href="<c:url value="/${url}/classroom"/>">
						<div class="text-white">
							<i class="fa-solid fa-id-card-clip"></i>
						</div> <span class="nav-link-text ms-1">Classroom</span>
				</a></li>
				</sec:authorize>
				
				
				<!-- Dash board Teacher -->	
				<sec:authorize access="hasRole('ROLE_TEACHER') or hasRole('ROLE_ADMIN')">
					<li class="nav-item"><a class="nav-link text-white " href="<c:url value="/${url}/createclassroom"/>">
						<div class="text-white">
							<i class="fa-solid fa-square-plus"></i>
						</div> <span class="nav-link-text ms-1">Create Classroom</span>
				</a></li>
				
				<li class="nav-item"><a class="nav-link text-white " href="<c:url value="/${url}/teach"/>">
						<div class="text-white">
							<i class="fa-solid fa-chalkboard-user"></i>
						</div> <span class="nav-link-text ms-1">Teach</span>
				</a></li>
				
				</sec:authorize>
				
				<!-- Dash board Admin -->
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<li class="nav-item"><a class="nav-link text-white " href="<c:url value="/admin/category"/>">
						<div class="text-white">
							<i class="fa-solid fa-rectangle-list"></i>
						</div> <span class="nav-link-text ms-1">Category</span>
				</a></li>
				</sec:authorize>
				
				<hr class="text-light" />
				<li class="nav-item"><a class="nav-link text-white "
					href="<c:url value="/logout"/>">
						<div class="text-white">
							<i class="fa-solid fa-right-from-bracket"></i>
						</div> <span class="nav-link-text ms-1">Logout</span>
				</a></li>
			</ul>
		</div>
	</div>
	<div class="main-content ">
		<nav class="navbar navbar-main navbar-expand-lg shadow-none "
			id="navbarBlur" navbar-scroll="true">
			<div class="container-fluid py-1 px-3">
				<div class="collapse navbar-collapse mt-sm-0 mt-2 me-md-0 me-sm-4"
					id="navbar">
					<ul
						class="ms-md-auto pe-md-3 d-flex navbar-nav  justify-content-end">
						<li class="nav-item d-flex align-items-center"><a href="#"
							class="nav-link text-body font-weight-bold px-0"> <span>Xin
									chào: <a class="nav-link text-dark " href="<c:url value="/${url}/profile"/>"> <strong>${pageContext.request.userPrincipal.name}</strong>
							</a>
							</span>
						</a></li>
						<li class="nav-item d-xl-none ps-3 d-flex align-items-center">
							<a href="#" class="nav-link text-body p-0" id="iconNavbarSidenav">

								<div class="sidenav-toggler-inner">
									<i class="sidenav-toggler-line"></i> <i
										class="sidenav-toggler-line"></i> <i
										class="sidenav-toggler-line"></i>
								</div>
						</a>
						</li>
					</ul>
				</div>
			</div>
		</nav>
		<!-- End Navbar -->
		<div class="container">
			<tiles:insertAttribute name="search" />
			<tiles:insertAttribute name="content" />
		
		
		
		<footer class="bg-dark text-white py-3 mt-3 mb-3">
			<div class="container"> 
			
				<div class="col text-center">
					<span>Copyright &copy; MNTA</span>
				</div>
			
			</div>
		</footer>
		
		</div>
		
	</div>



	<script type="text/javascript"
		src="<c:url value="/static/js/main.js" />"></script>

	<script type="text/javascript"
		src="<c:url value="/static/js/perfect-scrollbar.min.js" />"></script>
		
	
</body>
</html>