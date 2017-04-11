package torrent;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.FormElement;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by vaa25 on 25.03.2017.
 */
public class Login {

    private final String login;
    private final String password;

    public Login(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Map<String, String> cookies() throws IOException {
        Connection connect = Jsoup.connect("http://rutracker.org/");
        Connection.Response response = connect.execute();
        Document document = response.parse();
        List<FormElement> forms = document.getAllElements().forms();
        FormElement loginForm = forms
                .stream()
                .filter(formElement -> formElement.id().equals("login-form-quick"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No login form"));
        Connection submit = loginForm.submit()
                .data("login_username", login, "login_password", password)
                .cookies(response.cookies());
        Connection.Response execute = submit.execute();
        return execute.cookies();
    }
}
