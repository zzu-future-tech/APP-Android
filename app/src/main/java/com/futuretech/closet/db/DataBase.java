package com.futuretech.closet.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.futuretech.closet.model.Clothes;
import com.futuretech.closet.model.SuitClass;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

/**
 * Create by xu on 2019/4/20
 * 删除被注释的代码后阅读体验更佳，为日后扩充和测试方法 作者并未删除被注释的代码
 * 该类尚未完善，当需要对数据库中各表进行相应操作而该类中无该方法时请联系作者
 * 数据库各表的具体属性在dateBaseHelper类的onCreate方法中查看
 * 使用完本类后无其他操作应执行dateBase.close()
 * 需在try catch中使用该类及方法
 */
public class DataBase {

    private String userid;

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

        SharedPreferences sharedPreferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        userid = sharedPreferences.getString("Email", "");
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
            String sql = "select * from clothesInformation where style=? and userid=?";
            Cursor cursor = db.rawQuery(sql,  new String[]{String.valueOf(style),userid});

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

    /**
     * 插入套装 插入之前检查是否重复
     * @param values 套装
     * @return 重复则返回false 不重复则插入并返回true
     * @throws Exception 插入失败
     */
    public boolean insertSuit(ContentValues values)throws Exception{
        try {

            String sql = "select count(*) from suits where dressid1=? and dressid2=?";
            Cursor cursor = db.rawQuery(sql, new String[]{values.get("dressid1").toString(), values.get("dressid2").toString()});
            if(cursor.moveToNext()){
                int count = cursor.getInt(0);
                if(count>0){
                    return false;
                }
            }

            db.execSQL("insert into suits (dressid1,dressid2,userid,gmt_create) values(?,?,?,datetime('now','localtime'))", new Object[]{Integer.parseInt(values.get("dressid1").toString()),
                    Integer.parseInt(values.get("dressid2").toString()),values.get("userid")});

            return true;
        } catch (Exception e) {
            throw new Exception("插入失败", e);
        }
    }

    /*
     *查询所有套装
     */
    public List<SuitClass> queryAllSuits() throws Exception {
        List<SuitClass> list = new ArrayList<>();
        try {
            String sql = "select * from suits where userid=?";
            Cursor cursor = db.rawQuery(sql,  new String[]{userid});

            while (cursor.moveToNext()) {
                SuitClass suits = new SuitClass(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getInt(cursor.getColumnIndex("dressid1")),
                        cursor.getInt(cursor.getColumnIndex("dressid2"))
                );
                list.add(suits);
            }

        } catch (Exception e) {
            throw new Exception("查询失败", e);
        }
        return list;
    }

    /*
     * 根据套装id删除
     */
    public void deleteSuitsByid(int id) throws Exception {
        try {
            db.execSQL(String.format("delete from  suits where id=  %d", id));
        } catch (Exception e) {
            throw new Exception("删除失败", e);
        }
    }

    public void close() {
        db.close();
    }
}
