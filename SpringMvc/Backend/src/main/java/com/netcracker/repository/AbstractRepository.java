package com.netcracker.repository;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Locale;

public interface AbstractRepository {
    BigInteger getObjectId();
    boolean checkAttribute(BigInteger objectId, long attrId) throws SQLException;
    ResultSet getObjectsByObjectTypeId(long objectTypeId);
    ResultSet getObjectById(BigInteger objectId);
    ResultSet getObjectIdByAttrIdAndTextVal(long attrId, String textVal);
    ResultSet getParametersByObjectId(BigInteger objectId);
    ResultSet getParameter(BigInteger objectId, long attrId);
    ResultSet getLocStringsByObjectId(BigInteger objectId, long langId);
    void saveObject(String name, BigInteger objectId, BigInteger parentId, long objTypeId);
    void updateObjectName (BigInteger objectId, String newName);
    void saveTextParameter(BigInteger objectId, long attrId, String parameter);
    void saveDateParameter(BigInteger objectId, long attrId, Timestamp parameter);
    void saveEnumValue(BigInteger objectId, long attrId, long parameter);
    void savePointParameter(BigInteger objectId, long attrId, Object x, Object y);
    void removeParameter(String sql, BigInteger objectId, long attrId);
    void updateTextParameter(BigInteger objectId, long attrId, String parameter);
    void updateDateParameter(BigInteger objectId, long attrId, Timestamp parameter);
    void updateReferenceParameter(BigInteger objectId, long attrId, BigInteger parameter);
    void updateEnumParameter(BigInteger objectId, long attrId, long parameter);
    boolean isReferenceParameterExist(BigInteger objectId, long attrId);
    String getLocEnumValue(long enumId, Locale locale, String origValue);
}
