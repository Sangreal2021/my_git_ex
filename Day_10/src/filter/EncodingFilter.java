package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter("*.tje")
public class EncodingFilter implements Filter {
	private static String encoding;
	
	public void init(FilterConfig fConfig) throws ServletException {
		encoding = fConfig.getServletContext().getInitParameter("ENCODING");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//요청 객체의 인코딩 처리
		request.setCharacterEncoding(encoding);
		
		chain.doFilter(request, response);
	}

	public void destroy() {}
}
