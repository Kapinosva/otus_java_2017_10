package accountService;

import accountService.account.UserAccount;
import accountService.account.exception.DuplicateUserException;
import accountService.account.exception.EmptyLoginOrPasswordException;
import accountService.account.exception.NoSuchUserException;

import antlr.debug.MessageAdapter;
import app.AccountService;
import app.DBService;
import com.google.gson.Gson;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageServer;
import messageSystem.MessageServerClient;


import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DBAccountServiceImpl extends MessageServerClient implements AccountService  {
    private DBService dbService;
    private Address address = MessageServer.getAccountServiceAddress();

    public DBAccountServiceImpl(DBService dbService) {
        this(dbService, MessageServer.getHost(), MessageServer.getPort());
    }

    public DBAccountServiceImpl(DBService dbService, String host, int port) {
        this.dbService = dbService;
        initMessageServerClient(host, port);
        start();
    }
    public void start(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                while (true) {
                    Message msg = getSocketMsgWorker().take();
                    System.out.println("Message received in AccountService: " + msg.toString());
                    msg.exec(this);
                }
            } catch (InterruptedException e) {
                System.out.println("Bye back");
            }
        });
    }

    @Override
    public void registerUser(String login, String password) throws DuplicateUserException, EmptyLoginOrPasswordException {
        if (dbService.isRegisteredUserLogin(login)){
            throw new DuplicateUserException(login);
        }else{
            dbService.save(new UserAccount(login,password, ""));
        }
    }

    @Override
    public Collection<UserAccount> getUserList() {
        return dbService.getUserList();
    }

    @Override
    public UserAccount getRegisteredUserById(long id) throws NoSuchUserException {
        UserAccount result = dbService.load(id, UserAccount.class);
        if (result == null){
            throw new NoSuchUserException();
        }
        return result;
    }

    @Override
    public String getRegisteredUserByLogin(String login) throws NoSuchUserException {
        UserAccount result = dbService.getUserByLogin(login);
        if (result == null){
            throw new NoSuchUserException();
        }else {
            Gson gson = new Gson();
            return gson.toJson(result);
        }
    }

    @Override
    public void updateUser(long id, UserAccount newUserInfo) {
        if (newUserInfo.getId() != id ){newUserInfo.setId(id);}
        dbService.save(newUserInfo);
    }

    @Override
    public boolean isRegisteredUserLogin(String login) {
        return dbService.isRegisteredUserLogin(login);
    }

    @Override
    public boolean isRightLoginPasswordPair(String login, String password) throws NoSuchUserException {
        Gson g = new Gson();

        UserAccount user = g.fromJson(getRegisteredUserByLogin(login), UserAccount.class);
        return user.getPassword().equals(password);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
