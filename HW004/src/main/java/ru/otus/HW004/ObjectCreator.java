package ru.otus.HW004;


import javax.xml.transform.sax.SAXTransformerFactory;
import java.util.ArrayList;
import java.util.List;

public class ObjectCreator implements Runnable {
    private List list = new ArrayList<Object>();
    boolean needDel = false;

    public void run() {
        while(true){
            list.add(new Long(400000000));
            list.add(new Long(400000000));
            list.add(new Long(400000000));
            list.add(new Long(400000000));
            list.add(new Long(400000000));
            list.add(new Long(400000000));
            list.add(new StringBuffer("asdfafsdfswdfasdfasfasdf"));
            list.add(new StringBuffer("asdfafsdfswdfasdfasfasdf"));
            list.add(new StringBuffer("asdfafsdfswdfasdfasfasdf"));
            list.add(new StringBuffer("asdfafsdfswdfasdfasfasdf"));
            list.add(new StringBuffer("asdfafsdfswdfasdfasfasdf"));
            list.add(new StringBuffer("asdfafsdfswdfasdfasfasdf"));
            if (needDel){
                list.remove(0);
            }
            if (needDel){
                list.remove(0);
            }
            if (needDel){
                list.remove(0);
            }
            needDel = !needDel;
        }
    }
}
