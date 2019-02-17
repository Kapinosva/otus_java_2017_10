package accountService.account;

import accountService.account.exception.EmptyLoginOrPasswordException;
import dataSet.DataSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class UserAccount extends DataSet{

    @Column(name = "login")
    String login;
    @Column(name = "name")
    String name;
    @Column(name = "pass")
    String password;


    public UserAccount() {

    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public boolean isAdmin(){
        return login.equals("admin");
    }

    public UserAccount(String login, String password, String name) throws EmptyLoginOrPasswordException {
        if (login.isEmpty() || password.isEmpty()){
            throw  new EmptyLoginOrPasswordException();
        }
        this.login = login;
        this.password = password;
        this.name = name;
    }

    public UserAccount(String login, String password, String name, long id) throws EmptyLoginOrPasswordException {
        this(login,password, name);
        this.setId(id);
    }
}
