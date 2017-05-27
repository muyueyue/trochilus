package persistence;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Config;
import org.bson.Document;

import java.util.*;

/**
 * Created by pjh on 2017/2/10.
 *
 * Get MongoDB connections and related actions
 *
 * @author jiahao.pjh@gmail.com
 */
public class MongoDBJDBC {

    private static final Logger logger = LoggerFactory.getLogger(MongoDBJDBC.class);

    private static MongoDatabase mongoDatabase;

    private static MongoClient mongoClient;

    private static synchronized void setMongoClient(){
        try{
            mongoClient = new MongoClient(Config.mongoDBHost, Config.mongoDBPort);
        }catch (Exception e){
            logger.error("数据库连接出错");
        }
    }

    public static MongoDatabase getMongoDatabase(){
        if (mongoClient == null){
            setMongoClient();
        }
        mongoDatabase = mongoClient.getDatabase(Config.databaseName);
        return mongoDatabase;
    }

    public static void insert(String dbCollection, JSONObject jsonObject){
        try {
            getMongoDatabase();
            if(mongoDatabase == null){
                return;
            }
            Document document = new Document();
            for(Map.Entry<String, Object> entry : jsonObject.entrySet()){
                document.append(entry.getKey(), (String)entry.getValue());
            }
            MongoCollection<Document> collection = mongoDatabase.getCollection(dbCollection);
            collection.insertOne(document);
        }catch (Exception e){
            logger.error("数据插入出错:", e);
        }
    }

    public List<String> getStartUrls(String dbCollection, long rowId){
        if(rowId > Config.endId){
            return null;
        }
        try {
            getMongoDatabase();
            MongoCollection<Document> collection = mongoDatabase.getCollection(dbCollection);
            FindIterable<Document> findIterable = collection.find(new BasicDBObject("rowId", new BasicDBObject("$gt", rowId))).limit(Config.urlSize);
            MongoCursor<Document> mongoCursor = findIterable.iterator();
            List<String> list = new ArrayList<>();
            while(mongoCursor.hasNext()){
                String startUrl = mongoCursor.next().getString("startUrl");
                logger.info(startUrl);
                list.add(startUrl);
            }
            return list;
        }catch (Exception e){
            logger.error("数据查询出错:", e);
            return null;
        }
    }
}
