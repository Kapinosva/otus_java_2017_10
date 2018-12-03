package dataSet.dataSetSQL;

import dataSet.Address;
import dataSet.DataSet;
import dataSet.Phone;
import dataSet.User;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class DataSetSQLs {

    private static final Map<Class, DataSetSQL> dataSetSqls = new HashMap<>();

    static {
        dataSetSqls.put(User.class, new DataSetSQL<User>(
                "user"
                ,"create table if not exists user (" +
                    "id bigint auto_increment" +
                    ", age int" +
                    ", name varchar(256)" +
                    ", address_id bigint" +
                    ", primary key (id));\n "
                , User.class
                )
        );
        dataSetSqls.put(         Phone.class, new DataSetSQL<Phone>(
                        "phone"
                        ,"create table if not exists phone (id bigint auto_increment, phonenumber varchar(256), phoneMaster_id bigint, primary key (id));\n "
                        , Phone.class
                )
        );
        dataSetSqls.put(
                Address.class, new DataSetSQL<Address>(
                        "address"
                        ,"create table if not exists address (id bigint auto_increment, street varchar(256), house varchar(256), flat varchar(256), primary key (id));\n "
                        , Address.class
                )
        );
    }
    public static <T extends DataSet> void put(Class<T> dataSetClass, DataSetSQL<T> dataSetSQL){
        dataSetSqls.put(dataSetClass, dataSetSQL);
    }

    public static <T extends DataSet> DataSetSQL getDataSetSQL(Class<T> dataSetClass){
        if (dataSetSqls.containsKey(dataSetClass)){
            return dataSetSqls.get(dataSetClass);
        }else{
            throw new IllegalArgumentException("Not supported class " + dataSetClass.getName() + ". Please add a static initialization in DataSetSQLs class.");
        }
    }
}
