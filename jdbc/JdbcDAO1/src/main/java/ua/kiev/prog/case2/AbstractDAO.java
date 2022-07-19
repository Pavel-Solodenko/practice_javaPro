package ua.kiev.prog.case2;

import jdk.nashorn.internal.objects.annotations.Where;
import ua.kiev.prog.shared.Id;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDAO<T> {
    private final Connection conn;
    private final String table;

    public AbstractDAO(Connection conn, String table) {
        this.conn = conn;
        this.table = table;
    }

    public void createTable(Class<T> cls) {
        Field[] fields = cls.getDeclaredFields();
        Field id = getPrimaryKeyField(null, fields);

        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE ")
                .append(table)
                .append("(");

        sql.append(id.getName())
                .append(" ")
                .append(" INT AUTO_INCREMENT PRIMARY KEY,");

        for (Field f : fields) {
            if (f != id) {
                f.setAccessible(true);

                sql.append(f.getName()).append(" ");

                if (f.getType() == int.class) {
                    sql.append("INT,");
                } else if (f.getType() == String.class) {
                    sql.append("VARCHAR(100),");
                } else
                    throw new RuntimeException("Wrong type");
            }
        }

        sql.deleteCharAt(sql.length() - 1);
        sql.append(")");

        try {
            try (Statement st = conn.createStatement()) {
                st.execute(sql.toString());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void add(T t) {
        try {
            Field[] fields = t.getClass().getDeclaredFields();
            Field id = getPrimaryKeyField(t, fields);

            StringBuilder names = new StringBuilder();
            StringBuilder values = new StringBuilder();

            // insert into t (id,name,age) values("..",..

            for (Field f : fields) {
                if (f != id) {
                    f.setAccessible(true);

                    names.append(f.getName()).append(',');
                    values.append('"').append(f.get(t)).append("\",");
                }
            }

            names.deleteCharAt(names.length() - 1); // last ','
            values.deleteCharAt(values.length() - 1);

            String sql = new StringBuilder("INSERT INTO ").append(table)
                    .append("(").append(names).append(") VALUES(")
                    .append(values).append(")")
                    .toString();
            try (Statement st = conn.createStatement()) {

                st.execute(sql,Statement.RETURN_GENERATED_KEYS);

                ResultSet resultSet = st.getGeneratedKeys();
                resultSet.next();

                Field field = getPrimaryKeyField(t,t.getClass().getDeclaredFields());
                BigInteger temp = (BigInteger) resultSet.getObject(1);
                field.set(t,temp.intValue());
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void update(T t) {
        try {
            Field[] fields = t.getClass().getDeclaredFields();
            Field id = getPrimaryKeyField(t, fields);

            StringBuilder sb = new StringBuilder();

            for (Field f : fields) {
                if (f != id) {
                    f.setAccessible(true);

                    sb.append(f.getName())
                            .append(" = ")
                            .append('"')
                            .append(f.get(t))
                            .append('"')
                            .append(',');
                }
            }

            sb.deleteCharAt(sb.length() - 1);

            // update t set name = "aaaa", age = "22" where id = 5
            String sql = "UPDATE " + table + " SET " + sb.toString() + " WHERE " +
                    id.getName() + " = \"" + id.get(t) + "\"";

            try (Statement st = conn.createStatement()) {
                st.execute(sql);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void delete(T t) {
        try {
            Field[] fields = t.getClass().getDeclaredFields();
            Field id = getPrimaryKeyField(t, fields);

            String sql = "DELETE FROM " + table + " WHERE " + id.getName() +
                    " = \"" + id.get(t) + "\"";

            try (Statement st = conn.createStatement()) {
                st.execute(sql);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<T> getAll(Class<T> cls) {
        List<T> res = new ArrayList<>();

        try {
            try (Statement st = conn.createStatement()) {
                try (ResultSet rs = st.executeQuery("SELECT * FROM " + table)) {
                    ResultSetMetaData md = rs.getMetaData();

                    while (rs.next()) {
                        T t = cls.newInstance(); //!!!

                        for (int i = 1; i <= md.getColumnCount(); i++) {
                            String columnName = md.getColumnName(i);
                            Field field = cls.getDeclaredField(columnName);
                            field.setAccessible(true);

                            field.set(t, rs.getObject(columnName));
                        }

                        res.add(t);
                    }
                }
            }

            return res;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<T> getAll(Class<T> cls,String name,String age) {
        List<T> res = new ArrayList<>();

        try {

            try (Statement st = conn.createStatement()) {

                try (ResultSet resultSet = st.executeQuery(
                        new StringBuilder("SELECT * FROM ").append(table)
                                .append(" WHERE ").append("age=").append(age)
                                .append(" AND ").append("name=\'").append(name)
                                .append("\'").toString()
                )) {

                    ResultSetMetaData metaData = resultSet.getMetaData();

                    while (resultSet.next()) {

                        T t = cls.newInstance();

                        for (int i = 1; i <= metaData.getColumnCount();++i) {

                            String columnName = metaData.getColumnName(i);

                            Field field = cls.getDeclaredField(columnName);
                            field.setAccessible(true);

                            field.set(t,resultSet.getObject(columnName));
                        }

                        res.add(t);

                    }

                }

            }


            return res;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public List<T> getAll(Class<T> cls,String name) {
        List<T> result = new ArrayList<>();

        try {

            try (Statement statement = conn.createStatement()) {

                try (ResultSet resultSet = statement.executeQuery(
                        new StringBuilder("SELECT * FROM ").append(table)
                                .append(" WHERE ").append("name=\'")
                                .append(name).append("\'").toString()
                )) {

                    ResultSetMetaData metaData = resultSet.getMetaData();

                    while (resultSet.next()) {

                        T t = cls.newInstance();

                        for (int i = 1; i <= metaData.getColumnCount();++i) {
                            String columnName = metaData.getColumnName(i);
                            Field field = cls.getDeclaredField(columnName);

                            field.setAccessible(true);
                            field.set(t,resultSet.getObject(columnName));
                        }

                        result.add(t);
                    }

                }

            }

        return result;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public List<T> getAll(Class<T> cls, int age) {
        List<T> result = new ArrayList<>();

        try {

            try (Statement statement = conn.createStatement()) {

                try (ResultSet resultSet = statement.executeQuery(
                        new StringBuilder("SELECT * FROM ").append(table)
                                .append(" WHERE ")
                                .append("age=").append(age).toString()
                )){

                    ResultSetMetaData metaData = resultSet.getMetaData();

                    while(resultSet.next()) {

                        T t = cls.newInstance();

                        for (int i=1; i <= metaData.getColumnCount();++i) {
                            String columnName = metaData.getColumnName(i);

                            Field field = cls.getDeclaredField(columnName);
                            field.setAccessible(true);

                            field.set(t,resultSet.getObject(columnName));

                        }

                        result.add(t);

                    }

                }

            }

            return result;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private Field getPrimaryKeyField(T t, Field[] fields) {
        Field result = null;

        for (Field f : fields) {
            if (f.isAnnotationPresent(Id.class)) {
                result = f;
                result.setAccessible(true);
                break;
            }
        }

        if (result == null)
            throw new RuntimeException("No Id field found");

        return result;
    }
}
