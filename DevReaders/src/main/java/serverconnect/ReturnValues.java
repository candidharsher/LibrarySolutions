/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serverconnect;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class ReturnValues {
    private int returnCode;
    private List<Object> data;

    public ReturnValues(int returnCode) {
        this.returnCode = returnCode;
        data = new ArrayList<Object>();
    }

    public void addDataObject(Object obj) {
        data.add(obj);
    }

    public void addPrimitiveData(Object obj) {
        data.add(obj);
    }

    public int getReturnCode() {
        return returnCode;
    }

    public List<Object> getData() {
        return data;
    }

    public <T> T getData(int index, Class<T> clazz) {
        return clazz.cast(data.get(index));
    }
}

