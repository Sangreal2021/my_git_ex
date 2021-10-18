package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter({ "/member/logout.tje", "/member/update.tje", "/member/updatePassword.tje", "/member/delete.tje" })
public class LoginAuthFilter implements Filter {

	public void init(FilterConfig fConfig) throws ServletException {}

	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//로그인 여부를 확인하여 서비스 실행 여부를 결정하는 필터 생성
		//- 세션을 사용하기 위해  HttpServletRequest 타입으로 변환
		HttpServletRequest req = (HttpServletRequest) request; 
		//- 세션 객체를 추출
		HttpSession session = req.getSession();
		//- 세션 내부의 로그인 여부를 확인 할 수 있는 정보를 기반으로 
		//	실행 여부를 결정
		if(session==null || (session != null && session.getAttribute("isLogin")==null)) {
			System.out.println("로그인 정보가 존재하지 않아 메인페이지로 이동!!");
			((HttpServletResponse)response).sendRedirect(req.getContextPath());
			return;
		}
		chain.doFilter(request, response);
	}
}
