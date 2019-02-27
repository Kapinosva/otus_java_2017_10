package dbService;

import java.sql.*;

public class Executor {

    private Connection connection;

    public void setShowSQL(boolean showSQL) {
        this.showSQL = showSQL;
    }

    private boolean showSQL = false;

    public Executor(Connection connection){
        this.connection = connection;
    }

    public void execute(String sql) throws SQLException {
        execute(sql, s->{}, s->{});
    }

    public void execute(String sql, StatementHandler preExecuteHandler, StatementHandler postExecuteHandler) throws SQLException {
        if (showSQL){
            System.out.println(sql);
        }
        try(PreparedStatement statement = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            boolean wasAutoCommit = statement.getConnection().getAutoCommit();
            statement.getConnection().setAutoCommit(false);
            preExecuteHandler.accept(statement);
            statement.execute();
            postExecuteHandler.accept(statement);
            statement.getConnection().setAutoCommit(wasAutoCommit);
            statement.getConnection().commit();
        }
    }


    @FunctionalInterface
    public interface StatementHandler {
        void accept(PreparedStatement statement) throws SQLException;
    }
}
