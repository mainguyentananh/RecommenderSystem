package demo4.config;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import demo4.model.semester;
import demo4.model.year;

public class Interceptor implements HandlerInterceptor{

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		Calendar calendar = Calendar.getInstance();
		List<semester> semester = new ArrayList<semester>();
		
		semester s0 = new semester("0","Tất cả");
		semester s1 = new semester("1","1");
		semester s2 = new semester("2","2");
		semester s3 = new semester("3","Hè");
		semester.add(s0);
		semester.add(s1);
		semester.add(s2);
		semester.add(s3);
		
		List<year> year = new ArrayList<year>();
		year y0 = new year("0","Tất cả");
		year.add(y0);
		
		for (int i = calendar.get(Calendar.YEAR); i >= 1980; i--) {
			year yearTemp = new year(String.valueOf(i),String.valueOf(i));
			year.add(yearTemp);
		}
		
		request.setAttribute("semester", semester);
		request.setAttribute("year", year);
		
		
	}

	
	
}
