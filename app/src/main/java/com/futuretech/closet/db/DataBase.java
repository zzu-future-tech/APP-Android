package com.futuretech.closet.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.futuretech.closet.model.Clothes;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by xu on 2019/4/20
 * 删除被注释的代码后阅读体验更佳，为日后扩充和测试方法 作者并未删除被注释的代码
 * 该类尚未完善，当需要对数据库中各表进行相应操作而该类中无该方法时请联系作者
 * 数据库各表的具体属性在dateBaseHelper类的onCreate方法中查看
 * 使用完本类后无其他操作应执行dateBase.close()
 * 需在try catch中使用该类及方法
 */
public class DataBase {
    public SQLiteDatabase db;

    /*
     *注册时初始化构造方法即可创建数据库 目前只创建用户信息表user和衣物信息表clothesInformation
     * name为用户名 为该用户创建名为 ‘用户名’的数据库
     * 构造方法中values应为注册用户的基本信息 基本形式为 values.put("属性名","值"）；ContentValues values,
     */
    public DataBase(String name, Context context) throws Exception {
        try {
            DataBaseHelper dabhelper = new DataBaseHelper(context, name);
            db = dabhelper.getWritableDatabase();
            // db.insert("user",null,values);
        } catch (Exception e) {
            throw new Exception("创建数据库失败", e);
        }
    }

    /*
     * 添加衣服时使用时 向衣物信息表中插入数据
     */
    public void insertCloth(ContentValues values) throws Exception {
        try {
            db.execSQL("insert into clothesInformation (dressid,style,color,thickness,photo,attribute,userid,gmt_create) values(?,?,?,?,?,?,?,datetime('now','localtime'))", new Object[]{Integer.parseInt(values.get("dressid").toString()),
                    values.get("style"), values.get("color"), values.get("thickness"), values.get("photo"), values.get("attribute"), values.get("userid")});
            //  db.insert("clothesInformation",null,values);
        } catch (Exception e) {
            throw new Exception("插入失败", e);
        }
    }

    /*
     * 更新操作执行时需重新输入相关衣服的所有信息
     */
    public void update(ContentValues values) throws Exception {
        try {
            //String sql = "update user set name= ? where id= ?";
            //db.execSQL(sql, new Object[]{name, id});
            // int id= Integer.parseInt(values.get("dressid").toString());
            //   db.update("clothesInformation",values,"dressid=",new String[]{values.get("dress").toString()});
            db.execSQL("update clothesInformation set style=?, color=?,thickness=?,attribute=?,userid=?,gmt_modified=datetime('now','localtime') where dressid=? ", new Object[]{
                    values.get("style"), values.get("color"), values.get("thickness"), values.get("attribute"), values.get("userid"), values.get("dressid")});
        } catch (Exception e) {
            throw new Exception("更新失败", e);
        }
    }

    /*
     * 根据具体衣物id删除clothesInformation 中的相关数据
     */
    public void deleteClothesByDressid(int dressid) throws Exception {
        //  String sql="delete from ? where id=?";
        // db.execSQL(sql, new Object[]{tablename,String.valueOf(id)});
        // String.format("delete from %s where id=  %d",tablename,id)
        try {
            db.execSQL(String.format("delete from  clothesInformation where dressid=  %d", dressid));
        } catch (Exception e) {
            throw new Exception("删除失败", e);
        }
    }

    /*
     *根据衣物地址删除id
     */
    public void deleteClothesByAttribute(String attribute) throws Exception {
        //  String sql="delete from ? where id=?";
        // db.execSQL(sql, new Object[]{tablename,String.valueOf(id)});
        // String.format("delete from %s where id=  %d",tablename,id)
        try {
            db.execSQL(String.format("delete from  clothesInformation where attrubute=  %s", attribute));
        } catch (Exception e) {
            throw new Exception("删除失败", e);
        }
    }

    /*
     *显示详细信息时可根据衣服编号查询相关信息
     */
    public Clothes queryByDressid(int dressid) throws Exception {
        try {
            String sql = "select * from clothesInformation where dressid=?";
            Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(dressid)});
            cursor.moveToNext();
            Clothes clothes = new Clothes(
                    cursor.getInt(cursor.getColumnIndex("dressid")),
                    cursor.getString(cursor.getColumnIndex("style")),
                    cursor.getString(cursor.getColumnIndex("color")),
                    cursor.getString(cursor.getColumnIndex("thickness")),
                    cursor.getString(cursor.getColumnIndex("attribute"))
            );
            return clothes;
        } catch (Exception e) {
            throw new Exception("查询失败", e);
        }

    }

    /*
     *查询所有衣物id
     */
    public List<Integer> queryAllDressid() throws Exception {
        List<Integer> list = new ArrayList<>();
        try {
            String sql = "select dressid from clothesInformation";
            Cursor cursor = db.rawQuery(sql, null);

            while (cursor.moveToNext()) {
                list.add(cursor.getInt(cursor.getColumnIndex("dressid")));
            }

        } catch (Exception e) {
            throw new Exception("查询失败", e);
        }
        return list;
    }

    /*
     *查询所有衣物
     */
    public List<Clothes> queryAllClothes() throws Exception {
        List<Clothes> list = new ArrayList<>();
        try {
            String sql = "select * from clothesInformation";
            Cursor cursor = db.rawQuery(sql, null);

            while (cursor.moveToNext()) {
                Clothes clothes = new Clothes(
                        cursor.getInt(cursor.getColumnIndex("dressid")),
                        cursor.getString(cursor.getColumnIndex("style")),
                        cursor.getString(cursor.getColumnIndex("color")),
                        cursor.getString(cursor.getColumnIndex("thickness")),
                        cursor.getString(cursor.getColumnIndex("attribute"))
                );
                list.add(clothes);
            }

        } catch (Exception e) {
            throw new Exception("查询失败", e);
        }
        return list;
    }

    /*
     *查询特定样式的衣物
     */
    public List<Clothes> queryClothesByStyle(String style) throws Exception {
        List<Clothes> list = new ArrayList<>();
        try {
            String sql = "select * from clothesInformation where style=?";
            Cursor cursor = db.rawQuery(sql,  new String[]{String.valueOf(style)});

            while (cursor.moveToNext()) {
                Clothes clothes = new Clothes(
                        cursor.getInt(cursor.getColumnIndex("dressid")),
                        cursor.getString(cursor.getColumnIndex("style")),
                        cursor.getString(cursor.getColumnIndex("color")),
                        cursor.getString(cursor.getColumnIndex("thickness")),
                        cursor.getString(cursor.getColumnIndex("attribute"))
                );
                list.add(clothes);
            }

        } catch (Exception e) {
            throw new Exception("查询失败", e);
        }
        return list;
    }

    public void close() {
        db.close();
    }
}
