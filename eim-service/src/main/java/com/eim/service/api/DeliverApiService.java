package com.eim.service.api;

public interface DeliverApiService {
    public byte[] getBill(String billKey, String dbKey) throws Exception;
    public byte[] getBillList(String billKey, String dbKey) throws Exception;
}
