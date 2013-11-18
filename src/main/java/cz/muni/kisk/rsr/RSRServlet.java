package cz.muni.kisk.rsr;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.jackson.map.ObjectMapper;

public class RSRServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        ExampleBean example = new ExampleBean();
        example.setName("test");
        example.setLength(10);
        mapper.writeValue(response.getOutputStream(), example);
        //response.getOutputStream().print("OK");
    }
}
