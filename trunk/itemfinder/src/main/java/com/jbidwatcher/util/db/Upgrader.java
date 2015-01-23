package com.jbidwatcher.util.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Morgan
 * Date: Jun 24, 2007
 * Time: 5:32:20 AM
 *
 * Centralize all code to do upgrading-related operations in this class.
 */
public class Upgrader {
  public static void upgrade() throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
    Database db = new Database(null);
    if(dbMake(db)) {
      db.commit();
      db.shutdown();
      db = dbMigrate();
    }
    db.commit();
    db.shutdown();
  }

  private static Database dbMigrate() throws IllegalAccessException, SQLException, ClassNotFoundException, InstantiationException {
    Table schemaInfo = new Table("schema_info");
    List<Record> info = schemaInfo.findAll();
    if(info != null) {
      Record first = info.get(0);
      HashBacked record = new HashBacked(first);
      int version = record.getInteger("version", -1);
      if(version != -1) {
        int last_version = version;
        version++;
        NumberFormat nf = NumberFormat.getIntegerInstance();
        nf.setMinimumIntegerDigits(3);
        Statement s = schemaInfo.getDB().getStatement();
        while(runFile(schemaInfo.getDB(), s, "/ddl/" + nf.format(version) + ".sql")) {
          record.setInteger("version", version);
          schemaInfo.updateMap("schema_info", "version", Integer.toString(last_version), record.getBacking());
          last_version = version;
          version++;
        }
      }
    }
    return schemaInfo.getDB();
  }

  private static boolean tableExists(Statement s, String tableName) throws SQLException {
    ResultSet rs = s.getConnection().getMetaData().getTables(null, null, tableName, null);
    boolean result = rs.next();
    rs.close();
    return result;
  }

  private static boolean dbMake(Database db) {
    try {
      /*
         Creating a statement lets us issue commands against
         the connection.
       */
      Statement mS = db.getStatement();

      boolean schema_info_exists = tableExists(mS, "schema_info");
      if(!schema_info_exists) schema_info_exists = tableExists(mS, "SCHEMA_INFO");

      if(!schema_info_exists) {
        runFile(db, mS, "/jbidwatcher.sql");
        System.out.println("jbidwatcher.created_db");
      } else {
        System.out.println("Auction information database already exists.");
      }
    } catch (SQLException se) {
      System.err.println(se);
      return false;
    }
    return true;
  }

  private static boolean runFile(Database db, Statement mS, String filename) throws SQLException {
    String sql = StringTools.cat(JConfig.getResource(filename));
    if(sql != null && sql.length() != 0) {
      String[] statements = sql.split("(?m)^$");
      for (String statement : statements) {
        db.executeCanonicalizedSQL(mS, statement);
      }

      System.out.println("Executed " + filename + ".");
      System.out.println("Created database and various tables.");
      return true;
    } else {
      return false;
    }
  }
}
