package dbService.dao;

import dataSet.DataSet;
import dataSet.dataSetSQL.DataSetSQL;
import dataSet.dataSetSQL.DataSetSQLs;
import dbService.Executor;

import javax.xml.crypto.Data;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Dao{
    private Connection connection;
    private Executor executor;
    public Dao(Connection connection, Executor executor){
        this.connection = connection;
        this.executor = executor;
    }
    public final <T extends DataSet> void save (T dataset){
        String strSQL = (dataset.getId() == 0) ? dataset.getInsertSQL() : dataset.getUpdateSQL();
        try {
            executor.execute(strSQL, stmt -> {
                setStatementValues(getFieldValueMap(dataset).values(), stmt);
            }, stmt->{
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()){
                    dataset.setId(rs.getLong(1));
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public final <T extends DataSet> T load(long id, Class<T> datasetClass){
        DataSetSQL<T> userDataSetSQL = DataSetSQLs.getDataSetSQL(datasetClass);
        String selectSQL = userDataSetSQL.getSelectSQL();
        try {
            T result = datasetClass.getDeclaredConstructor().newInstance();
            executor.execute(selectSQL, s -> {
                        s.setLong(1, id);
                    }, s->{
                        ResultSet rs = s.getResultSet();
                        rs.next();
                        try {
                            for (String fieldName : userDataSetSQL.getFields()) {
                                if (fieldName.endsWith("_id")){
                                    Field field = datasetClass.getDeclaredField(fieldName.replace("_id", ""));
                                    Object o = null;
                                    try {
                                        o = field.getType().getDeclaredConstructor().newInstance();
                                    } catch (InstantiationException e) {
                                        e.printStackTrace();
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    } catch (NoSuchMethodException e) {
                                        e.printStackTrace();
                                    }
                                    Field idField = o.getClass().getSuperclass().getDeclaredField("id");
                                    idField.setAccessible(true);
                                    field.setAccessible(true);
                                    idField.set(o, rs.getObject(fieldName));
                                    field.set(result, o);;
                                }else {
                                    Field field = datasetClass.getDeclaredField(fieldName);
                                    field.setAccessible(true);
                                    field.set(result, rs.getObject(fieldName));
                                }
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                    }
            );
            result.setId(id);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;

    }

    private void setStatementValues(Collection<String> values, PreparedStatement statement) throws SQLException {
        int i = 0;
        for (String value: values){
            i++;
            statement.setString(i, value);
        }
    }

    private <T extends DataSet> Map<String, String> getFieldValueMap(T dataSet){
        Map<String, String> result = new LinkedHashMap<>();

        Class dataSetClass= dataSet.getClass();
        DataSetSQLs.getDataSetSQL(dataSetClass).getFields();
        for(Field field: dataSetClass.getDeclaredFields()){
            field.setAccessible(true);
            try {
                if (!field.getName().toLowerCase().equals ("id") && field.getType().isPrimitive()) {
                    result.put(field.getName(), field.get(dataSet).toString());
                }else if(field.getType().equals(String.class)){
                    result.put(field.getName(), "\"" + field.get(dataSet).toString() + "\"");
                }else if (DataSet.class.isAssignableFrom(field.getType())){
                    //links to another object must be save before
                    result.put(field.getName() + "_id", Long.toString(((DataSet)field.get(dataSet)).getId()));
                }else if (!Collection.class.isAssignableFrom(field.getType())){
                    //Collections need to save after the main object saved.
                    //That have id main object.
                    throw new IllegalArgumentException("Not supported field type " + field.getType().getCanonicalName());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
