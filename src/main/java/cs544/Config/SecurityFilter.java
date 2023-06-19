package cs544.Config;

import cs544.utility.JWTUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
//
@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

//    @Override
//    public void doFilter(ServletRequest servletRequest,
//                         ServletResponse servletResponse,
//                         FilterChain filterChain)
//            throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//
//        String token = request.getHeader("Authorization");
//
//
//        if (token != null) {
//            String userName = jwtUtil.getUsername(token);
//
//            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                UserDetails user = userDetailsService.loadUserByUsername(userName);
//
//                if (jwtUtil.validateToken(token, user.getUsername())) {
//                    UsernamePasswordAuthenticationToken authToken =
//                            new UsernamePasswordAuthenticationToken(userName, user.getPassword(),user.getAuthorities());
//                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                    SecurityContextHolder.getContext().setAuthentication(authToken);
//                }
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//            throws ServletException, IOException {
//
//
//
//        String token = request.getHeader("Authorization");
//        System.out.println(">>>>>>>>>>>>> please work"+token);
//
//
//        if (token != null) {
//            String userName = jwtUtil.getUsername(token);
//
//            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                UserDetails user = userDetailsService.loadUserByUsername(userName);
//                System.out.println(">>>>I found my user >>>>"+user.getUsername());
//                if (jwtUtil.validateToken(token, user.getUsername())) {
//                    UsernamePasswordAuthenticationToken authToken =
//                            new UsernamePasswordAuthenticationToken(userName, user.getPassword(),user.getAuthorities());
//                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                    SecurityContextHolder.getContext().setAuthentication(authToken);
//                }
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        var token = extractTokenFromRequest(request);
        if (token != null && jwtUtil.validateToken(token)) {

            SecurityContextHolder.getContext().setAuthentication(jwtUtil.getAuthentication(token));
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Helper method
     *
     * @param request
     * @return
     */
    public String extractTokenFromRequest(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            var token = authorizationHeader.substring(7);
            return token;
        }
        return null;
    }
    @Override
    public void destroy() {
        // Cleanup code if needed
    }
}

