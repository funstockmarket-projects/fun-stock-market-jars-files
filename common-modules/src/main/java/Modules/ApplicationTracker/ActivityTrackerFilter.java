package Modules.ApplicationTracker;

import jakarta.servlet.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ActivityTrackerFilter implements Filter {

    public static volatile long lastRequestTime = System.currentTimeMillis();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        lastRequestTime=System.currentTimeMillis();
        filterChain.doFilter(servletRequest, servletResponse);

    }
}
