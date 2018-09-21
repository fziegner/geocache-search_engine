<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Panel</title>
</head>
<body>
<%
String fileName = (String) request.getAttribute("fileName");
String filePath = (String) request.getAttribute("filePath");
String uploadTime = (String) request.getAttribute("uploadTime");
%>

<h1 style="color:red;">Admin Panel</h1>
<form action="upload" method="post" enctype="multipart/form-data">
	File: <input type="file" name="file" /> 
	<input type="submit" value="Upload File"/>
</form>
<br/>

<%
if(fileName != null && filePath != null && uploadTime != null) {
	out.print("[" + uploadTime + "] Uploaded " + fileName + " to " + filePath);
}
%>
</body>
</html>