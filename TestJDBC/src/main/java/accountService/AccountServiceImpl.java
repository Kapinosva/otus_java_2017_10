package accountService;


import accountService.account.UserAccount;
import accountService.account.exception.DuplicateUserException;
import accountService.account.exception.EmptyLoginOrPasswordException;
import accountService.account.exception.NoSuchUserException;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountServiceImpl implements AccountService{

    private Map<String, UserAccount> registeredUsers = new HashMap<>();

    public AccountServiceImpl(){
        try {
            this.registerUser("admin","a");
            this.registerUser("petya","p");
        } catch (DuplicateUserException e) {
            e.printStackTrace();
        } catch (EmptyLoginOrPasswordException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerUser(String login, String password) throws DuplicateUserException, EmptyLoginOrPasswordException {
        if (registeredUsers.containsKey(login)){
            throw new DuplicateUserException();
        }else {
            registeredUsers.put(login,new UserAccount(login,password, "",registeredUsers.size()+1));
        }
    }

    @Override
    public void loginUser(String login, String password, HttpSession session) throws NoSuchUserException {
        if ((!registeredUsers.containsKey(login)) || (!registeredUsers.get(login).getPassword().equals(password))){
            throw new NoSuchUserException();
        }else{
            session.setAttribute("currentUser", registeredUsers.get(login));
        }
    }

    @Override
    public Collection<UserAccount> getUserList() {
        return registeredUsers.values();
    }

    @Override
    public UserAccount getRegisteredUserById(long id) {
        for(UserAccount user: registeredUsers.values()){
            if (user.getId() == id){
                return user;
            }
        }
        return null;
    }

    @Override
    public void updateUser(long id, UserAccount newUserInfo) {
        UserAccount editingUser = getRegisteredUserById(id);
        editingUser.setLogin(newUserInfo.getLogin());
        editingUser.setName(newUserInfo.getName());
        editingUser.setPassword(newUserInfo.getPassword());
    }
}
