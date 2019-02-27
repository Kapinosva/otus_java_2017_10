import accountService.DBAccountServiceImpl;
import app.AccountService;
import cache.CacheEngineImpl;
import dbService.DBServiceHibernate;

public class AccountServiceMain {
    public static void main(String[] args) {
        AccountService accountService = new DBAccountServiceImpl(
                new DBServiceHibernate(
                        new CacheEngineImpl(
                                10
                                ,0
                                ,0
                                ,true)
                )
        );
    }
}
