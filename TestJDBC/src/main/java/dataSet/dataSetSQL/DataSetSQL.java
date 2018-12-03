package dataSet.dataSetSQL;

import dataSet.DataSet;
import dataSet.User;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class DataSetSQL<T extends DataSet> {
    private String createSQL;
    private String tableName;
    private String insertSQL;
    private String updateSQL;
    private Collection<String> fields;

    public Collection<String> getFields() {
        return fields;
    }

    public String getInsertSQL() {
        return insertSQL;
    }

    public String getUpdateSQL(long id) {
        return updateSQL + " " + id + ";";
    }

    public String getSelectSQL() {
        return selectSQL;
    }

    private String selectSQL;

    public  DataSetSQL(String tableName, String createSQL, Class<T> dataSetClass){
        this.createSQL = createSQL;
        this.tableName = tableName;

        fields = new ArrayList<>();
        for(Field field: dataSetClass.getDeclaredFields()){
            if (field.getType().isPrimitive() || field.getType().equals(String.class)
                    || Number.class.isAssignableFrom(field.getType())) {
                fields.add(field.getName());
            }else if (DataSet.class.isAssignableFrom(field.getType())){
                fields.add(field.getName()+ "_id");
            }
        }

        insertSQL = "insert into " + tableName + " ("
                + fields.stream().collect(Collectors.joining(","))
                + ") values(" + fields.stream().map((s)->s ="?").collect(Collectors.joining(","))
                + ");";
        updateSQL = "UPDATE " + tableName + " SET "
                + fields.stream().collect(Collectors.joining(" = ?,"))
                + "=? where id = ";
        selectSQL = "SELECT  " +
                getFields().stream().collect(Collectors.joining(", "))
                + " FROM "+ tableName
                + " WHERE ID = ?;";
    }

    public String getCreateSQL() {
        return createSQL;
    }

    public String getTableName() {
        return tableName;
    }


}
