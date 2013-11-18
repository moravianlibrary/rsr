package cz.muni.kisk.rsr;

import cz.muni.kisk.rsr.tokenizer.AuthorityRecord;
import cz.muni.kisk.rsr.tokenizer.AuthorityTokenizer;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.jackson.map.ObjectMapper;

public class RSRServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        /*
        ExampleBean example = new ExampleBean();
        example.setName("test");
        example.setLength(10);
        */
        List<AuthorityRecord> authorities =  AuthorityTokenizer.getAuthorityRecords("Skusobny text. Jan Novak");
        mapper.defaultPrettyPrintingWriter().writeValue(response.getOutputStream(), authorities);
        //mapper.writeValue(response.getOutputStream(), authorities);
        //response.getOutputStream().print("OK");
    }
}
