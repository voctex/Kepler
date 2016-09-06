package com.voctex.contacts.tools;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.voctex.contacts.bean.BaseContactBean;
import com.voctex.tools.VtLog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by Voctex on 2016/8/29.
 */

public class ContactFilterUtil {

    /**
     * 列表需要获得的长度，当数据小于这个长度，则以数据的长度为准
     */
    private final static int itemSize = 7;

    private final static String TYPE_CONTACT = "1";
    private final static String TYPE_NOT_CONTACT = "2";
    private final static String TYPE_KINSFOLK = "3";


    public static String getJsonData(Context mContext, String type) {
        if (type != null) {
            String resultStr = "{";
            String comma = ",";
            Gson gson = new Gson();
            List<BaseContactBean> calllogList = new ArrayList<>();

            //常联系
            if (type.contains(TYPE_CONTACT)) {
                calllogList = ContactFilterUtil.getOftenContacts(mContext);
                String calllogJson = gson.toJson(calllogList);
                VtLog.i("calllog==" + calllogJson);

                if (TextUtils.isEmpty(calllogJson) || calllogJson.equals("null")) {
                    resultStr = resultStr + "\"contact\":[]" + comma;
                } else {
                    resultStr = resultStr + "\"contact\":" + calllogJson + comma;
                }
            } else {
                resultStr = resultStr + "\"contact\":[]" + comma;
            }
            //不常联系
            if (type.contains(TYPE_NOT_CONTACT)) {
                List<BaseContactBean> contactsList = ContactFilterUtil.getNotOftenContacts(mContext, calllogList);
                String contactJson = gson.toJson(contactsList);
                VtLog.i("contact==" + contactJson);

                if (TextUtils.isEmpty(contactJson) || contactJson.equals("null")) {
                    resultStr = resultStr + "\"notContact\":[]" + comma;
                } else {
                    resultStr = resultStr + "\"notContact\":" + contactJson + comma;
                }
            } else {
                resultStr = resultStr + "\"notContact\":[]" + comma;
            }
            if (type.contains(TYPE_KINSFOLK)) {
                List<BaseContactBean> kinsfolkList = ContactFilterUtil.getKinsfolk(mContext);
                String kinsfolkJson = gson.toJson(kinsfolkList);
                VtLog.i("kinsfolk==" + kinsfolkJson);

                if (TextUtils.isEmpty(kinsfolkJson) || kinsfolkJson.equals("null")) {
                    resultStr = resultStr + "\"kinsfolk\":[]";
                } else {
                    resultStr = resultStr + "\"kinsfolk\":" + kinsfolkJson;
                }
            } else {
                resultStr = resultStr + "\"kinsfolk\":[]";
            }
            resultStr = resultStr + "}";
            return resultStr;
        }
        return null;
    }

    /**
     * 获得常联系的列表
     */
    public static List<BaseContactBean> getOftenContacts(Context mContext) {
        try {

            Cursor cursor = mContext.getContentResolver()
                    .query(CallLog.Calls.CONTENT_URI,
                            new String[]{CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME},
                            CallLog.Calls.NUMBER + " like ? and type=? ",
                            new String[]{"1%", "" + CallLog.Calls.OUTGOING_TYPE},
                            CallLog.Calls.DATE + " limit 100");
            if (cursor != null && cursor.getCount() > 0) {
                try {
                    VtLog.i("count=" + cursor.getCount());

                    //获得通讯记录的前200条
                    List<BaseContactBean> phoneList = new ArrayList<>();
                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToPosition(i);
                        String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                        String phone = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));

                        BaseContactBean infoBean = new BaseContactBean();
                        infoBean.setPhone(phone);
                        infoBean.setName(TextUtils.isEmpty(name) ? "陌生号码" : name);
                        phoneList.add(infoBean);
                    }
                    //将手机号码去重复操作
                    phoneList = removeDuplicateByPhone(phoneList);

                    //在去重复之后进行获得数据
                    List<BaseContactBean> mList = new ArrayList<>();
                    if (phoneList.size() >= itemSize) {
                        int[] a = getRandomArray(itemSize, phoneList.size());
                        mList.clear();
                        for (int i = 0; i < a.length; i++) {
                            mList.add(phoneList.get(a[i]));
                        }
                    } else {
                        mList.clear();
                        for (int i = 0; i < phoneList.size(); i++) {
                            mList.add(phoneList.get(i));
                        }
                    }

                    for (int i = 0; i < mList.size(); i++) {
                        VtLog.i("calllog.name=" + mList.get(i).getName() +
                                "--calllog.phone=" + mList.get(i).getPhone());
                    }
                    return mList;

                } catch (Exception ex) {
                    ex.printStackTrace();
                    VtLog.i("calllog.ex=" + ex.getMessage());
                } finally {
                    cursor.close();
                }
                return null;
            } else {
                VtLog.i("calllog.query==nodata");
            }
        } catch (SecurityException e) {
            e.printStackTrace();
            VtLog.i("预防没权限报错走这里，ex=" + e.getMessage());
        }
        return null;
    }

