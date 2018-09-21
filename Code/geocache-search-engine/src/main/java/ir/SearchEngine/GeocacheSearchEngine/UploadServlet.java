package ir.SearchEngine.GeocacheSearchEngine;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import ir.SearchEngine.GeocacheSearchEngine.Util.CONSTANTS;

@MultipartConfig
public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
		    Part filePart = request.getPart("file");
		    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
		    
		    File uploads = new File(CONSTANTS.DATA_DIRECTORY);
		    File file = new File(uploads, fileName);
		    
		    System.out.println("Uploading " + fileName + " to " + file.getAbsolutePath());
		    
	        InputStream fileContent = filePart.getInputStream();
		    Files.copy(fileContent, file.toPath());
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    Date resultDate = new Date(System.currentTimeMillis());
		    request.setAttribute("fileName", fileName);
		    request.setAttribute("filePath", file.getAbsolutePath());
		    request.setAttribute("uploadTime", sdf.format(resultDate));
		    RequestDispatcher rd = request.getRequestDispatcher("/panel.jsp");
		    rd.forward(request, response);
		} catch (ServletException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}