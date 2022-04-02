<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<div class="row">
	<nav aria-label="breadcrumb">
		<ol class="breadcrumb">
			<li class="breadcrumb-item active" aria-current="page">Home</li>
		</ol>
	</nav>
</div>
<div class="row">
	<div class="col">
		<form class="text-center">
			<span>Tìm kiếm tài liệu: </span> <input class="col-3" type="text"
				id="input-search" name="search" required placeholder="search">
			<input class="btn btn-sm mb-1 btn-primary" type="submit"
				value="search" id="submit">

		</form>
	</div>
</div>

<script type="text/javascript">
 function isEmpty(str){
	    return !str.trim().length;
	  }

	  document.getElementById("input-search").addEventListener("input", function() {
	  if( isEmpty(this.value) ) {
	   document.getElementById('submit').disabled =true;
	  } else{
	      document.getElementById('submit').disabled =false;
	  }
	});
	  </script>