package accountService;


import accountService.account.UserAccount;
import accountService.account.exception.DuplicateUserException;
import accountService.account.exception.EmptyLoginOrPasswordException;
import accountService.account.exception.NoSuchUserException;
import app.MessageSystemContext;
import messageSystem.MessageSystem;

import java.util.Collection;
import java.util.HashMap;

import java.util.Map;
public class AccountServiceImpl implements AccountService{
    private MessageSystemContext msContext;
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
            throw new DuplicateUserException(login);
        }else {
            registeredUsers.put(login,new UserAccount(login,password, "",registeredUsers.size()+1));
        }
    }

    @Override
    public Collection<UserAccount> getUserList() {
        return registeredUsers.values();
    }

    @Override
    public UserAccount getRegisteredUserById(long id) throws NoSuchUserException {
        for(UserAccount user: registeredUsers.values()){
            if (user.getId() == id){
                return user;
            }
        }
        throw new NoSuchUserException();
    }

    @Override
    public UserAccount getRegisteredUserByLogin(String login) throws NoSuchUserException {
        if (registeredUsers.containsKey(login)){
            return registeredUsers.get(login);
        }else {
            throw new NoSuchUserException();
        }
    }

    @Override
    public void updateUser(long id, UserAccount newUserInfo) throws NoSuchUserException {
        UserAccount editingUser = getRegisteredUserById(id);
        editingUser.setLogin(newUserInfo.getLogin());
        editingUser.setName(newUserInfo.getName());
        editingUser.setPassword(newUserInfo.getPassword());
    }

    @Override
    public boolean isRegisteredUserLogin(String login) {
        return registeredUsers.containsKey(login);
    }

    @Override
    public boolean isRightLoginPasswordPair(String login, String password) throws NoSuchUserException {
        return getRegisteredUserByLogin(login).getPassword().equals(password);
    }

    @Override
    public String getId() {
        return "AccountService";
    }

    @Override
    public void setMsContext(MessageSystemContext msContext) {
        this.msContext = msContext;
    }

    @Override
    public MessageSystem getMS() {
        return msContext.getMessageSystem();
    }
}
