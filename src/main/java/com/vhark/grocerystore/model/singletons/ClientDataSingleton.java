package com.vhark.grocerystore.model.singletons;

public class ClientDataSingleton {

  private static final ClientDataSingleton instance = new ClientDataSingleton();

  private String ClientUserId;

  private ClientDataSingleton() {}
  ;

  public static ClientDataSingleton getInstance() {
    return instance;
  }

  public String getClientUserId() {
    return ClientUserId;
  }

  public void setClientUserId(String ClientUserId) {
    this.ClientUserId = ClientUserId;
  }
}