//    public static void main(String[] args) {
//        getRandomArray(6, 10);
//    }

    /**
     * 获得不常联系的列表
     */
    public static List<BaseContactBean> getNotOftenContacts(Context mContext, List<BaseContactBean> calllogList) {
        try {
            Cursor cursor = mContext.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER},
                    null,
                    null,
                    null);
            if (cursor != null && cursor.getCount() > 0) {
                try {
                    List<BaseContactBean> contactsList = new ArrayList<>();
                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToPosition(i);
                        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                        VtLog.i("contact.name==" + name + "--contact.phone==" + phone);

                        BaseContactBean baseContactBean = new BaseContactBean();
                        baseContactBean.setName(name);
                        baseContactBean.setPhone(phone);
                        contactsList.add(baseContactBean);
                    }

                    //通过姓名去除重复数据
                    contactsList = removeDuplicateByName(contactsList);

                    if (calllogList != null && calllogList.size() > 0) {
                        //过滤常用联系人
                        contactsList = getFilterOftenContacts(contactsList, calllogList);
                    }

                    List<BaseContactBean> resultList = new ArrayList<>();
                    if (contactsList.size() >= itemSize) {
                        int[] arr = getRandomArray(itemSize, contactsList.size());

                        for (int i = 0; i < arr.length; i++) {
                            resultList.add(contactsList.get(arr[i]));
                        }
                    } else {
                        resultList.addAll(contactsList);
                    }

                    for (int i = 0; i < resultList.size(); i++) {
                        VtLog.i("contact.name==" + resultList.get(i).getName() +
                                "--contact.phone==" + resultList.get(i).getPhone());
                    }
                    return resultList;

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    cursor.close();
                }
            } else {
                VtLog.i("contact.query==nodata");
            }
        } catch (Exception e) {
            e.printStackTrace();
            VtLog.i("contact.exception==" + e.getMessage());
        }
        return null;
    }

    /**
     * 获得亲戚列表
     */
    public static List<BaseContactBean> getKinsfolk(Context mContext) {
        try {
            Cursor cursor = mContext.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER},
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " like ? " +
                            " or display_name like ? or display_name like ? or display_name like ?" +
                            " or display_name like ? or display_name like ? or display_name like ?" +
                            " or display_name like ? or display_name like ? or display_name like ?" +
                            " or display_name like ? or display_name like ? or display_name like ?" +
                            " or display_name like ? or display_name like ? or display_name like ?" +
                            " or display_name like ? or display_name like ? or display_name like ?" +
                            " or " + ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " like ?" +
                            " or " + ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " like ?" +
                            " or " + ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " like ?",
                    new String[]{"%爷%", "%奶%", "%公%", "%婆%", "%爹%", "%娘%", "%父%",
                            "%母%", "%爸%", "%妈%", "%伯%", "%叔%", "%丈%", "%姨%",
                            "%姑%", "%哥%", "%姐%", "%弟%", "%妹%", "%侄%", "%孙%",
                            "%甥%"},
                    null
            );
            if (cursor != null && cursor.getCount() > 0) {
                try {
                    List<BaseContactBean> kinsfolkList = new ArrayList<>();
                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToPosition(i);
                        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                        VtLog.i("kinsfolk.name==" + name);

                        BaseContactBean baseContactBean = new BaseContactBean();
                        baseContactBean.setName(name);
                        baseContactBean.setPhone(phone);
                        kinsfolkList.add(baseContactBean);
                    }

                    List<BaseContactBean> resultList = new ArrayList<>();
                    if (kinsfolkList.size() > itemSize) {
                        int[] arr = getRandomArray(itemSize, kinsfolkList.size());

                        for (int i = 0; i < arr.length; i++) {
                            resultList.add(kinsfolkList.get(arr[i]));
                        }
                    } else {
                        resultList.addAll(kinsfolkList);
                    }

                    for (int i = 0; i < resultList.size(); i++) {
                        VtLog.i("kinsfolk.name==" + resultList.get(i).getName() +
                                "--kinsfolk.phone==" + resultList.get(i).getPhone());
                    }
                    return resultList;

                } catch (Exception e) {
                    e.printStackTrace();
                    VtLog.i("Exception.getMessage==" + e.getMessage());
                } finally {
                    cursor.close();
                }
            } else {
                VtLog.i("kinsfolk.query==nodata");
            }

        } catch (Exception e) {
            e.printStackTrace();
            VtLog.i("Exception.getMessage==" + e.getMessage());
        }
        return null;
    }


    /**
     * 获得一组不重复的随机数
     */
    private static int[] getRandomArray(int count, int range) {
        Random random = new Random();
        int a[] = new int[count];
        a[0] = random.nextInt(range);
        for (int i = 1; i < a.length; i++) {
            a[i] = random.nextInt(range);
            for (int j = 0; j < i; j++) {
                while (a[i] == a[j]) {
                    i--;
                }
            }
        }
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i] + "  ");
        }
        return a;
    }


    /**
     * 通过电话号码删除ArrayList中重复元素
     */
    public static List<BaseContactBean> removeDuplicateByPhone(List<BaseContactBean> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getPhone().equals(list.get(i).getPhone())) {
                    list.remove(j);
                }
            }
        }
        System.out.println(list);
        return list;
    }

    /**
     * 通过姓名删除ArrayList中重复元素
     */
    public static List<BaseContactBean> removeDuplicateByName(List<BaseContactBean> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getName().equals(list.get(i).getName())) {
                    list.remove(j);
                }
            }
        }
        System.out.println(list);
        return list;
    }

    /**
     * 通过姓名和电话号码删除ArrayList中重复元素
     */
    public static List<BaseContactBean> getFilterOftenContacts(
            List<BaseContactBean> contactList, List<BaseContactBean> calllogList) {
        VtLog.i("before.contactList.size==" + contactList.size());
        for (int i = 0; i < contactList.size(); i++) {
            for (int j = 0; j < calllogList.size(); j++) {
                if (contactList.get(i).getName().equals(calllogList.get(j).getName()) ||
                        contactList.get(i).getPhone().equals(calllogList.get(j).getPhone())) {
                    contactList.remove(i);
                }
            }
        }
        VtLog.i("after.contactList.size==" + contactList.size());
        return contactList;
    }

    /**
     * 通过xxx删除ArrayList中重复元素
     */
    public static List removeDuplicate3(List list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
        System.out.println(list);
        return list;
    }

    /**
     * 通过HashSet删除ArrayList中重复元素
     */
    public static void removeDuplicate2(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        System.out.println(list);
    }

    /**
     * 通过xxx删除ArrayList中重复元素，保持顺序
     */
    public static void removeDuplicateWithOrder(List list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        list.clear();
        list.addAll(newList);
        System.out.println(" remove duplicate " + list);
    }


}
