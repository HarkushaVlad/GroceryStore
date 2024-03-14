package com.vhark.grocerystore.model.singletons;

public class ClientDataSingleton {

    private static final ClientDataSingleton instance = new ClientDataSingleton();

    private String ClientIdCOde;

    private ClientDataSingleton() {}
    ;

    public static ClientDataSingleton getInstance() {
        return instance;
    }

    public String getClientIdCOde() {
        return ClientIdCOde;
    }

    public void setClientIdCOde(String ClientIdCOde) {
        this.ClientIdCOde = ClientIdCOde;
    }
}
