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
    
    private static class Error {
        
        private String error;
        
        public Error(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        ObjectMapper mapper = new ObjectMapper();
        String action = request.getParameter("action");
        if (action == null) {
            mapper.defaultPrettyPrintingWriter().writeValue(response.getOutputStream(), new Error("missing action parameter"));
        }
        if (action.equals("parse")) {
            String text = request.getParameter("text");
            AuthorityTokenizer tokenizer = new AuthorityTokenizer();
            List<AuthorityRecord> authorities =  tokenizer.getAuthorityRecords(text);
            mapper.defaultPrettyPrintingWriter().writeValue(response.getOutputStream(), authorities);
        }
    }
    
}
