/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serverconnect;

/**
 *
 * @author User
 */
import java.util.ArrayList;
import java.util.List;

public class EndPointValues {
    private String endpoint;
    private List<Object> data;

    public EndPointValues(String endpoint) {
        this.endpoint = endpoint;
        data = new ArrayList<Object>();
    }

    public void addDataObject(Object obj) {
        data.add(obj);
    }

    public void addPrimitiveData(Object obj) {
        data.add(obj);
    }

    public String getEndpoint() {
        return endpoint;
    }

    public List<Object> getData() {
        return data;
    }

    public <T> T getData(int index, Class<T> clazz) {
        return clazz.cast(data.get(index));
    }
}
